package com.mediot.ygb.mrqs.analysis.medRecManage.rowProcessors;

import cn.hutool.core.util.StrUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.StandardCodeEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse.EntryStoreHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse.EntryStoreRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.dto.ParseDataDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanRequest;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;

import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase.StateEnum;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CVSRowProcessors implements RowProcessor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FileAnalysisDto fileAnalysisDto;

    private static final int BATCH_COUNT = 20;

    private static final Integer BATCH_INSERT_COUNT=20;

    private int total;

    private AtomicInteger dataErrorNum=new AtomicInteger();

    private List<ParseDataDto> datas= Lists.newArrayList();

    private CountDownLatch countDownLatch;

    private FetchFileRequest fetchFileRequest;


    public CVSRowProcessors(FileAnalysisDto fileAnalysisDto,int total,FetchFileRequest fetchFileRequest){
        this.fileAnalysisDto=fileAnalysisDto;
        this.total=total;
        this.fetchFileRequest=fetchFileRequest;
    }


    @Override
    public void processStarted(ParsingContext parsingContext) {
        //创建临时表
    }

    @Override
    public void rowProcessed(String[] strings, ParsingContext parsingContext) {
        try {
            if(!StrUtil.isAllEmpty(strings)){
                ParseDataDto parseDataDto=new ParseDataDto();
                parseDataDto.setBaseData(strings);
                parseDataDto.setCurrentRow(parsingContext.currentLine());
                if(parsingContext.currentLine()==1){
                    fileAnalysisDto.setSheetNum(strings.length);
                    //fileAnalysisDto.setRowNum(total-1);
                    if(fileAnalysisDto.getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.HQMS))){
                        DataCleanUtils.createHQMSStandardList(strings,fileAnalysisDto);
                    }else if(fileAnalysisDto.getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.JXKH))) {
                        DataCleanUtils.createJXKHStandardList(strings,fileAnalysisDto);
                    }else {
                        DataCleanUtils.createWTStandardList(strings,fileAnalysisDto);
                    }
                }else {
                    parseDataDto.setIsSheet(false);
                    parseDataDto.setBaseData(strings);
                    parseDataDto.setCurrentRow(parsingContext.currentLine());
                    if(strings.length==fileAnalysisDto.getSheetNum()){
                        datas.add(parseDataDto);
                    }
                    if(datas.size()%BATCH_COUNT==0){
                        //synchronized (this){
                        doAnalysis(datas,strings,parsingContext);
                        if(parsingContext.currentLine()==total){
                            doAnalysisedHandle();
                        }
                        //}
                    }else {
                        //parsingContext.currentChar();
                        //parsingContext.extractedFieldIndexes();
                        if(parsingContext.currentLine()==total){
                            try {
                                doAnalysis(datas,strings,parsingContext);
                            }catch (Throwable e){
                                e.printStackTrace();
                            }
                            // 所有解析解析完毕，执行入库还是返回错误信息操作
                            doAnalysisedHandle();
                        }
                    }
                }
            }
        }catch (Throwable e){
            logger.info(e.getMessage());
        }
    }

    public void doAnalysisedHandle(){
        if (dataErrorNum.get()>0) {
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("MD5",fileAnalysisDto.getMd5());
            FileUploadEntity f=fileAnalysisDto.getFileUploadMapper().selectFileUpload(queryMap);
            f.setState(StateEnum.getValue(StateEnum.ClEANCOMPELETED));
            fileAnalysisDto.getFileUploadMapper().updateById(f);
            //
            ProgressVo progressVo=new ProgressVo();
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE_FAIL));
            progressVo.setProgress(0.00);
            progressVo.setErrorMsg("存在错误信息，该批次没有入库！");
            progressVo.setAnalysisStatus(true);
            fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE_FAIL),progressVo);
            //
            Set<String> rkeys = fileAnalysisDto.getRedisTemplate().keys("right_"+f.getFileId()+"*");
            fileAnalysisDto.getRedisTemplate().delete(rkeys);
            logger.info("存在错误信息，该批次没有入库！");
        }else {
            try {
                //入库数据
                entryStore(fileAnalysisDto);
            }catch (Exception e){
                //入库失败
                ProgressVo progressVo=new ProgressVo();
                progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE_FAIL));
                progressVo.setProgress(0.00);
                progressVo.setAnalysisStatus(true);
                fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE_FAIL),progressVo);
                Map<String,Object> m= Maps.newHashMap();
                m.put("fileId",fileAnalysisDto.getFileId());
                FileUploadEntity f=fileAnalysisDto.getFileUploadMapper().selectFileUpload(m);
                f.setState(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE_FAIL)));
                fileAnalysisDto.getFileUploadMapper().updateById(f);
                logger.info("入库失败！原因是："+e.getMessage());
            }
        }
    }

    public void entryStore(FileAnalysisDto fileAnalysisDto){
        Map<String,Object> queryMap= Maps.newHashMap();
        queryMap.put("MD5",fileAnalysisDto.getMd5());
        FileUploadEntity f=fileAnalysisDto.getFileUploadMapper().selectFileUpload(queryMap);
        f.setState(StateEnum.getValue(StateEnum.ClEANCOMPELETED));
        fileAnalysisDto.getFileUploadMapper().updateById(f);
        //logger.info("已进入入库流程！");
        //获取redis缓存
        String key="right_"+fileAnalysisDto.getFileId()+"_"+fetchFileRequest.getE().getName();
        Long totalNum=fileAnalysisDto.getRedisTemplate().opsForList().size(key);
        batchInsertResult(key,totalNum,fileAnalysisDto);
    }

    public void batchInsertResult(String key,Long totalNum,FileAnalysisDto fileAnalysisDto){
        //logger.info("已进入批量插入方法！");
        int cycleNum=(int)Math.ceil((float)totalNum / BATCH_INSERT_COUNT);
        ExecutorService service = Executors.newFixedThreadPool(25);
        for(int i=0;i<cycleNum;i++){
            if(i==cycleNum-1){
                List resultList=fileAnalysisDto.getRedisTemplate().opsForList().range(key,i*BATCH_INSERT_COUNT,totalNum);
                FutureTask<String> futureTask=new FutureTask(new EntryStoreHandler(new EntryStoreRequest(fileAnalysisDto,resultList,totalNum-BATCH_INSERT_COUNT*(cycleNum-1))));
                service.execute(futureTask);
            }else {
                List resultList=fileAnalysisDto.getRedisTemplate().opsForList().range(key,i*BATCH_INSERT_COUNT,i*BATCH_INSERT_COUNT+BATCH_INSERT_COUNT-1);
                FutureTask<String> futureTask=new FutureTask(new EntryStoreHandler(new EntryStoreRequest(fileAnalysisDto,resultList,BATCH_INSERT_COUNT.longValue())));
                service.execute(futureTask);
            }
        }
    }



    public void doAnalysis(List<ParseDataDto> datas,Object o,ParsingContext parsingContext) throws Exception{
        String dataArr[]=(String[])o;
        countDownLatch=new CountDownLatch(datas.size());
        datas.stream().forEach(e->{
            DataCleanRequest dataAnalysisRequest;
            if(e.getIsSheet()){
                dataAnalysisRequest=new DataCleanRequest(e,fileAnalysisDto,e.getCurrentRow(),true,dataErrorNum,fetchFileRequest);
            }else {
                dataAnalysisRequest=new DataCleanRequest(e,fileAnalysisDto,e.getCurrentRow(),false,dataErrorNum,fetchFileRequest);
            }
            DataCleanHandler dataCleanHandler =new DataCleanHandler(dataAnalysisRequest,countDownLatch);
            FutureTask<String> task=new FutureTask<String>(dataCleanHandler);
            Thread thread=new Thread(task);
            thread.start();
        });
        countDownLatch.await();
        datas.clear();
    }

    @Override
    public void processEnded(ParsingContext parsingContext) {

    }
}

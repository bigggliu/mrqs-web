package com.mediot.ygb.mrqs.analysis.medRecManage.excelDataListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.StandardCodeEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse.EntryStoreHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse.EntryStoreRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.dto.ParseDataDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanRequest;

import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase.StateEnum;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;



@Data
public class ExcelListener extends AnalysisEventListener implements Serializable {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private File file;

    private FileAnalysisDto fileAnalysisDto;

    private static final int BATCH_COUNT = 20;

    private List<ParseDataDto> datas= Lists.newArrayList();

    private CountDownLatch countDownLatch=new CountDownLatch(BATCH_COUNT);

    private AtomicInteger dataErrorNum=new AtomicInteger();

    private static final Integer BATCH_INSERT_COUNT=20;

    private FetchFileRequest fetchFileRequest;

    //private int num=0;

    public ExcelListener(FetchFileRequest fetchFileRequest,FileAnalysisDto fileAnalysisDto){
        this.fetchFileRequest=fetchFileRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        try {
            ParseDataDto parseDataDto=new ParseDataDto();
            LinkedHashMap d=(LinkedHashMap)o;
            List stringList=Lists.newArrayList();
            d.forEach((k,v)->{
                stringList.add(v);
            });
            String[] strings=(String[])stringList.stream().toArray(String[]::new);
            parseDataDto.setBaseData(strings);
            parseDataDto.setCurrentRow(analysisContext.readRowHolder().getRowIndex().longValue());
            if(analysisContext.readRowHolder().getRowIndex()==0){
                fileAnalysisDto.setSheetNum(strings.length);
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
                parseDataDto.setCurrentRow(analysisContext.readRowHolder().getRowIndex().longValue());
                if(strings.length==fileAnalysisDto.getSheetNum()){
                    datas.add(parseDataDto);
                }
                if(datas.size()%BATCH_COUNT==0){
                    //synchronized (this){
                    doAnalysis(datas,strings,analysisContext);
                    if(analysisContext.readRowHolder().getRowIndex()==analysisContext.readSheetHolder().getApproximateTotalRowNumber()-1) {
                        doAnalysisedHandle();
                    }
                    //}
                }else {
                    //parsingContext.currentChar();
                    //parsingContext.extractedFieldIndexes();
                    if(analysisContext.readRowHolder().getRowIndex()==analysisContext.readSheetHolder().getApproximateTotalRowNumber()-1){
                        try {
                            doAnalysis(datas,strings,analysisContext);
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                        // 所有解析解析完毕，执行入库还是返回错误信息操作
                        doAnalysisedHandle();
                    }
                }
            }
        }catch (Throwable e){
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }

    public void doAnalysis(List<ParseDataDto> datas,Object o,AnalysisContext analysisContext) throws Exception{
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
                logger.info("入库失败！");
            }
        }
    }

    public void entryStore(FileAnalysisDto fileAnalysisDto){
        Map<String,Object> queryMap= Maps.newHashMap();
        queryMap.put("MD5",fileAnalysisDto.getMd5());
        FileUploadEntity f=fileAnalysisDto.getFileUploadMapper().selectFileUpload(queryMap);
        f.setState(StateEnum.getValue(StateEnum.ClEANCOMPELETED));
        fileAnalysisDto.getFileUploadMapper().updateById(f);
        String key="right_"+fileAnalysisDto.getFileId()+"_"+fetchFileRequest.getE().getName();
        Long totalNum=fileAnalysisDto.getRedisTemplate().opsForList().size(key);
        batchInsertResult(key,totalNum,fileAnalysisDto);
    }

    public void batchInsertResult(String key,Long totalNum,FileAnalysisDto fileAnalysisDto){
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
//        int cycleNum=(int)Math.ceil((float)totalNum / BATCH_INSERT_COUNT);
//        for(int i=0;i<cycleNum;i++){
//            if(i==cycleNum-1){
//                List resultList=fileAnalysisDto.getRedisTemplate().opsForList().range(key,i*BATCH_INSERT_COUNT,totalNum);
//                parseDataAndInsertData(fileAnalysisDto,resultList);
//
//            }else {
//                List resultList=fileAnalysisDto.getRedisTemplate().opsForList().range(key,i*BATCH_INSERT_COUNT,i*BATCH_INSERT_COUNT+BATCH_INSERT_COUNT);
//                parseDataAndInsertData(fileAnalysisDto,resultList);
//            }
//        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

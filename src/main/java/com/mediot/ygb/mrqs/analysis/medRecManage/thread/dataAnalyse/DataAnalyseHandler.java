package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.medCaseThread.UploadThreadOperator;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorEntity;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class DataAnalyseHandler implements Callable<String> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DataAnalyseRequset dataAnalyseRequset;

    //数据总数
    private Integer totalNumForCurrentBatchid;
    private Integer threadNum = 100;
    private Integer batchNum = 1;
    private Integer maxThreadNum = 100;
    // 默认一次处理100条左右
    private Integer onceNum = 500;
    private CountDownLatch countDownLatch;
    private AtomicInteger at=new AtomicInteger();


    public DataAnalyseHandler(DataAnalyseRequset dataAnalyseRequset){
        this.dataAnalyseRequset=dataAnalyseRequset;
        this.totalNumForCurrentBatchid=dataAnalyseRequset.getFileAnalysisDto().getTotalNumForCurrentBatchId();
    }

    @Override
    public String call() throws Exception {
        long t1 = System.currentTimeMillis();
        UploadThreadOperator uploadThreadOperator=new UploadThreadOperator();
        ExecutorService es=null;
        ProgressVo progressVo=new ProgressVo();
        progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE));
        progressVo.setProgress(0.00);
        progressVo.setAnalysisStatus(true);
        dataAnalyseRequset.getFileAnalysisDto().getRedisTemplate().opsForValue().set(dataAnalyseRequset.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE),progressVo);
        startDataAnalyseByBatchId(dataAnalyseRequset,es,uploadThreadOperator);
        logger.info("--B--总耗时："+(System.currentTimeMillis()-t1));
        return "完成开启解析任务";
    }

    private void startDataAnalyseByBatchId(DataAnalyseRequset dataAnalyseRequset,ExecutorService es,UploadThreadOperator uploadThreadOperator) {
        try{
            Map<String,Object> m= Maps.newHashMap();
            m.put("fileId",dataAnalyseRequset.getFileAnalysisDto().getFileId());
            FileUploadEntity f=dataAnalyseRequset.getFileAnalysisDto().getFileUploadMapper().selectFileUpload(m);
            f.setState(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE)));
            dataAnalyseRequset.getFileAnalysisDto().getFileUploadMapper().updateById(f);
            if(totalNumForCurrentBatchid<100){
                batchNum = 1;
                uploadThreadOperator.createThreadPool(maxThreadNum,Thread.currentThread().getName(),"fix");
            }else if(100 < totalNumForCurrentBatchid && totalNumForCurrentBatchid <= 10000){
                if(totalNumForCurrentBatchid %onceNum!=0){
                    batchNum=totalNumForCurrentBatchid /onceNum+1;
                }else {
                    batchNum = totalNumForCurrentBatchid/onceNum;
                }
                uploadThreadOperator.createThreadPool(maxThreadNum,Thread.currentThread().getName(),"fix");
            }else{
                // 计划每批次500条左右
                //onceNum = 500;
                // 批次数计算
                if(totalNumForCurrentBatchid %onceNum!=0){
                    batchNum=totalNumForCurrentBatchid/onceNum+1;
                }else {
                    batchNum = totalNumForCurrentBatchid/onceNum;
                }
                if (batchNum > maxThreadNum) {
                    // 设置固定线程数100
                    threadNum = maxThreadNum;
                } else {
                    // 线程数等于批次数
                    threadNum = batchNum;
                }
                uploadThreadOperator.createThreadPool(50,Thread.currentThread().getName(),"fix");
            }
            countDownLatch=new CountDownLatch(batchNum);
            logger.info("--B--预计线程数为：{"+threadNum+"},预计批次数：{"+batchNum+"},总待处理数量为：{"+totalNumForCurrentBatchid+"}");
            for (int i = 0; i < batchNum; i++) {
                FutureTask futureTask=new FutureTask(new CaseOfBatchHandler(new CaseOfBatchRequest(batchNum,onceNum,i,countDownLatch,dataAnalyseRequset,at)));
                uploadThreadOperator.getUploadThreadPool().getExecutorService().execute(futureTask);
            }
            logger.info("当前处理数为："+countDownLatch.getCount());
            countDownLatch.await();
            logger.info("已完成命中扫描！");
            uploadThreadOperator.getUploadThreadPool().getExecutorService().shutdown();
            uploadThreadOperator.releasePool();
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("BATCH_ID",dataAnalyseRequset.getFileAnalysisDto().getBatchId());
            TErrorEntity tErrorEntity=dataAnalyseRequset.getFileAnalysisDto().getTErrorMapper().selectOne(queryWrapper);
            tErrorEntity.setBatchId(dataAnalyseRequset.getFileAnalysisDto().getBatchId());
            tErrorEntity.setTotal(String.valueOf(totalNumForCurrentBatchid));
            int analysedNum=dataAnalyseRequset.getFileAnalysisDto().getTErrorDetailMapper().selectCount(queryWrapper);
            tErrorEntity.setHitDataVolume(String.valueOf(analysedNum));
            tErrorEntity.setNumberOfTestIndicators(String.valueOf(dataAnalyseRequset.getFileAnalysisDto().getIndicators()));
            tErrorEntity.setTotalNumberOfFields(dataAnalyseRequset.getFileAnalysisDto().getTotalStandards());
            Map<String,Object> qm= Maps.newHashMap();
            qm.put("batchId",dataAnalyseRequset.getFileAnalysisDto().getBatchId());
            tErrorEntity.setErrorFields(dataAnalyseRequset.getFileAnalysisDto().getTCheckColMapper().getNumOfErrorFileds(qm));
            float fn=Float.parseFloat(tErrorEntity.getErrorFields())/Float.parseFloat(dataAnalyseRequset.getFileAnalysisDto().getTotalStandards());
            Double d=new BigDecimal(fn).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            tErrorEntity.setProportionOfError(String.valueOf(d));
            dataAnalyseRequset.getFileAnalysisDto().getTErrorMapper().updateById(tErrorEntity);
            //修改文件状态
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("fileId",dataAnalyseRequset.getFileAnalysisDto().getFileId());
            FileUploadEntity fe=dataAnalyseRequset.getFileAnalysisDto().getFileUploadMapper().selectFileUpload(queryMap);
            fe.setState(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE_SUCCESS)));
            //加入进度缓存
            ProgressVo pv=new ProgressVo();
            pv.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE_SUCCESS));
            pv.setProgress(1.00);
            pv.setAnalysisStatus(true);
            dataAnalyseRequset.getFileAnalysisDto().getRedisTemplate().opsForValue().set(dataAnalyseRequset.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE_SUCCESS),pv);
            queryMap.clear();
            queryMap.put("batchId",fe.getBatchId());
            String ft=dataAnalyseRequset.getFileAnalysisDto().getTFirstpageTestingMapper().selectFirstRowByParam(queryMap);
            String lt=dataAnalyseRequset.getFileAnalysisDto().getTFirstpageTestingMapper().selectLastRowByParam(queryMap);
            fe.setStartTime(ft);
            fe.setEndTime(lt);
            dataAnalyseRequset.getFileAnalysisDto().getFileUploadMapper().updateById(fe);
        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("fileId",dataAnalyseRequset.getFileAnalysisDto().getFileId());
            FileUploadEntity fe=dataAnalyseRequset.getFileAnalysisDto().getFileUploadMapper().selectFileUpload(queryMap);
            fe.setState(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE_FAIL)));
            dataAnalyseRequset.getFileAnalysisDto().getFileUploadMapper().updateById(fe);
        }
    }
}

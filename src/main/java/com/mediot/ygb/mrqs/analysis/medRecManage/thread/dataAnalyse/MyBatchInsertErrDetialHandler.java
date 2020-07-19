package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;

import com.mediot.ygb.mrqs.index.errorInfoManage.entity.MyErrorDetaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class MyBatchInsertErrDetialHandler implements Callable<String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private DataAnalyseRequset dataAnalyseRequset;
    private List<MyErrorDetaEntity> myErrorDetaEntityList;
    private CountDownLatch myCountDownLatch;

    public MyBatchInsertErrDetialHandler(DataAnalyseRequset dataAnalyseRequset,List<MyErrorDetaEntity> myErrorDetaEntityList,CountDownLatch myCountDownLatch){
        this.dataAnalyseRequset =dataAnalyseRequset;
        this.myErrorDetaEntityList = myErrorDetaEntityList;
        this.myCountDownLatch = myCountDownLatch;
    }

    @Override
    public String call() throws Exception {
        try {
            int num = 0;
            if (myErrorDetaEntityList.size() > 0) {
                num = dataAnalyseRequset.getFileAnalysisDto().getMyErrorDetaMapper().batchInsertTErrorDetails(myErrorDetaEntityList);
            }
            logger.info("入库错误详细数目为：" + num);
        }catch (Exception e){
            logger.info("入库错误详细线程出错，原因是："+e.getMessage());
        }finally {
            myCountDownLatch.countDown();
            logger.info("入库错误详细任务数减1,其值为："+myCountDownLatch.getCount());
        }
        return null;
    }
}
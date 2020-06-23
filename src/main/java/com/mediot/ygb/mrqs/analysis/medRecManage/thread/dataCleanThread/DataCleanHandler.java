package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread;

import com.mediot.ygb.mrqs.analysis.medRecManage.chain.FileAnalysisHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.chain.abstractHandler.ImportHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.dto.ThreadStateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class DataCleanHandler extends Thread implements Callable<String>{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DataCleanRequest dataAnalysisRequest;

    private CountDownLatch countDownLatch;


    public DataCleanHandler(DataCleanRequest dataAnalysisRequest, CountDownLatch countDownLatch){
        this.dataAnalysisRequest=dataAnalysisRequest;
        this.countDownLatch=countDownLatch;
    }

    @Override
    public String call() {
        //logger.info("线程"+ Thread.currentThread().getName()+"_属于"+dataAnalysisRequest.getFileAnalysisDto().getFileName()+"运行");
        StringBuffer sb=new StringBuffer();
        sb.append(dataAnalysisRequest.getFileAnalysisDto().getFileId()).append(":").append(Thread.currentThread().getName());
        if(Thread.currentThread().isInterrupted()){
            dataAnalysisRequest.getFileAnalysisDto().getTheadMaps().remove(sb.toString());
            System.out.println("检测到中断状态");
            return Thread.currentThread().getName()+"解析中断！";
        }else {
            ThreadStateDto threadStateDto=new ThreadStateDto();
            threadStateDto.setIsPause(false);
            threadStateDto.setThread(Thread.currentThread());
            dataAnalysisRequest.getFileAnalysisDto().getTheadMaps().put(sb.toString(),threadStateDto);
            //职责链管理四步解析
            ImportHandler hStep1,hStep2,hStep3;
            hStep1=new FileAnalysisHandler();
            //hStep2=new DataCleaningHandler();
            //hStep3=new DataDetectionHandler();
            //hStep1.setNextHandler(hStep2);
            //hStep2.setNextHandler(hStep3);
            hStep1.handelReq(dataAnalysisRequest);
            hStep1.setNextHandler(null);
            dataAnalysisRequest.getFileAnalysisDto().getTheadMaps().remove(sb.toString());
            countDownLatch.countDown();
            return Thread.currentThread().getName()+"解析成功！";
        }
    }
}

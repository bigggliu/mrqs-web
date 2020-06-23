package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;


import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class CaseOfBatchRequest {

    private Integer batchNum;

    private DataAnalyseRequset dataAnalyseRequset;

    private Integer onceNum;

    private Integer currentNum;

    private CountDownLatch countDownLatch;

    private AtomicInteger at;


    public CaseOfBatchRequest(Integer batchNum,Integer onceNum,Integer currentNum,CountDownLatch countDownLatch,DataAnalyseRequset dataAnalyseRequset,AtomicInteger at) {
        this.batchNum=batchNum;
        this.dataAnalyseRequset=dataAnalyseRequset;
        this.onceNum=onceNum;
        this.countDownLatch=countDownLatch;
        this.currentNum=currentNum;
        this.at=at;
    }
}

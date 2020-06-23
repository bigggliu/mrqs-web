package com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse;

import com.mediot.ygb.mrqs.analysis.medRecManage.dbfListener.impl.DbfListener;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl.ParseCSVStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl.ParseDBFStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl.ParseExcelStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.strategyContext.StrategyContext;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;

import java.util.concurrent.Callable;

public class FetchFileHandle implements Callable<String> {

    private  FetchFileRequest fetchFileRequest;

    public FetchFileHandle(FetchFileRequest fetchFileRequest) {
        this.fetchFileRequest=fetchFileRequest;
    }

    @Override
    public String call() throws Exception {
        System.out.println("前置分析任务派发开启，线程为："+Thread.currentThread().getName());
        String type = fetchFileRequest.getE().getName().substring(fetchFileRequest.getE().getName().lastIndexOf(".")+1);
        if(type.toLowerCase().equals("xlsx")||type.toLowerCase().equals("xls")){
            new StrategyContext(new ParseExcelStrategy(fetchFileRequest,fetchFileRequest.getFileAnalysisDto())).excuteData();
        }
        else if(type.toLowerCase().equals("csv")){
            new StrategyContext(new ParseCSVStrategy(fetchFileRequest,fetchFileRequest.getFileAnalysisDto())).excuteData();
        }
        else if(type.toLowerCase().equals("dbf")){
            ParseDBFStrategy parseDBFStrategy=new ParseDBFStrategy(fetchFileRequest,fetchFileRequest.getFileAnalysisDto());
            parseDBFStrategy.regDBFListener(new DbfListener(fetchFileRequest.getFileAnalysisDto(),fetchFileRequest));
            new StrategyContext(parseDBFStrategy).excuteData();
        }
        return null;
    }
}

package com.mediot.ygb.mrqs.analysis.medRecManage.thread.medCaseThread;

import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse.DataAnalyseHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse.DataAnalyseRequset;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;

import java.util.concurrent.FutureTask;


public class UploadThreadOperator {

    private UploadThreadPool uploadThreadPool;

    public UploadThreadPool getUploadThreadPool(){
        return this.uploadThreadPool;
    }

    public UploadThreadPool createThreadPool(int size,String facName,String poolType){
        this.uploadThreadPool=UploadThreadPool.getInstance(size,facName,poolType);
        return uploadThreadPool;
    }

    public void releasePool(){
        UploadThreadPool.releaseUploadThreadPool();
    }

    public void startDataClean(FileAnalysisDto fileAnalysisDto){
        ProgressVo progressVo=new ProgressVo();
        progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING));
        progressVo.setProgress(0.00);
        progressVo.setAnalysisStatus(true);
        fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo);
        UploadTaskHandler handler=new UploadTaskHandler(new MedRecordRequest(fileAnalysisDto));
        FutureTask futureTask=new FutureTask(handler);
        uploadThreadPool.getExecutorService().execute(futureTask);
    }

    public void startAnalysis(FileAnalysisDto fileAnalysisDto){
        ProgressVo progressVo=new ProgressVo();
        progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING));
        progressVo.setProgress(0.00);
        progressVo.setAnalysisStatus(true);
        fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getBatchId()+"$$$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo);
        DataAnalyseHandler handler=new DataAnalyseHandler(new DataAnalyseRequset(fileAnalysisDto));
        FutureTask futureTask=new FutureTask(handler);
        uploadThreadPool.getExecutorService().execute(futureTask);
    }

}

package com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread;

import com.mediot.ygb.mrqs.analysis.medRecManage.dbfListener.impl.CheckheadLineListner;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl.*;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.strategyContext.CheckHeadLineStratetyContext;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import lombok.Data;

import java.util.concurrent.Callable;


@Data
public class CheckHeadLineHandler implements Callable<String> {

    private CheckHeadLineRequest checkHeadLineRequest;

    public CheckHeadLineHandler(CheckHeadLineRequest checkHeadLineRequest){
        this.checkHeadLineRequest=checkHeadLineRequest;
    }

    @Override
    public String call() throws Exception {
        System.out.println("表头分析线程开启，线程为："+Thread.currentThread().getName());
        String type = checkHeadLineRequest.getFile().getName().substring(checkHeadLineRequest.getFile().getName().lastIndexOf(".")+1);
        if(type.toLowerCase().equals("xlsx")||type.toLowerCase().equals("xls")){
            new CheckHeadLineStratetyContext(new CheckHeadLineForExcelStrategy(checkHeadLineRequest,checkHeadLineRequest.getFileAnalysisDto())).startCheckHDLine();
        }
        else if(type.toLowerCase().equals("csv")){
            new CheckHeadLineStratetyContext(new CheckHeadLineForCSVStrategy(checkHeadLineRequest,checkHeadLineRequest.getFileAnalysisDto())).startCheckHDLine();
        }
        else if(type.toLowerCase().equals("dbf")){
            CheckHeadlineForDbfStrategy cHFD=new CheckHeadlineForDbfStrategy(checkHeadLineRequest,checkHeadLineRequest.getFileAnalysisDto());
            cHFD.regDBFListener(new CheckheadLineListner(checkHeadLineRequest.getFileAnalysisDto(),checkHeadLineRequest));
            new CheckHeadLineStratetyContext(cHFD).startCheckHDLine();
        }else {
            ProgressVo progressVo=new ProgressVo();
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL));
            progressVo.setProgress(0.00);
            progressVo.setAnalysisStatus(false);
            progressVo.setErrorMsg("该文件格式不正确！");
            checkHeadLineRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().set(checkHeadLineRequest.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL),progressVo);
        }
        return null;
    }
}

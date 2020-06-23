package com.mediot.ygb.mrqs.analysis.medRecManage.dbfListener.impl;

import com.linuxense.javadbf.DBFReader;
import com.mediot.ygb.mrqs.analysis.medRecManage.dbfListener.DBFListener;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;
import lombok.Data;

@Data
public class CheckheadLineListner implements DBFListener {

    private FileAnalysisDto fileAnalysisDto;

    private CheckHeadLineRequest checkHeadLineRequest;

    private static Object lock=new Object();


    public CheckheadLineListner(FileAnalysisDto fileAnalysisDto, CheckHeadLineRequest checkHeadLineRequest) {
        this.checkHeadLineRequest=checkHeadLineRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void rowProcessed(Object[] rowObjects, DBFReader reader, Integer currentNum, Boolean isTitle) {
        String[] strings=new String[rowObjects.length];
        if(!DataCleanUtils.checkHeadLine(strings,fileAnalysisDto)){
            synchronized (lock){
                fileAnalysisDto.setErrorHeadLine(fileAnalysisDto.getErrorHeadLine()+1);
            }
        };
    }
}

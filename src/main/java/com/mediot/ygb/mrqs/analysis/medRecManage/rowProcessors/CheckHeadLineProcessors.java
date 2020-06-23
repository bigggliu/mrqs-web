package com.mediot.ygb.mrqs.analysis.medRecManage.rowProcessors;

import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.RowProcessor;
import lombok.Data;

@Data
public class CheckHeadLineProcessors implements RowProcessor {

    private FileAnalysisDto fileAnalysisDto;

    private CheckHeadLineRequest checkHeadLineRequest;

    private static Object lock=new Object();

    public CheckHeadLineProcessors(FileAnalysisDto fileAnalysisDto, CheckHeadLineRequest checkHeadLineRequest) {
        this.checkHeadLineRequest=checkHeadLineRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void processStarted(ParsingContext parsingContext) {

    }

    @Override
    public void rowProcessed(String[] strings, ParsingContext parsingContext) {
        if(parsingContext.currentLine()==1){
            if(!DataCleanUtils.checkHeadLine(strings,fileAnalysisDto)){
                synchronized (lock){
                    fileAnalysisDto.setErrorHeadLine(fileAnalysisDto.getErrorHeadLine()+1);
                }
            };
        }else {
            synchronized (lock){
                fileAnalysisDto.setRowNum(fileAnalysisDto.getRowNum()+1);
            }
        }
    }

    @Override
    public void processEnded(ParsingContext parsingContext) {
//        synchronized (lock){
//            fileAnalysisDto.setRowNum(fileAnalysisDto.getRowNum()+(Long.valueOf(parsingContext.currentLine()).intValue()-1));
//        }
    }
}

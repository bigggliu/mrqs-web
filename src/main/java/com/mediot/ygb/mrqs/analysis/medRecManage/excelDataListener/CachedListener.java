package com.mediot.ygb.mrqs.analysis.medRecManage.excelDataListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;


@Data
public class CachedListener extends AnalysisEventListener implements Serializable {

    private FileAnalysisDto fileAnalysisDto;

    private CheckHeadLineRequest checkHeadLineRequest;

    private static Object lock=new Object();

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CachedListener(FileAnalysisDto fileAnalysisDto,CheckHeadLineRequest checkHeadLineRequest) {
        this.fileAnalysisDto=fileAnalysisDto;
        this.checkHeadLineRequest=checkHeadLineRequest;
    }

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        try {
            if(analysisContext.readRowHolder().getRowIndex()==0){
                LinkedHashMap d=(LinkedHashMap)o;
                List stringList= Lists.newArrayList();
                d.forEach((k,v)->{
                    stringList.add(v);
                });
                String[] strings=(String[])stringList.stream().toArray(String[]::new);
                if(!DataCleanUtils.checkHeadLine(strings,fileAnalysisDto)){
                    synchronized (lock){
                        fileAnalysisDto.setErrorHeadLine(fileAnalysisDto.getErrorHeadLine()+1);
                    }
                };
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //lock.unlock();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        synchronized (this){
            fileAnalysisDto.setRowNum(fileAnalysisDto.getRowNum()+(analysisContext.readSheetHolder().getApproximateTotalRowNumber()-1));
        }
    }
}

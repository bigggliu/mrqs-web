package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import com.mediot.ygb.mrqs.analysis.medRecManage.dbfListener.DBFListener;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.CheckHeadLineStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

import java.io.FileInputStream;
import java.nio.charset.Charset;

@Data
public class CheckHeadlineForDbfStrategy implements CheckHeadLineStrategy {

    private CheckHeadLineRequest checkHeadLineRequest;

    private FileAnalysisDto fileAnalysisDto;

    private DBFListener dbfListener;

    private static Object o=new Object();

    public CheckHeadlineForDbfStrategy(CheckHeadLineRequest checkHeadLineRequest, FileAnalysisDto fileAnalysisDto) {
        this.checkHeadLineRequest=checkHeadLineRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    public void regDBFListener(DBFListener dbfListener){
        this.dbfListener=dbfListener;
    }

    @Override
    public void checkHeadline() {
        DBFReader reader = null;
        try {
            Object[] rowObjects;
            reader = new DBFReader(new FileInputStream(checkHeadLineRequest.getFile().getAbsolutePath()), Charset.forName("GBK"));
            rowObjects=new Object[reader.getFieldCount()];
            for (int i = 0; i < reader.getFieldCount(); i++) {
                DBFField field = reader.getField(i);
                rowObjects[i]=field.getName();
            }
            synchronized (o){
                fileAnalysisDto.setRowNum(fileAnalysisDto.getRowNum()+(reader.getRecordCount()));
            }
            if(dbfListener!=null){
                this.dbfListener.rowProcessed(rowObjects,reader,0,true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBFUtils.close(reader);
            checkHeadLineRequest.getCountDownLatch().countDown();
        }
    }
}

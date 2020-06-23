package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import com.mediot.ygb.mrqs.analysis.medRecManage.dbfListener.DBFListener;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.ParseFileStrategy;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;


@Data
public class ParseDBFStrategy implements ParseFileStrategy {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FetchFileRequest fetchFileRequest;

    private FileAnalysisDto fileAnalysisDto;

    private DBFListener dbfListener;


    public void regDBFListener(DBFListener dbfListener){
        this.dbfListener=dbfListener;
    }

    public ParseDBFStrategy(FetchFileRequest fetchFileRequest, FileAnalysisDto fileAnalysisDto){
        this.fetchFileRequest=fetchFileRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void parseFile2Date() {
        DBFReader reader = null;
        try {
            Object[] rowObjects;
            reader = new DBFReader(new FileInputStream(fetchFileRequest.getE().getAbsolutePath()), Charset.forName("GBK"));
            rowObjects=new Object[reader.getFieldCount()];
            for (int i = 0; i < reader.getFieldCount(); i++) {
                DBFField field = reader.getField(i);
                rowObjects[i]=field.getName();
            }
            if(dbfListener!=null){
                this.dbfListener.rowProcessed(rowObjects,reader,0,true);
            }
            Integer currentRow=1;
            while ((rowObjects = reader.nextRecord()) != null) {
                if(dbfListener!=null){
                    this.dbfListener.rowProcessed(rowObjects,reader,currentRow,false);
                    currentRow++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBFUtils.close(reader);
        }
    }
}

package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread;

import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class DataCleanRequest<T> {

    private T excelData;

    private FileAnalysisDto fileAnalysisDto;

    private Boolean isSheet;

    private String cleanType;

    private Long currentRow;

    private AtomicInteger dataErrorNum;

    private FetchFileRequest fetchFileRequest;

    public DataCleanRequest(T excelData, FileAnalysisDto fileAnalysisDto, Long currentRow, Boolean isSheet, AtomicInteger dataErrorNum,FetchFileRequest fetchFileRequest){
        this.excelData=excelData;
        this.fileAnalysisDto=fileAnalysisDto;
        this.isSheet=isSheet;
        this.currentRow=currentRow;
        this.dataErrorNum=dataErrorNum;
        this.fetchFileRequest=fetchFileRequest;
    }

}

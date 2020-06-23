package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;

import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

import java.util.List;

@Data
public class EntryStoreRequest {

    private FileAnalysisDto fileAnalysisDto;

    private List resultList;

    private Long totalNum;

    public EntryStoreRequest(FileAnalysisDto fileAnalysisDto, List resultList,Long totalNum){
        this.fileAnalysisDto=fileAnalysisDto;
        this.resultList=resultList;
        this.totalNum=totalNum;
    }

}

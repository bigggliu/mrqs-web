package com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse;

import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

import java.io.File;


@Data
public class FetchFileRequest {

    private  File e;

    private FileAnalysisDto fileAnalysisDto;

    public FetchFileRequest(File e, FileAnalysisDto fileAnalysisDto) {
        this.e=e;
        this.fileAnalysisDto=fileAnalysisDto;
    }
}

package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;

import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

@Data
public class DataAnalyseRequset {

   private FileAnalysisDto fileAnalysisDto;

    public DataAnalyseRequset(FileAnalysisDto fileAnalysisDto){
        this.fileAnalysisDto=fileAnalysisDto;
    }
}

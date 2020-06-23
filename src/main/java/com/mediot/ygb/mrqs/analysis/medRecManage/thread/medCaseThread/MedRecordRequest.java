package com.mediot.ygb.mrqs.analysis.medRecManage.thread.medCaseThread;



import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

@Data
public class MedRecordRequest {

    private FileAnalysisDto fileAnalysisDto;

    public MedRecordRequest(FileAnalysisDto fileAnalysisDto){
        this.fileAnalysisDto=fileAnalysisDto;
    }
}

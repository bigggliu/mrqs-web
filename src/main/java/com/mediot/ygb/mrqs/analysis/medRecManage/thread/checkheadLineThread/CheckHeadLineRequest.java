package com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread;

import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

import java.io.File;
import java.util.concurrent.CountDownLatch;


@Data
public class CheckHeadLineRequest {

    private CountDownLatch countDownLatch;

    private FileAnalysisDto fileAnalysisDto;

    private File file;

    public CheckHeadLineRequest(File file,CountDownLatch countDownLatch,FileAnalysisDto fileAnalysisDto){
        this.countDownLatch=countDownLatch;
        this.fileAnalysisDto=fileAnalysisDto;
        this.file=file;
    }

}

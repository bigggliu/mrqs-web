package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.cache.selector.SimpleReadCacheSelector;
import com.mediot.ygb.mrqs.analysis.medRecManage.excelDataListener.CachedListener;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.CheckHeadLineStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import lombok.Data;

@Data
public class CheckHeadLineForExcelStrategy implements CheckHeadLineStrategy {

    private CheckHeadLineRequest checkHeadLineRequest;

    private FileAnalysisDto fileAnalysisDto;

    public CheckHeadLineForExcelStrategy(CheckHeadLineRequest checkHeadLineRequest, FileAnalysisDto fileAnalysisDto) {
        this.checkHeadLineRequest=checkHeadLineRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void checkHeadline() {
        //先读一次获取总数
        CachedListener cachedListener=new CachedListener(fileAnalysisDto,checkHeadLineRequest);
        EasyExcel.read(checkHeadLineRequest.getFile().getAbsolutePath(),cachedListener).readCacheSelector(new SimpleReadCacheSelector(500, 1000))
                .sheet().headRowNumber(0).doRead();
        //
        checkHeadLineRequest.getCountDownLatch().countDown();
    }
}

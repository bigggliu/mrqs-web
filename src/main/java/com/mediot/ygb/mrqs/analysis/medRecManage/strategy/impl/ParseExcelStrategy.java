package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl;

import com.alibaba.excel.EasyExcel;

import com.alibaba.excel.cache.selector.SimpleReadCacheSelector;

import com.mediot.ygb.mrqs.analysis.medRecManage.excelDataListener.CustomStringConverter;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.excelDataListener.ExcelListener;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.ParseFileStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParseExcelStrategy implements ParseFileStrategy {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FetchFileRequest fetchFileRequest;

    private FileAnalysisDto fileAnalysisDto;


    public ParseExcelStrategy(FetchFileRequest fetchFileRequest, FileAnalysisDto fileAnalysisDto){
        this.fetchFileRequest=fetchFileRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void parseFile2Date() {
        try {
            //Long startTime=System.currentTimeMillis();
            ExcelListener excelListener=new ExcelListener(fetchFileRequest,fileAnalysisDto);
            EasyExcel.read(fetchFileRequest.getE().getAbsolutePath(),excelListener).readCacheSelector(new SimpleReadCacheSelector(500, 1000))
                    //.registerConverter(new CustomStringConverter())
                    .sheet().headRowNumber(0).doRead();
            //Long endTime=System.currentTimeMillis();
            //logger.info(fetchFileRequest.getE().getName()+"解析时间为"+(endTime-startTime)/1000+"s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

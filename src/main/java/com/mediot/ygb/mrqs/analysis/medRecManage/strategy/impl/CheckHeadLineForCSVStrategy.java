package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl;

import com.mediot.ygb.mrqs.analysis.medRecManage.rowProcessors.CVSRowProcessors;
import com.mediot.ygb.mrqs.analysis.medRecManage.rowProcessors.CheckHeadLineProcessors;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.CheckHeadLineStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.Data;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import static cn.hutool.core.util.StrUtil.getReader;

@Data
public class CheckHeadLineForCSVStrategy implements CheckHeadLineStrategy {

    private CheckHeadLineRequest checkHeadLineRequest;

    private  FileAnalysisDto fileAnalysisDto;

    private static Object o=new Object();

    public CheckHeadLineForCSVStrategy(CheckHeadLineRequest checkHeadLineRequest, FileAnalysisDto fileAnalysisDto) {
        this.checkHeadLineRequest=checkHeadLineRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void checkHeadline() {
        //组件机制不提供总读，读单列以获取总数
        CsvParserSettings settings = new CsvParserSettings();
        settings.setMaxColumns(700);
        settings.setMaxCharsPerColumn(7000);
        settings.getFormat().setLineSeparator("\n");
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(false);
        CheckHeadLineProcessors c = new CheckHeadLineProcessors(fileAnalysisDto,checkHeadLineRequest);
        settings.setProcessor(c);
        CsvParser parser = new CsvParser(settings);
        parser.parse(getReader(checkHeadLineRequest.getFile().getAbsolutePath()));
        checkHeadLineRequest.getCountDownLatch().countDown();
    }

    private Reader getReader(String relativePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(relativePath),"gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }
}

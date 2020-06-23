package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl;

import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.rowProcessors.CVSRowProcessors;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.ParseFileStrategy;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.common.record.RecordMetaData;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.*;
import java.util.*;


public class ParseCSVStrategy implements ParseFileStrategy {

    private FetchFileRequest fetchFileRequest;

    private FileAnalysisDto fileAnalysisDto;

    private static Object o=new Object();


    public ParseCSVStrategy(FetchFileRequest fetchFileRequest, FileAnalysisDto fileAnalysisDto){
        this.fetchFileRequest=fetchFileRequest;
        this.fileAnalysisDto=fileAnalysisDto;
    }

    @Override
    public void parseFile2Date() {
        try {
            //组件机制不提供总读，读单列以获取总数
            CsvParserSettings settings = new CsvParserSettings();
            settings.setMaxColumns(700);
            settings.setMaxCharsPerColumn(7000);
            settings.getFormat().setLineSeparator("\n");
            settings.selectIndexes(0);
            CsvParser parser = new CsvParser(settings);
            List<Record> list=parser.parseAllRecords(getReader(fetchFileRequest.getE().getAbsolutePath()));
            //
            CsvParserSettings parserSettings = new CsvParserSettings();
            parserSettings.setMaxColumns(700);
            parserSettings.setMaxCharsPerColumn(7000);
            parserSettings.getFormat().setLineSeparator("\n");
            parserSettings.setLineSeparatorDetectionEnabled(true);
            CVSRowProcessors cvsRowProcessors = new CVSRowProcessors(fileAnalysisDto,list.size(),fetchFileRequest);
            parserSettings.setProcessor(cvsRowProcessors);
            parserSettings.setHeaderExtractionEnabled(false);
            CsvParser _parser = new CsvParser(parserSettings);
            _parser.parse(getReader(fetchFileRequest.getE().getAbsolutePath()));
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    private Reader getReader(String relativePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fetchFileRequest.getE().getAbsolutePath()),"gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }
}

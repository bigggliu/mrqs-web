package com.mediot.ygb.mrqs.analysis.reportManage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.StandardCodeEnum;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckColMapper;
import com.mediot.ygb.mrqs.analysis.reportManage.enumcase.DwStatusEnum;
import com.mediot.ygb.mrqs.analysis.reportManage.service.ReportManageService;
import com.mediot.ygb.mrqs.common.core.exception.MediotException;
import com.mediot.ygb.mrqs.common.entity.excelModel.HQMSData;
import com.mediot.ygb.mrqs.common.entity.excelModel.JXKHData;
import com.mediot.ygb.mrqs.common.entity.excelModel.WTData;
import com.mediot.ygb.mrqs.common.entity.vo.ErrorDetailVo;
import com.mediot.ygb.mrqs.common.entity.vo.ReportManageVo;
import com.mediot.ygb.mrqs.common.util.DataAnalyseUtils;
import com.mediot.ygb.mrqs.common.util.PdfFontUtils;
import com.mediot.ygb.mrqs.config.FileUploadConfig;
import com.mediot.ygb.mrqs.dict.dao.TDataStandardMapper;
import com.mediot.ygb.mrqs.dict.entity.TDataStandard;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorEntity;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutdiagTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao.FileUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;

@Service
public class ReportManageServiceImpl implements ReportManageService {

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private TCheckColMapper tCheckColMapper;

    @Autowired
    private TDataStandardMapper tDataStandardMapper;

    @Autowired
    private TOrgsMapper tOrgsMapper;

    @Autowired
    private TFirstpageTestingMapper tFirstpageTestingMapper;

    @Autowired
    private TFirstoutoperTestingMapper tFirstoutoperTestingMapper;

    @Autowired
    private TFirstoutdiagTestingMapper tFirstoutdiagTestingMapper;

    @Autowired
    private TErrorMapper tErrorMapper;

    private static int batchSize=5;

    @Override
    public void exportReportByFileId(HttpServletResponse response,String fileId) {
        try {
            Map<String,Object> m= Maps.newHashMap();
            m.put("fileId",fileId);
            FileUploadEntity f=fileUploadMapper.selectFileUpload(m);
            QueryWrapper qw=new QueryWrapper();
            qw.eq("BATCH_ID",f.getBatchId());
            TErrorEntity tErrorEntity=tErrorMapper.selectOne(qw);
            if(f.getState().equals(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE_SUCCESS)))&&
                    Integer.parseInt(tErrorEntity.getErrorFields())!=0){
                File file=new File(FileUploadConfig.pdfPath);
                if(!file.exists()&&!file.isDirectory()){
                    file.mkdir();
                }
                Document doc = createPdf(FileUploadConfig.pdfPath+f.getFileName()+"的检测报告.pdf");
                createFile(doc,f);
                doc.close();
                toWebDownLoad(response,f);
            }else {
                File file=new File(FileUploadConfig.pdfPath);
                if(!file.exists()&&!file.isDirectory()){
                    file.mkdir();
                }
                Document doc = createPdf(FileUploadConfig.pdfPath+f.getFileName()+"的检测报告.pdf");
                createNoneInfoFile(doc);
                doc.close();
                toWebDownLoad(response,f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toWebDownLoad(HttpServletResponse response,FileUploadEntity f){
        BufferedInputStream bis=null;
        OutputStream os=null;
        File pdfFile = new File(FileUploadConfig.pdfPath+f.getFileName()+"的检测报告.pdf");
        response.reset();
        try {
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            String fn=f.getFileName()+"的检测报告";
            fn= URLEncoder.encode(fn, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fn + ".pdf");
            bis=new BufferedInputStream(new FileInputStream(pdfFile));
            byte[] b=new byte[bis.available()+1000];
            int i=0;
            os = response.getOutputStream();   //直接下载导出
            while((i=bis.read(b))!=-1) {
                os.write(b, 0, i);
            }
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void toExcelDownLoad(HttpServletResponse response,String path,String fn){
        BufferedInputStream bis=null;
        OutputStream os=null;
        File excelFile = new File(path);
        response.reset();
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            fn=URLEncoder.encode(fn.toString(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fn);
            bis=new BufferedInputStream(new FileInputStream(excelFile));
            byte[] b=new byte[bis.available()+1000];
            int i=0;
            os = response.getOutputStream();   //直接下载导出
            while((i=bis.read(b))!=-1) {
                os.write(b, 0, i);
            }
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mergeReportByFileIds(HttpServletResponse response, String fileIds) {
        try {
            String[] fileIdsArr=fileIds.split(",");
            List<FileUploadEntity> filteredFEs=Lists.newArrayList();
            StringBuffer title=new StringBuffer();
            if(fileIdsArr.length==1){
                exportReportByFileId(response,fileIdsArr[0]);
            }else {
                Stream.of(fileIdsArr).forEach(e->{
                    Map<String,Object> queryMap=Maps.newHashMap();
                    queryMap.put("fileId",e);
                    FileUploadEntity fe=fileUploadMapper.selectFileUploadEntityByParam(queryMap);
                    if(fe!=null){
                        title.append(fe.getFileName()).append(",");
                        if(fe.getState().equals(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE_SUCCESS)))){
                            filteredFEs.add(fe);
                        }
                    }
                });
            }
            if(filteredFEs.size()==0){
                File file=new File(FileUploadConfig.pdfPath);
                if(!file.exists()&&!file.isDirectory()){
                    file.mkdir();
                }
                Document doc = createPdf(FileUploadConfig.pdfPath+
                        title.substring(0,title.length()-1)
                        +"的检测报告.pdf");
                createNoneInfoFile(doc);
                doc.close();
            }else if(filteredFEs.size()>1){
                File file=new File(FileUploadConfig.pdfPath);
                if(!file.exists()&&!file.isDirectory()){
                    file.mkdir();
                }
                Document doc = createPdf(FileUploadConfig.pdfPath+
                        title.substring(0,title.length()-1)
                        +"的检测报告.pdf");
                filteredFEs.forEach(e->{
                    try {
                        createFile(doc,e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
                doc.close();
                FileUploadEntity f=new FileUploadEntity();
                f.setFileName(
                        title.substring(0,title.length()-1)
                );
                toWebDownLoad(response,f);
            }else {
                exportReportByFileId(response,String.valueOf(filteredFEs.get(0).getFileId()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createExcelFileFForStandardCode(String fileId, String standardCode){
        try {
            Map<String,Object> m= Maps.newHashMap();
            m.put("fileId",fileId);
            FileUploadEntity f=fileUploadMapper.selectFileUpload(m);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("STANDARD_CODE",f.getStandardCode());
            TDataStandard ts=tDataStandardMapper.selectOne(queryWrapper);
            QueryWrapper qw=new QueryWrapper();
            qw.eq("ORG_ID",f.getOrgId());
            TOrgsEntity o=tOrgsMapper.selectOne(qw);
            StringBuffer fileName =new StringBuffer();
            fileName.append(f.getStartTime())
                    .append("-")
                    .append(f.getEndTime())
                    .append(o.getOrgName())
                    .append(ts.getStandardName())
                    .append("标准.xlsx");
            String path=FileUploadConfig.excelPath+File.separator+fileName.toString();
            File file=new File(path);
            if(file.exists()){
                file.delete();
            }
            ExcelWriter excelWriter=null;
            standardCode="04";
            if(standardCode.equals(StandardCodeEnum.getValue(StandardCodeEnum.JXKH))){
                excelWriter = EasyExcel.write(path, JXKHData.class).build();
            }else if(standardCode.equals(StandardCodeEnum.getValue(StandardCodeEnum.WT))){
                excelWriter = EasyExcel.write(path, WTData.class).build();
            }else if(standardCode.equals(StandardCodeEnum.getValue(StandardCodeEnum.HQMS))){
                excelWriter = EasyExcel.write(path, HQMSData.class).build();
            }
            WriteSheet writeSheet = EasyExcel.writerSheet("数据").build();
            Map<String,Object> queryMap=Maps.newHashMap();
            queryMap.put("batchId",f.getBatchId());
            int total=tFirstpageTestingMapper.selectCountByMap(queryMap);
            int batchNum=total/batchSize;
            for (int i = 0; i <= batchNum; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                if(standardCode.equals(StandardCodeEnum.getValue(StandardCodeEnum.JXKH))){
                    getCaseInfoForJXKH(String.valueOf(f.getBatchId()),batchSize,i,excelWriter,writeSheet);
                }else if(standardCode.equals(StandardCodeEnum.getValue(StandardCodeEnum.WT))){
                    getCaseInfoForWT(String.valueOf(f.getBatchId()),batchSize,i,excelWriter,writeSheet);
                }else if(standardCode.equals(StandardCodeEnum.getValue(StandardCodeEnum.HQMS))){
                    getCaseInfoForHQMS(String.valueOf(f.getBatchId()),batchSize,i,excelWriter,writeSheet);
                }
            }
            //EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
            excelWriter.finish();
            f.setDwStatus(DwStatusEnum.getValue(DwStatusEnum.canDw));
            QueryWrapper q=new QueryWrapper();
            q.eq("BATCH_ID",f.getBatchId());
            fileUploadMapper.update(f,q);
            //toExcelDownLoad(response,path,fileName.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void exportDataOfCasesForStandardCode(HttpServletResponse response,String fileId, String standardCode) {
        Map<String,Object> m= Maps.newHashMap();
        m.put("fileId",fileId);
        FileUploadEntity file=fileUploadMapper.selectFileUpload(m);
        if(file.getDwStatus()==0){
            file.setDwStatus(DwStatusEnum.getValue(DwStatusEnum.generate));
            QueryWrapper q=new QueryWrapper();
            q.eq("BATCH_ID",file.getBatchId());
            fileUploadMapper.update(file,q);
            new Thread(()->{
                createExcelFileFForStandardCode(fileId,standardCode);
            }).start();
            throw new MediotException("文件开始生成，可能需要较长时间(根据数据量大小而异)");
        }else if(file.getDwStatus()==1){
            throw new MediotException("文件正在生成中，请稍后再试");
        }else {
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("STANDARD_CODE",file.getStandardCode());
            TDataStandard ts=tDataStandardMapper.selectOne(queryWrapper);
            QueryWrapper qw=new QueryWrapper();
            qw.eq("ORG_ID",file.getOrgId());
            TOrgsEntity o=tOrgsMapper.selectOne(qw);
            StringBuffer fileName =new StringBuffer();
            fileName.append(file.getStartTime())
                    .append("-")
                    .append(file.getEndTime())
                    .append(o.getOrgName())
                    .append(ts.getStandardName())
                    .append("标准.xlsx");
            String path=FileUploadConfig.excelPath+File.separator+fileName.toString();
            toExcelDownLoad(response,path,fileName.toString());
        }
    }

    private void getCaseInfoForJXKH(String batchId,int batchSize,int currentPage,ExcelWriter excelWriter,WriteSheet writeSheet){
        List<JXKHData> list=Lists.newArrayList();
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("batchId",batchId);
        int upperBound=(currentPage+1)*batchSize;
        int lowerBound=(currentPage*batchSize)+1;
        queryMap.put("upperBound",upperBound);
        queryMap.put("lowerBound",lowerBound);
        List<TFirstPageTesting> tfs=tFirstpageTestingMapper.selectPageList(queryMap);
        tfs.stream().forEach(e->{
            JXKHData jd=DataAnalyseUtils.parse2JXKHData(e,tFirstoutoperTestingMapper,tFirstoutdiagTestingMapper);
            list.add(jd);
        });
        excelWriter.write(list, writeSheet);
    }

    private void getCaseInfoForWT(String batchId,int batchSize,int currentPage,ExcelWriter excelWriter,WriteSheet writeSheet){
        List<WTData> list=Lists.newArrayList();
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("batchId",batchId);
        int upperBound=(currentPage+1)*batchSize;
        int lowerBound=(currentPage*batchSize)+1;
        queryMap.put("upperBound",upperBound);
        queryMap.put("lowerBound",lowerBound);
        List<TFirstPageTesting> tfs=tFirstpageTestingMapper.selectPageList(queryMap);
        tfs.stream().forEach(e->{
            WTData w=DataAnalyseUtils.parse2WTData(e,tFirstoutoperTestingMapper,tFirstoutdiagTestingMapper);
            list.add(w);
        });
    }

    private void getCaseInfoForHQMS(String batchId,int batchSize,int currentPage,ExcelWriter excelWriter,WriteSheet writeSheet){
        List<HQMSData> list=Lists.newArrayList();
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("batchId",batchId);
        int upperBound=(currentPage+1)*batchSize;
        int lowerBound=(currentPage*batchSize)+1;
        queryMap.put("upperBound",upperBound);
        queryMap.put("lowerBound",lowerBound);
        List<TFirstPageTesting> tfs=tFirstpageTestingMapper.selectPageList(queryMap);
        tfs.stream().forEach(e->{
            HQMSData h=DataAnalyseUtils.parse2HQMSData(e,tFirstoutoperTestingMapper,tFirstoutdiagTestingMapper);
            list.add(h);
        });
    }


    public static Document createPdf(String outpath) throws DocumentException, IOException{
        //页面大小
        //Rectangle rect = new Rectangle(PageSize.A4.rotate());//文档横方向
        Rectangle rect = new Rectangle(PageSize.A4);//文档竖方向
        //如果没有则创建
        File saveDir = new File(outpath);
        File dir = saveDir.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Document doc = new Document(rect);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outpath));
        //PDF版本(默认1.4)
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
        //文档属性
        doc.addTitle("Title@wpixel");
        doc.addAuthor("Author@wpixel");
        doc.addSubject("Subject@wpixel");
        doc.addKeywords("Keywords@wpixel");
        doc.addCreator("Creator@wpixel");
        //页边空白
        doc.setMargins(40, 40, 40, 40);
        //打开文档
        doc.open();
        return doc;
    }

    public void createFile(Document doc,FileUploadEntity f){
        try {
            doc.add(PdfFontUtils.getFont(1, "第一部分：病案首页数据完整性统计"));

            Paragraph text01 = PdfFontUtils.getFont(5, f.getFileName()+"病案首页数据完整性检验结果");
            doc.add(text01);

            Paragraph text02 = PdfFontUtils.getFont(5, "本检验将说明病案数据疑似问题总体情况：");
            doc.add(text02);

            float[] columnWidths = {80,80,80,80,80,80,80,80};//表格每一列的宽度
            //组装数据
            Map<String,Object> queryMap=Maps.newHashMap();
            queryMap.put("batchId",f.getBatchId());
            List<ReportManageVo> reportManageVos=tCheckColMapper.getRptListByBatchId(queryMap);
            reportManageVos.stream().forEach(e->{
                e.setStartTime(e.getYear()+"-01");
                e.setEndTime(e.getYear()+"-12");
                queryMap.put("year",e.getYear());
                e.setErrorFileds(tCheckColMapper.getNumOfErrorFileds(queryMap));
                e.setTotalNumForCurrentYear(tCheckColMapper.getNumOfCurrentYear(queryMap));
                e.setTotalErrorNumForCurrentYear(tCheckColMapper.getErrorNumOfCurrentYear(queryMap));
                QueryWrapper queryWrapper=new QueryWrapper();
                queryWrapper.eq("STANDARD_CODE",f.getStandardCode());
                e.setTotalStandards(tDataStandardMapper.selectOne(queryWrapper).getTotalNumber());
                float fn=Float.parseFloat(e.getErrorFileds())/Float.parseFloat(e.getTotalStandards());
                Double d=new BigDecimal(fn).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                e.setProportionOfError(String.format("%.2f",d*100)+"%");
            });
            //表格
            PdfPTable table = new PdfPTable(columnWidths);
            table.setTotalWidth(640);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            String[][] datas=new String[1+reportManageVos.size()][8];
            datas[0]=new String[]{"数据年份","开始年份","结束年份","总数据量","错误数据量","总字段数","疑似问题数据字段数","疑似问题数据字段所占比例"};
            for(int i=0;i<reportManageVos.size();i++){
                String[] data=new String[]{
                        reportManageVos.get(i).getYear(),
                        reportManageVos.get(i).getStartTime(),
                        reportManageVos.get(i).getEndTime(),
                        reportManageVos.get(i).getTotalNumForCurrentYear(),
                        reportManageVos.get(i).getTotalErrorNumForCurrentYear(),
                        reportManageVos.get(i).getTotalStandards(),
                        reportManageVos.get(i).getErrorFileds(),
                        reportManageVos.get(i).getProportionOfError()
                };
                datas[i+1]=data;
            }
            //Object[][] datas = {{"区域", "总销售额(万元)", "总利润(万元)简单的表格"}, {"江苏省" , 9045,  2256}, {"广东省", 3000, 690}};
            for(int i = 0; i < datas.length; i++) {
                for(int j = 0; j < datas[i].length; j++) {
                    PdfPCell pdfCell = new PdfPCell(); //表格的单元格
                    Paragraph paragraph = new Paragraph(String.valueOf(datas[i][j]), getPdfChineseFont());
                    pdfCell.setPhrase(paragraph);
                    table.addCell(pdfCell);
                }
            }
            doc.add(table);

            doc.add(PdfFontUtils.getFont(1, "第二部分：病案首页数据疑似错误统计"));

            Paragraph text03 = PdfFontUtils.getFont(5, "本检验将说明病案数据缺失与逻辑错误情况，包括病案首页字段名、字段描述、" +
                    "疑似数据说明，错误数量等等，具体描述如下：");
            doc.add(text03);
            //
            for(int k=0;k<reportManageVos.size();k++){
                Paragraph text = PdfFontUtils.getFont(5, reportManageVos.get(k).getYear()+"("
                        +reportManageVos.get(k).getStartTime()+"至"+reportManageVos.get(k).getEndTime()+")年度病案首页检测结果汇总");
                doc.add(text);
                float[] dColumnWidths = {40,100,150,150,40};//表格每一列的宽度
                PdfPTable tab = new PdfPTable(dColumnWidths);
                tab.setTotalWidth(530);
                tab.setHorizontalAlignment(Element.ALIGN_CENTER);
                Map<String,Object> qm=Maps.newHashMap();
                qm.put("batchId",f.getBatchId());
                qm.put("year",reportManageVos.get(k).getYear());
                List<ErrorDetailVo> errorDetailVos= tCheckColMapper.selectErrorDetailByYear(qm);
                String[][] dDatas=new String[1+errorDetailVos.size()][4];
                dDatas[0]=new String[]{"序号","信息分类","字段描述","疑似问题数据说明","错误数量"};
                for(int i=0;i<errorDetailVos.size();i++){
                    if(checkByColName(dDatas,errorDetailVos,i)){
                        continue;
                    }else {
                        String[] data=new String[]{
                                String.valueOf(i+1),
                                getInformationClass(errorDetailVos.get(i)),
                                errorDetailVos.get(i).getColComments(),
                                errorDetailVos.get(i).getErrorMessage(),
                                errorDetailVos.get(i).getTotal(),
                        };
                        dDatas[i+1]=data;
                    }
                }
                for(int i = 0; i < dDatas.length; i++) {
                    for(int j = 0; j < dDatas[i].length; j++) {
                        PdfPCell pdfCell = new PdfPCell(); //表格的单元格
                        Paragraph paragraph = new Paragraph(String.valueOf(dDatas[i][j]), getPdfChineseFont());
                        pdfCell.setPhrase(paragraph);
                        tab.addCell(pdfCell);
                    }
                }
                doc.add(tab);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createNoneInfoFile(Document doc) throws Exception{
        doc.add(PdfFontUtils.getFont(1, "没有任何统计数据"));
    }

    public static Font getPdfChineseFont() throws Exception {
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
                BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
        return fontChinese;
    }

    public String[] sortByOperation(ErrorDetailVo errorDetailVo,int i){
        String colComments = "";
        if(errorDetailVo.getOperationType() != null && errorDetailVo.getOperationType().equals("01")){
            colComments = "主要手术及操作" + "," + errorDetailVo.getColComments();
        }
        if(errorDetailVo.getOperationType() != null && errorDetailVo.getOperationType().equals("02")){
            colComments = "其他手术及操作" + String.valueOf(errorDetailVo.getOperationOrder() - 1) + "," + errorDetailVo.getColComments();
        }
        String[] data=new String[]{
                String.valueOf(i+1),
                getInformationClass(errorDetailVo),
                colComments,
                errorDetailVo.getErrorMessage(),
                errorDetailVo.getTotal(),
        };
        return data;
    }

    public String[] sortByDiagnosis(ErrorDetailVo errorDetailVo,int i){
        String colComments = "";
        if(errorDetailVo.getDiagType() != null && errorDetailVo.getDiagType().equals("01")){
            colComments = "主要诊断" + "," + errorDetailVo.getColComments();
        }
        if(errorDetailVo.getDiagType() != null && errorDetailVo.getDiagType().equals("02")){
            colComments = "其他诊断" + String.valueOf(errorDetailVo.getDiagOrder()) + "," + errorDetailVo.getColComments();
        }
        if(errorDetailVo.getDiagType() != null && errorDetailVo.getDiagType().equals("03")){
            colComments = "病理诊断" + String.valueOf(errorDetailVo.getDiagOrder()) + "," + errorDetailVo.getColComments();
        }
        if(errorDetailVo.getDiagType() != null && errorDetailVo.getDiagType().equals("04")){
            colComments = "损伤、中毒的外部原因" + String.valueOf(errorDetailVo.getDiagOrder()) + "," + errorDetailVo.getColComments();
        }
        String[] data=new String[]{
                String.valueOf(i+1),
                getInformationClass(errorDetailVo),
                colComments,
                errorDetailVo.getErrorMessage(),
                errorDetailVo.getTotal(),
        };
        return data;
    }

    public boolean checkByColName(String[][] dDatas,List<ErrorDetailVo> errorDetailVos,int i){
        if(errorDetailVos.get(i).getColName().equals("ZY/QTZDRYBQQZFW")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("QKYHDJDMQZFW")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("MZFSDMQZFW")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("CYQKDMQZFW")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSJBDMQZFW")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("FHCDDMQZFW")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSCZBWDMQZFW")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("ZDRYBQ")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("ZDJBMS")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSXGXXWZX1")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSXGXXWZX2")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSJCZBM&SSJB")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSJCZBM&YHDJ")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("XSECSTZ&QTZDBM")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSJCZBM&MZYS")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("MZFS&MZYS")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("ZDXGXXWZX")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("MZFS&SSJCZBM")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("BLZDJBBM")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSZDJBBM")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("12SJYXETCYSZD")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("MZFS&MZF")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("ZDJBBMFW(A00A20)")){
            dDatas[i+1]=sortByDiagnosis(errorDetailVos.get(i),i);
            return true;
        }
        if(errorDetailVos.get(i).getColName().equals("SSJCZRQBNZY")){
            dDatas[i+1]=sortByOperation(errorDetailVos.get(i),i);
            return true;
        }
        return false;
    }

    public String getInformationClass(ErrorDetailVo errorDetailVo){
        if(errorDetailVo.getInformationClass() != null && errorDetailVo.getInformationClass().equals("1")){
            return "患者基本信息";
        }
        if(errorDetailVo.getInformationClass() != null && errorDetailVo.getInformationClass().equals("2")){
            return "住院过程信息";
        }
        if(errorDetailVo.getInformationClass() != null && errorDetailVo.getInformationClass().equals("3")){
            return "诊疗信息";
        }
        if(errorDetailVo.getInformationClass() != null && errorDetailVo.getInformationClass().equals("4")){
            return "费用信息";
        }
        return "";
    }
}

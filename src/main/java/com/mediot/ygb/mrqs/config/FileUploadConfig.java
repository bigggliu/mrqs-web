package com.mediot.ygb.mrqs.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfig {
    public static String path;
    public static String basePath;
    public static String tempPath;
    public static String unZipPath;
    public static String pdfPath;
    public static String excelPath;

    @Value("${fileUpload.path}")
    public void setPath(String path) {
        FileUploadConfig.path = path;
    }

    @Value("${fileUpload.basePath}")
    public void setBasePath(String basePath) {
        FileUploadConfig.basePath = basePath;
    }

    @Value("${fileUpload.tempPath}")
    public void setTempPath(String tempPath) {
        FileUploadConfig.tempPath = tempPath;
    }

    @Value("${fileUpload.unZipPath}")
    public void setUnZipPath(String unZipPath) {
        FileUploadConfig.unZipPath = unZipPath;
    }

    @Value("${fileUpload.pdfPath}")
    public void setPdfPath(String pdfPath) {
        FileUploadConfig.pdfPath = pdfPath;
    }

    @Value("${fileUpload.excelPath}")
    public void setExcelPath(String excelPath) {
        FileUploadConfig.excelPath = excelPath;
    }
}

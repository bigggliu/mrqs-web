package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.vo;

import lombok.Data;

@Data
public class FileUploadVo {

    private String fileId;

    private String fileName;

    private String filePath;

    private String md5;

    private String uploadTime;

    private String upDateTime;

    private String startTime;

    private String endTime;

    private float duration;

    private String chunks;

    private String chunk;

    private Integer passedSize;

    private String standardCode;

    private String batchId;

    private String actualYear;

    private String sourceCode;

    private String total;

    private String totalNumberOfFields;

    private String numberOfRequiredFields;

    private String hitDataVolume;

    private String numberOfTestIndicators;

    private String status;

    private String state;

    private long chunkSize;

    private String orgId;

    private String errorFields;

    private String proportionOfError;

}

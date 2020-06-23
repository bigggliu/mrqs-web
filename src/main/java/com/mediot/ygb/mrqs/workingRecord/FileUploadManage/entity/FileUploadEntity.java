package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("T_FILE_UPLOAD_INFO")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileUploadEntity implements Serializable {

    private static final long serialVersionUID = 8009334349451029022L;

    @TableId("FILE_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long fileId;

    @TableField("FILE_NAME")
    private String fileName;

    @TableField("FILE_PATH")
    private String filePath;

    @TableField("MD5")
    private String md5;

    @TableField("UPLOAD_TIME")
    private Date uploadTime;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("CHUNKS")
    private Integer chunks;

    @TableField("CHUNK")
    private Integer chunk;

    @TableField("SIZE")
    private Long size;

    @TableField("STATE")
    private String state;

    @TableField("STANDARD_CODE")
    private String standardCode;

    @TableField("BATCH_ID")
    private Long batchId;

    @TableField("ACTUAL_YEAR")
    private String actualYear;

    @TableField("SOURCE_CODE")
    private String sourceCode;

    @TableField("START_TIME")
    private String startTime;

    @TableField("END_TIME")
    private String endTime;

    @TableField("ORG_ID")
    private String orgId;

    @TableField("DW_STATUS")
    private int dwStatus;

    @TableField(exist = false)
    private String total;

    @TableField(exist = false)
    private String totalNumberOfFields;

    @TableField(exist = false)
    private String numberOfRequiredFields;

    @TableField(exist = false)
    private String hitDataVolume;

    @TableField(exist = false)
    private String numberOfTestIndicators;

    @TableField(exist = false)
    private String errorFields;

    @TableField(exist = false)
    private String proportionOfError;


}

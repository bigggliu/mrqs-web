package com.mediot.ygb.mrqs.index.errorInfoManage.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_ERROR_DETAIL")
public class TErrorDetailEntity {

    @TableId("BATCH_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long batchId;

    @TableField("CASE_ID")
    private String caseId;

    @TableField("COL_NAME")
    private String colName;

    @TableField("COL_COMMENTS")
    private String colComments;

    @TableField("ERROR_MESSAGE")
    private String errorMessage;

    @TableField("SCORE")
    private Integer score;

    @TableField("MUSTFILL")
    private Integer mustFill;

    @TableField("ANALYSE_TYPE")
    private String analyseType;

    @TableField("REMARK")
    private Integer remark;

    @TableField("OUT_DTIME")
    private Date outDtime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstPageTestingId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstoutoperTestingId;

    private String operationType;

    private Integer operationOrder;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstoutdiagTestingId;

    private String diagType;

    private Integer diagOrder;

    private String informationClass;
}

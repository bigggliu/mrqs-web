package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_CHECK_COL")
public class TCheckCol implements Serializable {

    private static final long serialVersionUID = -9173858787547758567L;

    @TableId(value = "CHECK_COL_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long checkColId;

    @TableField(value = "COL_NAME")
    private String colName;

    @TableField("COL_COMMENTS")
    private String colComments;

//    @TableField("DATA_TYPE")
//    private int dataType;

    @TableField("FQUN")
    private String fQun;

    @TableField("CMIS_CHOICE")
    private String cmisChoice;

    @TableField("EMR_CHOICE")
    private String emrChoice;

    @TableField("FSORT")
    private String fSort;

    @TableField("QUALITY_CLASS")
    private String qualityClass;

    @TableField("GRADING_LEVEL")
    private String gradingLevel;

    @TableField("INFORMATION_CLASS")
    private String informationClass;

    @TableField("RULE_TYPE")
    private String ruleType;

    @TableField("SCORE")
    private String score;

    @TableField("MUST_FILL")
    private String mustFill;

    @TableField("RULE_DESCRIPTION")
    private String ruleDescription;

    @TableField("VERIFICATION_LOGIC")
    private String verificationLogic;

    @TableField("TABLE_NAME")
    private String tableName;

    @TableField("COL_TYPE")
    private String colType;

    @TableField("IS_MULTI_TABLE_QUERY")
    private Integer isMultiTableQuery;

    @TableField(exist = false)
    private String orgNames;


}

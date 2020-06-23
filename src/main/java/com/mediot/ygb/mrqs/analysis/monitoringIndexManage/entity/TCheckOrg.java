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
@TableName("T_CHECK_ORG")
public class TCheckOrg implements Serializable {

    private static final long serialVersionUID = -2311661716272534049L;

    @TableId(value="CHECK_ORG_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long checkOrgId;

    @TableField(value = "ORG_ID")
    private Long orgId;

    @TableField(value = "ORG_NAME")
    private String orgName;

    @TableField(value = "CHECK_COL_ID")
    private Long checkColId;

    @TableField(value = "COL_NAME")
    private String colName;

    @TableField("COL_COMMENTS")
    private String colComments;

    @TableField("FSORT")
    private String fSort;

    @TableField("SCORE")
    private String score;

    @TableField("MUST_FILL")
    private String mustFill;

    @TableField("CMIS_CHOICE")
    private String cmisChoice;

    @TableField("EMR_CHOICE")
    private String emrChoice;

    @TableField("RULE_TYPE")
    private String ruleType;

    @TableField(exist = false)
    private String verificationLogic;

    @TableField(exist = false)
    private String ruleDescription;

}

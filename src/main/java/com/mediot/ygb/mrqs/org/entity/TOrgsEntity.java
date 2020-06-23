package com.mediot.ygb.mrqs.org.entity;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_ORGS")
public class TOrgsEntity implements Serializable {

    private static final long serialVersionUID = -8666420620695625916L;

    @TableId(value = "ORG_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orgId;

    @TableField("ORG_CODE")
    private String orgCode;

    @TableField("ORG_NAME")
    private String orgName;

    @TableField("FQUN")
    private String fqun;

    @TableField("ORG_ALIAS")
    private String orgAlias;

    @TableField("AREA")
    private String area;

    @TableField("ADDR")
    private String addr;

    @TableField("PARENT_ID")
    private Long parentId;

    @TableField("ORG_GRADE")
    private String orgGrade;

    @TableField("ORG_TYPE")
    private String orgType;

    @TableField("DISEASE_CODING")
    private String diseaseCoding;

    @TableField("OPERATIVE_CODING")
    private String operativeCode;

    @TableField("DATA_FORMAT")
    private String dataFormat;

    @TableField("DOCKING_SCHEME")
    private String dockingScheme;

    @TableField("STST_MODE")
    private String ststMode;

    @TableField("LOGO")
    private String logo;

    @TableField("FSTATE")
    private Integer fState;

    @TableField("PROVINCE")
    private String province;

    @TableField("CITY")
    private String city;

    @TableField("COUNTY")
    private String country;

    @TableField("EVALUATION_CRITERIA")
    private String evaluationCriteria;

    @TableField("DEFAULT_DOCKING_SCHEME")
    private String defaultDockingScheme;

    @TableField("EMAIL")
    private String email;

    @TableField("PHONE")
    private String phone;

    @TableField("QUERYCODE")
    private String queryCode;

    @TableField("CATEGORYCODE")
    private String categoryCode;

    @TableField("APPCODE")
    private String appCode;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField(exist = false)
    private String pid;

    @TableField(exist = false)
    private String pname;

}

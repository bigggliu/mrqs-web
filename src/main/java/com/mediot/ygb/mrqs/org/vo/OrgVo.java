package com.mediot.ygb.mrqs.org.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class OrgVo  implements Serializable {

    public static final long serialVersionUID= 7809114871896533299L;


    private String orgId;


    private String orgCode;


    private String orgName;

    private String fqun;

    private String orgAlias;


    private String area;


    private String addr;

    private String parentId;


    private String orgGrade;


    private String orgType;

    private String diseaseCoding;

    private String operativeCode;

    private String dataFormat;


    private String dockingScheme;

    private String ststMode;

    private String logo;

    private String fState;

    private String province;

    private String city;

    private String country;

    private String evaluationCriteria;

    private String defaultDockingScheme;

    private String email;

    private String phone;

    private String queryCode;

    private String categoryCode;

    private String appCode;

    private Integer pageNum;

    private Integer pageSize;

    private Date createTime;

    private String pid;

    private String pname;

}

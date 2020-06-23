package com.mediot.ygb.mrqs.system.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = -3040934305405171244L;

    private String userId;

    private String userNo;

    private String pwd;

    private String userName;

    private String sortNo;

    private String userLevel;

    private String mobilePhone;

    private String userAddress;

    private String tfFlag;

    private String ftypeUser;

    private String orgId;

    private String auditId;

    private String auditName;

    private Date auditTime;

    private String auditFstate;

    private String tfRemark;

    private String orgType;

    private Date modifyTime;

    private Date createTime;

    private String createUserid;

    private String modifyUserid;

    private String auditOrgCode;

    private String auditTfAccessory;

    private String email;

    private String deptName;

    private String postName;

    private String tfProvince;

    private String tfCity;

    private String tfDistrict;

    private String address;

    private int pageNum;

    private int pageSize;

    private String queryStr;

    private String imageCode;

    private String systemCodes;

    private int fstate;

}

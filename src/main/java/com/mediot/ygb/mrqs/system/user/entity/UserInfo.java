package com.mediot.ygb.mrqs.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * TS_USER_INFO
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("TS_USER_INFO")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -3040934305405171244L;

    @TableId(value = "USER_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

    private String userNo;

    private String pwd;

    private String userName;

    private String sortNo;

    private String userLevel;

    private String mobilePhone;

    private String userAddress;

    private String tfFlag;

    private String ftypeUser;

    private Long orgId;

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

    private Integer fstate;

    private String systemCodes;

    @TableField(exist = false)
    private String orgName;

    @TableField(exist = false)
    private String oldPwd;

    @TableField(exist = false)
    private String newPwd;

}
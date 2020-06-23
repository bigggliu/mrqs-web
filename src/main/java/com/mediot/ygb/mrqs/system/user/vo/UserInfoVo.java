package com.mediot.ygb.mrqs.system.user.vo;

import com.mediot.ygb.mrqs.system.menu.vo.MenuOfSystemVo;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;
import com.mediot.ygb.mrqs.system.menu.vo.SystemVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = -2993074851481692533L;

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

    private Integer fstate;

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

    private String sessionId;

    private String systemCodes;

    private String orgName;

    private String currentSystemCode;

    private List<MenuVo> menuList;

    private List<SystemVo> systemList;

    private List<MenuOfSystemVo> userMenuOfSystemList;

    private String appCode;

    private String fqun;

    private String groups;

}

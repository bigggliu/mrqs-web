package com.mediot.ygb.mrqs.system.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("SYS_USER")
public class User implements Serializable {

    private static final long serialVersionUID = 750022192486180974L;
    @TableId("USER_ID")
    private Long userId;
    private String username;
    private String password;
    private Long orgId;
    private String email;
    private String phone;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private Integer state;
}

package com.mediot.ygb.mrqs.system.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("SYS_ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 8757507068255924413L;
    @TableId("ROLE_ID")
    private Long roleId;
    private String roleName;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private Integer state;
}

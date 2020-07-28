package com.mediot.ygb.mrqs.system.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_USER_ROLE")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -8226443242742316093L;
    private Long userId;
    private Long roleId;

    public UserRole() {
    }

    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

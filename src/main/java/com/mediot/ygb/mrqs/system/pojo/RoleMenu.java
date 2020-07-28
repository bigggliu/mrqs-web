package com.mediot.ygb.mrqs.system.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_ROLE_MENU")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 2513889795114464102L;
    private Long roleId;
    private Long menuId;

    public RoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public RoleMenu() {
    }
}

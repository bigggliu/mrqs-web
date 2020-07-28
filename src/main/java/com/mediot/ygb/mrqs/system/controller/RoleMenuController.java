package com.mediot.ygb.mrqs.system.controller;

import com.mediot.ygb.mrqs.system.Vo.RoleMenuVo;
import com.mediot.ygb.mrqs.system.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/roleMenu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @RequestMapping("/saveRoleMenus")
    public int saveRoleMenus(RoleMenuVo roleMenuVo){
        return roleMenuService.saveRoleMenus(roleMenuVo);
    }
}

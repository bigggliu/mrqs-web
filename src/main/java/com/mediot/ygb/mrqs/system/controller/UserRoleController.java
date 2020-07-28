package com.mediot.ygb.mrqs.system.controller;

import com.mediot.ygb.mrqs.system.Vo.UserRoleVo;
import com.mediot.ygb.mrqs.system.pojo.UserRole;
import com.mediot.ygb.mrqs.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/insertUserRoleByUserIds")
    public int insertUserRoleByUserIds(UserRoleVo userRoleVo){
        return userRoleService.insertUserRoleByUserIds(userRoleVo);
    }

    @RequestMapping("/delete")
    public int delete(UserRole userRole){
        return userRoleService.delete(userRole);
    }
}

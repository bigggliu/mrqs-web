package com.mediot.ygb.mrqs.system.controller;

import com.mediot.ygb.mrqs.system.pojo.Org;
import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.pojo.User;
import com.mediot.ygb.mrqs.system.service.OrgService;
import com.mediot.ygb.mrqs.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/roleListFuzzyQuery")
    public List<Role> roleListFuzzyQuery(String queryStr){
        return roleService.roleListFuzzyQuery(queryStr);
    }

    @RequestMapping("/insert")
    public int insert(Role role) throws Exception{
        return roleService.insert(role);
    }

    @RequestMapping("/update")
    public int update(Role role) throws Exception {
        return roleService.update(role);
    }

    @RequestMapping("/delete")
    public int delete(Role role){
        return roleService.delete(role);
    }

    @RequestMapping("/selectById")
    public Role selectById(Role role){
        return roleService.selectById(role);
    }
}

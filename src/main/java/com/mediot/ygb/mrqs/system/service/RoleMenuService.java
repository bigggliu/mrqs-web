package com.mediot.ygb.mrqs.system.service;


import com.mediot.ygb.mrqs.system.vo.RoleMenuVo;
import com.mediot.ygb.mrqs.system.dao.RoleMenuDao;
import com.mediot.ygb.mrqs.system.pojo.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    public int saveRoleMenus(RoleMenuVo roleMenuVo){
        int num = 0;
       //清除角色关联菜单
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("ROLE_ID",roleMenuVo.getRoleId());
        roleMenuDao.deleteByMap(queryMap);
        // 保存角色关联菜单
        String[] mids = roleMenuVo.getMenuIds().split(",");
        for(String menuId : mids){
            RoleMenu roleMenu = new RoleMenu(roleMenuVo.getRoleId(),Long.valueOf(menuId));
            num = num + roleMenuDao.insert(roleMenu);
        }
        return num;
    }
}

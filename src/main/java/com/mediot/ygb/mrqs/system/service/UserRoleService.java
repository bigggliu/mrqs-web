package com.mediot.ygb.mrqs.system.service;

import com.mediot.ygb.mrqs.system.Vo.UserRoleVo;
import com.mediot.ygb.mrqs.system.dao.UserRoleDao;
import com.mediot.ygb.mrqs.system.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    public int insertUserRoleByUserIds(UserRoleVo userRoleVo){
        String[] uids = userRoleVo.getUserIds().split(",");
        int num = 0;
        for(String userId : uids){
            UserRole userRole = new UserRole(Long.valueOf(userId),userRoleVo.getRoleId());
            num = num + userRoleDao.insert(userRole);
        }
        return num;
    }

    public int delete(UserRole userRole){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("ROLE_ID",userRole.getRoleId());
        queryMap.put("USER_ID",userRole.getUserId());
        return userRoleDao.deleteByMap(queryMap);
    }
}

package com.mediot.ygb.mrqs.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.system.dao.RoleDao;
import com.mediot.ygb.mrqs.system.dao.RoleMenuDao;
import com.mediot.ygb.mrqs.system.dao.UserRoleDao;
import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserRoleDao userRoleDao;

    public int insert(Role role) throws Exception {
        validationRole(role);
        checkRoleExist(role);
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        return roleDao.insert(role);
    }

    public int update(Role role) throws Exception {
        validationRole(role);
        checkRoleExist(role);
        role.setUpdateTime(new Date());
        return roleDao.insert(role);
    }

    public int delete(Role role){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("ROLE_ID",role.getRoleId());
        //删除用户角色关系
        userRoleDao.deleteByMap(queryMap);
        //删除角色菜单关系
        roleMenuDao.deleteByMap(queryMap);
        return roleDao.deleteById(role);
    }

    public List<Role> roleListFuzzyQuery(String queryStr){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("UPDATE_TIME");
        if(StringUtils.isNotBlank(queryStr)) {
            queryWrapper.like("ROLE_NAME", queryStr);
        }
        return roleDao.selectList(queryWrapper);
    }

    public Role selectById(Role role){
        LocalAssert.notNull(role.getRoleId(),"角色ID不能为空");
        return roleDao.selectById(role);
    }

    public List<Role> selectRoleListByUserId(User user){
        return roleDao.selectRoleListByUserId(user.getUserId());
    }

    public void validationRole(Role role){
        LocalAssert.notNull(role.getRoleName(),"角色名不能为空");
    }

    public void checkRoleExist(Role role) throws Exception {
        Map<String,Object> roleNameQueryMap = new HashMap<>();
        roleNameQueryMap.put("ROLE_NAME",role.getRoleName());
        if(roleDao.selectByMap(roleNameQueryMap).size() > 0){
            throw new Exception("角色名已存在");
        }
    }
}

package com.mediot.ygb.mrqs.system.service;

import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.system.dao.UserDao;
import com.mediot.ygb.mrqs.system.dao.UserRoleDao;
import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.pojo.User;
import com.mediot.ygb.mrqs.system.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    public int insert(User user) throws Exception {
            validationUser(user);
            checkUserExist(user);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            return userDao.insert(user);
    }

    public int update(User user) throws Exception {
            validationUser(user);
            checkUserExist(user);
            user.setUpdateTime(new Date());
            return userDao.updateById(user);
    }

    public int delete(User user){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("USER_ID",user.getUserId());
        //删除用户角色关系
        userRoleDao.deleteByMap(queryMap);
        return userDao.deleteById(user);
    }

    public List<User> userListFuzzyQuery(String queryStr){
        return userDao.userListFuzzyQuery(queryStr);
    }

    public User selectById(User user){
        LocalAssert.notNull(user.getUserId(),"用户ID不能为空");
        return userDao.selectById(user);
    }

    public List<User> selectUserListByroleId(Role role){
        return userDao.selectUserListByroleId(role.getRoleId());
    }

    public void validationUser(User user){
        LocalAssert.notNull(user.getUsername(),"用户名不能为空");
        LocalAssert.notNull(user.getPassword(),"密码不能为空");
        LocalAssert.notNull(user.getOrgId(),"用户机构不能为空");
    }

    public void checkUserExist(User user) throws Exception {
        if(userDao.selectUserByUserName(user.getUsername()).size() > 0){
            throw new Exception("用户名已存在");
        }
    }
}

package com.mediot.ygb.mrqs.system.controller;

import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.pojo.User;
import com.mediot.ygb.mrqs.system.pojo.UserRole;
import com.mediot.ygb.mrqs.system.service.UserService;
import com.mediot.ygb.mrqs.system.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userListFuzzyQuery")
    public List<User> userListFuzzyQuery(String queryStr){
        return userService.userListFuzzyQuery(queryStr);
    }

    @RequestMapping("/insert")
    public int insert(User user) throws Exception{
        return userService.insert(user);
    }

    @RequestMapping("/update")
    public int update(User user) throws Exception {
        return userService.update(user);
    }

    @RequestMapping("/delete")
    public int delete(User user){
        return userService.delete(user);
    }

    @RequestMapping("/userListFuzzyQueryByroleId")
    public List<User> userListFuzzyQueryByroleId(UserRoleVo userRoleVo){
        return userService.userListFuzzyQueryByroleId(userRoleVo);
    }

    @RequestMapping("/selectById")
    public User selectById(User user){
        return userService.selectById(user);
    }

    @RequestMapping("/userListFuzzyQueryWithOutRole")
    public List<User> userListFuzzyQueryWithOutRole(UserRoleVo userRoleVo){
        return userService.userListFuzzyQueryWithOutRole(userRoleVo);
    }
}

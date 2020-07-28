package com.mediot.ygb.mrqs.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseMapper<User> {

    List<User> userListFuzzyQuery(String queryStr);
    List<User> selectUserByUserName(String username);
    List<User> selectUserListByroleId(Long roleId);
}

package com.mediot.ygb.mrqs.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.pojo.User;
import com.mediot.ygb.mrqs.system.vo.UserRoleVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao extends BaseMapper<User> {

    List<User> userListFuzzyQuery(String queryStr);
    User selectByUserNameAndPassWord(Map<String,Object> querMap);
    List<User> selectUserListByroleId(Long roleId);
    List<User> userListFuzzyQueryWithOutRole(UserRoleVo userRoleVo);
}

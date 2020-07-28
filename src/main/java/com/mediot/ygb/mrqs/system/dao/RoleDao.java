package com.mediot.ygb.mrqs.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseMapper<Role> {
    List<Role> selectRoleListByUserId(Long userId);
}

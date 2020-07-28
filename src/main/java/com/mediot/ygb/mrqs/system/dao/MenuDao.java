package com.mediot.ygb.mrqs.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> selectMenuListByRoleId(Long roleId);
    List<Menu> selectMenuListByParentIdAndUserId(Map<String,Object> queryMap);
}

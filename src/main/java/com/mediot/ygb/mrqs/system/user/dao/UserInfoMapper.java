package com.mediot.ygb.mrqs.system.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.mediot.ygb.mrqs.system.user.entity.UserInfo;

import java.util.List;
import java.util.Map;


public interface UserInfoMapper extends BaseMapper<UserInfo> {
    List<UserInfo> selecUsertList(Map<String, Object> queryMap);

    /**
     * 分页查询
     * @param pager
     * @return
     */
    //List<UserInfo> findList(Pager pager);


    /**
     * 模糊匹配用户信息
     * @param userInfoDto
     * @return
     */
    //List<UserInfo> findList(@Param("userInfoDto") UserInfoDto userInfoDto);
}

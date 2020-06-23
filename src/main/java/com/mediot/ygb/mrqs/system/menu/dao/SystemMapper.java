package com.mediot.ygb.mrqs.system.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.mediot.ygb.mrqs.system.menu.entity.System;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统表 Mapper 接口
 * </p>
 *
 * @author
 * @since
 */
public interface SystemMapper extends BaseMapper<System> {

    /**
     * 查询系统列表
     * @param pager
     * @return
     */
    List<System> querySystemList(Map<String,Object> queryMap);

    /**
     * 查询当前系统编码的最大值
     * @return
     */
    String getSystemMaxCode();
}

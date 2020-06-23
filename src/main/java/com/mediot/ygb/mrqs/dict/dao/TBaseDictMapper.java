package com.mediot.ygb.mrqs.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.dict.entity.TBaseDict;

import java.util.List;
import java.util.Map;

public interface TBaseDictMapper extends BaseMapper<TBaseDict> {

    TBaseDict selectTBaseDictByMap(Map<String,Object> queryMap);

    List<TBaseDict> selectTBaseDictListByMap(Map<String,Object> queryMap);

    TBaseDict selectSexByDCode(String sex);

    TBaseDict selectPayWayByBC(String payWay);
}

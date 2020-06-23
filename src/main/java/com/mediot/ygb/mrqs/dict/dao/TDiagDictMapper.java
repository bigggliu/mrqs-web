package com.mediot.ygb.mrqs.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.dict.entity.TDiagDict;

import java.util.Map;

public interface TDiagDictMapper extends BaseMapper<TDiagDict> {

    TDiagDict selectTDiagDictByMap(Map<String,Object> queryMap);

}
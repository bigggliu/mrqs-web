package com.mediot.ygb.mrqs.dict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.dict.entity.TBase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TBaseMapper extends BaseMapper<TBase> {

    List<TBase> selectTBaseByMap(Map<String, Object> queryMap);
}

package com.mediot.ygb.mrqs.org.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;

import java.util.List;
import java.util.Map;

public interface TOrgsMapper extends BaseMapper<TOrgsEntity> {

    int batchUpdateOrgs(List<TOrgsEntity> existOrgList);

    int batchInsertOrgs(List<TOrgsEntity> insertList);

    TOrgsEntity selectPOInfoByOrgId(Map<String, Object> map);
}

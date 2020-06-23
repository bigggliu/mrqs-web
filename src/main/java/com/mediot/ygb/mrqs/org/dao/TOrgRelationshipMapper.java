package com.mediot.ygb.mrqs.org.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.org.entity.TOrgRelationship;

import java.util.List;
import java.util.Map;

public interface TOrgRelationshipMapper extends BaseMapper<TOrgRelationship> {

    List<TOrgRelationship> selectRefOrgList(Map<String, Object> queryMap);

    void batchInsertRefOrgs(List<TOrgRelationship> list);
}
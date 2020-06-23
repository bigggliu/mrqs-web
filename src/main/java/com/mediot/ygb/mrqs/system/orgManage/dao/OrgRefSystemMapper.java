package com.mediot.ygb.mrqs.system.orgManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.orgManage.entity.OrgRefSystem;

import java.util.List;

public interface OrgRefSystemMapper extends BaseMapper<OrgRefSystem> {

    void batchInsertRelation(List<OrgRefSystem> orgRefSystemList);
}

package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckOrg;

import java.util.List;
import java.util.Map;

public interface TCheckOrgMapper extends BaseMapper<TCheckOrg> {
    int batchInsertByMap(List<TCheckOrg> tCheckOrgList);

    List<TCheckOrg> selectisExistTCheckCol(List<TCheckOrg> list);

    int deleteBatchCheckColIds(List<String> collect);
}

package com.mediot.ygb.mrqs.index.indexInfoManage.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;

import java.util.List;
import java.util.Map;

public interface TFirstoutdiagTestingMapper extends BaseMapper<TFirstoutdiagTesting> {

    int batchInsertOutDiag(List<TFirstoutdiagTesting> tftList);

    void batchDelOutDiag(List<TFirstoutdiagTesting> tftList);

    int batchDelOutDiagByMap(Map<String, Object> queryMap);
}
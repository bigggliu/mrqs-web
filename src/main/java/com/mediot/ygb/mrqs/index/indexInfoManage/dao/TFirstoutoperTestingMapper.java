package com.mediot.ygb.mrqs.index.indexInfoManage.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstoutoperTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;

import java.util.List;
import java.util.Map;

public interface TFirstoutoperTestingMapper extends BaseMapper<TFirstoutoperTesting> {

    int batchInsertOutOper(List<TFirstoutoperTesting> TFTList);

    void batchDelOutOper(List<TFirstoutoperTesting> tftList);

    void batchDelOutOperByMap(Map<String, Object> queryMap);
}
package com.mediot.ygb.mrqs.analysis.medRecManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.entity.TDatacleanStandard;

import java.util.List;
import java.util.Map;

public interface TDatacleanStandardMapper extends BaseMapper<TDatacleanStandard> {

    List<TDatacleanStandard> selectListByMap(Map<String,Object> queryMap);

    List<TDatacleanStandard> selectBatchByDataCol(List<String> cellNameList);
}
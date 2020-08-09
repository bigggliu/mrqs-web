package com.mediot.ygb.mrqs.index.errorInfoManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorDetailEntity;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorEntity;
import com.mediot.ygb.mrqs.index.errorInfoManage.vo.ErrorDetailExportVo;
import com.mediot.ygb.mrqs.index.errorInfoManage.vo.TErrorDetailVo;

import java.util.List;
import java.util.Map;

public interface TErrorDetailMapper extends BaseMapper<TErrorDetailEntity> {

    List<TErrorDetailEntity> selecttErrorDetailList(Map<String, Object> queryMap);

    int batchInsertTErrorDetails(List<TErrorDetailEntity> tErrorDetails);

    List<TErrorDetailVo> selectPageListByBatchId(Map<String,Object> queryMap);

    List<ErrorDetailExportVo> selectErrorDetailExportVoList(Map<String, Object> queryMap);

    List<String> selectYearsByBatchId(String batchId);
}

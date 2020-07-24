package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.common.entity.vo.ErrorDetailVo;
import com.mediot.ygb.mrqs.common.entity.vo.ReportManageVo;

import java.util.List;
import java.util.Map;

public interface TCheckColMapper extends BaseMapper<TCheckCol> {

    List<TCheckCol> selectTCheckColList(Map<String, Object> queryMap);

    List<TCheckCol> selectTCheckColListWithOutQs(Map<String, Object> queryMap);

    List<TCheckCol> selectTCheckColsByOrgId(Map<String, Object> queryMap);

    List<ReportManageVo> getRptListByBatchId(Map<String,Object> queryMap);

    String getNumOfErrorFileds(Map<String, Object> queryMap);

    List<ErrorDetailVo> selectErrorDetailByYear(Map<String, Object> qm);

    String getNumOfCurrentYear(Map<String, Object> queryMap);

    String getErrorNumOfCurrentYear(Map<String, Object> queryMap);

    List<TCheckCol> selecAllCheckCol();
}

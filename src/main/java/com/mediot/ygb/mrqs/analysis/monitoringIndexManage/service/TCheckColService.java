package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service;


import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto.TCheckColDto;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.vo.TCheckColVo;
import com.mediot.ygb.mrqs.common.core.service.BaseService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface TCheckColService extends BaseService<TCheckCol> {
    Map<String, Object> queryTCheckColListPage(TCheckColDto tCheckColDto);

    int insertAndUpdateTCheckCol(TCheckColDto tCheckColDto);

    void exportTCheckColsExcelData(HttpServletResponse response, String tCheckColIds);

    TCheckColVo queryTCheckColListById(TCheckColDto tCheckColDto);
}

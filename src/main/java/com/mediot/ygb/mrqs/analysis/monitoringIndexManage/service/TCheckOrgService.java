package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service;


import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto.TCheckOrgDto;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckOrg;
import com.mediot.ygb.mrqs.common.core.service.BaseService;


import java.util.Map;

public interface TCheckOrgService extends BaseService<TCheckOrg> {
    Map<String, Object> queryTCheckOrgsByParam(TCheckOrgDto tCheckOrgDto);

    int setTCheckOrgRefTCheckCol(TCheckOrgDto tCheckOrgDto);

    int setTCheckOrgUnRefTCheckCol(TCheckOrgDto tCheckOrgDto);
}

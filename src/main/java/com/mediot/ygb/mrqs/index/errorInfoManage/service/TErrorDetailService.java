package com.mediot.ygb.mrqs.index.errorInfoManage.service;

import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.index.errorInfoManage.dto.TErrorDetailDto;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorDetailEntity;

import java.util.Map;

public interface TErrorDetailService extends BaseService<TErrorDetailEntity> {

    Map<String, Object> findtOrgByParam(TErrorDetailDto tErrorDetailDto);
}

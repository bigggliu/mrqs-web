package com.mediot.ygb.mrqs.dict.service;

import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.dict.dto.TBaseDto;
import com.mediot.ygb.mrqs.dict.entity.TBase;

import java.util.Map;

public interface TBaseService extends BaseService<TBase> {


    Map<String, Object> findTBasePageListByParam(TBaseDto tBaseDto);
}

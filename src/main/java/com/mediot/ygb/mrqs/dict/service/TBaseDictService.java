package com.mediot.ygb.mrqs.dict.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.dict.dto.TBaseDictDto;
import com.mediot.ygb.mrqs.dict.entity.TBaseDict;

import java.util.Map;

public interface TBaseDictService extends BaseService<TBaseDict> {

    Map<String, Object> queryTBaseDictPageByParam(TBaseDictDto tBaseDictDto);
}

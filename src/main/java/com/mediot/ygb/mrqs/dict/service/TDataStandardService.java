package com.mediot.ygb.mrqs.dict.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.dict.entity.TDataStandard;



public interface TDataStandardService extends BaseService<TDataStandard> {

    ResultUtil queryTDataList();
}

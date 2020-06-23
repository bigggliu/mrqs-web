package com.mediot.ygb.mrqs.dict.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.dict.entity.TDiagDict;
import com.mediot.ygb.mrqs.dict.entity.TOperationDict;

import java.util.List;

/**
 * <p>
 * 诊断字典表 服务类
 * </p>
 *
 *
 * @since
 */
public interface TOperationDictService extends BaseService<TOperationDict> {

    List<TDiagDict> findtOperDictList(String queryStr);
}

package com.mediot.ygb.mrqs.dict.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.dict.entity.TDiagDict;


import java.util.List;

/**
 * <p>
 * 诊断字典表 服务类
 * </p>
 *
 *
 * @since
 */
public interface TDiagDictService extends BaseService<TDiagDict> {

    List<TDiagDict> findtDiagDictList(String queryStr);
}

package com.mediot.ygb.mrqs.index.indexInfoManage.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.vo.TFirstPageTestingVo;

import java.util.List;
import java.util.Map;

public interface IndexInfoManageService extends BaseService<TFirstPageTesting> {
    TFirstPageTesting insertAndUpdate(TFirstPageTestingDto tFirstPageTestingDto);

    Map<String, Object> findIndexInfoByParam(TFirstPageTestingDto tFirstPageTestingDto);

    TFirstPageTestingVo findIndexInfoById(TFirstPageTestingDto tFirstPageTestingDto);

    Map<String,Object> findSelectionDict(String fqun);

    void update(TFirstPageTestingDto tFirstPageTestingDto);
}

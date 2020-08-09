package com.mediot.ygb.mrqs.index.indexInfoManage.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;

import java.util.List;

public interface OutOperService extends BaseService<TFirstoutoperTesting> {
    List<TFirstoutoperTesting> insertAndUpdateOutOper(TFirstPageTestingDto tFirstPageTestingDto);

    void update(TFirstPageTestingDto tFirstPageTestingDto);
}

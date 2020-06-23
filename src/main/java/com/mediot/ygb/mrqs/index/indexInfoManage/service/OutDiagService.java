package com.mediot.ygb.mrqs.index.indexInfoManage.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;

import java.util.List;

public interface OutDiagService extends BaseService<TFirstoutdiagTesting> {
    List<TFirstoutdiagTesting> insertAndUpdateOutDiag(TFirstPageTestingDto tFirstPageTestingDto);
}

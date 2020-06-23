package com.mediot.ygb.mrqs.org.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.org.dto.RefOrgsDto;
import com.mediot.ygb.mrqs.org.entity.TOrgRelationship;

import java.util.List;

public interface TOrgRelationShipService extends BaseService<TOrgRelationship> {

    List<TOrgRelationship> getOrgsByPid(RefOrgsDto refOrgsDto);

    void addRefOrgs(RefOrgsDto refOrgsDto);
}

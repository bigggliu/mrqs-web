package com.mediot.ygb.mrqs.org.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.mediot.ygb.mrqs.org.api.MrqsOrgsApi;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.org.service.TOrgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0",timeout = 10000,interfaceClass = MrqsOrgsApi.class)
public class OrgProvider implements MrqsOrgsApi {

    @Autowired
    TOrgsService tOrgsService;

    @Override
    public TOrgsEntity findMrqsOrgById(String orgId) {
        return tOrgsService.selectById(orgId);
    }
}

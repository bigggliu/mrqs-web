package com.mediot.ygb.mrqs.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.org.dao.TOrgRelationshipMapper;
import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;
import com.mediot.ygb.mrqs.org.dto.RefOrgsDto;
import com.mediot.ygb.mrqs.org.entity.TOrgRelationship;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.org.service.TOrgRelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class TOrgRetionShipServiceImpl extends BaseServiceImpl<TOrgRelationshipMapper, TOrgRelationship> implements TOrgRelationShipService {

    @Autowired
    private TOrgRelationshipMapper tOrgRelationshipMapper;

    @Autowired
    private TOrgsMapper tOrgsMapper;

    @Override
    public List<TOrgRelationship> getOrgsByPid(RefOrgsDto refOrgsDto) {
        LocalAssert.notBlank(refOrgsDto.getPid(), "上级机构id不能为空！");
        Map<String,Object> queryMap= Maps.newHashMap();
        queryMap.put("pid",refOrgsDto.getPid());
        return tOrgRelationshipMapper.selectRefOrgList(queryMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRefOrgs(RefOrgsDto refOrgsDto) {
        LocalAssert.notBlank(refOrgsDto.getPid(), "上级机构id不能为空！");
        LocalAssert.notBlank(refOrgsDto.getOrgIds(), "下级机构id不能为空！");
        String[] oidArr=refOrgsDto.getOrgIds().split(",");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("PID",refOrgsDto.getPid());
        queryWrapper.eq("ORG_ID",oidArr[0]);
        tOrgRelationshipMapper.delete(queryWrapper);
        List<TOrgRelationship> tOrgRelationships= Lists.newArrayList();
        for(int i=0;i<oidArr.length;i++){
            TOrgRelationship relationship=new TOrgRelationship();
            relationship.setPid(refOrgsDto.getPid());
            relationship.setOrgId(oidArr[i]);
            QueryWrapper qw=new QueryWrapper();
            qw.eq("ORG_ID",oidArr[i]);
            TOrgsEntity tOrgs=tOrgsMapper.selectOne(qw);
            relationship.setOrgName(tOrgs.getOrgName());
            tOrgRelationships.add(relationship);
        }
        tOrgRelationshipMapper.batchInsertRefOrgs(tOrgRelationships);
    }
}

package com.mediot.ygb.mrqs.index.indexInfoManage.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstoutoperTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.service.OutOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OutOperServiceImpl extends BaseServiceImpl<TFirstoutoperTestingMapper, TFirstoutoperTesting> implements OutOperService {

    @Autowired
    private TFirstoutoperTestingMapper tFirstoutoperTestingMapper;

    @Autowired
    private TFirstpageTestingMapper tFirstpageTestingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TFirstoutoperTesting> insertAndUpdateOutOper(TFirstPageTestingDto tFirstPageTestingDto) {
        TFirstPageTesting tFirstPageTesting= JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(tFirstPageTestingDto),TFirstPageTesting.class);
        if(tFirstPageTesting.getBatchId()==null){
            throw new ValidationException("批次id不能为空！");
        }
        if(tFirstPageTesting.getCaseId()==null){
            throw new ValidationException("病案号不能为空！");
        }
        if(tFirstPageTesting.getTFirstPageTestingId()==null){
            throw new ValidationException("基本信息主键不能为空！");
        }
        //这条记录必须存在
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("T_FIRST_PAGE_TESTING_ID",tFirstPageTesting.getTFirstPageTestingId());
        queryWrapper.eq("BATCH_ID",tFirstPageTesting.getBatchId());
        queryWrapper.eq("CASE_ID",tFirstPageTesting.getCaseId());
        if(tFirstpageTestingMapper.selectCount(queryWrapper)==0){
            throw new ValidationException("基本信息id与批次id或病案号不匹配！");
        }
        List<TFirstoutoperTestingDto> TFDList= JSONObject.parseArray(tFirstPageTestingDto.getOutOperList(),TFirstoutoperTestingDto.class);
        if(TFDList.size()==0){
            QueryWrapper qW=new QueryWrapper();
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("batchId",tFirstPageTesting.getBatchId());
            queryMap.put("caseId",tFirstPageTesting.getCaseId());
            tFirstoutoperTestingMapper.batchDelOutDiagByMap(queryMap);
            return null;
            //tFirstoutdiagTestingMapper.d
        }
        List<TFirstoutoperTesting> TFTList=TFDList.stream().map(e->{
            if(e.getOperationOrder()==null){
                throw new ValidationException("手术次序不能为空！");
            }
            e.setBatchId(tFirstPageTestingDto.getBatchId());
            e.setCaseId(tFirstPageTestingDto.getCaseId());
            return JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),TFirstoutoperTesting.class);
        }).collect(Collectors.toList());
        tFirstoutoperTestingMapper.batchDelOutOper(TFTList);
        tFirstoutoperTestingMapper.batchInsertOutOper(TFTList);
        QueryWrapper qw=new QueryWrapper();
        qw.eq("BATCH_ID",tFirstPageTesting.getBatchId());
        qw.eq("CASE_ID",tFirstPageTesting.getCaseId());
        return tFirstoutoperTestingMapper.selectList(qw);
    }
}

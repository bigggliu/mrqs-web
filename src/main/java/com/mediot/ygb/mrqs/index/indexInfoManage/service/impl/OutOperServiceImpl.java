package com.mediot.ygb.mrqs.index.indexInfoManage.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstoutoperTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.service.OutOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        if(tFirstPageTesting.getCaseNo()==null){
            throw new ValidationException("病案号不能为空！");
        }
        if(tFirstPageTesting.getTFirstPageTestingId()==null){
            throw new ValidationException("基本信息主键不能为空！");
        }
        //这条记录必须存在
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("T_FIRST_PAGE_TESTING_ID",tFirstPageTesting.getTFirstPageTestingId());
        queryWrapper.eq("BATCH_ID",tFirstPageTesting.getBatchId());
        queryWrapper.eq("CASE_NO",tFirstPageTesting.getCaseNo());
        if(tFirstpageTestingMapper.selectCount(queryWrapper)==0){
            throw new ValidationException("基本信息id与批次id或病案号不匹配！");
        }
        List<TFirstoutoperTestingDto> TFDList = new ArrayList<>();
        if(StringUtils.isNotBlank(tFirstPageTestingDto.getOutOperList())){
            TFDList = JSONObject.parseArray(tFirstPageTestingDto.getOutOperList(),TFirstoutoperTestingDto.class);
        }

        if(TFDList.size()==0){
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("batchId",tFirstPageTesting.getBatchId());
            queryMap.put("caseId",tFirstPageTesting.getCaseNo());
            tFirstoutoperTestingMapper.batchDelOutOperByMap(queryMap);
            return null;
        }
        List<TFirstoutoperTesting> TFTList=TFDList.stream().map(e->{
            if(e.getOperationOrder()==null){
                throw new ValidationException("手术次序不能为空！");
            }
            if(StringUtils.isBlank(e.getOperationType())){
               e.setOperationType(e.getOperationOrder() == 1 ? "01":"02");
            }
            return JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),TFirstoutoperTesting.class);
        }).collect(Collectors.toList());
        //tFirstoutoperTestingMapper.batchDelOutOper(TFTList);
        QueryWrapper<TFirstoutoperTesting> delqw = new QueryWrapper<>();
        delqw.eq("T_FIRST_PAGE_TESTING_ID",tFirstPageTesting.getTFirstPageTestingId());
        tFirstoutoperTestingMapper.delete(delqw);
        tFirstoutoperTestingMapper.batchInsertOutOper(TFTList);
        QueryWrapper qw=new QueryWrapper();
        qw.eq("BATCH_ID",tFirstPageTesting.getBatchId());
        qw.eq("CASE_ID",tFirstPageTesting.getCaseNo());
        return tFirstoutoperTestingMapper.selectList(qw);
    }

    @Override
    public void update(TFirstPageTestingDto tFirstPageTestingDto) {
        List<TFirstoutoperTesting> TFTList = new ArrayList<>();
        if(StringUtils.isNotBlank(tFirstPageTestingDto.getOutOperList())){
            TFTList = JSONObject.parseArray(tFirstPageTestingDto.getOutOperList(),TFirstoutoperTesting.class);
        }
        for(TFirstoutoperTesting tFirstoutoperTesting : TFTList){
            if(tFirstoutoperTesting.getOperationOrder()==null){
                throw new ValidationException("手术次序不能为空！");
            }
            if(StringUtils.isBlank(tFirstoutoperTesting.getOperationType())){
                tFirstoutoperTesting.setOperationType(tFirstoutoperTesting.getOperationOrder() == 1 ? "01":"02");
            }
            tFirstoutoperTestingMapper.updateById(tFirstoutoperTesting);
        }
    }
}

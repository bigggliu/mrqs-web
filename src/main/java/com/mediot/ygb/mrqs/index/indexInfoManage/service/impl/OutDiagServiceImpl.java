package com.mediot.ygb.mrqs.index.indexInfoManage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutdiagTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstoutdiagTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.service.OutDiagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OutDiagServiceImpl extends BaseServiceImpl<TFirstoutdiagTestingMapper, TFirstoutdiagTesting> implements OutDiagService {

    @Autowired
    TFirstoutdiagTestingMapper tFirstoutdiagTestingMapper;

    @Autowired
    private TFirstpageTestingMapper tFirstpageTestingMapper;

    @Override
    public List<TFirstoutdiagTesting> insertAndUpdateOutDiag(TFirstPageTestingDto tFirstPageTestingDto) {
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
        List<TFirstoutdiagTestingDto> TFTDList = new ArrayList<>();
        if(StringUtils.isNotBlank(tFirstPageTestingDto.getOutDiagList())){
            TFTDList = JSONObject.parseArray(tFirstPageTestingDto.getOutDiagList(),TFirstoutdiagTestingDto.class);
        }
        if(TFTDList.size()==0){
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("batchId",tFirstPageTesting.getBatchId());
            queryMap.put("caseId",tFirstPageTesting.getCaseNo());
            tFirstoutdiagTestingMapper.batchDelOutDiagByMap(queryMap);
            return null;
        }
        List<TFirstoutdiagTesting> TFTList=TFTDList.stream().map(e->{
            if(e.getDiagOrder()==null){
                throw new ValidationException("诊断次序不能为空！");
            }
            if(StringUtils.isBlank(e.getDiagType())){
                throw new ValidationException("诊断类型不能为空！");
            }
            return JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),TFirstoutdiagTesting.class);
        }).collect(Collectors.toList());
        //删除所有关联信息
        //tFirstoutdiagTestingMapper.batchDelOutDiag(TFTList);
        QueryWrapper<TFirstoutdiagTesting> delqw = new QueryWrapper<>();
        delqw.eq("T_FIRST_PAGE_TESTING_ID",tFirstPageTesting.getTFirstPageTestingId());
        tFirstoutdiagTestingMapper.delete(delqw);
        tFirstoutdiagTestingMapper.batchInsertOutDiag(TFTList);
        QueryWrapper qw=new QueryWrapper();
        qw.eq("BATCH_ID",tFirstPageTesting.getBatchId());
        qw.eq("CASE_ID",tFirstPageTesting.getCaseNo());
        return tFirstoutdiagTestingMapper.selectList(qw);
    }

    @Override
    public void update(TFirstPageTestingDto tFirstPageTestingDto) {
        List<TFirstoutdiagTesting> TFTList = new ArrayList<>();
        if(StringUtils.isNotBlank(tFirstPageTestingDto.getOutDiagList())){
            TFTList = JSONObject.parseArray(tFirstPageTestingDto.getOutDiagList(),TFirstoutdiagTesting.class);
        }
        for(TFirstoutdiagTesting tFirstoutdiagTesting : TFTList){
            if(tFirstoutdiagTesting.getDiagOrder()==null){
                throw new ValidationException("诊断次序不能为空！");
            }
            if(StringUtils.isBlank(tFirstoutdiagTesting.getDiagType())){
                throw new ValidationException("诊断类型不能为空！");
            }
            tFirstoutdiagTestingMapper.updateById(tFirstoutdiagTesting);
        }
    }
}

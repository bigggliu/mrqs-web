package com.mediot.ygb.mrqs.dict.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.dict.dao.TBaseMapper;
import com.mediot.ygb.mrqs.dict.dto.TBaseDto;
import com.mediot.ygb.mrqs.dict.entity.TBase;
import com.mediot.ygb.mrqs.dict.service.TBaseService;
import com.mediot.ygb.mrqs.dict.vo.TBaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TBaseServiceImpl extends BaseServiceImpl<TBaseMapper, TBase> implements TBaseService {

    @Autowired
    TBaseMapper tBaseMapper;

    @Override
    public Map<String, Object> findTBasePageListByParam(TBaseDto tBaseDto) {
        List<TBaseVo> tBaseVoList= Lists.newArrayList();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        //QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotBlank(tBaseDto.getBaseCode())){
            queryMap.put("baseCode",tBaseDto.getBaseCode());
        }
        if(StringUtils.isNotBlank(tBaseDto.getBaseName())){
            queryMap.put("baseName",tBaseDto.getBaseName());
        }
        List<TBase> tBaseList= tBaseMapper.selectTBaseByMap(queryMap);
        tBaseVoList=tBaseList.stream().map(
                e-> JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), TBaseVo.class)
        ).collect(Collectors.toList());
        if(tBaseDto.getPageNum()!=null&&tBaseDto.getPageSize()!=null){
            Page<TBaseVo> page=PageHelper.startPage(tBaseDto.getPageNum(),tBaseDto.getPageSize());
            PageInfo<TBaseVo> pageInfo = new PageInfo<>(tBaseVoList);
            pageInfo.setPages(page.getPages());//总页数
            pageInfo.setTotal(page.getTotal());//总条数
            jsonMap.put("data",tBaseVoList);//数据结果
            jsonMap.put("total", pageInfo.getTotal());//获取数据总数
            jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
            jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        }else {
            jsonMap.put("data",tBaseVoList);
        }
        return jsonMap;
    }
}

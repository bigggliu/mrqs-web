package com.mediot.ygb.mrqs.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.dict.dao.TBaseDictMapper;
import com.mediot.ygb.mrqs.dict.dto.TBaseDictDto;
import com.mediot.ygb.mrqs.dict.entity.TBaseDict;
import com.mediot.ygb.mrqs.dict.service.TBaseDictService;
import com.mediot.ygb.mrqs.dict.vo.TBaseDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TBaseDictServiceImpl extends BaseServiceImpl<TBaseDictMapper, TBaseDict> implements TBaseDictService {

    @Autowired
    TBaseDictMapper tBaseDictMapper;

    @Override
    public Map<String, Object> queryTBaseDictPageByParam(TBaseDictDto tBaseDictDto) {
        LocalAssert.notNull(tBaseDictDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(tBaseDictDto.getPageSize(),"pageSize不能为空");
        List<TBaseDictVo> tBaseDictVoList= Lists.newArrayList();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Page<TBaseDictVo> page=PageHelper.startPage(tBaseDictDto.getPageNum(),tBaseDictDto.getPageSize());
        List<TBaseDict> tBaseList;
        if(StringUtils.isNotEmpty(tBaseDictDto.getQueryStr())){
            QueryWrapper queryWrapper=new QueryWrapper();
            if(StringUtils.isNotBlank(tBaseDictDto.getQueryStr())){
                queryWrapper.like("BASE_CODE",tBaseDictDto.getQueryStr());
                queryWrapper.or();
                queryWrapper.like("DICT_NAME",tBaseDictDto.getQueryStr());
            }
            tBaseList= tBaseDictMapper.selectList(queryWrapper);
        }else {
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("BASE_CODE",tBaseDictDto.getBaseCode());
            tBaseList=tBaseDictMapper.selectList(queryWrapper);
        }
        tBaseDictVoList=tBaseList.stream().map(
                e-> JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), TBaseDictVo.class)
        ).collect(Collectors.toList());
        //Page<TBaseDictVo> page=PageHelper.startPage(tBaseDictDto.getPageNum(),  tBaseDictDto.getPageSize());
        //int total= (int) page.getTotal();
        PageInfo<TBaseDictVo> pageInfo = new PageInfo<>(tBaseDictVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",tBaseDictVoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }
}

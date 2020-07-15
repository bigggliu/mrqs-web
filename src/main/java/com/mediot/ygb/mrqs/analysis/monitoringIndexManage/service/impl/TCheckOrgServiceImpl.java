package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckColMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckOrgMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto.TCheckOrgDto;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckOrg;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service.TCheckOrgService;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.vo.TCheckColVo;

import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class TCheckOrgServiceImpl extends BaseServiceImpl<TCheckOrgMapper,TCheckOrg> implements TCheckOrgService {

    @Autowired
    TCheckOrgMapper tCheckOrgMapper;

    @Autowired
    TCheckColMapper tCheckColMapper;

    @Autowired
    TOrgsMapper tOrgsMapper;

    @Override
    public Map<String, Object> queryTCheckOrgsByParam(TCheckOrgDto tCheckOrgDto) {
        LocalAssert.notNull(tCheckOrgDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(tCheckOrgDto.getPageSize(),"pageSize不能为空");
        Map<String, Object> jsonMap= Maps.newHashMap();
        QueryWrapper<TCheckOrg> queryWrapper=new QueryWrapper<>();
        if(tCheckOrgDto.getOrgId()!=null){
            queryWrapper.eq("ORG_ID",tCheckOrgDto.getOrgId());
            if(StringUtils.isNotBlank(tCheckOrgDto.getQueryStr())){
                queryWrapper.and(wrapper->wrapper.like("COL_NAME",tCheckOrgDto.getQueryStr()).or().like("COL_COMMENTS",tCheckOrgDto.getQueryStr()));
            }
        }
        Page<TCheckOrg> page= PageHelper.startPage(tCheckOrgDto.getPageNum(),tCheckOrgDto.getPageSize());
        List<TCheckOrg> tCheckOrgList;
        if(tCheckOrgDto.getOrgId()!=null){
            tCheckOrgList=tCheckOrgMapper.selectList(queryWrapper);
        }else {
            List<TCheckCol> tCheckColList;
            if(StringUtils.isNotBlank(tCheckOrgDto.getQueryStr())){
                Map<String,Object> queryMap=Maps.newHashMap();
                queryMap.put("queryStr",tCheckOrgDto.getQueryStr());
                tCheckColList=tCheckColMapper.selectTCheckColList(queryMap);
            }else {
                tCheckColList=tCheckColMapper.selectTCheckColListWithOutQs();
            }
            tCheckOrgList=tCheckColList.stream().map(e->JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),TCheckOrg.class)).collect(Collectors.toList());
        }
        //List<TCheckOrg> tCheckOrgList=tCheckOrgMapper.selectList(queryWrapper);
        List<TCheckColVo> tCheckColVoList = tCheckOrgList.stream().map(e -> JsonUtil.
                getJsonToBean(JsonUtil.getBeanToJson(e), TCheckColVo.class)).
                collect(Collectors.toList());
        PageInfo<TCheckColVo> pageInfo=new PageInfo<>(tCheckColVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",tCheckColVoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

    @Override
    public int setTCheckOrgRefTCheckCol(TCheckOrgDto tCheckOrgDto) {
        LocalAssert.notNull(tCheckOrgDto.getOrgId(),"机构id不能为空");
        LocalAssert.notNull(tCheckOrgDto.getCheckColIds(),"检测指标ids不能为空");
        String[] tCheckColArr=tCheckOrgDto.getCheckColIds().split(",");
        QueryWrapper<TCheckCol> queryWrapper=new QueryWrapper<>();
        TOrgsEntity tOrgsEntity=tOrgsMapper.selectById(tCheckOrgDto.getOrgId());
        //去掉可能在数据库中存在的记录
        List<TCheckOrg> existTCheckOrgList= Lists.newArrayList();
        existTCheckOrgList=IntStream.range(0,tCheckColArr.length).mapToObj(i->{
            TCheckOrg tCheckOrg=new TCheckOrg();
            tCheckOrg.setCheckColId(Long.parseLong(tCheckColArr[i]));
            tCheckOrg.setOrgId(tCheckOrgDto.getOrgId());
            return tCheckOrg;
        }).collect(Collectors.toList());
        existTCheckOrgList= tCheckOrgMapper.selectisExistTCheckCol(existTCheckOrgList);
        if(existTCheckOrgList.size()==tCheckColArr.length){
            return 0;
        }
        if(existTCheckOrgList.size()==0){
            queryWrapper.in("CHECK_COL_ID",tCheckColArr);
            List<TCheckCol> tCheckColList=tCheckColMapper.selectList(queryWrapper);
            List<TCheckOrg> tCheckOrgList=tCheckColList.stream().map(e->JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),TCheckOrg.class)).collect(Collectors.toList());
            tCheckOrgList.stream().map(e->{e.setOrgId(tCheckOrgDto.getOrgId());e.setOrgName(tOrgsEntity.getOrgName());return e;}).collect(Collectors.toList());
            return tCheckOrgMapper.batchInsertByMap(tCheckOrgList);
        }
        List<String> existTCOIdArr=existTCheckOrgList.stream().map(e->String.valueOf(e.getCheckColId())).collect(Collectors.toList());
        List<String> finalArr=new ArrayList<>();
        if(existTCheckOrgList.size()>0){
            List<String> tCheckColArrList=Stream.of(tCheckColArr).collect(Collectors.toList());
            finalArr=tCheckColArrList.stream().filter(e->!existTCOIdArr.contains(e)).collect(Collectors.toList());
        }
        String[] filteredTCOArr=existTCheckOrgList.stream().map(e->String.valueOf(e.getCheckColId())).collect(Collectors.toList()).stream().toArray(String[]::new);
        queryWrapper.in("CHECK_COL_ID",finalArr);
        List<TCheckCol> tCheckColList=tCheckColMapper.selectList(queryWrapper);
        List<TCheckOrg> tCheckOrgList=tCheckColList.stream().map(e->JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),TCheckOrg.class)).collect(Collectors.toList());
        tCheckOrgList.stream().map(e->{e.setOrgId(tCheckOrgDto.getOrgId());e.setOrgName(tOrgsEntity.getOrgName());return e;}).collect(Collectors.toList());
        return tCheckOrgMapper.batchInsertByMap(tCheckOrgList);
    }

    @Override
    public int setTCheckOrgUnRefTCheckCol(TCheckOrgDto tCheckOrgDto) {
        LocalAssert.notNull(tCheckOrgDto.getOrgId(),"机构id不能为空");
        LocalAssert.notNull(tCheckOrgDto.getCheckColIds(),"检测指标ids不能为空");
        //ids是否存在
        String[] tCheckColArr=tCheckOrgDto.getCheckColIds().split(",");
        QueryWrapper<TCheckOrg> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("CHECK_COL_ID",tCheckColArr);
        queryWrapper.eq("ORG_ID",tCheckOrgDto.getOrgId());
        int refNum=tCheckOrgMapper.selectCount(queryWrapper);
        if(refNum!=tCheckColArr.length){
            throw  new ValidationException("存在该机构不匹配的监测指标！");
        }
        if(refNum==0){
            throw  new ValidationException("不能删除id不匹配的机构监测指标！");
        }
        return tCheckOrgMapper.deleteBatchCheckColIds(Stream.of(tCheckColArr).collect(Collectors.toList()));
    }

    @Override
    public int setAllTCheckOrgRefTCheckCol(TCheckOrgDto tCheckOrgDto) {
        List<TCheckCol> tCheckColList = tCheckColMapper.selecAllCheckCol();
        TOrgsEntity tOrgsEntity=tOrgsMapper.selectById(tCheckOrgDto.getOrgId());
        List<TCheckOrg> tCheckOrgList=tCheckColList.stream().map(e->JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),TCheckOrg.class)).collect(Collectors.toList());
        tCheckOrgList.stream().map(e->{e.setOrgId(tCheckOrgDto.getOrgId());e.setOrgName(tOrgsEntity.getOrgName());return e;}).collect(Collectors.toList());
        return tCheckOrgMapper.batchInsertByMap(tCheckOrgList);
    }


}

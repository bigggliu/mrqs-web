package com.mediot.ygb.mrqs.index.indexInfoManage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.common.util.WebUtils;
import com.mediot.ygb.mrqs.dict.dao.TBaseDictMapper;
import com.mediot.ygb.mrqs.dict.dao.TBaseMapper;
import com.mediot.ygb.mrqs.dict.dao.TDataStandardMapper;
import com.mediot.ygb.mrqs.dict.entity.TBase;
import com.mediot.ygb.mrqs.dict.entity.TBaseDict;
import com.mediot.ygb.mrqs.dict.entity.TDataStandard;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutdiagTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.service.IndexInfoManageService;
import com.mediot.ygb.mrqs.index.indexInfoManage.vo.TFirstPageTestingVo;

import com.mediot.ygb.mrqs.org.dao.TOrgRelationshipMapper;
import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.system.user.vo.UserInfoVo;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao.FileUploadMapper;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IndexInfoManageServiceImpl extends BaseServiceImpl<TFirstpageTestingMapper, TFirstPageTesting> implements IndexInfoManageService {

    @Autowired
    TFirstpageTestingMapper tFirstpageTestingMapper;

    @Autowired
    TFirstoutoperTestingMapper tFirstoutoperTestingMapper;

    @Autowired
    TFirstoutdiagTestingMapper tFirstoutdiagTestingMapper;

    @Autowired
    TDataStandardMapper tDataStandardMapper;

    @Autowired
    TBaseDictMapper tBaseDictMapper;

    @Autowired
    TBaseMapper tBaseMapper;

    @Autowired
    TOrgRelationshipMapper tOrgRelationshipMapper;

    @Autowired
    TOrgsMapper tOrgsMapper;

    @Autowired
    FileUploadMapper fileUploadMapper;

    @Override
    public TFirstPageTesting insertAndUpdate(TFirstPageTestingDto tFirstPageTestingDto) {
        if(tFirstPageTestingDto.getTFirstPageTestingId() == null){
            throw new ValidationException("基本信息id不能为空！");
        }
        if(StringUtils.isBlank(String.valueOf(tFirstPageTestingDto.getBatchId()))&&tFirstPageTestingDto.getBatchId()==null){
            throw new ValidationException("批次id不能为空！");
        }
        if(tFirstPageTestingDto.getCaseNo()==null){
            throw new ValidationException("病案号不能为空！");
        }
        if(StringUtils.isBlank(String.valueOf(tFirstPageTestingDto.getUpOrgId()))){
            throw new ValidationException("机构id不能为空！");
        }
        TFirstPageTesting tFirstPageTesting= JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(tFirstPageTestingDto),TFirstPageTesting.class);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("BATCH_ID",tFirstPageTesting.getBatchId());
        queryWrapper.eq("CASE_NO",tFirstPageTesting.getCaseNo());
        if(tFirstPageTestingDto.getTFirstPageTestingId()==null){
            int count=tFirstpageTestingMapper.selectCount(queryWrapper);
            if(count>0){
                throw new ValidationException("该批次下的病案号已经存在，不能重复添加！");
            }
            tFirstPageTesting.setCreateTime(new Date());
            tFirstpageTestingMapper.insert(tFirstPageTesting);
            return tFirstpageTestingMapper.selectOne(queryWrapper);
        }else {
            tFirstpageTestingMapper.updateById(tFirstPageTesting);
            return tFirstpageTestingMapper.selectOne(queryWrapper);
        }
    }

    @Override
    public Map<String, Object> findIndexInfoByParam(TFirstPageTestingDto tFirstPageTestingDto) {
        LocalAssert.notNull(tFirstPageTestingDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(tFirstPageTestingDto.getPageSize(),"pageSize不能为空");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("caseNo",tFirstPageTestingDto.getCaseNo());
        queryMap.put("fname",tFirstPageTestingDto.getFname());
        queryMap.put("diagnosisCode",tFirstPageTestingDto.getDiagnosisCode());
        queryMap.put("operationCode",tFirstPageTestingDto.getOperationCode());
        queryMap.put("outDeptName",tFirstPageTestingDto.getOutDeptName());
        UserInfoVo userInfoVo = WebUtils.getSessionAttribute(CustomConst.LoginUser.SESSION_USER_INFO);
        if(userInfoVo != null && StringUtils.isNotBlank(userInfoVo.getOrgId())){
            List<String> orgIds=Lists.newArrayList();
                if(StringUtils.isNotBlank(tFirstPageTestingDto.getOrgId())){
                    orgIds.add(tFirstPageTestingDto.getOrgId());
                }else {
                    orgIds.add(userInfoVo.getOrgId());
                    Map<String,Object> qm= Maps.newHashMap();
                    qm.put("pid",userInfoVo.getOrgId());
                    tOrgRelationshipMapper.selectRefOrgList(qm).stream().forEach(e->{
                        orgIds.add(e.getOrgId());
                    });
                }
                queryMap.put("orgIds",orgIds);
        }
        Page page= PageHelper.startPage(tFirstPageTestingDto.getPageNum(),tFirstPageTestingDto.getPageSize());
        List<TFirstPageTesting> tFirstPageTestingList=Lists.newArrayList();
        tFirstPageTestingList=tFirstpageTestingMapper.queryFirstPageList(queryMap);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<TFirstPageTestingVo> tFirstPageTestingVoList=Lists.newArrayList();
        tFirstPageTestingList.stream().forEach(e->{
            TFirstPageTestingVo tv=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), TFirstPageTestingVo.class);
            if(StringUtils.isNotBlank(e.getSexCode())){
                TBaseDict tb=tBaseDictMapper.selectSexByDCode(e.getSexCode());
                if(tb!=null){
                    tv.setSexCode(tb.getDictName());
                }else{
                    tv.setSexCode("-");
                }
            }
            if(StringUtils.isNotBlank(e.getPayWayCode())){
                TBaseDict _tb=tBaseDictMapper.selectPayWayByBC(e.getPayWayCode());
                if(_tb!=null){
                    tv.setPayWayCode(_tb.getDictName());
                }else{
                    tv.setPayWayCode("-");
                }
            }
            TOrgsEntity temp = new TOrgsEntity();
            temp.setOrgId(Long.valueOf(e.getUpOrgId()));
            TOrgsEntity org = tOrgsMapper.selectById(temp);
            if(org != null && StringUtils.isNotBlank(org.getOrgCode())){
                tv.setOrgCode(org.getOrgCode());
            }else {
                tv.setOrgCode("-");
            }
            tFirstPageTestingVoList.add(tv);
        });
        jsonMap.put("data",tFirstPageTestingVoList);//数据结果
        jsonMap.put("total", page.getTotal());//获取数据总数
        jsonMap.put("pageSize", page.getPageSize());//获取长度
        jsonMap.put("pageNum", page.getPageNum());//获取当前页数
        return jsonMap;
    }

    @Override
    public TFirstPageTestingVo findIndexInfoById(TFirstPageTestingDto tFirstPageTestingDto) {
        LocalAssert.notNull(tFirstPageTestingDto.getTFirstPageTestingId(),"id不能为空");
        TFirstPageTesting tFirstPageTesting=tFirstpageTestingMapper.selectById(tFirstPageTestingDto.getTFirstPageTestingId());
        TFirstPageTestingVo tFirstPageTestingVo=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(tFirstPageTesting), TFirstPageTestingVo.class);
        TOrgsEntity temp = new TOrgsEntity();
        temp.setOrgId(Long.valueOf(tFirstPageTesting.getUpOrgId()));
        TOrgsEntity org = tOrgsMapper.selectById(temp);
        if(org != null && StringUtils.isNotBlank(org.getOrgCode())){
            tFirstPageTestingVo.setOrgCode(org.getOrgCode());
        }else {
            tFirstPageTestingVo.setOrgCode("-");
        }
        QueryWrapper<FileUploadEntity> qw = new QueryWrapper<>();
        qw.eq("BATCH_ID",tFirstPageTesting.getBatchId());
        List<FileUploadEntity> list = fileUploadMapper.selectList(qw);
        if(list.size() > 0){
            tFirstPageTestingVo.setStandardCode(list.get(0).getStandardCode());
        }
        //插入诊断和手术信息
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("T_FIRST_PAGE_TESTING_ID",tFirstPageTestingVo.getTFirstPageTestingId());
        queryWrapper.eq("CASE_ID",tFirstPageTestingVo.getCaseNo());
        List<TFirstoutoperTesting> tFirstoutoperTestingList=tFirstoutoperTestingMapper.selectList(queryWrapper);
        List<TFirstoutdiagTesting> tFirstoutdiagTestingList=tFirstoutdiagTestingMapper.selectList(queryWrapper);
        tFirstPageTestingVo.setOutDiagList(tFirstoutdiagTestingList);
        tFirstPageTestingVo.setOutOperList(tFirstoutoperTestingList);
        return tFirstPageTestingVo;
    }

    @Override
    public Map<String,Object> findSelectionDict(String fqun) {
        Map<String,Object> resultMap= Maps.newHashMap();
        QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotBlank(fqun)){
            queryWrapper.eq("FQUN",fqun);
        }
        List<TDataStandard> tDataStandardList=tDataStandardMapper.selectList(queryWrapper);
        tDataStandardList.stream().forEach(e->{
            Map<String,Object> tBaseData=Maps.newHashMap();
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("baseCode",e.getStandardCode());
            List<TBase> tBaseList=tBaseMapper.selectTBaseByMap(queryMap);
            tBaseList.stream().forEach(tb->{
                QueryWrapper qw=new QueryWrapper();
                qw.eq("BASE_CODE",tb.getBaseCode());
                tBaseData.put(tb.getBaseName(), tBaseDictMapper.selectList(qw));
            });
            resultMap.put(e.getFQun(),tBaseData);
        });
        return resultMap;
    }

    @Override
    public void update(TFirstPageTestingDto tFirstPageTestingDto) {
        LocalAssert.notNull(tFirstPageTestingDto.getTFirstPageTestingId(),"基本信息id不能为空");
        TFirstPageTesting tFirstPageTesting= JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(tFirstPageTestingDto),TFirstPageTesting.class);
        tFirstpageTestingMapper.updateById(tFirstPageTesting);
    }
}

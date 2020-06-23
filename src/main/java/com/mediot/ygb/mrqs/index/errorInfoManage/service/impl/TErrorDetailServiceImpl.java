package com.mediot.ygb.mrqs.index.errorInfoManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;

import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorDetailMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.dto.TErrorDetailDto;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorDetailEntity;
import com.mediot.ygb.mrqs.index.errorInfoManage.service.TErrorDetailService;
import com.mediot.ygb.mrqs.index.errorInfoManage.vo.TErrorDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;



@Service
public class TErrorDetailServiceImpl extends BaseServiceImpl<TErrorDetailMapper,TErrorDetailEntity> implements TErrorDetailService {

    @Autowired
    TErrorDetailMapper tErrorDetailMapper;

    @Override
    public Map<String, Object> findtOrgByParam(TErrorDetailDto tErrorDetailDto) {
        LocalAssert.notNull(tErrorDetailDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(tErrorDetailDto.getPageSize(),"pageSize不能为空");
        LocalAssert.notNull(tErrorDetailDto.getBatchId(),"batchId不能为空");
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("batchId",tErrorDetailDto.getBatchId());
        int upperBound=tErrorDetailDto.getPageNum()*tErrorDetailDto.getPageSize();
        int lowerBound=(tErrorDetailDto.getPageNum()-1)*tErrorDetailDto.getPageSize()+1;
        queryMap.put("upperBound",upperBound);
        queryMap.put("lowerBound",lowerBound);
        List<TErrorDetailVo> tErrorDetailVos=tErrorDetailMapper.selectPageListByBatchId(queryMap);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("BATCH_ID",tErrorDetailDto.getBatchId());
        int total=tErrorDetailMapper.selectCount(queryWrapper);
        Map<String, Object> map=Maps.newHashMap();
        map.put("data",tErrorDetailVos);//数据结果
        map.put("total", total);//获取数据总数
        map.put("pageSize", tErrorDetailDto.getPageSize());//获取长度
        map.put("pageNum", tErrorDetailDto.getPageNum());//获取当前页数
        return map;
    }
}

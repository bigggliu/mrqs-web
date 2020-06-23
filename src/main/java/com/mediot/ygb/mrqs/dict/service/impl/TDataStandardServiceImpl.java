package com.mediot.ygb.mrqs.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.enumcase.ResultCodeEnum;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.dict.dao.TDataStandardMapper;
import com.mediot.ygb.mrqs.dict.entity.TDataStandard;
import com.mediot.ygb.mrqs.dict.service.TDataStandardService;
import com.mediot.ygb.mrqs.dict.vo.TDataStandardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TDataStandardServiceImpl extends BaseServiceImpl<TDataStandardMapper,TDataStandard> implements TDataStandardService {


    @Autowired
    TDataStandardMapper tDataStandardMapper;

    @Override
    public ResultUtil queryTDataList() {
        ResultUtil res=ResultUtil.build();
        QueryWrapper<TDataStandard> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("FSTATE",1);
        List<TDataStandard> resultList=tDataStandardMapper.selectList(queryWrapper);
        List<TDataStandardVo> voList=resultList.stream().map(e->
                JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), TDataStandardVo.class)
        ).collect(Collectors.toList());
        res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).data(voList).msg("操作成功");
        return res;
    }
}

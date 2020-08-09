package com.mediot.ygb.mrqs.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.dict.entity.TDiagDict;
import com.mediot.ygb.mrqs.dict.dao.TDiagDictMapper;
import com.mediot.ygb.mrqs.dict.service.TDiagDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 诊断字典表 服务实现类
 * </p>
 *
 *
 * @since
 */
@Service
public class TDiagDictServiceImpl extends BaseServiceImpl<TDiagDictMapper, TDiagDict> implements TDiagDictService {

    @Autowired
    private TDiagDictMapper tDiagDictMapper;

    @Override
    public List<TDiagDict> findtDiagDictList(String queryStr) {
        LocalAssert.notBlank(queryStr,"请输入关键字");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like("DICT_NAME",queryStr);
        return tDiagDictMapper.selectList(queryWrapper);
    }
}

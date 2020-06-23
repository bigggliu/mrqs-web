package com.mediot.ygb.mrqs.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.dict.dao.TOperationDictMapper;
import com.mediot.ygb.mrqs.dict.entity.TDiagDict;
import com.mediot.ygb.mrqs.dict.entity.TOperationDict;
import com.mediot.ygb.mrqs.dict.service.TOperationDictService;
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
public class TOperationDictServiceImpl extends BaseServiceImpl<TOperationDictMapper, TOperationDict> implements TOperationDictService {

    @Autowired
    private TOperationDictMapper tOperationDictMapper;

    @Override
    public List<TDiagDict> findtOperDictList(String queryStr) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.like("DICT_NAME",queryStr);
        return tOperationDictMapper.selectList(queryWrapper);
    }
}

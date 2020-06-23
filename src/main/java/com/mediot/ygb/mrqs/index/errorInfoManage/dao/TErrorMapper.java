package com.mediot.ygb.mrqs.index.errorInfoManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorEntity;

import java.util.List;

public interface TErrorMapper extends BaseMapper<TErrorEntity> {

    void batchDeleteTError(List<Long> filteredBIdsList);
}

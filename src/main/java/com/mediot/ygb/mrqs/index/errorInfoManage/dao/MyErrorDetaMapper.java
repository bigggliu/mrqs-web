package com.mediot.ygb.mrqs.index.errorInfoManage.dao;

import com.mediot.ygb.mrqs.index.errorInfoManage.entity.MyErrorDetaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MyErrorDetaMapper {

    int batchInsertTErrorDetails(List<MyErrorDetaEntity> myErrorDetaEntityList);
}

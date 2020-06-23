package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;

import java.util.List;
import java.util.Map;

public interface FileUploadMapper extends BaseMapper<FileUploadEntity> {
    FileUploadEntity selectFileUpload(Map<String, Object> queryMap);

    List<FileUploadEntity> selectListByParam(Map<String,Object> queryMap);

    void batchDeleteFile(List<Long> filteredIdsList);

    List<FileUploadEntity> selectFileUploadList(String[] fileIdsArr);

    FileUploadEntity selectFileUploadEntityByParam(Map<String, Object> queryMap);
}

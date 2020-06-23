package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dto.FileUploadDto;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;

import java.util.Map;

public interface FileUploadService extends BaseService<FileUploadEntity> {

    ResultUtil uploadFile(FileUploadDto fileUploadDto);

    ResultUtil quickUpload(FileUploadEntity fileUploadEntity);

    ResultUtil getProgressInfo(FileUploadDto fileUploadDto);

    //ResultUtil releaseUploadInfo(FileUploadDto fileUploadDto);

    ResultUtil syncUploadBlokState(FileUploadDto fileUploadDto);

    Map<String, Object> getUploadInfoByParam(FileUploadDto fileUploadDto);

    ResultUtil delUploadInfoByParam(FileUploadDto fileUploadDto);

    ResultUtil batchDelUploadInfoByParam(String fileIds);
}

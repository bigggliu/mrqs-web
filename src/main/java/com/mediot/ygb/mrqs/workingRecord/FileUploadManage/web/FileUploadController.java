package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.web;



import com.mediot.ygb.mrqs.common.util.ResultUtil;

import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dto.FileUploadDto;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/uploadFile")
@CrossOrigin
public class FileUploadController {


    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping("/uploadAllSizeFile")
    public ResultUtil uploadAllSizeFile(FileUploadDto fileUploadDto){
        return fileUploadService.uploadFile(fileUploadDto);
    }

    @RequestMapping("/getProgressInfo")
    public ResultUtil getProgressInfo(FileUploadDto fileUploadDto){
        return fileUploadService.getProgressInfo(fileUploadDto);
    }

    @RequestMapping("/syncUploadBlokState")
    public ResultUtil syncUploadBlokState(FileUploadDto fileUploadDto){
        return fileUploadService.syncUploadBlokState(fileUploadDto);
    }

    @RequestMapping({"/findUploadInfoByParam"})
    public Map<String, Object> findUploadInfoByParam(FileUploadDto fileUploadDto) {
        return fileUploadService.getUploadInfoByParam(fileUploadDto);
    }

    @RequestMapping({"/removeUploadInfo"})
    public ResultUtil removeUploadInfo(FileUploadDto fileUploadDto) {
        return fileUploadService.delUploadInfoByParam(fileUploadDto);
    }

    @RequestMapping({"/batchRemoveUploadInfo"})
    public ResultUtil batchRemoveUploadInfo(String fileIds) {
        return fileUploadService.batchDelUploadInfoByParam(fileIds);
    }
}

package com.mediot.ygb.mrqs.analysis.medRecManage.web;



import com.mediot.ygb.mrqs.analysis.medRecManage.dto.MedRecDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.service.MedRecordManageService;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
@RequestMapping("/medRecManager")
public class MedRecManageController {

    @Autowired
    MedRecordManageService medRecordManageService;

    @RequestMapping(value = {"/analyseMedRecords"},method = RequestMethod.POST)
    public ResultUtil analyseMedRecords(MedRecDto medRecDto) {
        return medRecordManageService.analyseMedRecords(medRecDto);
    }

    @RequestMapping(value = {"/cleanAndAddMedRecords"},method = RequestMethod.POST)
    public ResultUtil cleanAndAddMedRecords(MedRecDto medRecDto) {
        return medRecordManageService.cleanAndAddMedRecords(medRecDto);
    }

    @RequestMapping(value = {"/getProgressByFileId"},method = RequestMethod.POST)
    public ResultUtil getProgressByFileId(String fileIds) {
        return medRecordManageService.getProgressByFileId(fileIds);
    }

    @RequestMapping(value = {"/suspendThreads"},method = RequestMethod.POST)
    public ResultUtil suspendThreads(MedRecDto medRecDto) {
        return medRecordManageService.suspendThreadsByFileId(medRecDto);
    }

    @RequestMapping(value = {"/awakeThreads"},method = RequestMethod.POST)
    public ResultUtil awakeThreads(MedRecDto medRecDto) {
        return medRecordManageService.awakeThreadByFileId(medRecDto);
    }

    @RequestMapping(value = {"/cancelThreadByIds"},method = RequestMethod.POST)
    public ResultUtil cancelAllThread(MedRecDto medRecDto) {
        return medRecordManageService.cancelThreadByIds(medRecDto);
    }

    @RequestMapping(value = {"/delAllTempData"},method = RequestMethod.POST)
    public void delAllTempData(String fileId) {
        medRecordManageService.clearAllTempData(fileId);
    }

    @RequestMapping(value = {"/importDataOfDataClean"},method = RequestMethod.POST)
    public void  importDataOfDataClean(String path){
        medRecordManageService.importDataOfDataClean(path);
    }

    @RequestMapping(value = {"/exportErrorInfoByFileId"})
    public void  exportErrorInfoByFileId(HttpServletResponse response,String fileId,String fileName){
        medRecordManageService.exportErrorInfoByFileId(response,fileId,fileName);
    }

    @RequestMapping(value = {"/getDataCleanInfoByFileId"},method = RequestMethod.POST)
    public Map<String,Object> getDataCleanInfoByFileId(String fileId, int pageSize, int pageNum ){
        return medRecordManageService.getDataCleanInfoByFileId(fileId,pageSize,pageNum);
    }

    @RequestMapping(value = {"/getRightInfoByFileId"},method = RequestMethod.POST)
    public Map<String,Object> getRightInfoByFileId(String fileId, int pageSize, int pageNum ){
        return medRecordManageService.getRightInfoByFileId(fileId,pageSize,pageNum);
    }


}

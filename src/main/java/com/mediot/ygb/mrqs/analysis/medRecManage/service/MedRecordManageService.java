package com.mediot.ygb.mrqs.analysis.medRecManage.service;


import com.mediot.ygb.mrqs.analysis.medRecManage.dto.MedRecDto;
import com.mediot.ygb.mrqs.common.util.ResultUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public interface MedRecordManageService {

    ResultUtil analyseMedRecords(MedRecDto medRecDto);


    ResultUtil suspendThreadsByFileId(MedRecDto medRecDto);

    ResultUtil awakeThreadByFileId(MedRecDto medRecDto);

    ResultUtil getProgressByFileId(String fileId);

    ResultUtil cancelThreadByIds(MedRecDto medRecDto);

    void clearAllTempData(String fileId);

    void importDataOfDataClean(String path);

    ResultUtil cleanAndAddMedRecords(MedRecDto medRecDto);

    void exportErrorInfoByFileId(HttpServletResponse response,String fileId,String fileName);

    Map<String,Object> getDataCleanInfoByFileId(String fileId, int pageSize, int pageNum );

    Map<String,Object> getRightInfoByFileId(String fileId, int pageSize, int pageNum) ;

//    void batchInsertResult(String fileId);
}

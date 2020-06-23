package com.mediot.ygb.mrqs.analysis.reportManage.service;


import javax.servlet.http.HttpServletResponse;

public interface ReportManageService {
    void exportReportByFileId(HttpServletResponse response,String fileId);

    void mergeReportByFileIds(HttpServletResponse response,String fileIds);

    void exportDataOfCasesForStandardCode(HttpServletResponse response,String fileId, String standardCode);
}

package com.mediot.ygb.mrqs.analysis.reportManage.web;



import com.mediot.ygb.mrqs.analysis.reportManage.service.ReportManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/reportManage")
public class ReportManageController {

    @Autowired
    ReportManageService reportManageService;

    @RequestMapping(value = "exportReportByFileId",method = RequestMethod.GET)
    public void exportReportByFileId(HttpServletResponse response, String fileId){
        reportManageService.exportReportByFileId(response,fileId);
        //return tCheckOrgService.queryTCheckOrgsByParam(tCheckOrgDto);
    }

    @RequestMapping(value = "mergeReportByFileIds",method = RequestMethod.POST)
    public void mergeReportByFileIds(HttpServletResponse response,String fileIds){
        reportManageService.mergeReportByFileIds(response,fileIds);
    }

    @RequestMapping(value = "exportDataOfCasesForStandardCode",method = RequestMethod.GET)
    public void exportDataOfCasesForStandardCode(HttpServletResponse response,String fileId,String standardCode){
        reportManageService.exportDataOfCasesForStandardCode(response,fileId,standardCode);
    }
}

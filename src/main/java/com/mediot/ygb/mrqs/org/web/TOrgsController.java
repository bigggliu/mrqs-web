package com.mediot.ygb.mrqs.org.web;



import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.org.dto.OrgDto;
import com.mediot.ygb.mrqs.org.service.TOrgsService;
import com.mediot.ygb.mrqs.org.vo.OrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 诊断字典表 前端控制器
 * </p>
 *
 *
 * @since
 */
@RestController
@RequestMapping("/tOrgs")
public class TOrgsController {
    @Autowired
    TOrgsService tOrgsService;

    @RequestMapping({"findtOrgById"})
    public OrgVo queryOrgById(String orgId) {
        return tOrgsService.selectOrgsById(orgId);
    }

    @RequestMapping({"findtOrgsByParam"})
    public Map<String, Object> findtOrgByParam(OrgDto orgDto) {
        return tOrgsService.findtOrgByParam(orgDto);
    }

    @RequestMapping({"getTOrgsByParam"})
    public Map<String, Object> getTOrgByParam(OrgDto orgDto) {
        return tOrgsService.getTOrgsByParam(orgDto);
    }

    @RequestMapping(value = {"addAndModifyOrg"},method = RequestMethod.POST)
    public int addAndModifyOrg(@Valid OrgDto orgDto) {
        return tOrgsService.insertAndUpdateOrg(orgDto);
    }

    @RequestMapping(value = {"removeOrg"},method = RequestMethod.POST)
    public Boolean removeOrg(String orgId) {
        return tOrgsService.deleteOrgById(orgId);
    }

    @RequestMapping(value = {"batchRemoveOrgs"},method = RequestMethod.POST)
    public int batchRemoveOrgs(String orgIds) {
        return tOrgsService.batchDelOrgs(orgIds);
    }

    @RequestMapping(value = "exportTemplate")
    public void exportTemplate(HttpServletResponse response,String orgIds) throws IOException {
        tOrgsService.exportOrgExcelData(response,orgIds);
    }

    @RequestMapping(value = "exportOrgs",method = RequestMethod.GET)
    public void exportOrgs(HttpServletResponse response,String orgIds) throws IOException {
        tOrgsService.exportOrgExcelData(response,orgIds);
    }

    @RequestMapping(value = "importOrgs")
    public ResultUtil importOrgs(@RequestParam(value = "uploadFile") MultipartFile uploadFile){
        return tOrgsService.importOrgsFromExcel(uploadFile);
    }

}

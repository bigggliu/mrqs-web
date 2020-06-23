package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.web;



import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto.TCheckOrgDto;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service.TCheckOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tCheckOrg")
public class TCheckOrgController {

    @Autowired
    TCheckOrgService tCheckOrgService;

    @RequestMapping("findTCheckOrgsByParam")
    public Map<String, Object> getTCheckOrgsByParam(TCheckOrgDto tCheckOrgDto){
        return tCheckOrgService.queryTCheckOrgsByParam(tCheckOrgDto);
    }

    @RequestMapping("applyTCheckOrgRefTCheckCol")
    public int applyTCheckOrgRefTCheckCol(TCheckOrgDto tCheckOrgDto){
        return tCheckOrgService.setTCheckOrgRefTCheckCol(tCheckOrgDto);
    }

    @RequestMapping("cancelTCheckOrgRefTCheckCol")
    public int cancelTCheckOrgRefTCheckCol(TCheckOrgDto tCheckOrgDto){
        return tCheckOrgService.setTCheckOrgUnRefTCheckCol(tCheckOrgDto);
    }

}

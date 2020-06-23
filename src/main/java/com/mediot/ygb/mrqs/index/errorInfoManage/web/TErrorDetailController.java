package com.mediot.ygb.mrqs.index.errorInfoManage.web;


import com.mediot.ygb.mrqs.index.errorInfoManage.dto.TErrorDetailDto;
import com.mediot.ygb.mrqs.index.errorInfoManage.service.TErrorDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tErrorDetail")
public class TErrorDetailController {

    @Autowired
    TErrorDetailService tErrorDetailService;

    @RequestMapping({"findtErrorDetailByParam"})
    public Map<String, Object> findtOrgByParam(TErrorDetailDto tErrorDetailDto) {
        return tErrorDetailService.findtOrgByParam(tErrorDetailDto);
    }


}

package com.mediot.ygb.mrqs.dict.web;


import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.dict.service.TDataStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tDataStandard")
public class TDataStandardController  {

    @Autowired
    TDataStandardService tDataStandardService;

    @RequestMapping({"findTDataStandardList"})
    public ResultUtil findTDataStandardList() {
        return tDataStandardService.queryTDataList();
    }
}

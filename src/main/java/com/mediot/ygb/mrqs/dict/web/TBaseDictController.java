package com.mediot.ygb.mrqs.dict.web;



import com.mediot.ygb.mrqs.dict.dto.TBaseDictDto;
import com.mediot.ygb.mrqs.dict.service.TBaseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tBaseDict")
public class TBaseDictController {

    @Autowired
    TBaseDictService tBaseDictService;

    @RequestMapping({"findTBaseDictPageByParam"})
    public Map<String, Object> findTBaseDictPageByParam(TBaseDictDto tBaseDictDto) {
        return tBaseDictService.queryTBaseDictPageByParam(tBaseDictDto);
    }
}

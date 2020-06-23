package com.mediot.ygb.mrqs.dict.web;



import com.mediot.ygb.mrqs.dict.dto.TBaseDto;
import com.mediot.ygb.mrqs.dict.service.TBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tBase")
public class TBaseController {
    @Autowired
    TBaseService tBaseService;

    @RequestMapping("/getTBasePageList")
    public Map<String, Object> getTBasePageList(TBaseDto tbaseDto){
        return tBaseService.findTBasePageListByParam(tbaseDto);
    }
}

package com.mediot.ygb.mrqs.dict.web;



import com.mediot.ygb.mrqs.dict.entity.TDiagDict;
import com.mediot.ygb.mrqs.dict.service.TDiagDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 诊断字典表 前端控制器
 * </p>
 *
 *
 * @since
 */
@RestController
@RequestMapping("/tDiagDict")
public class TDiagDictController {
    @Autowired
    TDiagDictService tDiagDictService;

    @RequestMapping({"queryListByParam"})
    public List<TDiagDict> findtOperDictList(String queryStr) {
        return tDiagDictService.findtDiagDictList(queryStr);
    }
}

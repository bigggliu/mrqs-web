package com.mediot.ygb.mrqs.index.indexInfoManage.web;


import com.mediot.ygb.mrqs.index.indexInfoManage.dto.TFirstPageTestingDto;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.service.IndexInfoManageService;
import com.mediot.ygb.mrqs.index.indexInfoManage.service.OutDiagService;
import com.mediot.ygb.mrqs.index.indexInfoManage.service.OutOperService;
import com.mediot.ygb.mrqs.index.indexInfoManage.vo.TFirstPageTestingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/indexInfoManage")
public class IndexInfoManageController {

    @Autowired
    private IndexInfoManageService indexInfoManageService;

    @Autowired
    private OutDiagService outDiagService;

    @Autowired
    private OutOperService outOperService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping({"addAndModifyIndexInfo"})
    public TFirstPageTesting addAndModifyIndexInfo(TFirstPageTestingDto tFirstPageTestingDto) {
        return indexInfoManageService.insertAndUpdate(tFirstPageTestingDto);
    }

    @RequestMapping(value = {"addAndModifyOutOper"},method = RequestMethod.POST)
    public List<TFirstoutoperTesting> addAndModifyOutOper(TFirstPageTestingDto tFirstPageTestingDto) {
        return outOperService.insertAndUpdateOutOper(tFirstPageTestingDto);
    }

    @RequestMapping(value = {"addAndModifyOutDiag"},method = RequestMethod.POST)
    public List<TFirstoutdiagTesting> addAndModifyOutDiag(TFirstPageTestingDto tFirstPageTestingDto) {
        return outDiagService.insertAndUpdateOutDiag(tFirstPageTestingDto);
    }

    @RequestMapping({"findIndexInfoByParam"})
    public Map<String,Object> findIndexInfoByParam(TFirstPageTestingDto tFirstPageTestingDto) {
        return indexInfoManageService.findIndexInfoByParam(tFirstPageTestingDto);
    }

    @RequestMapping("findIndexInfoById")
    public TFirstPageTestingVo findIndexInfoById(TFirstPageTestingDto tFirstPageTestingDto){
        return indexInfoManageService.findIndexInfoById(tFirstPageTestingDto);
    }

    @RequestMapping("/findSelectionDict")
    public  Map<String,Object> findSelectionDict(String fqun){
        return indexInfoManageService.findSelectionDict(fqun);
    }

}

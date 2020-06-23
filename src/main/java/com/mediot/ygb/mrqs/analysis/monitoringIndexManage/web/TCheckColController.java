package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.web;



import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto.TCheckColDto;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service.TCheckColService;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.vo.TCheckColVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/tCheckCol")
public class TCheckColController {

    @Autowired
    TCheckColService tCheckColService;

    @RequestMapping("/findTCheckColById")
    public TCheckColVo getTCheckColById(TCheckColDto tCheckColDto){
        return  tCheckColService.queryTCheckColListById(tCheckColDto);
    }

    @RequestMapping("/findTCheckColPageListByParam")
    public Map<String,Object> findTCheckColPageListByParam(TCheckColDto tCheckColDto){
        return  tCheckColService.queryTCheckColListPage(tCheckColDto);
    }

    @RequestMapping("/addAndMOdifyTCheckCol")
    public int addAndModifyTCheckCol(TCheckColDto tCheckColDto){
        return  tCheckColService.insertAndUpdateTCheckCol(tCheckColDto);
    }

    @RequestMapping(value = "exportTCheckCols")
    public void exportTCheckCols(HttpServletResponse response, String checkColIds) throws IOException {
        tCheckColService.exportTCheckColsExcelData(response,checkColIds);
    }
}

package com.mediot.ygb.mrqs.index.errorInfoManage.web;


import com.mediot.ygb.mrqs.index.errorInfoManage.dto.TErrorDetailDto;
import com.mediot.ygb.mrqs.index.errorInfoManage.service.TErrorDetailService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

    @RequestMapping("/exportErrorDetail")
    public void exportErrorDetail(String batchId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> map = tErrorDetailService.exportErrorDetail(batchId);
        this.setResponseHeader(response, (String) map.get("fileName") + ".xls");
        OutputStream os = response.getOutputStream();
        HSSFWorkbook wb = (HSSFWorkbook)map.get("wb");
        wb.write(os);
        os.flush();
        os.close();
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

package com.mediot.ygb.mrqs.index.errorInfoManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;

import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorDetailMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.dto.TErrorDetailDto;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorDetailEntity;
import com.mediot.ygb.mrqs.index.errorInfoManage.service.TErrorDetailService;
import com.mediot.ygb.mrqs.index.errorInfoManage.vo.ErrorDetailExportVo;
import com.mediot.ygb.mrqs.index.errorInfoManage.vo.TErrorDetailVo;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao.FileUploadMapper;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class TErrorDetailServiceImpl extends BaseServiceImpl<TErrorDetailMapper,TErrorDetailEntity> implements TErrorDetailService {

    @Autowired
    TErrorDetailMapper tErrorDetailMapper;
    @Autowired
    FileUploadMapper fileUploadMapper;

    @Override
    public Map<String, Object> findtOrgByParam(TErrorDetailDto tErrorDetailDto) {
        LocalAssert.notNull(tErrorDetailDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(tErrorDetailDto.getPageSize(),"pageSize不能为空");
        LocalAssert.notNull(tErrorDetailDto.getBatchId(),"batchId不能为空");
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("batchId",tErrorDetailDto.getBatchId());
        int upperBound=tErrorDetailDto.getPageNum()*tErrorDetailDto.getPageSize();
        int lowerBound=(tErrorDetailDto.getPageNum()-1)*tErrorDetailDto.getPageSize()+1;
        queryMap.put("upperBound",upperBound);
        queryMap.put("lowerBound",lowerBound);
        List<TErrorDetailVo> tErrorDetailVos=tErrorDetailMapper.selectPageListByBatchId(queryMap);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("BATCH_ID",tErrorDetailDto.getBatchId());
        int total=tErrorDetailMapper.selectCount(queryWrapper);
        Map<String, Object> map=Maps.newHashMap();
        map.put("data",tErrorDetailVos);//数据结果
        map.put("total", total);//获取数据总数
        map.put("pageSize", tErrorDetailDto.getPageSize());//获取长度
        map.put("pageNum", tErrorDetailDto.getPageNum());//获取当前页数
        return map;
    }

    @Override
    public Map<String,Object> exportErrorDetail(String batchId) throws Exception {
        QueryWrapper<FileUploadEntity> qw = new QueryWrapper<>();
        qw.eq("BATCH_ID",batchId);
        FileUploadEntity fileUploadEntity = fileUploadMapper.selectOne(qw);
        if(fileUploadEntity == null || StringUtils.isEmpty(fileUploadEntity.getFileName())){
           throw new Exception("文件不存在");
        }
        if(StringUtils.isNotBlank(fileUploadEntity.getState()) && !fileUploadEntity.getState().equals("7")){
            throw new Exception("文件未成功检测");
        }
        List<String> years = tErrorDetailMapper.selectYearsByBatchId(batchId);
        Map<String,List<ErrorDetailExportVo>> dataMap = new HashMap<>();
        for(String year : years){
            Map<String,Object> queryMap = new HashMap<>();
            queryMap.put("batchId",batchId);
            queryMap.put("year",year);
            List<ErrorDetailExportVo> errorDetailExportVoList = tErrorDetailMapper.selectErrorDetailExportVoList(queryMap);
            dataMap.put(year,errorDetailExportVoList);
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        String[] title = {"出院科别名称","编码员","病案号","住院次数","姓名","性别","入院时间","出院时间","出院科别","出院病房","主治医师","住院医师","住院总费用","疑似问题"};
        for(Map.Entry<String,List<ErrorDetailExportVo>> entry : dataMap.entrySet()){
            HSSFSheet sheet = wb.createSheet(fileUploadEntity.getFileName().substring(0,fileUploadEntity.getFileName().lastIndexOf(".")) + entry.getKey() + "01-" + entry.getKey() + "12检测结果");
            HSSFRow row = sheet.createRow(0);
            //声明列对象
            HSSFCell cell = null;
            //创建标题
            for(int i=0;i<title.length;i++){
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
            }
            //创建内容
            for(int i=0;i<entry.getValue().size();i++){
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(entry.getValue().get(i).getOutDeptName());
                row.createCell(1).setCellValue(entry.getValue().get(i).getCataloger());
                row.createCell(2).setCellValue(entry.getValue().get(i).getCaseNo());
                row.createCell(3).setCellValue(entry.getValue().get(i).getInHospitalTimes());
                row.createCell(4).setCellValue(entry.getValue().get(i).getFname());
                row.createCell(5).setCellValue(entry.getValue().get(i).getSexCode());
                row.createCell(6).setCellValue(entry.getValue().get(i).getInDtime());
                row.createCell(7).setCellValue(entry.getValue().get(i).getOutDtime());
                row.createCell(8).setCellValue(entry.getValue().get(i).getOutDeptCode());
                row.createCell(9).setCellValue(entry.getValue().get(i).getOutDeptRoom());
                row.createCell(10).setCellValue(entry.getValue().get(i).getInChargeDoctor());
                row.createCell(11).setCellValue(entry.getValue().get(i).getResidentDoctor());
                row.createCell(12).setCellValue(String.valueOf(entry.getValue().get(i).getFeeTotal()));
                row.createCell(13).setCellValue(entry.getValue().get(i).getErrorInfos());
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("fileName",fileUploadEntity.getFileName().substring(0,fileUploadEntity.getFileName().lastIndexOf(".")) + "检测结果");
        map.put("wb",wb);
        return map;
    }
}

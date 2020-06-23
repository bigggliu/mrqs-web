package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;




import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckColMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto.TCheckColDto;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto.TCheckColExcelDto;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.enumcase.AvailableEnum;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.enumcase.ColTypeEnum;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.enumcase.RuleTypeEnum;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.service.TCheckColService;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.vo.TCheckColVo;
import com.mediot.ygb.mrqs.common.core.exception.MediotException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.excel.ExportExcel;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class TCheckColServiceImpl extends BaseServiceImpl<TCheckColMapper, TCheckCol> implements TCheckColService {

    @Autowired
    TCheckColMapper tCheckColMapper;

    @Override
    public Map<String, Object> queryTCheckColListPage(TCheckColDto tCheckColDto) {
        LocalAssert.notNull(tCheckColDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(tCheckColDto.getPageSize(),"pageSize不能为空");
        Map<String, Object> jsonMap= Maps.newHashMap();
        Map<String, Object> queryMap= Maps.newHashMap();
        if(StringUtils.isNotBlank(tCheckColDto.getQueryStr())){
            queryMap.put("queryStr",tCheckColDto.getQueryStr());
        }
        Page<TCheckCol> page=PageHelper.startPage(tCheckColDto.getPageNum(),tCheckColDto.getPageSize());
        List<TCheckCol> tCheckColList;
        if(StringUtils.isNotBlank(tCheckColDto.getQueryStr())){
            tCheckColList=tCheckColMapper.selectTCheckColList(queryMap);
        }else {
            tCheckColList=tCheckColMapper.selectTCheckColListWithOutQs();
        }
        List<TCheckColVo> tCheckColVoVoList = tCheckColList.stream().map(e -> JsonUtil.
                getJsonToBean(JsonUtil.getBeanToJson(e), TCheckColVo.class)).
                collect(Collectors.toList());
        PageInfo<TCheckColVo> pageInfo=new PageInfo<>(tCheckColVoVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",tCheckColList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

    public void validateFiled(TCheckColDto tCheckColDto){
        if(tCheckColDto.getCheckColId()==null){
            LocalAssert.notNull(tCheckColDto.getColName(),"指标名称不能为空");
            LocalAssert.notNull(tCheckColDto.getColType(),"指标分类不能为空");
            LocalAssert.notNull(tCheckColDto.getRuleType(),"验证逻辑不能为空");
            LocalAssert.notNull(tCheckColDto.getCmisChoice(),"是否可用不能为空");
            LocalAssert.notNull(tCheckColDto.getEmrChoice(),"是否邮箱验证不能为空");
            LocalAssert.notNull(tCheckColDto.getRuleDescription(),"指标描述不能为空");
            //LocalAssert.notNull(tCheckColDto.getRuleType(),"验证逻辑不能为空");
            //LocalAssert.notNull(tCheckColDto.getColComments(),"错误提示不能为空");
        }
    }

    @Override
    public int insertAndUpdateTCheckCol(TCheckColDto tCheckColDto) {
        TCheckCol tCheckCol=new TCheckCol();
        validateFiled(tCheckColDto);
        tCheckCol=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(tCheckColDto), TCheckCol.class);
        if(tCheckColDto.getCheckColId()==null){
            return tCheckColMapper.insert(tCheckCol);
        }else {
            return tCheckColMapper.updateById(tCheckCol);
        }
    }

    @Override
    public void exportTCheckColsExcelData(HttpServletResponse response, String checkColIds) {
        String fileName;
        StringBuffer stringBuffer = new StringBuffer();
        List<TCheckColExcelDto> tCheckColExcelDtoList=Lists.newArrayList();
        if(StringUtils.isEmpty(checkColIds)){
            fileName = "检测指标导入模板.xlsx";
            stringBuffer.append("注意事项:");
            // "1.组件名称、型号、规格、单位(最小)，必填。\n" +
            // "2.产品材质、骨科产品属性、包装材质、条形码、REF，非必填。";
        }else {
            fileName = "检测指标列表数据.xlsx";
            String ids[]=checkColIds.split(",");
            List<String> tCheckColIdsList= Stream.of(ids).collect(Collectors.toList());
            List<TCheckCol> tCheckColList=tCheckColMapper.selectBatchIds(tCheckColIdsList);
            if(tCheckColList.size()==0){
                throw new MediotException("不存在这些检测指标！");
            }
            tCheckColExcelDtoList=tCheckColList.stream().map(
                    e->{
                        TCheckColExcelDto tCheckColExcelDto=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), TCheckColExcelDto.class);
                        tCheckColExcelDto.setCmisChoice(AvailableEnum.getName(tCheckColExcelDto.getCmisChoice()));
                        tCheckColExcelDto.setEmrChoice(AvailableEnum.getName(tCheckColExcelDto.getEmrChoice()));
                        tCheckColExcelDto.setColType(ColTypeEnum.colTypeLabel(tCheckColExcelDto.getColType()));
                        tCheckColExcelDto.setRuleType(RuleTypeEnum.getRuleTypeLabel(tCheckColExcelDto.getRuleType()));
                        return tCheckColExcelDto;
                    }
            ).collect(Collectors.toList());
        }
        try {
            new ExportExcel(stringBuffer.toString(), TCheckColDto.class, 2).setDataList(tCheckColExcelDtoList).write(response, fileName).dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TCheckColVo queryTCheckColListById(TCheckColDto tCheckColDto) {
        LocalAssert.notNull(tCheckColDto.getCheckColId(),"id不能为空");
        TCheckCol tCheckCol=tCheckColMapper.selectById(tCheckColDto.getCheckColId());
        TCheckColVo tCheckColVo=new TCheckColVo();
        BeanUtils.copyProperties(tCheckCol,tCheckColVo);
        return tCheckColVo;
    }

}

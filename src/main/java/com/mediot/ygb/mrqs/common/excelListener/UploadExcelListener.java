package com.mediot.ygb.mrqs.common.excelListener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.common.enumcase.ResultCodeEnum;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;
import com.mediot.ygb.mrqs.org.dto.OrgExcelDto;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.org.service.impl.TOrgsServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class UploadExcelListener extends AnalysisEventListener<OrgExcelDto> {

    private TOrgsMapper tOrgsMapper;

    private ResultUtil res;

    private TOrgsServiceImpl tOrgsService;

    List<OrgExcelDto> totalExcelColumnsList= Lists.newArrayList();

    public UploadExcelListener(TOrgsMapper tOrgsMapper, ResultUtil res,TOrgsServiceImpl tOrgsService) {
        this.tOrgsMapper=tOrgsMapper;
        this.res=res;
        this.tOrgsService=tOrgsService;
    }


    @Override
    public void invoke(OrgExcelDto orgExcelDto, AnalysisContext analysisContext) {
        totalExcelColumnsList.add(orgExcelDto);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        List<TOrgsEntity> parseTeList = totalExcelColumnsList.stream().map(e -> JsonUtil.getJsonToBean(
                JsonUtil.getBeanToJson(e), TOrgsEntity.class)
        ).collect(Collectors.toList());
        res= tOrgsService.validateExcelColumns(parseTeList,res);
        List<String> orgNamesList=Lists.newArrayList();
        //是否有重名机构
        List<TOrgsEntity> filteredExcelColumnsList= parseTeList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(TOrgsEntity::getOrgName))), ArrayList::new)
        );
        orgNamesList=filteredExcelColumnsList.stream().map(org->org.getOrgName()).collect(Collectors.toList());
        QueryWrapper<TOrgsEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("ORG_NAME",orgNamesList);
        List<TOrgsEntity> existOrgList=tOrgsMapper.selectList(queryWrapper);
        int updateNum=0;
        int insertNum=0;
        if(existOrgList.size()>0){
            updateNum=tOrgsMapper.batchUpdateOrgs(existOrgList);
        }else {
            List<TOrgsEntity> insertList=filteredExcelColumnsList.stream().filter(item->!filteredExcelColumnsList.contains(existOrgList)).collect(Collectors.toList());
            insertNum=tOrgsMapper.batchInsertOrgs(insertList);
        }
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("插入"+insertNum+"条").append("更新"+updateNum+"条").append("重复"+(filteredExcelColumnsList.size()-insertNum-updateNum)+"条");
        res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg(stringBuffer.toString()).data(null);
    }
}

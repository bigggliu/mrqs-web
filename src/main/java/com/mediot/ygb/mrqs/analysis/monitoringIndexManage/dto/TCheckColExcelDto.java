package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto;


import com.mediot.ygb.mrqs.common.excel.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;

@Data
public class TCheckColExcelDto implements Serializable {

    private static long serialVersionUID= -8795032730405638134L;

    @ExcelField(title="指标名称", align=2, sort=3)
    private String colName;


    @ExcelField(title="指标分类", align=2, sort=3)
    private String colType;

    @ExcelField(title="指标描述", align=2, sort=3)
    private String ruleDescription;

    @ExcelField(title="错误提示", align=2, sort=3)
    private String colComments;

    private String fQun;

    @ExcelField(title="是否可用", align=2, sort=3)
    private String cmisChoice;

    @ExcelField(title="是否电子病历验证", align=2, sort=3)
    private String emrChoice;

    private String fSort;

    private String qualityClass;

    private String gradingLevel;

    private String informationClass;

    @ExcelField(title="验证逻辑", align=2, sort=1)
    private String ruleType;

    private String score;

    private String mustFill;

    private String hqmsCol;

    private String weiTongCol;


    private String verificationLogic;

    private String tableName;

    @ExcelField(title="应用医疗机构", align=2, sort=3)
    private String tCheckOrgVoList;
}

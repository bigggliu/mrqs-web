package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto;



import com.mediot.ygb.mrqs.common.excel.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;

@Data
public class TCheckColDto implements Serializable {

    private static long serialVersionUID= -5673694285121877440L;

    private Long checkColId;

    @ExcelField(title="指标名称", align=2, sort=1)
    private String colName;

    @ExcelField(title="错误提示", align=2, sort=3)
    private String colComments;

//    @ExcelField(title="指标分类", align=2, sort=1)
//    private String dataType;

    private String fQun;

    @ExcelField(title="是否可用", align=2, sort=1)
    private String cmisChoice;

    @ExcelField(title="是否电子病例验证", align=2, sort=1)
    private String emrChoice;

    private String fSort;

    @ExcelField(title="指标分类", align=2, sort=1)
    private String colType;

    private String gradingLevel;

    private String qualityClass;

    private String informationClass;

    @ExcelField(title="验证逻辑", align=2, sort=1)
    private String ruleType;

    private String score;

    private String mustFill;

    private String hqmsCol;

    private String weiTongCol;

    @ExcelField(title="指标描述", align=2, sort=3)
    private String ruleDescription;

    private String verificationLogic;

    private String tableName;

    private Integer pageSize;

    private Integer pageNum;

    private String queryStr;

}

package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TCheckColVo implements Serializable {

    private static long serialVersionUID= 1546948084109943546L;

    private String checkColId;

    @JsonProperty("fQun")
    private String fQun ;

    private String colName;

    //private String fQun ;

    private String colComments;

    private String qualityClass;

    private String cmisChoice;

    private String emrChoice;

    private String gradingLevel;

    private String informationClass;

    private String ruleType;

    private String colType;

    private String verificationLogic;

    private String orgNames;

    private String ruleDescription;



}

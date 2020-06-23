package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class TCheckOrgDto implements Serializable {

    private static final long serialVersionUID = 7903709207898468269L;

    private Long orgId;

    private String colName;

    private String colComments;

    private String fSort;

    private String score;

    private String mustFill;

    private String cmisChoice;

    private String emrChoice;

    private String checkColIds;

    private int pageSize;

    private int pageNum;

    private String queryStr;

}

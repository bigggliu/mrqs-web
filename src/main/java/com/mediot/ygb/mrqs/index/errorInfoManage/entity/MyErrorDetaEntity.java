package com.mediot.ygb.mrqs.index.errorInfoManage.entity;



import lombok.Data;

import java.util.Date;
@Data
public class MyErrorDetaEntity {

    private Long batchId;

    private String caseId;

    private String colName;

    private String colComments;

    private String errorMessage;

    private Integer score;

    private Integer mustFill;

    private String analyseType;

    private Integer remark;

    private Date outDtime;

    private Long tFirstPageTestingId;

    private Long tFirstoutoperTestingId;

    private String operationType;

    private Integer operationOrder;

    private Long tFirstoutdiagTestingId;

    private String diagType;

    private Integer diagOrder;
}

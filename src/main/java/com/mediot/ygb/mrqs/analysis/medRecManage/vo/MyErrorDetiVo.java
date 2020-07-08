package com.mediot.ygb.mrqs.analysis.medRecManage.vo;

import lombok.Data;

@Data
public class MyErrorDetiVo {
    private Long tFirstPageTestingId;
    private String caseNo;
    private Long tFirstoutdiagTestingId;
    private String diagType;
    private Integer diagOrder;
    private Long tFirstoutoperTestingId;
    private String operationType;
    private Integer operationOrder;
}

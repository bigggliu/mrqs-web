package com.mediot.ygb.mrqs.index.indexInfoManage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * T_FIRSTOUTDIAG_TESTING
 * @author 
 */
@Data
public class TFirstoutdiagTestingDto implements Serializable {

    private static final long serialVersionUID = -1077097669479455164L;

    private String tFirstoutdiagTestingId;

    private Long batchId;

    private Long caseId;

    private String diagType;

    private Integer diagOrder;

    private String diagnosisCode;

    private String diagnosisName;

    private String inCondition;

    private String dischargeStatus;

    private String pathologicalNumber;

    private String degreeOfDifferentiation;


}
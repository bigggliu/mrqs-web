package com.mediot.ygb.mrqs.index.indexInfoManage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TFirstoutoperTestingDto implements Serializable {

    private static final long serialVersionUID = -7092996844988557580L;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstoutoperTestingId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstPageTestingId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long batchId;

    private Long caseId;

    private Integer operationOrder;

    private String operationCode;

    private String operationDtime;

    private BigDecimal operDuration;

    private String operationLevel;

    private String bodyPart;

    private String operationName;

    private String surgeon;

    private String assistant1;

    private String assistant2;

    private String incisionType;

    private String incisionHealing;

    private String anesthesiaMode;

    private String anesthesiaLevel;

    private String anesthesiologist;

    private String surgicalPatientType;

    private String operationType;

    private String incisionHealingType;

}

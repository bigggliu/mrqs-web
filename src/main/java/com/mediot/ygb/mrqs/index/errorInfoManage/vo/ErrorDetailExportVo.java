package com.mediot.ygb.mrqs.index.errorInfoManage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ErrorDetailExportVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstPageTestingId;

    private String outDeptName;

    private String cataloger;

    private String caseNo;

    private String inHospitalTimes;

    private String fname;

    private String sexCode;

    private Date inDtime;

    private Date outDtime;

    private String outDeptCode;

    private String outDeptRoom;

    private String inChargeDoctor;

    private String residentDoctor;

    private BigDecimal feeTotal;

    private String errorInfos;

}

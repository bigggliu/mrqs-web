package com.mediot.ygb.mrqs.index.indexInfoManage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * T_FIRSTOUTDIAG_TESTING
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_FIRSTOUTDIAG_TESTING")
public class TFirstoutdiagTesting implements Serializable {

    private static final long serialVersionUID = -1077097669479455164L;

    @TableId("T_FIRSTOUTDIAG_TESTING_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstoutdiagTestingId;

    private Long batchId;

    private String caseId;

    private String diagType;

    private Integer diagOrder;

    private String diagnosisCode;

    private String diagnosisName;

    private String inCondition;

    private String dischargeStatus;

    private String pathologicalNumber;

    private String degreeOfDifferentiation;

    private Long tFirstPageTestingId;

    @TableField(exist = false)
    private String tempSid;
}
package com.mediot.ygb.mrqs.index.indexInfoManage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * T_FIRSTOUTOPER_TESTING
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_FIRSTOUTOPER_TESTING")
public class TFirstoutoperTesting implements Serializable {

    private static final long serialVersionUID = -79619104748100295L;

    @TableId("T_FIRSTOUTOPER_TESTING_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstoutoperTestingId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long batchId;

    private String caseId;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstPageTestingId;

    @TableField(exist = false)
    private String tempSid;

}
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
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_FIRSTPAGE_TESTING")
public class TFirstPageTesting implements Serializable {

    private static final long serialVersionUID = 8755385434816158099L;

    @TableId("T_FIRST_PAGE_TESTING_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long tFirstPageTestingId;

    private String orgName;

    private Long batchId;

    private String caseId;

    private String orgId;

    private String inHospitalTimes;

    private String caseNo;

    private String medicareCard;

    private String payWayCode;

    private String cardNo;

    private String fname;

    private String sexCode;

    private Date birthDate;

    private Integer age;

    private String country;

    private Integer birthWeight;

    private Integer birthWeight5;

    private Integer birthWeight4;

    private Integer birthWeight3;

    private Integer birthWeight2;

    private Integer inWeight;

    private String idNo;

    private String occupationCode;

    private String marriageCode;

    private String birthAddrProvince;

    private String birthAddrCity;

    private String birthAddrCounty;

    private String nativeAddrProvince;

    private String nativeAddrCity;

    private String nativeAddrCounty;

    private String nationalityCode;

    private String presentAddrProvince;

    private String presentAddrCity;

    private String presentAddrCounty;

    private String presentAddrTel;

    private String presentAddrPostalCode;

    private String registerAddrProvince;

    private String registerAddrCity;

    private String registerAddrCounty;

    private String registerAddrTel;

    private String registerAddrPostalCode;

    private String employerAddr;

    private String employerTel;

    private String employerPostalCode;

    private String contactName;

    private String contactRelationship;

    private String contactAddr;

    private String contactTel;

    private String inRoad;

    private String outpatientDoctor;

    private Date inDtime;

    private Date outDtime;

    private String inDeptCode;

    private String inDeptRoom;

    private String moveDept;

    private String outDeptCode;

    private String outDeptRoom;

    private Long actualInDays;

    private String outpatDiagCode;

    private String outpatDiagName;

    private String onAdmission;

    private String inDiagCode;

    private String inDiagName;

    private Date dateOfDiagnosis;

    private String totalNumberOfInfections;

    private String allergySource;

    private String nameOfAllergicDrug;

    private String aboCode;

    private String rhCode9;

    private String transfusionReaction;

    private String redBloodCell;

    private String platelet;

    private String plasma;

    private String wholeBlood;

    private String selfRecovery;

    private String otherInputs;

    private String drugAllergyMark;

    private String drugAllergensName;

    private String autopsyMark;

    private String deptDirector;

    private String chiefDoctor;

    private String inChargeDoctor;

    private String residentDoctor;

    private String respNurse;

    private String learningDoctor;

    private String internDoctor;

    private String cataloger;

    private String caseQuality;

    private String qcDoctor;

    private String qcNurse;

    private Date qcDtime;

    private String dischargeClass;

    private String orderReferralOrg;

    private String rehospAfter31Mark;

    private String rehospAfter31Purpose;

    private Integer comaDurationBeforeDay;

    private Integer comaDurationBeforeHour;

    private Integer comaDurationBeforeMinute;

    private Integer comaDurationAfterDay;

    private Integer comaDurationAfterHour;

    private Integer comaDurationAfterMinute;

    private String hbsag;

    private String hcvAb;

    private String hivAb;

    private String clinic2out;

    private String in2out;

    private String preOper2oper;

    private String clinic2autopsy;

    private String radiation2pathology;

    private String rescueTimes;

    private String rescueSuccessTimes;

    private String highestDiagnosticBasis;

    private String daysOfIntensiveCare;

    private String daysOfPrimaryCare;

    private String daysOfSecondaryCare;

    private String daysOfTertiaryCare;

    private String icuName1;

    private Date entryTime1;

    private Date exitTime1;

    private String icuName2;

    private Date entryTime2;

    private Date exitTime2;

    private String icuName3;

    private Date entryTime3;

    private Date exitTime3;

    private String icuName4;

    private Date entryTime4;

    private Date exitTime4;

    private String icuName5;

    private Date entryTime5;

    private Date exitTime5;

    private String firstCase;

    private String surgicalPatientType;

    private String followUp;

    private String weeksOfFollowUp;

    private String monthsOfFollowUp;

    private String yearsOfFollowUp;

    private String teachingCases;

    private String postgraduateIntern;

    private String ageMonth;

    private String ventilatorUseTime;

    private String nameOfTransferredHospital;

    private String communityServiceOrganization;

    private BigDecimal feeTotal;

    private BigDecimal feeSelfPay;

    private BigDecimal feeGeneralMedical;

    private BigDecimal feeGeneralTreat;

    private BigDecimal feeTend;

    private BigDecimal feeMedicalOther;

    private BigDecimal feePathology;

    private BigDecimal feeLaboratory;

    private BigDecimal feeImaging;

    private BigDecimal feeClinc;

    private BigDecimal feeNonsurgicalTreat;

    private BigDecimal feeClinPhysical;

    private BigDecimal feeSurgicalTreat;

    private BigDecimal feeAnaes;

    private BigDecimal feeOperation;

    private BigDecimal feeRecovery;

    private BigDecimal feeCnTreatment;

    private BigDecimal feeWesternMedicine;

    private BigDecimal feeAntimicrobial;

    private BigDecimal feeCnMedicine;

    private BigDecimal feeCnHerbalMedicine;

    private BigDecimal feeBlood;

    private BigDecimal feeAlbumin;

    private BigDecimal feeGlobulin;

    private BigDecimal feeBcf;

    private BigDecimal feeCytokine;

    private BigDecimal feeCheckMaterial;

    private BigDecimal feeTreatMaterial;

    private BigDecimal feeOperMaterial;

    private BigDecimal feeOther;

    private Short fstate;

    private Date createTime;

    private String upOrgId;

    @TableField(exist = false)
    private String tempSid;

    @TableField(exist = false)
    private Integer total;
}

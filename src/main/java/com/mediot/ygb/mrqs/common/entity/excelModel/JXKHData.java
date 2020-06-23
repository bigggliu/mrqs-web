package com.mediot.ygb.mrqs.common.entity.excelModel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class JXKHData {

    @ExcelProperty("A02")
    private String orgName;

    @ExcelProperty("A01")
    private Long orgId;

    @ExcelProperty("A49")
    private String inHospitalTimes;

    @ExcelProperty("A48")
    private String caseNo;

    @ExcelProperty("A46C")
    private String payWayCode;

    @ExcelProperty("A47")
    private String cardNo;

    @ExcelProperty("A11")
    private String fname;

    @ExcelProperty("A12C")
    private String sexCode;

    @ExcelProperty("A13")
    private Date birthDate;

    @ExcelProperty("A14")
    private Integer age;

    @ExcelProperty("A15C")
    private String country;

    @ExcelProperty("A18X01")
    private Integer birthWeight;

    @ExcelProperty("A18X05")
    private Integer birthWeight5;

    @ExcelProperty("A18X04")
    private Integer birthWeight4;

    @ExcelProperty("A18X03")
    private Integer birthWeight3;

    @ExcelProperty("A18X02")
    private Integer birthWeight2;

    @ExcelProperty("A17")
    private Integer inWeight;

    @ExcelProperty("A20")
    private String idNo;

    @ExcelProperty("A38C")
    private String occupationCode;

    @ExcelProperty("A21C")
    private String marriageCode;

    @ExcelProperty("A22")
    private String birthAddrProvince;

    @ExcelProperty("A23C")
    private String nativeAddrProvince;

    @ExcelProperty("A19C")
    private String nationalityCode;

    @ExcelProperty("A26")
    private String presentAddrCounty;

    @ExcelProperty("A27")
    private String presentAddrTel;

    @ExcelProperty("A28C")
    private String presentAddrPostalCode;

    @ExcelProperty("A24")
    private String registerAddrCounty;

    @ExcelProperty("A25C")
    private String registerAddrPostalCode;

    @ExcelProperty("A29")
    private String employerAddr;

    @ExcelProperty("A30")
    private String employerTel;

    @ExcelProperty("A31C")
    private String employerPostalCode;

    @ExcelProperty("A32")
    private String contactName;

    @ExcelProperty("A33")
    private String contactRelationship;

    @ExcelProperty("A34")
    private String contactAddr;

    @ExcelProperty("A35")
    private String contactTel;

    @ExcelProperty("B11C")
    private String inRoad;

    @ExcelProperty("B12")
    private Date inDtime;

    @ExcelProperty("B15")
    private Date outDtime;

    @ExcelProperty("B13C")
    private String inDeptCode;

    @ExcelProperty("B14")
    private String inDeptRoom;

    @ExcelProperty("B21C")
    private String moveDept;

    @ExcelProperty("B16C")
    private String outDeptCode;

    @ExcelProperty("B17")
    private String outDeptRoom;

    @ExcelProperty("B20")
    private Long actualInDays;

    @ExcelProperty("C01C")
    private String outpatDiagCode;

    @ExcelProperty("C02N")
    private String outpatDiagName;

    @ExcelProperty("C25")
    private String nameOfAllergicDrug;

    @ExcelProperty("C26C")
    private String aboCode;

    @ExcelProperty("C27C")
    private String rhCode9;

    @ExcelProperty("C24C")
    private String drugAllergyMark;

    @ExcelProperty("C34C")
    private String autopsyMark;

    @ExcelProperty("B22")
    private String deptDirector;

    @ExcelProperty("B23")
    private String chiefDoctor;

    @ExcelProperty("B24")
    private String inChargeDoctor;

    @ExcelProperty("B25")
    private String residentDoctor;

    @ExcelProperty("B26")
    private String respNurse;

    @ExcelProperty("B27")
    private String learningDoctor;

    @ExcelProperty("B28")
    private String internDoctor;

    @ExcelProperty("B29")
    private String cataloger;

    @ExcelProperty("B30C")
    private String caseQuality;

    @ExcelProperty("B31")
    private String qcDoctor;

    @ExcelProperty("B32")
    private String qcNurse;

    @ExcelProperty("B33")
    private Date qcDtime;

    @ExcelProperty("B34C")
    private String dischargeClass;

    @ExcelProperty("B35")
    private String orderReferralOrg;

    @ExcelProperty("B36C")
    private String rehospAfter31Mark;

    @ExcelProperty("B37")
    private String rehospAfter31Purpose;

    @ExcelProperty("C28")
    private Integer comaDurationBeforeDay;

    @ExcelProperty("C29")
    private Integer comaDurationBeforeHour;

    @ExcelProperty("C30")
    private Integer comaDurationBeforeMinute;

    @ExcelProperty("C31")
    private Integer comaDurationAfterDay;

    @ExcelProperty("C32")
    private Integer comaDurationAfterHour;

    @ExcelProperty("C33")
    private Integer comaDurationAfterMinute;

    @ExcelProperty("A16")
    private String ageMonth;

    @ExcelProperty("D01")
    private BigDecimal feeTotal;

    @ExcelProperty("D09")
    private BigDecimal feeSelfPay;

    @ExcelProperty("D11")
    private BigDecimal feeGeneralMedical;

    @ExcelProperty("D12")
    private BigDecimal feeGeneralTreat;

    @ExcelProperty("D13")
    private BigDecimal feeTend;

    @ExcelProperty("D14")
    private BigDecimal feeMedicalOther;

    @ExcelProperty("D15")
    private BigDecimal feePathology;

    @ExcelProperty("D16")
    private BigDecimal feeLaboratory;

    @ExcelProperty("D17")
    private BigDecimal feeImaging;

    @ExcelProperty("D18")
    private BigDecimal feeClinc;

    @ExcelProperty("D19")
    private BigDecimal feeNonsurgicalTreat;

    @ExcelProperty("D19X01")
    private BigDecimal feeClinPhysical;

    @ExcelProperty("D20")
    private BigDecimal feeSurgicalTreat;

    @ExcelProperty("D20X01")
    private BigDecimal feeAnaes;

    @ExcelProperty("D20X02")
    private BigDecimal feeOperation;

    @ExcelProperty("D21")
    private BigDecimal feeRecovery;

    @ExcelProperty("D22")
    private BigDecimal feeCnTreatment;

    @ExcelProperty("D23")
    private BigDecimal feeWesternMedicine;

    @ExcelProperty("D23X01")
    private BigDecimal feeAntimicrobial;

    @ExcelProperty("D24")
    private BigDecimal feeCnMedicine;

    @ExcelProperty("D25")
    private BigDecimal feeCnHerbalMedicine;

    @ExcelProperty("D26")
    private BigDecimal feeBlood;

    @ExcelProperty("D27")
    private BigDecimal feeAlbumin;

    @ExcelProperty("D28")
    private BigDecimal feeGlobulin;

    @ExcelProperty("D29")
    private BigDecimal feeBcf;

    @ExcelProperty("D30")
    private BigDecimal feeCytokine;

    @ExcelProperty("D31")
    private BigDecimal feeCheckMaterial;

    @ExcelProperty("D32")
    private BigDecimal feeTreatMaterial;

    @ExcelProperty("D33")
    private BigDecimal feeOperMaterial;

    @ExcelProperty("D34")
    private BigDecimal feeOther;

    @ExcelProperty("C03C")
    private String c03C;

    @ExcelProperty("C04N")
    private String c04N;

    @ExcelProperty("C05C")
    private String c05C;

    @ExcelProperty("C09C")
    private String c09C;

    @ExcelProperty("C10N")
    private String c10N;

    @ExcelProperty("C11")
    private String c11;

    @ExcelProperty("C12C")
    private String c12C;

    @ExcelProperty("C13N")
    private String c13N;

    @ExcelProperty("C14X01C")
    private String c14X01C;

    @ExcelProperty("C16X01")
    private String c16X01;

    @ExcelProperty("C17X01")
    private String c17X01;

    @ExcelProperty("C15X01N")
    private String c15X01N;

    @ExcelProperty("C18X01")
    private String c18x01;

    @ExcelProperty("C19X01")
    private String c19X01;

    @ExcelProperty("C20X01")
    private String c20X01;

    @ExcelProperty("C22X01C")
    private String c22X01C;

    @ExcelProperty("C21X01C")
    private String c21X01C;

    @ExcelProperty("C23X01")
    private String c23X01;

    //
    @ExcelProperty("C06X01C")
    private String c06X01C;

    @ExcelProperty("C06X02C")
    private String c06X02C;

    @ExcelProperty("C06X03C")
    private String c06X03C;

    @ExcelProperty("C06X04C")
    private String c06X04C;

    @ExcelProperty("C06X05C")
    private String c06X05C;

    @ExcelProperty("C06X06C")
    private String c06X06C;

    @ExcelProperty("C06X07C")
    private String c06X07C;

    @ExcelProperty("C06X08C")
    private String c06X08C;

    @ExcelProperty("C06X09C")
    private String c06X09C;

    @ExcelProperty("C06X10C")
    private String c06X10C;

    @ExcelProperty("C06X11C")
    private String c06X11C;

    @ExcelProperty("C06X12C")
    private String c06X12C;

    @ExcelProperty("C06X13C")
    private String c06X13C;

    @ExcelProperty("C06X14C")
    private String c06X14C;

    @ExcelProperty("C06X15C")
    private String c06X15C;

    @ExcelProperty("C06X16C")
    private String c06X16C;

    @ExcelProperty("C06X17C")
    private String c06X17C;

    @ExcelProperty("C06X18C")
    private String c06X18C;

    @ExcelProperty("C06X19C")
    private String c06X19C;

    @ExcelProperty("C06X20C")
    private String c06X20C;

    @ExcelProperty("C06X21C")
    private String c06X21C;

    @ExcelProperty("C06X22C")
    private String c06X22C;

    @ExcelProperty("C06X23C")
    private String c06X23C;

    @ExcelProperty("C06X24C")
    private String c06X24C;

    @ExcelProperty("C06X25C")
    private String c06X25C;

    @ExcelProperty("C06X26C")
    private String c06X26C;

    @ExcelProperty("C06X27C")
    private String c06X27C;

    @ExcelProperty("C06X28C")
    private String c06X28C;

    @ExcelProperty("C06X29C")
    private String c06X29C;

    @ExcelProperty("C06X30C")
    private String c06X30C;

    @ExcelProperty("C06X31C")
    private String c06X31C;

    @ExcelProperty("C06X32C")
    private String c06X32C;

    @ExcelProperty("C06X33C")
    private String c06X33C;

    @ExcelProperty("C06X34C")
    private String c06X34C;

    @ExcelProperty("C06X35C")
    private String c06X35C;

    @ExcelProperty("C06X36C")
    private String c06X36C;

    @ExcelProperty("C06X37C")
    private String c06X37C;

    @ExcelProperty("C06X38C")
    private String c06X38C;

    @ExcelProperty("C06X39C")
    private String c06X39C;

    @ExcelProperty("C06X40C")
    private String c06X40C;

    @ExcelProperty("C07X01N")
    private String c07X01N;

    @ExcelProperty("C07X02N")
    private String c07X02N;

    @ExcelProperty("C07X03N")
    private String c07X03N;

    @ExcelProperty("C07X04N")
    private String c07X04N;

    @ExcelProperty("C07X05N")
    private String c07X05N;

    @ExcelProperty("C07X06N")
    private String c07X06N;

    @ExcelProperty("C07X07N")
    private String c07X07N;

    @ExcelProperty("C07X08N")
    private String c07X08N;

    @ExcelProperty("C07X09N")
    private String c07X09N;

    @ExcelProperty("C07X10N")
    private String c07X10N;

    @ExcelProperty("C07X11N")
    private String c07X11N;

    @ExcelProperty("C07X12N")
    private String c07X12N;

    @ExcelProperty("C07X13N")
    private String c07X13N;

    @ExcelProperty("C07X14N")
    private String c07X14N;

    @ExcelProperty("C07X15N")
    private String c07X15N;

    @ExcelProperty("C07X16N")
    private String c07X16N;

    @ExcelProperty("C07X17N")
    private String c07X17N;

    @ExcelProperty("C07X18N")
    private String c07X18N;

    @ExcelProperty("C07X19N")
    private String c07X19N;

    @ExcelProperty("C07X20N")
    private String c07X20N;

    @ExcelProperty("C07X21N")
    private String c07X21N;

    @ExcelProperty("C07X22N")
    private String c07X22N;

    @ExcelProperty("C07X23N")
    private String c07X23N;

    @ExcelProperty("C07X24N")
    private String c07X24N;

    @ExcelProperty("C07X25N")
    private String c07X25N;

    @ExcelProperty("C07X26N")
    private String c07X26N;

    @ExcelProperty("C07X27N")
    private String c07X27N;

    @ExcelProperty("C07X28N")
    private String c07X28N;

    @ExcelProperty("C07X29N")
    private String c07X29N;

    @ExcelProperty("C07X30N")
    private String c07X30N;

    @ExcelProperty("C07X31N")
    private String c07X31N;

    @ExcelProperty("C07X32N")
    private String c07X32N;

    @ExcelProperty("C07X33N")
    private String c07X33N;

    @ExcelProperty("C07X34N")
    private String c07X34N;

    @ExcelProperty("C07X35N")
    private String c07X35N;

    @ExcelProperty("C07X36N")
    private String c07X36N;

    @ExcelProperty("C07X37N")
    private String c07X37N;

    @ExcelProperty("C07X38N")
    private String c07X38N;

    @ExcelProperty("C07X39N")
    private String c07X39N;

    @ExcelProperty("C07X40N")
    private String c07X40N;

    @ExcelProperty("C08X01C")
    private String c08X01C;

    @ExcelProperty("C08X02C")
    private String c08X02C;

    @ExcelProperty("C08X03C")
    private String c08X03C;

    @ExcelProperty("C08X04C")
    private String c08X04C;

    @ExcelProperty("C08X05C")
    private String c08X05C;

    @ExcelProperty("C08X06C")
    private String c08X06C;

    @ExcelProperty("C08X07C")
    private String c08X07C;

    @ExcelProperty("C08X08C")
    private String c08X08C;

    @ExcelProperty("C08X09C")
    private String c08X09C;

    @ExcelProperty("C08X10C")
    private String c08X10C;

    @ExcelProperty("C08X11C")
    private String c08X11C;

    @ExcelProperty("C08X12C")
    private String c08X012C;

    @ExcelProperty("C08X13C")
    private String c08X13C;

    @ExcelProperty("C08X14C")
    private String c08X14C;

    @ExcelProperty("C08X15C")
    private String c08X15C;

    @ExcelProperty("C08X16C")
    private String c08X16C;

    @ExcelProperty("C08X17C")
    private String c08X17C;

    @ExcelProperty("C08X18C")
    private String c08X18C;

    @ExcelProperty("C08X19C")
    private String c08X19C;

    @ExcelProperty("C08X20C")
    private String c08X20C;

    @ExcelProperty("C08X21C")
    private String c08X21C;

    @ExcelProperty("C08X22C")
    private String c08X22C;

    @ExcelProperty("C08X23C")
    private String c08X23C;

    @ExcelProperty("C08X24C")
    private String c08X24C;

    @ExcelProperty("C08X25C")
    private String c08X25C;

    @ExcelProperty("C08X26C")
    private String c08X26C;

    @ExcelProperty("C08X27C")
    private String c08X27C;

    @ExcelProperty("C08X28C")
    private String c08X28C;

    @ExcelProperty("C08X29C")
    private String c08X29C;

    @ExcelProperty("C08X30C")
    private String c08X30C;

    @ExcelProperty("C08X31C")
    private String c08X31C;

    @ExcelProperty("C08X32C")
    private String c08X32C;

    @ExcelProperty("C08X33C")
    private String c08X33C;

    @ExcelProperty("C08X34C")
    private String c08X34C;

    @ExcelProperty("C08X35C")
    private String c08X35C;

    @ExcelProperty("C08X36C")
    private String c08X36C;

    @ExcelProperty("C08X37C")
    private String c08X37C;

    @ExcelProperty("C08X38C")
    private String c08X38C;

    @ExcelProperty("C08X39C")
    private String c08X39C;

    @ExcelProperty("C08X40C")
    private String c08X40C;

    @ExcelProperty("C35X01C")
    private String c35X01C;

    @ExcelProperty("C37X01")
    private String c37X01;

    @ExcelProperty("C37X02")
    private String c37X02;

    @ExcelProperty("C37X03")
    private String c37X03;

    @ExcelProperty("C37X04")
    private String c37X04;

    @ExcelProperty("C37X05")
    private String c37X05;

    @ExcelProperty("C37X06")
    private String c37X06;

    @ExcelProperty("C37X07")
    private String c37X07;

    @ExcelProperty("C37X08")
    private String c37X08;

    @ExcelProperty("C37X09")
    private String c37X09;

    @ExcelProperty("C37X10")
    private String c37X010;

    @ExcelProperty("C37X11")
    private String c37X11;

    @ExcelProperty("C37X12")
    private String c37X12;

    @ExcelProperty("C37X13")
    private String c37X13;

    @ExcelProperty("C37X14")
    private String c37X14;

    @ExcelProperty("C37X15")
    private String c37X15;

    @ExcelProperty("C37X16")
    private String c37X16;

    @ExcelProperty("C37X17")
    private String c37X17;

    @ExcelProperty("C37X18")
    private String c37X18;

    @ExcelProperty("C37X19")
    private String c37X19;

    @ExcelProperty("C37X20")
    private String c37X20;

    @ExcelProperty("C37X21")
    private String c37X21;

    @ExcelProperty("C37X22")
    private String c37X22;

    @ExcelProperty("C37X23")
    private String c37X23;

    @ExcelProperty("C37X24")
    private String c37X24;

    @ExcelProperty("C37X25")
    private String c37X25;

    @ExcelProperty("C37X26")
    private String c37X26;

    @ExcelProperty("C37X27")
    private String c37X27;

    @ExcelProperty("C37X28")
    private String c37X28;

    @ExcelProperty("C37X29")
    private String c37X29;

    @ExcelProperty("C37X30")
    private String c37X30;

    @ExcelProperty("C37X31")
    private String c37X31;

    @ExcelProperty("C37X32")
    private String c37X32;

    @ExcelProperty("C37X33")
    private String c37X33;

    @ExcelProperty("C37X34")
    private String c37X34;

    @ExcelProperty("C37X35")
    private String c37X35;

    @ExcelProperty("C37X36")
    private String c37X36;

    @ExcelProperty("C37X37")
    private String c37X37;

    @ExcelProperty("C37X38")
    private String c37X38;

    @ExcelProperty("C37X39")
    private String c37X39;

    @ExcelProperty("C37X40")
    private String c37X40;

    @ExcelProperty("C38X01")
    private String c38X01;

    @ExcelProperty("C38X02")
    private String c38X02;

    @ExcelProperty("C38X03")
    private String c38X03;

    @ExcelProperty("C38X04")
    private String c38X04;

    @ExcelProperty("C38X05")
    private String c38X05;

    @ExcelProperty("C38X06")
    private String c38X06;

    @ExcelProperty("C38X07")
    private String c38X07;

    @ExcelProperty("C38X08")
    private String c38X08;

    @ExcelProperty("C38X09")
    private String c38X09;

    @ExcelProperty("C38X10")
    private String c38X10;

    @ExcelProperty("C38X11")
    private String c38X11;

    @ExcelProperty("C38X12")
    private String c38X12;

    @ExcelProperty("C38X13")
    private String c38X13;

    @ExcelProperty("C38X14")
    private String c38X14;

    @ExcelProperty("C38X15")
    private String c38X15;

    @ExcelProperty("C38X16")
    private String c38X16;

    @ExcelProperty("C38X17")
    private String c38X17;

    @ExcelProperty("C38X18")
    private String c38X18;

    @ExcelProperty("C38X19")
    private String c38X19;

    @ExcelProperty("C38X20")
    private String c38X20;

    @ExcelProperty("C38X21")
    private String c38X21;

    @ExcelProperty("C38X22")
    private String c38X22;

    @ExcelProperty("C38X23")
    private String c38X23;

    @ExcelProperty("C38X24")
    private String c38X24;

    @ExcelProperty("C38X25")
    private String c38X25;

    @ExcelProperty("C38X26")
    private String c38X26;

    @ExcelProperty("C38X27")
    private String c38X27;

    @ExcelProperty("C38X28")
    private String c38X28;

    @ExcelProperty("C38X29")
    private String c38X29;

    @ExcelProperty("C38X30")
    private String c38X30;

    @ExcelProperty("C38X31")
    private String c38X31;

    @ExcelProperty("C38X32")
    private String c38X32;

    @ExcelProperty("C38X33")
    private String c38X33;

    @ExcelProperty("C38X34")
    private String c38X34;

    @ExcelProperty("C38X35")
    private String c38X35;

    @ExcelProperty("C38X36")
    private String c38X36;

    @ExcelProperty("C38X37")
    private String c38X37;

    @ExcelProperty("C38X38")
    private String c38X38;

    @ExcelProperty("C38X39")
    private String c38X39;

    @ExcelProperty("C38X40")
    private String c38X40;

    @ExcelProperty("C36X01N")
    private String c36X01N;

    @ExcelProperty("C36X02N")
    private String c36X02N;

    @ExcelProperty("C36X03N")
    private String c36X03N;

    @ExcelProperty("C36X04N")
    private String c36X04N;

    @ExcelProperty("C36X05N")
    private String c36X05N;

    @ExcelProperty("C36X06N")
    private String c36X06N;

    @ExcelProperty("C36X07N")
    private String c36X07N;

    @ExcelProperty("C36X08N")
    private String c36X08N;

    @ExcelProperty("C36X09N")
    private String c36X09N;

    @ExcelProperty("C36X10N")
    private String c36X10N;

    @ExcelProperty("C36X11N")
    private String c36X11N;

    @ExcelProperty("C36X12N")
    private String c36X12N;

    @ExcelProperty("C36X13N")
    private String c36X13N;

    @ExcelProperty("C36X14N")
    private String c36X14N;

    @ExcelProperty("C36X15N")
    private String c36X15N;

    @ExcelProperty("C36X16N")
    private String c36X016N;

    @ExcelProperty("C36X17N")
    private String c36X17N;

    @ExcelProperty("C36X18N")
    private String c36X18N;

    @ExcelProperty("C36X19N")
    private String c36X19N;

    @ExcelProperty("C36X20N")
    private String c36X20N;

    @ExcelProperty("C36X21N")
    private String c36X21N;

    @ExcelProperty("C36X22N")
    private String c36X22N;

    @ExcelProperty("C36X23N")
    private String c36X23N;

    @ExcelProperty("C36X24N")
    private String c36X24N;

    @ExcelProperty("C36X25N")
    private String c36X25N;

    @ExcelProperty("C36X26N")
    private String c36X26N;

    @ExcelProperty("C36X27N")
    private String c36X27N;

    @ExcelProperty("C36X28N")
    private String c36X28N;

    @ExcelProperty("C36X29N")
    private String c36X29N;

    @ExcelProperty("C36X30N")
    private String c36X30N;

    @ExcelProperty("C36X31N")
    private String c36X31N;

    @ExcelProperty("C36X32N")
    private String c36X32N;

    @ExcelProperty("C36X33N")
    private String c36X33N;

    @ExcelProperty("C36X34N")
    private String c36X34N;

    @ExcelProperty("C36X35N")
    private String c36X35N;

    @ExcelProperty("C36X36N")
    private String c36X36N;

    @ExcelProperty("C36X37N")
    private String c36X37N;

    @ExcelProperty("C36X38N")
    private String c36X38N;

    @ExcelProperty("C36X39N")
    private String c36X39N;

    @ExcelProperty("C36X40N")
    private String c36X40N;

    @ExcelProperty("C39X01")
    private String c39X01;

    @ExcelProperty("C39X02")
    private String c39X02;

    @ExcelProperty("C39X03")
    private String c39X03;

    @ExcelProperty("C39X04")
    private String c39X04;

    @ExcelProperty("C39X05")
    private String c39X05;

    @ExcelProperty("C39X06")
    private String c39X06;

    @ExcelProperty("C39X07")
    private String c39X07;

    @ExcelProperty("C39X08")
    private String c39X08;

    @ExcelProperty("C39X09")
    private String c39X09;

    @ExcelProperty("C39X10")
    private String c39X10;

    @ExcelProperty("C39X11")
    private String c39X11;

    @ExcelProperty("C39X12")
    private String c39X12;

    @ExcelProperty("C39X13")
    private String c39X13;

    @ExcelProperty("C39X14")
    private String c39X14;

    @ExcelProperty("C39X15")
    private String c39X15;

    @ExcelProperty("C39X16")
    private String c39X16;

    @ExcelProperty("C39X17")
    private String c39X17;

    @ExcelProperty("C39X18")
    private String c39X0118;

    @ExcelProperty("C39X19")
    private String c39X19;

    @ExcelProperty("C39X20")
    private String c39X0120;

    @ExcelProperty("C39X21")
    private String c39X21;

    @ExcelProperty("C39X22")
    private String c39X22;

    @ExcelProperty("C39X23")
    private String c39X23;

    @ExcelProperty("C39X24")
    private String c39X24;

    @ExcelProperty("C39X25")
    private String c39X25;

    @ExcelProperty("C39X26")
    private String c39X26;

    @ExcelProperty("C39X27")
    private String c39X27;

    @ExcelProperty("C39X28")
    private String c39X28;

    @ExcelProperty("C39X29")
    private String c39X29;

    @ExcelProperty("C39X30")
    private String c39X30;

    @ExcelProperty("C39X31")
    private String c39X31;

    @ExcelProperty("C39X32")
    private String c39X32;

    @ExcelProperty("C39X33")
    private String c39X33;

    @ExcelProperty("C39X34")
    private String c39X34;

    @ExcelProperty("C39X35")
    private String c39X35;

    @ExcelProperty("C39X36")
    private String c39X36;

    @ExcelProperty("C39X37")
    private String c39X37;

    @ExcelProperty("C39X38")
    private String c39X38;

    @ExcelProperty("C39X39")
    private String c39X39;

    @ExcelProperty("C39X40")
    private String c39X40;

    @ExcelProperty("C40X01")
    private String c40X01;

    @ExcelProperty("C40X02")
    private String c40X02;

    @ExcelProperty("C40X03")
    private String c40X03;

    @ExcelProperty("C40X04")
    private String c40X04;

    @ExcelProperty("C40X05")
    private String c40X05;

    @ExcelProperty("C40X06")
    private String c40X06;

    @ExcelProperty("C40X07")
    private String c40X07;

    @ExcelProperty("C40X08")
    private String c40X08;

    @ExcelProperty("C40X09")
    private String c40X09;

    @ExcelProperty("C40X10")
    private String c40X10;

    @ExcelProperty("C40X11")
    private String c40X11;

    @ExcelProperty("C40X12")
    private String c40X12;

    @ExcelProperty("C40X13")
    private String c40X13;

    @ExcelProperty("C40X14")
    private String c40X14;

    @ExcelProperty("C40X15")
    private String c40X15;

    @ExcelProperty("C40X16")
    private String c40X16;

    @ExcelProperty("C40X17")
    private String c40X17;

    @ExcelProperty("C40X18")
    private String c40X18;

    @ExcelProperty("C40X19")
    private String c40X19;

    @ExcelProperty("C40X20")
    private String c40X20;

    @ExcelProperty("C40X21")
    private String c40X21;

    @ExcelProperty("C40X22")
    private String c40X22;

    @ExcelProperty("C40X23")
    private String c40X23;

    @ExcelProperty("C40X24")
    private String c40X24;

    @ExcelProperty("C40X25")
    private String c40X25;

    @ExcelProperty("C40X26")
    private String c40X26;

    @ExcelProperty("C40X27")
    private String c40X27;

    @ExcelProperty("C40X28")
    private String c40X028;

    @ExcelProperty("C40X29")
    private String c40X29;

    @ExcelProperty("C40X30")
    private String c40X30;

    @ExcelProperty("C40X31")
    private String c40X31;

    @ExcelProperty("C40X32")
    private String c40X32;

    @ExcelProperty("C40X33")
    private String c40X33;

    @ExcelProperty("C40X34")
    private String c40X34;

    @ExcelProperty("C40X35")
    private String c40X35;

    @ExcelProperty("C40X36")
    private String c40X36;

    @ExcelProperty("C40X37")
    private String c40X37;

    @ExcelProperty("C40X38")
    private String c40X38;

    @ExcelProperty("C40X39")
    private String c40X39;

    @ExcelProperty("C40X40")
    private String c40X40;

    @ExcelProperty("C41X01")
    private String c41X01;

    @ExcelProperty("C41X02")
    private String c41X02;

    @ExcelProperty("C41X03")
    private String c41X03;

    @ExcelProperty("C41X04")
    private String c41X04;

    @ExcelProperty("C41X05")
    private String c41X05;

    @ExcelProperty("C41X06")
    private String c41X06;

    @ExcelProperty("C41X07")
    private String c41X07;

    @ExcelProperty("C41X08")
    private String c41X08;

    @ExcelProperty("C41X09")
    private String c41X09;

    @ExcelProperty("C41X10")
    private String c41X10;

    @ExcelProperty("C41X11")
    private String c41X11;

    @ExcelProperty("C41X12")
    private String c41X12;

    @ExcelProperty("C41X13")
    private String c41X13;

    @ExcelProperty("C41X14")
    private String c41X14;

    @ExcelProperty("C41X15")
    private String c41X15;

    @ExcelProperty("C41X16")
    private String c41X16;

    @ExcelProperty("C41X17")
    private String c41X17;

    @ExcelProperty("C41X18")
    private String c41X18;

    @ExcelProperty("C41X19")
    private String c41X19;

    @ExcelProperty("C41X20")
    private String c41X20;

    @ExcelProperty("C41X21")
    private String c41X21;

    @ExcelProperty("C41X22")
    private String c41X22;

    @ExcelProperty("C41X23")
    private String c41X23;

    @ExcelProperty("C41X24")
    private String c41X24;

    @ExcelProperty("C41X25")
    private String c41X25;

    @ExcelProperty("C41X26")
    private String c41X26;

    @ExcelProperty("C41X27")
    private String c41X27;

    @ExcelProperty("C41X28")
    private String c41X28;

    @ExcelProperty("C41X29")
    private String c41X29;

    @ExcelProperty("C41X30")
    private String c41X30;

    @ExcelProperty("C41X31")
    private String c41X31;

    @ExcelProperty("C41X32")
    private String c41X32;

    @ExcelProperty("C41X33")
    private String c41X33;

    @ExcelProperty("C41X34")
    private String c41X34;

    @ExcelProperty("C41X35")
    private String c41X35;

    @ExcelProperty("C41X36")
    private String c41X36;

    @ExcelProperty("C41X37")
    private String c41X37;

    @ExcelProperty("C41X38")
    private String c41X38;

    @ExcelProperty("C41X39")
    private String c41X39;

    @ExcelProperty("C41X40")
    private String c41X40;

    @ExcelProperty("C43X01")
    private String c43X01;

    @ExcelProperty("C43X02")
    private String c43X02;

    @ExcelProperty("C43X03")
    private String c43X03;

    @ExcelProperty("C43X04")
    private String c43X04;

    @ExcelProperty("C43X05")
    private String c43X05;

    @ExcelProperty("C43X06")
    private String c43X06;

    @ExcelProperty("C43X07")
    private String c43X07;

    @ExcelProperty("C43X08")
    private String c43X08;

    @ExcelProperty("C43X09")
    private String c43X09;

    @ExcelProperty("C43X10")
    private String c43X10;

    @ExcelProperty("C43X11")
    private String c43X11;

    @ExcelProperty("C43X12")
    private String c43X12;

    @ExcelProperty("C43X13")
    private String c43X13;

    @ExcelProperty("C43X14")
    private String c43X14;

    @ExcelProperty("C43X15")
    private String c43X15;

    @ExcelProperty("C43X16")
    private String c43X16;

    @ExcelProperty("C43X17")
    private String c43X17;

    @ExcelProperty("C43X18")
    private String c43X18;

    @ExcelProperty("C43X19")
    private String c43X19;

    @ExcelProperty("C43X20")
    private String c43X20;

    @ExcelProperty("C43X21")
    private String c43X21;

    @ExcelProperty("C43X22")
    private String c43X22;

    @ExcelProperty("C43X23")
    private String c43X23;

    @ExcelProperty("C43X24")
    private String c43X24;

    @ExcelProperty("C43X25")
    private String c43X25;

    @ExcelProperty("C43X26")
    private String c43X26;

    @ExcelProperty("C43X27")
    private String c43X27;

    @ExcelProperty("C43X28")
    private String c43X28;

    @ExcelProperty("C43X29")
    private String c43X29;

    @ExcelProperty("C43X30")
    private String c43X30;

    @ExcelProperty("C43X31")
    private String c43X31;

    @ExcelProperty("C43X32")
    private String c43X32;

    @ExcelProperty("C43X33")
    private String c43X33;

    @ExcelProperty("C43X34")
    private String c43X34;

    @ExcelProperty("C43X35")
    private String c43X35;

    @ExcelProperty("C43X36")
    private String c43X36;

    @ExcelProperty("C43X37")
    private String c43X37;

    @ExcelProperty("C43X38")
    private String c43X38;

    @ExcelProperty("C43X39")
    private String c43X39;

    @ExcelProperty("C43X40")
    private String c43X40;

    @ExcelProperty("C42X01C")
    private String c42X01C;

    @ExcelProperty("C42X02C")
    private String c42X02C;

    @ExcelProperty("C42X03C")
    private String c42X03C;

    @ExcelProperty("C42X04C")
    private String c42X04C;

    @ExcelProperty("C42X05C")
    private String c42X05C;

    @ExcelProperty("C42X06C")
    private String c42X06C;

    @ExcelProperty("C42X07C")
    private String c42X07C;

    @ExcelProperty("C42X08C")
    private String c42X08C;

    @ExcelProperty("C42X09C")
    private String c42X09C;

    @ExcelProperty("C42X10C")
    private String c42X10C;

    @ExcelProperty("C42X11C")
    private String c42X11C;

    @ExcelProperty("C42X12C")
    private String c42X12C;

    @ExcelProperty("C42X13C")
    private String c42X13C;

    @ExcelProperty("C42X14C")
    private String c42X14C;

    @ExcelProperty("C42X15C")
    private String c42X15C;

    @ExcelProperty("C42X16C")
    private String c42X16C;

    @ExcelProperty("C42X17C")
    private String c42X17C;

    @ExcelProperty("C42X18C")
    private String c42X18C;

    @ExcelProperty("C42X19C")
    private String c42X19C;

    @ExcelProperty("C42X20C")
    private String c42X20C;

    @ExcelProperty("C42X21C")
    private String c42X21C;

    @ExcelProperty("C42X22C")
    private String c42X22C;

    @ExcelProperty("C42X23C")
    private String c42X23C;

    @ExcelProperty("C42X24C")
    private String c42X24C;

    @ExcelProperty("C42X25C")
    private String c42X25C;

    @ExcelProperty("C42X26C")
    private String c42X26C;

    @ExcelProperty("C42X27C")
    private String c42X27C;

    @ExcelProperty("C42X28C")
    private String c42X28C;

    @ExcelProperty("C42X29C")
    private String c42X29C;

    @ExcelProperty("C42X30C")
    private String c42X30C;

    @ExcelProperty("C42X31C")
    private String c42X31C;

    @ExcelProperty("C42X32C")
    private String c42X32C;

    @ExcelProperty("C42X33C")
    private String c42X34C;

    @ExcelProperty("C42X35C")
    private String c42X36C;

    @ExcelProperty("C42X37C")
    private String c42X37C;

    @ExcelProperty("C42X38C")
    private String c42X38C;

    @ExcelProperty("C42X39C")
    private String c42X39C;

    @ExcelProperty("C42X40C")
    private String c42X40C;

    @ExcelProperty("C44X01")
    private String c44X01;

    @ExcelProperty("C44X02")
    private String c44X02;

    @ExcelProperty("C44X03")
    private String c44X03;

    @ExcelProperty("C44X04")
    private String c44X04;

    @ExcelProperty("C44X05")
    private String c44X05;

    @ExcelProperty("C44X06")
    private String c44X06;

    @ExcelProperty("C44X07")
    private String c44X07;

    @ExcelProperty("C44X08")
    private String c44X08;

    @ExcelProperty("C44X09")
    private String c44X09;

    @ExcelProperty("C44X10")
    private String c44X10;

    @ExcelProperty("C44X11")
    private String c44X11;

    @ExcelProperty("C44X12")
    private String c44X12;

    @ExcelProperty("C44X13")
    private String c44X13;

    @ExcelProperty("C44X14")
    private String c44X14;

    @ExcelProperty("C44X15")
    private String c44X15;

    @ExcelProperty("C44X16")
    private String c44X16;

    @ExcelProperty("C44X17")
    private String c44X17;

    @ExcelProperty("C44X18")
    private String c44X18;

    @ExcelProperty("C44X19")
    private String c44X19;

    @ExcelProperty("C44X20")
    private String c44X20;

    @ExcelProperty("C44X21")
    private String c44X21;

    @ExcelProperty("C44X22")
    private String c44X22;

    @ExcelProperty("C44X23")
    private String c44X23;

    @ExcelProperty("C44X24")
    private String c44X24;

    @ExcelProperty("C44X25")
    private String c44X25;

    @ExcelProperty("C44X26")
    private String c44X26;

    @ExcelProperty("C44X27")
    private String c44X27;

    @ExcelProperty("C44X28")
    private String c44X28;

    @ExcelProperty("C44X29")
    private String c44X29;

    @ExcelProperty("C44X30")
    private String c44X30;

    @ExcelProperty("C44X31")
    private String c44X31;

    @ExcelProperty("C44X32")
    private String c44X32;

    @ExcelProperty("C44X33")
    private String c44X33;

    @ExcelProperty("C44X34")
    private String c44X34;

    @ExcelProperty("C44X35")
    private String c44X35;

    @ExcelProperty("C44X36")
    private String c44X36;

    @ExcelProperty("C44X37")
    private String c44X37;

    @ExcelProperty("C44X38")
    private String c44X38;

    @ExcelProperty("C44X39")
    private String c44X39;

    @ExcelProperty("C44X40")
    private String c44X40;

}

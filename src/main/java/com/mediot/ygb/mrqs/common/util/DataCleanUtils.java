package com.mediot.ygb.mrqs.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.cache.selector.SimpleReadCacheSelector;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.mediot.ygb.mrqs.analysis.medRecManage.dao.TDatacleanStandardMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.entity.TDatacleanStandard;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.StandardCodeEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl.HQMSCleanStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl.JXKHCleanStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl.WTCleanStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.strategyContext.DataCleanStrategyContext;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.common.excelListener.DataCleanListener;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.File;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DataCleanUtils {

    private static Object lock1=new Object();
    private static Object lock2=new Object();

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void importDataOfDataClean(String path, TDatacleanStandardMapper tDatacleanStandardMapper){
        File file=new File(path);
        DataCleanListener dataCleanListener= new DataCleanListener(tDatacleanStandardMapper);
        EasyExcel.read(file.getAbsolutePath(),dataCleanListener).readCacheSelector(new SimpleReadCacheSelector(500, 1000))
                .sheet().headRowNumber(1).doRead();
    }

    public static void createHQMSStandardList(Object o, FileAnalysisDto fileAnalysisDto){
        //把数据转换过来
        String[] datas=(String[])o;
        try {
            fileAnalysisDto.setCellNameList(Stream.of(datas).collect(Collectors.toList()));
            //String standardLabel= StandardCodeEnum.getStateLabel(fileAnalysisDto.getStandardCode());
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("STANDARD_TYPE","HQMS");
            //queryMap.put("must_fill","是");
            List<TDatacleanStandard> tDatacleanStandardList = fileAnalysisDto.getTDatacleanStandardMapper().selectList(queryWrapper);
            fileAnalysisDto.setTDatacleanStandardList(tDatacleanStandardList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void cleanCaseDataBySC(DataCleanRequest dataCleanRequest){
        if(dataCleanRequest.getFileAnalysisDto().getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.HQMS))) {
            new DataCleanStrategyContext(new HQMSCleanStrategy(dataCleanRequest)).cleanData();
        }else if(dataCleanRequest.getFileAnalysisDto().getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.WT))){
            new DataCleanStrategyContext(new WTCleanStrategy(dataCleanRequest)).cleanData();
        }else if(dataCleanRequest.getFileAnalysisDto().getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.JXKH))){
            new DataCleanStrategyContext(new JXKHCleanStrategy(dataCleanRequest)).cleanData();
        }else {
            ProgressVo progressVo=new ProgressVo();
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL));
            progressVo.setProgress(0.00);
            progressVo.setAnalysisStatus(false);
            progressVo.setErrorMsg("该文件不符合HQMS,卫统或绩效标准！");
            dataCleanRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().set(dataCleanRequest.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL),progressVo);
        }
    }

    public static Map<String,Object>  getCaseListBeforeInsertForHQMS(List dataList,FileAnalysisDto fileAnalysisDto){
        Map<String,Object> resultMap=Maps.newHashMap();
        try {
            List<TFirstPageTesting> tFirstPageTestingList=Lists.newArrayList();
            List<TFirstoutdiagTesting> tFirstoutdiagTestingList=Lists.newArrayList();
            List<TFirstoutoperTesting> tFirstoutoperTestingList=Lists.newArrayList();
            Map<String,Object> outDiagMap=Maps.newHashMap();
            String[] diagStArr=new String[]{"P321","P324","P327","P3291","P3294","P3297","P3281","P3284","P3287","P3271","P3274","P351"
                    ,"P351","P353","P355"};
            String[] diagInfoArr=new String[]{
                    "P321","P324","P327","P3291","P3294","P3297","P3281","P3284","P3287","P3271","P3274"
                    ,"P322","P325","P328","P3292","P3295","P3298","P3282","P3285","P3288","P3272","P3275"
                    ,"P805","P806","P807","P808","P809","P810","P811","P812","P813","P814","P815"
                    ,"P323","P326","P329","P3293","P3296","P3299","P3283","P3286","P3289","P3273","P3276"
                    ,"P351","P352","P816"
                    ,"P353","P354","P817"
                    ,"P354","P355","P818"
                    ,"P361","P362"
                    ,"P363","P364"
                    ,"P365","P366"
                    ,"P688"
            };
            Map<String,Object> outOperMap=Maps.newHashMap();
            //String[] operStArr=new String[]{"P490","P4911","P4922","P4533","P4544","P45002","P45014","P45026","P45038","P45050"};
            String[] operInfoArr=new String[]{
                     "P490","P491","P820","P492","P493","P494","P495","P496","P497","P498","P4981","P499","P4910",
                    "P4911","P4912","P4913","P4914","P4915","P4916","P4917","P4918","P4919","P4982","P4920","P4921",
                    "P4922","P4923","P4924","P4925","P4926","P4927","P4928","P4929","P4930","P4983","P4931","P4932",
                    "P4533","P4534","P4535","P4536","P4537","P4538","P4539","P4540","P4541","P4984","P4532","P4543",
                    "P4544","P4545","P4546","P4547","P4548","P4549","P4550","P4551","P4552","P4985","P4553","P4554",
                    "P45002","P45003","P45004","P45005","P45006","P45007","P45008","P45009","P45010","P45011","P45012","P45013",
                    "P45014","P45015","P45016","P45017","P45018","P45019","P45020","P45021","P45022","P45023","P45024","P45025",
                    "P45026","P45027","P45028","P45029","P45030","P45031","P45032","P45033","P45034","P45035","P45036","P45037",
                    "P45038","P45039","P45040","P45041","P45042","P45043","P45044","P45045","P45046","P45047","P45048","P45049",
                    "P45050","P45051","P45052","P45053","P45054","P45055","P45056","P45057","P45058","P45059","P45060","P45061"
            };
            dataList.stream().forEach(e->{
                //转换基本信息
                LinkedHashMap o=(LinkedHashMap)e;
                TFirstPageTesting tFirstPageTesting=new TFirstPageTesting();
                //continueout:
                o.forEach((k,v)->{
                    try {
                        String tInfo=(String) k;
                        String[] resultArr=tInfo.split("&");
                        String dcName=resultArr[1];
                        String dcVal=resultArr[2].equals("null")?"":resultArr[2];
                        if(dcVal==null){
                            dcVal="";
                        }
                        if(dcVal.equals("-")){
                            dcVal="";
                        }
                        tFirstPageTesting.setBatchId(fileAnalysisDto.getBatchId());
                        tFirstPageTesting.setUpOrgId(fileAnalysisDto.getUpOrgId());
                        if(dcName.equals("P6891")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setOrgName(null);
                            }else {
                                tFirstPageTesting.setOrgName(dcVal);
                            }
                            return;
                        }
                        if(dcName.equals("P900")){
                            try {
                                tFirstPageTesting.setOrgId(dcVal);
                            }catch (Exception e1){
                                System.out.println(e1.getMessage());
                            }
                            return;
                        }
                        if(dcName.equals("P686")){
                            tFirstPageTesting.setMedicareCard(dcVal);
                            return;
                        }
                        if(dcName.equals("P800")){
                            tFirstPageTesting.setCardNo(dcVal);
                            return;
                        }
                        if(dcName.equals("P1")){
                            tFirstPageTesting.setPayWayCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P2")){
                            tFirstPageTesting.setInHospitalTimes(dcVal);
                            return;
                        }
                        if(dcName.equals("P3")){
                            tFirstPageTesting.setCaseNo(dcVal);
                            return;
                        }
                        if(dcName.equals("P4")){
                            tFirstPageTesting.setFname(dcVal);
                            return;
                        }
                        if(dcName.equals("P5")){
                            tFirstPageTesting.setSexCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P6")){
                            tFirstPageTesting.setBirthDate(DateUtils.parse2DateByTimeStr(dcVal));
                            return;
                        }
                        if(dcName.equals("P7")){
                            tFirstPageTesting.setAge(Integer.valueOf(dcVal));
                            return;
                        }
                        if(dcName.equals("P8")){
                            tFirstPageTesting.setMarriageCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P9")){
                            tFirstPageTesting.setOccupationCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P101")){
                            tFirstPageTesting.setBirthAddrProvince(dcVal);
                            return;
                        }
                        if(dcName.equals("P102")){
                            tFirstPageTesting.setBirthAddrCity(dcVal);
                            return;
                        }
                        if(dcName.equals("P103")){
                            tFirstPageTesting.setBirthAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("P11")){
                            tFirstPageTesting.setNationalityCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P12")){
                            tFirstPageTesting.setCountry(dcVal);
                            return;
                        }
                        if(dcName.equals("P13")){
                            tFirstPageTesting.setIdNo(dcVal);
                            return;
                        }
                        if(dcName.equals("P801")){
                            tFirstPageTesting.setPresentAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("P802")){
                            tFirstPageTesting.setPresentAddrTel(dcVal);
                            return;
                        }
                        if(dcName.equals("P803")){
                            tFirstPageTesting.setPresentAddrPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P14")){
                            tFirstPageTesting.setEmployerAddr(dcVal);
                            return;
                        }
                        if(dcName.equals("P15")){
                            tFirstPageTesting.setEmployerTel(dcVal);
                            return;
                        }
                        if(dcName.equals("P16")){
                            tFirstPageTesting.setEmployerPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P17")){
                            tFirstPageTesting.setRegisterAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("P171")){
                            tFirstPageTesting.setRegisterAddrPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P18")){
                            tFirstPageTesting.setContactName(dcVal);
                            return;
                        }
                        if(dcName.equals("P19")){
                            tFirstPageTesting.setContactRelationship(dcVal);
                            return;
                        }
                        if(dcName.equals("P20")){
                            tFirstPageTesting.setContactAddr(dcVal);
                            return;
                        }
                        if(dcName.equals("P804")){
                            tFirstPageTesting.setInRoad(dcVal);
                            return;
                        }
                        if(dcName.equals("P21")){
                            tFirstPageTesting.setContactTel(dcVal);
                            return;
                        }
                        if(dcName.equals("P22")){
                            if(StringUtils.isNotBlank(dcVal)){
                                tFirstPageTesting.setInDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P23")){
                            tFirstPageTesting.setInDeptCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P231")){
                            tFirstPageTesting.setInDeptRoom(dcVal);
                            return;
                        }
                        if(dcName.equals("P24")){
                            tFirstPageTesting.setMoveDept(dcVal);
                            return;
                        }
                        if(dcName.equals("P25")){
                            if(StringUtils.isNotBlank(dcVal)){
                                tFirstPageTesting.setOutDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P26")){
                            tFirstPageTesting.setOutDeptCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P261")){
                            tFirstPageTesting.setOutDeptRoom(dcVal);
                            return;
                        }
                        if(dcName.equals("P27")){
                            tFirstPageTesting.setActualInDays(Long.valueOf(dcVal));
                            return;
                        }
                        if(dcName.equals("P28")){
                            tFirstPageTesting.setOutpatDiagCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P281")){
                            tFirstPageTesting.setOutpatDiagName(dcVal);
                            return;
                        }
                        if(dcName.equals("P29")){
                            tFirstPageTesting.setOnAdmission(dcVal);
                            return;
                        }
                        if(dcName.equals("P30")){
                            tFirstPageTesting.setInDiagCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P301")){
                            tFirstPageTesting.setInDiagName(dcVal);
                            return;
                        }
                        if(dcName.equals("P31")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setDateOfDiagnosis(null);
                            }else {
                                tFirstPageTesting.setDateOfDiagnosis(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P689")){
                            tFirstPageTesting.setTotalNumberOfInfections(dcVal);
                            return;
                        }
                        if(dcName.equals("P371")){
                            tFirstPageTesting.setAllergySource(dcVal);
                            return;
                        }
                        if(dcName.equals("P372")){
                            tFirstPageTesting.setNameOfAllergicDrug(dcVal);
                            return;
                        }
                        if(dcName.equals("P38")){
                            tFirstPageTesting.setHbsag(dcVal);
                            return;
                        }
                        if(dcName.equals("P39")){
                            tFirstPageTesting.setHcvAb(dcVal);
                            return;
                        }
                        if(dcName.equals("P40")){
                            tFirstPageTesting.setHivAb(dcVal);
                            return;
                        }
                        if(dcName.equals("P411")){
                            tFirstPageTesting.setClinic2out(dcVal);
                            return;
                        }
                        if(dcName.equals("P412")){
                            tFirstPageTesting.setIn2out(dcVal);
                            return;
                        }
                        if(dcName.equals("P413")){
                            tFirstPageTesting.setPreOper2oper(dcVal);
                            return;
                        }
                        if(dcName.equals("P414")){
                            tFirstPageTesting.setClinic2autopsy(dcVal);
                            return;
                        }
                        if(dcName.equals("P415")){
                            tFirstPageTesting.setRadiation2pathology(dcVal);
                            return;
                        }
                        if(dcName.equals("P421")){
                            tFirstPageTesting.setRescueTimes(dcVal);
                            return;
                        }
                        if(dcName.equals("P422")){
                            tFirstPageTesting.setRescueSuccessTimes(dcVal);
                            return;
                        }
                        if(dcName.equals("P687")){
                            tFirstPageTesting.setHighestDiagnosticBasis(dcVal);
                            return;
                        }
//                    if(dcName.equals("P688")){
//                        tFirstPageTesting.setHighestDiagnosticBasis(dcVal);
//                        return;
//                    }
                        if(dcName.equals("P431")){
                            tFirstPageTesting.setDeptDirector(dcVal);
                            return;
                        }
                        if(dcName.equals("P432")){
                            tFirstPageTesting.setChiefDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("P433")){
                            tFirstPageTesting.setInChargeDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("P434")){
                            tFirstPageTesting.setResidentDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("P819")){
                            tFirstPageTesting.setRespNurse(dcVal);
                            return;
                        }
                        if(dcName.equals("P435")){
                            tFirstPageTesting.setLearningDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("P436")){
                            tFirstPageTesting.setPostgraduateIntern(dcVal);
                            return;
                        }
                        if(dcName.equals("P437")){
                            tFirstPageTesting.setInternDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("P438")){
                            tFirstPageTesting.setCataloger(dcVal);
                            return;
                        }
                        if(dcName.equals("P44")){
                            tFirstPageTesting.setCaseQuality(dcVal);
                            return;
                        }
                        if(dcName.equals("P45")){
                            tFirstPageTesting.setQcDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("P46")){
                            tFirstPageTesting.setQcNurse(dcVal);
                            return;
                        }
                        if(dcName.equals("P47")){
                            tFirstPageTesting.setQcDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            return;
                        }
                        if(dcName.equals("P561")){
                            tFirstPageTesting.setDaysOfIntensiveCare(dcVal);
                            return;
                        }
                        if(dcName.equals("P562")){
                            tFirstPageTesting.setDaysOfPrimaryCare(dcVal);
                            return;
                        }
                        if(dcName.equals("P563")){
                            tFirstPageTesting.setDaysOfSecondaryCare(dcVal);
                            return;
                        }
                        if(dcName.equals("P564")){
                            tFirstPageTesting.setDaysOfTertiaryCare(dcVal);
                            return;
                        }
                        if(dcName.equals("P6911")){
                            tFirstPageTesting.setIcuName1(dcVal);
                            return;
                        }
                        if(dcName.equals("P6912")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setEntryTime1(null);
                            }else {
                                tFirstPageTesting.setEntryTime1(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6913")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setExitTime1(null);
                            }else {
                                tFirstPageTesting.setExitTime1(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6914")){
                            tFirstPageTesting.setIcuName2(dcVal);
                            return;
                        }
                        if(dcName.equals("P6915")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setEntryTime2(null);
                            }else {
                                tFirstPageTesting.setEntryTime2(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6916")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setExitTime2(null);
                            }else {
                                tFirstPageTesting.setExitTime2(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6917")){
                            tFirstPageTesting.setIcuName3(dcVal);
                            return;
                        }
                        if(dcName.equals("P6918")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setEntryTime3(null);
                            }else {
                                tFirstPageTesting.setEntryTime3(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6919")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setExitTime3(null);
                            }else {
                                tFirstPageTesting.setExitTime3(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6920")){
                            tFirstPageTesting.setIcuName4(dcVal);
                            return;
                        }
                        if(dcName.equals("P6921")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setEntryTime4(null);
                            }else {
                                tFirstPageTesting.setEntryTime4(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6922")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setExitTime4(null);
                            }else {
                                tFirstPageTesting.setExitTime4(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6923")){
                            tFirstPageTesting.setIcuName5(dcVal);
                            return;
                        }
                        if(dcName.equals("P6924")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setEntryTime5(null);
                            }else {
                                tFirstPageTesting.setEntryTime5(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P6925")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setExitTime5(null);
                            }else {
                                tFirstPageTesting.setExitTime5(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P57")){
                            tFirstPageTesting.setAutopsyMark(dcVal);
                            return;
                        }
                        if(dcName.equals("P58")){
                            tFirstPageTesting.setFirstCase(dcVal);
                            return;
                        }
                        if(dcName.equals("P581")){
                            tFirstPageTesting.setSurgicalPatientType(dcVal);
                            return;
                        }
                        if(dcName.equals("P60")){
                            tFirstPageTesting.setFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("P611")){
                            tFirstPageTesting.setWeeksOfFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("P612")){
                            tFirstPageTesting.setMonthsOfFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("P613")){
                            tFirstPageTesting.setYearsOfFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("P59")){
                            tFirstPageTesting.setTeachingCases(dcVal);
                            return;
                        }
                        if(dcName.equals("P62")){
                            tFirstPageTesting.setAboCode(dcVal);
                            return;
                        }
                        if(dcName.equals("P63")){
                            tFirstPageTesting.setRhCode9(dcVal);
                            return;
                        }
                        if(dcName.equals("P64")){
                            tFirstPageTesting.setTransfusionReaction(dcVal);
                            return;
                        }
                        if(dcName.equals("P651")){
                            tFirstPageTesting.setRedBloodCell(dcVal);
                            return;
                        }
                        if(dcName.equals("P652")){
                            tFirstPageTesting.setPlatelet(dcVal);
                            return;
                        }
                        if(dcName.equals("P653")){
                            tFirstPageTesting.setPlasma(dcVal);
                            return;
                        }
                        if(dcName.equals("P654")){
                            tFirstPageTesting.setWholeBlood(dcVal);
                            return;
                        }
                        if(dcName.equals("P655")){
                            tFirstPageTesting.setSelfRecovery(dcVal);
                            return;
                        }
                        if(dcName.equals("P656")){
                            tFirstPageTesting.setOtherInputs(dcVal);
                            return;
                        }
                        if(dcName.equals("P66")){
                            tFirstPageTesting.setAgeMonth(dcVal);
                            return;
                        }
                        if(dcName.equals("P681")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setBirthWeight(null);
                            }else {
                                tFirstPageTesting.setBirthWeight(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P682")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setBirthWeight2(null);
                            }else {
                                tFirstPageTesting.setBirthWeight2(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P683")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setBirthWeight3(null);
                            }else {
                                tFirstPageTesting.setBirthWeight3(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P684")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setBirthWeight4(null);
                            }else {
                                tFirstPageTesting.setBirthWeight4(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P685")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setBirthWeight5(null);
                            }else {
                                tFirstPageTesting.setBirthWeight5(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P67")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setInWeight(null);
                            }else {
                                tFirstPageTesting.setInWeight(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P731")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setComaDurationBeforeHour(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeHour(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P732")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setComaDurationBeforeMinute(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeMinute(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P733")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setComaDurationAfterHour(null);
                            }else {
                                tFirstPageTesting.setComaDurationAfterHour(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P734")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setComaDurationAfterMinute(null);
                            }else {
                                tFirstPageTesting.setComaDurationAfterMinute(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P72")){
                            tFirstPageTesting.setVentilatorUseTime(dcVal);
                            return;
                        }
                        if(dcName.equals("P830")){
                            tFirstPageTesting.setRehospAfter31Mark(dcVal);
                            return;
                        }
                        if(dcName.equals("P831")){
                            tFirstPageTesting.setRehospAfter31Purpose(dcVal);
                            return;
                        }
                        if(dcName.equals("P741")){
                            tFirstPageTesting.setDischargeClass(dcVal);
                            return;
                        }
                        if(dcName.equals("P742")){
                            tFirstPageTesting.setOrderReferralOrg(dcVal);
                            return;
                        }
                        if(dcName.equals("P743")){
                            tFirstPageTesting.setOrderReferralOrg(dcVal);
                            return;
                        }
                        if(dcName.equals("P782")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setFeeTotal(null);
                            }else {
                                tFirstPageTesting.setFeeTotal(new BigDecimal(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("P751")){
                            tFirstPageTesting.setFeeSelfPay(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P752")){
                            tFirstPageTesting.setFeeGeneralMedical(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P754")){
                            tFirstPageTesting.setFeeGeneralTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P755")){
                            tFirstPageTesting.setFeeTend(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P756")){
                            tFirstPageTesting.setFeeMedicalOther(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P757")){
                            tFirstPageTesting.setFeePathology(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P758")){
                            tFirstPageTesting.setFeeLaboratory(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P759")){
                            tFirstPageTesting.setFeeImaging(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P760")){
                            tFirstPageTesting.setFeeClinc(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P761")){
                            tFirstPageTesting.setFeeNonsurgicalTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P762")){
                            tFirstPageTesting.setFeeClinPhysical(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P763")){
                            tFirstPageTesting.setFeeSurgicalTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P764")){
                            tFirstPageTesting.setFeeAnaes(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P765")){
                            tFirstPageTesting.setFeeOperation(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P767")){
                            tFirstPageTesting.setFeeRecovery(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P768")){
                            tFirstPageTesting.setFeeCnTreatment(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P769")){
                            tFirstPageTesting.setFeeWesternMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P770")){
                            tFirstPageTesting.setFeeAntimicrobial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P771")){
                            tFirstPageTesting.setFeeCnMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P772")){
                            tFirstPageTesting.setFeeCnHerbalMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P773")){
                            tFirstPageTesting.setFeeBlood(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P774")){
                            tFirstPageTesting.setFeeAlbumin(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P775")){
                            tFirstPageTesting.setFeeGlobulin(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P776")){
                            tFirstPageTesting.setFeeBcf(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P777")){
                            tFirstPageTesting.setFeeCytokine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P778")){
                            tFirstPageTesting.setFeeCheckMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P779")){
                            tFirstPageTesting.setFeeTreatMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P780")){
                            tFirstPageTesting.setFeeOperMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("P781")){
                            tFirstPageTesting.setFeeOther(new BigDecimal(dcVal));
                            return;
                        }
                        tFirstPageTesting.setCreateTime(new Date());
                        if(Arrays.asList(diagInfoArr).contains(dcName)){
                            outDiagMap.put(dcName,dcVal);
                            return;
                        }
                        if(Arrays.asList(operInfoArr).contains(dcName)){
                            outOperMap.put(dcName,dcVal);
                            return;
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                });
                long nowDate = new Date().getTime();
                String sid = Integer.toHexString((int)nowDate);
                tFirstPageTesting.setTempSid(sid);
                tFirstPageTestingList.add(tFirstPageTesting);
                //转换诊断信息
                parseToDiagInfo(outDiagMap,tFirstPageTesting,tFirstoutdiagTestingList);
                if(tFirstoutdiagTestingList.size()==0){
                    resultMap.put("diagInfo",null);
                }else {
                    resultMap.put("diagInfo",tFirstoutdiagTestingList);
                }
                //转换手术信息
                parseToOPerInfo(outOperMap,tFirstPageTesting,tFirstoutoperTestingList);
                if(tFirstoutoperTestingList.size()==0){
                    resultMap.put("operInfo",null);
                }else {
                    resultMap.put("operInfo",tFirstoutoperTestingList);
                }
            });
            resultMap.put("firtPageInfo",tFirstPageTestingList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    public static void parseToOPerInfoForJXKH(Map<String,Object> outOperMap,TFirstPageTesting tFirstPageTesting,List<TFirstoutoperTesting> tFirstoutoperTestingList){
        if(!StringUtils.isBlank((String)outOperMap.get("C14X01C"))){
            String[][] orderSTArr=new String[41][10];
            orderSTArr[0]=new String[]{ "C14X01C","C16X01","C17X01","C15X01N","C18X01","C19X01","C20X01","C22X01C","C21X01C","C23X01"};
            for(int i=1;i<=40;i++){
                String[] orderData=new String[10];
                StringBuffer sb1=new StringBuffer();
                StringBuffer sb2=new StringBuffer();
                StringBuffer sb3=new StringBuffer();
                StringBuffer sb4=new StringBuffer();
                StringBuffer sb5=new StringBuffer();
                StringBuffer sb6=new StringBuffer();
                StringBuffer sb7=new StringBuffer();
                StringBuffer sb8=new StringBuffer();
                StringBuffer sb9=new StringBuffer();
                StringBuffer sb10=new StringBuffer();
                if(i<9){
                    orderData[0]=sb1.append("C35X").append(0).append(i).append("C").toString();
                    orderData[1]=sb2.append("C37X").append(0).append(i).toString();
                    orderData[2]=sb3.append("C38X").append(0).append(i).toString();
                    orderData[3]=sb4.append("C36X").append(0).append(i).append("N").toString();
                    orderData[4]=sb5.append("C39X").append(0).append(i).toString();
                    orderData[5]=sb6.append("C40X").append(0).append(i).toString();
                    orderData[6]=sb7.append("C41X").append(0).append(i).toString();
                    orderData[7]=sb8.append("C43X").append(0).append(i).append("C").toString();
                    orderData[8]=sb9.append("C42X").append(0).append(i).append("C").toString();
                    orderData[9]=sb10.append("C08X").append(0).append(i).toString();
                }else {
                    orderData[0]=sb1.append("C35X").append(i).append("C").toString();
                    orderData[1]=sb2.append("C37X").append(i).toString();
                    orderData[2]=sb3.append("C38X").append(i).toString();
                    orderData[3]=sb4.append("C36X").append(i).append("N").toString();
                    orderData[4]=sb5.append("C39X").append(i).toString();
                    orderData[5]=sb6.append("C40X").append(i).toString();
                    orderData[6]=sb7.append("C41X").append(i).toString();
                    orderData[7]=sb8.append("C43X").append(i).append("C").toString();
                    orderData[8]=sb9.append("C42X").append(i).append("C").toString();
                    orderData[9]=sb10.append("C08X").append(i).toString();
                }
                orderSTArr[i]=orderData;
            }
            Stream.of(orderSTArr).forEach(e->{
                if(e[0].equals("C14X01C")){
                    TFirstoutoperTesting t=new TFirstoutoperTesting();
                    t.setTempSid(tFirstPageTesting.getTempSid());
                    t.setOperationType("01");
                    t.setBatchId(tFirstPageTesting.getBatchId());
                    t.setCaseId(tFirstPageTesting.getCaseNo());
                    t.setOperationCode((String)outOperMap.get(e[0]));
                    t.setOperationDtime((String)outOperMap.get(e[1]));
                    t.setOperationLevel((String)outOperMap.get(e[2]));
                    t.setOperationName((String)outOperMap.get(e[3]));
                    //t.setBodyPart((String)outOperMap.get(e[4]));
                    t.setSurgeon((String)outOperMap.get(e[4]));
                    t.setAssistant1((String)outOperMap.get(e[5]));
                    t.setAssistant2((String)outOperMap.get(e[6]));
                    t.setAnesthesiaMode((String)outOperMap.get(e[7]));
                    //t.setAnesthesiaLevel((String)outOperMap.get(e[10]));
                    t.setIncisionHealing((String)outOperMap.get(e[8]));
                    t.setAnesthesiologist((String)outOperMap.get(e[9]));
                    t.setOperationOrder(1);
                    if(!(outOperMap.get(e[0])).equals("-")){
                        tFirstoutoperTestingList.add(t);
                    }
                    return;
                }else {
                    if(StringUtils.isNotBlank((String)outOperMap.get(e[0]))){
                        TFirstoutoperTesting t=new TFirstoutoperTesting();
                        t.setTempSid(tFirstPageTesting.getTempSid());
                        t.setOperationType("02");
                        t.setBatchId(tFirstPageTesting.getBatchId());
                        t.setCaseId(tFirstPageTesting.getCaseNo());
                        t.setOperationCode((String)outOperMap.get(e[0]));
                        t.setOperationDtime((String)outOperMap.get(e[1]));
                        t.setOperationLevel((String)outOperMap.get(e[2]));
                        t.setOperationName((String)outOperMap.get(e[3]));
                        //t.setBodyPart((String)outOperMap.get(e[4]));
                        t.setSurgeon((String)outOperMap.get(e[4]));
                        t.setAssistant1((String)outOperMap.get(e[5]));
                        t.setAssistant2((String)outOperMap.get(e[6]));
                        t.setAnesthesiaMode((String)outOperMap.get(e[7]));
                        //t.setAnesthesiaLevel((String)outOperMap.get(e[10]));
                        t.setIncisionHealing((String)outOperMap.get(e[8]));
                        t.setAnesthesiologist((String)outOperMap.get(e[9]));
                        String od=e[0].substring(5,6);
                        t.setOperationOrder(Integer.valueOf(od));
                        if(!(outOperMap.get(e[0])).equals("-")){
                            tFirstoutoperTestingList.add(t);
                        }
                        return;
                    }
                }
            });
        }
    }

    public static void parseToOPerInfo(Map<String,Object> outOperMap,TFirstPageTesting tFirstPageTesting,List<TFirstoutoperTesting> tFirstoutoperTestingList){
        if(!StringUtils.isBlank((String)outOperMap.get("P490"))){
            String[][] orderSTArr={
                    { "P490","P491","P820","P492","P493","P494","P495","P496","P497","P498","P4981","P499","P4910"},
                    {"P4911","P4912","P821","P4913","P4914","P4915","P4916","P4917","P4918","P4919","P4982","P4920","P4921"},
                    {"P4922","P4923","P822","P4924","P4925","P4926","P4927","P4928","P4929","P4930","P4983","P4931","P4932"},
                    {"P4533","P4534","P823","P4535","P4536","P4537","P4538","P4539","P4540","P4541","P4984","P4532","P4543"},
                    {"P4544","P4545","P824","P4546","P4547","P4548","P4549","P4550","P4551","P4552","P4985","P4553","P4554"},
                    {"P45002","P45003","P825","P45004","P45005","P45006","P45007","P45008","P45009","P45010","P45011","P45012","P45013"},
                    {"P45014","P45015","P826","P45016","P45017","P45018","P45019","P45020","P45021","P45022","P45023","P45024","P45025"},
                    {"P45026","P45027","P827","P45028","P45029","P45030","P45031","P45032","P45033","P45034","P45035","P45036","P45037"},
                    {"P45038","P45039","P828","P45040","P45041","P45042","P45043","P45044","P45045","P45046","P45047","P45048","P45049"},
                    {"P45050","P45051","P829","P45052","P45053","P45054","P45055","P45056","P45057","P45058","P45059","P45060","P45061"}
            };
            Stream.of(orderSTArr).forEach(e->{
                if(e[0].equals("P490")){
                    TFirstoutoperTesting t=new TFirstoutoperTesting();
                    t.setTempSid(tFirstPageTesting.getTempSid());
                    t.setOperationType("01");
                    t.setBatchId(tFirstPageTesting.getBatchId());
                    t.setCaseId(tFirstPageTesting.getCaseNo());
                    t.setOperationCode((String)outOperMap.get(e[0]));
                    t.setOperationDtime((String)outOperMap.get(e[1]));
                    t.setOperationLevel((String)outOperMap.get(e[2]));
                    t.setOperationName((String)outOperMap.get(e[3]));
                    t.setBodyPart((String)outOperMap.get(e[4]));
                    if(StringUtils.isNotBlank((String)outOperMap.get(e[5]))){
                        t.setOperDuration(new BigDecimal((String)outOperMap.get(e[5])));
                    }else {
                        t.setOperDuration(null);
                    }
                    t.setSurgeon((String)outOperMap.get(e[6]));
                    t.setAssistant1((String)outOperMap.get(e[7]));
                    t.setAssistant2((String)outOperMap.get(e[8]));
                    t.setAnesthesiaMode((String)outOperMap.get(e[9]));
                    t.setAnesthesiaLevel((String)outOperMap.get(e[10]));
                    t.setIncisionHealing((String)outOperMap.get(e[11]));
                    t.setAnesthesiologist((String)outOperMap.get(e[12]));
                    t.setOperationOrder(1);
                    tFirstoutoperTestingList.add(t);
                    return;
                }else {
                    if(StringUtils.isNotBlank((String)outOperMap.get(e[0]))){
                        TFirstoutoperTesting t=new TFirstoutoperTesting();
                        t.setTempSid(tFirstPageTesting.getTempSid());
                        t.setOperationType("02");
                        t.setBatchId(tFirstPageTesting.getBatchId());
                        t.setCaseId(tFirstPageTesting.getCaseNo());
                        t.setOperationCode((String)outOperMap.get(e[0]));
                        t.setOperationDtime((String)outOperMap.get(e[1]));
                        t.setOperationLevel((String)outOperMap.get(e[2]));
                        t.setOperationName((String)outOperMap.get(e[3]));
                        t.setBodyPart((String)outOperMap.get(e[4]));
                        if(StringUtils.isNotBlank((String)outOperMap.get(e[5]))){
                            t.setOperDuration(new BigDecimal((String)outOperMap.get(e[5])));
                        }else {
                            t.setOperDuration(null);
                        }
                        t.setSurgeon((String)outOperMap.get(e[6]));
                        t.setAssistant1((String)outOperMap.get(e[7]));
                        t.setAssistant2((String)outOperMap.get(e[8]));
                        t.setAnesthesiaMode((String)outOperMap.get(e[9]));
                        t.setAnesthesiaLevel((String)outOperMap.get(e[10]));
                        t.setIncisionHealing((String)outOperMap.get(e[11]));
                        t.setAnesthesiologist((String)outOperMap.get(e[12]));
                        if(e[0].equals("P4911")){
                            t.setOperationOrder(2);
                        }else if(e[0].equals("P4922")){
                            t.setOperationOrder(3);
                        }else if(e[0].equals("P4533")){
                            t.setOperationOrder(4);
                        }else if(e[0].equals("P4933")){
                            t.setOperationOrder(5);
                        }else if(e[0].equals("P4544")){
                            t.setOperationOrder(6);
                        }else if(e[0].equals("P45002")){
                            t.setOperationOrder(7);
                        }else if(e[0].equals("P45014")){
                            t.setOperationOrder(8);
                        }else if(e[0].equals("P45026")){
                            t.setOperationOrder(9);
                        }else if(e[0].equals("P45038")){
                            t.setOperationOrder(10);
                        }else if(e[0].equals("P45050")){
                            t.setOperationOrder(11);
                        }
                        tFirstoutoperTestingList.add(t);
                        return;
                    }
                }
            });
        }
    }

    public static void parseToDiagInfoForJXKH(Map<String,Object> outDiagMap,TFirstPageTesting tFirstPageTesting,List<TFirstoutdiagTesting> tFirstoutdiagTestingList){
        try {
            if(!StringUtils.isBlank((String)outDiagMap.get("C03C"))){
                String[][] orderSTArr=new String[43][3];
                orderSTArr[0]=new String[]{"C03C","C04N","C05C"};
                orderSTArr[1]=new String[]{"C09C","C10N","C11"};
                orderSTArr[2]=new String[]{"C12C","C13N"};
                for(int i=1;i<=40;i++){
                    String[] orderData=new String[3];
                    StringBuffer sb1=new StringBuffer();
                    StringBuffer sb2=new StringBuffer();
                    StringBuffer sb3=new StringBuffer();
                    if(i<9){
                        orderData[0]=sb1.append("C06X").append(0).append(i).append("C").toString();
                        orderData[1]=sb2.append("C07X").append(0).append(i).append("N").toString();
                        orderData[2]=sb3.append("C08X").append(0).append(i).append("C").toString();
                    }else {
                        orderData[0]=sb1.append("C06X").append(i).append("C").toString();
                        orderData[1]=sb2.append("C06X").append(i).append("N").toString();
                        orderData[2]=sb3.append("C08X").append(i).append("C").toString();
                    }
                    orderSTArr[i+2]=orderData;
                }
                Stream.of(orderSTArr).forEach(e->{
                    if(e[0].equals("C03C")){
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("01");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setDiagOrder(1);
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setInCondition((String)outDiagMap.get(e[2]));
                            if(!((String)outDiagMap.get(e[0])).equals("-")){
                                tFirstoutdiagTestingList.add(t);
                            }
                            return;
                        }
                    }else if(e[0].equals("C09C")){
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("03");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setDiagOrder(2);
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setPathologicalNumber((String)outDiagMap.get(e[2]));
                            if(!((String)outDiagMap.get(e[0])).equals("-")){
                                tFirstoutdiagTestingList.add(t);
                            }
                            return;
                        }
                    }else if(e[0].equals("C12C")){
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("04");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setDiagOrder(1);
                            if(!((String)outDiagMap.get(e[0])).equals("-")){
                                tFirstoutdiagTestingList.add(t);
                            }
                            return;
                        }
                    }else {
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("02");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setInCondition((String)outDiagMap.get(e[2]));
                            String od=((String) outDiagMap.get(e[0])).substring(5,7);
                            t.setDiagOrder(Integer.valueOf(od));
                            if(!(outDiagMap.get(e[0])).equals("-")){
                                tFirstoutdiagTestingList.add(t);
                            }
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void parseToDiagInfo(Map<String,Object> outDiagMap,TFirstPageTesting tFirstPageTesting,List<TFirstoutdiagTesting> tFirstoutdiagTestingList){
        if(!StringUtils.isBlank((String)outDiagMap.get("P321"))){
            String[][] orderSTArr={
                    {"P321","P322","P805","P323"},
                    {"P324","P325","P806","P326"},
                    {"P327","P328","P807","P329"},
                    {"P3291","P3292","P808","P3293"},
                    {"P3294","P3295","P809","P3296"},
                    {"P3297","P3298","P810","P3299"},
                    {"P3281","P3282","P811","P3283"},
                    {"P3284","P3285","P812","P3286"},
                    {"P3287","P3288","P813","P3289"},
                    {"P3271","P3272","P814","P3273"},
                    {"P3274","P3275","P815","P3276"},
                    {"P351","P352","P816"},
                    {"P353","P384","P817"},
                    {"P355","P356","P818"},
                    {"P361","P362"},
                    {"P363","P364"},
                    {"P365","P366"}
            };
            Stream.of(orderSTArr).forEach(e->{
                if(e[0].equals("P351")||e[0].equals("P353")||e[0].equals("P355")){
                    if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                        TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                        t.setTempSid(tFirstPageTesting.getTempSid());
                        t.setDiagType("03");
                        t.setBatchId(tFirstPageTesting.getBatchId());
                        t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                        t.setDiagnosisName((String)outDiagMap.get(e[1]));
                        t.setPathologicalNumber((String)outDiagMap.get(e[2]));
                        t.setDiagOrder(Integer.valueOf(e[0].substring(e[0].length()-1)));
                        t.setDegreeOfDifferentiation((String) outDiagMap.get("P688"));
                        t.setCaseId(tFirstPageTesting.getCaseNo());
                        tFirstoutdiagTestingList.add(t);
                        return;
                    }
                }else if(e[0].equals("P361")||e[0].equals("P363")||e[0].equals("P365")){
                    if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                        TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                        t.setTempSid(tFirstPageTesting.getTempSid());
                        t.setDiagType("04");
                        t.setBatchId(tFirstPageTesting.getBatchId());
                        t.setDiagOrder(Integer.valueOf(e[0].substring(e[0].length()-1)));
                        t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                        t.setDiagnosisName((String)outDiagMap.get(e[1]));
                        t.setCaseId(tFirstPageTesting.getCaseNo());
                        tFirstoutdiagTestingList.add(t);
                        return;
                    }
                }else if(e[0].equals("P321")){
                    TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                    t.setTempSid(tFirstPageTesting.getTempSid());
                    t.setDiagType("01");
                    t.setBatchId(tFirstPageTesting.getBatchId());
                    t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                    t.setDiagnosisName((String)outDiagMap.get(e[1]));
                    t.setInCondition((String)outDiagMap.get(e[2]));
                    t.setDischargeStatus((String)outDiagMap.get(e[3]));
                    t.setDiagOrder(Integer.valueOf(e[0].substring(e[0].length()-1)));
                    t.setCaseId(tFirstPageTesting.getCaseNo());
                    tFirstoutdiagTestingList.add(t);
                    return;
                }else {
                    if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                        TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                        t.setTempSid(tFirstPageTesting.getTempSid());
                        t.setDiagType("02");
                        t.setBatchId(tFirstPageTesting.getBatchId());
                        t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                        t.setDiagnosisName((String)outDiagMap.get(e[1]));
                        t.setInCondition((String)outDiagMap.get(e[2]));
                        t.setDischargeStatus((String)outDiagMap.get(e[3]));
                        t.setDiagOrder(Integer.valueOf(e[0].substring(e[0].length()-1)));
                        t.setCaseId(tFirstPageTesting.getCaseNo());
                        tFirstoutdiagTestingList.add(t);
                    }
                }
            });
        }
    }


    public static Map<String, Object> getCaseListBeforeInsertForJXKH(List dataList,FileAnalysisDto fileAnalysisDto) {
        Map<String,Object> resultMap=Maps.newHashMap();
        try {
            List<TFirstPageTesting> tFirstPageTestingList=Lists.newArrayList();
            List<TFirstoutdiagTesting> tFirstoutdiagTestingList=Lists.newArrayList();
            List<TFirstoutoperTesting> tFirstoutoperTestingList=Lists.newArrayList();
            Map<String,Object> outDiagMap=Maps.newHashMap();
            //构造有序集合
            String[] diagInfoArr=new String[]{
                    "C03C","C04N","C05C",
                    "C09C","C10N","C11",
                    "C12C","C13N"
            };
            List<String> dlist=Stream.of(diagInfoArr).collect(Collectors.toList());
            for(int i=1;i<=40;i++){
                StringBuffer sb1=new StringBuffer();
                StringBuffer sb2=new StringBuffer();
                StringBuffer sb3=new StringBuffer();
                if(i<9){
                    sb1.append("C06X").append(0).append(i).append("C");
                    sb2.append("C07X").append(0).append(i).append("N");
                    sb3.append("C08X").append(0).append(i).append("C");
                }else {
                    sb1.append("C06X").append(i).append("C");
                    sb2.append("C07X").append(i).append("N");
                    sb3.append("C08X").append(i).append("C");
                }
                dlist.add(sb1.toString());
                dlist.add(sb2.toString());
                dlist.add(sb3.toString());
            }
            Map<String,Object> outOperMap=Maps.newHashMap();
            //构造有序集合
            String[] operInfoArr=new String[]{
                    "C14X01C","C16X01","C17X01","C15X01N","C18X01","C19X01","C20X01","C22X01C","C21X01C","C23X01"
            };
            List<String> olist=Stream.of(operInfoArr).collect(Collectors.toList());
            for(int i=1;i<=40;i++){
                StringBuffer sb1=new StringBuffer();
                StringBuffer sb2=new StringBuffer();
                StringBuffer sb3=new StringBuffer();
                StringBuffer sb4=new StringBuffer();
                StringBuffer sb5=new StringBuffer();
                StringBuffer sb6=new StringBuffer();
                StringBuffer sb7=new StringBuffer();
                StringBuffer sb8=new StringBuffer();
                StringBuffer sb9=new StringBuffer();
                StringBuffer sb10=new StringBuffer();
                if(i<9){
                    sb1.append("C35X").append(0).append(i).append("C");
                    sb2.append("C37X").append(0).append(i);
                    sb3.append("C38X").append(0).append(i);
                    sb4.append("C36X").append(0).append(i).append("N");
                    sb5.append("C39X").append(0).append(i);
                    sb6.append("C40X").append(0).append(i);
                    sb7.append("C41X").append(0).append(i);
                    sb8.append("C43X").append(0).append(i).append("C");
                    sb9.append("C42X").append(0).append(i).append("C");
                    sb10.append("C44X").append(0).append(i);
                }else {
                    sb1.append("C35X").append(i).append("C");
                    sb2.append("C37X").append(i);
                    sb3.append("C38X").append(i);
                    sb4.append("C36X").append(i).append("N");
                    sb5.append("C39X").append(i);
                    sb6.append("C40X").append(i);
                    sb7.append("C41X").append(i);
                    sb8.append("C43X").append(i).append("C");
                    sb9.append("C42X").append(i).append("C");
                    sb10.append("C44X").append(i);
                }
                olist.add(sb1.toString());
                olist.add(sb2.toString());
                olist.add(sb3.toString());
                olist.add(sb4.toString());
                olist.add(sb5.toString());
                olist.add(sb6.toString());
                olist.add(sb7.toString());
                olist.add(sb8.toString());
                olist.add(sb9.toString());
                olist.add(sb10.toString());
            }
            dataList.stream().forEach(e->{
                //转换基本信息
                LinkedHashMap o=(LinkedHashMap)e;
                TFirstPageTesting tFirstPageTesting=new TFirstPageTesting();
                try {
                    //continueout:
                    o.forEach((k,v)->{
                        String tInfo=(String) k;
                        String[] resultArr=tInfo.split("&");
                        String dcName=resultArr[1];
                        String dcVal=resultArr[2].equals("null")?"":resultArr[2];
                        if(dcVal==null){
                            dcVal="";
                        }
                        if(dcVal.equals("-")){
                            dcVal="";
                        }
                        tFirstPageTesting.setBatchId(fileAnalysisDto.getBatchId());
                        tFirstPageTesting.setUpOrgId(fileAnalysisDto.getUpOrgId());
                        if(dcName.equals("A02")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setOrgName(null);
                            }else {
                                tFirstPageTesting.setOrgName(dcVal);
                            }
                            return;
                        }
                        if(dcName.equals("A01")){
                            try {
                                tFirstPageTesting.setOrgId(dcVal);
                            }catch (Exception e1){
                                 System.out.println(e1.getMessage());
                            }
                            return;
                        }
                        if(dcName.equals("A47")){
                            tFirstPageTesting.setCardNo(dcVal);
                            return;
                        }
                        if(dcName.equals("A46C")){
                            tFirstPageTesting.setPayWayCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A49")){
                            tFirstPageTesting.setInHospitalTimes(dcVal);
                            return;
                        }
                        if(dcName.equals("A48")){
                            tFirstPageTesting.setCaseNo(dcVal);
                            return;
                        }
                        if(dcName.equals("A11")){
                            tFirstPageTesting.setFname(dcVal);
                            return;
                        }
                        if(dcName.equals("A12C")){
                            tFirstPageTesting.setSexCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A13")){
                            tFirstPageTesting.setBirthDate(DateUtils.parse2DateByTimeStr(dcVal));
                            return;
                        }
                        if(dcName.equals("A14")){
                            tFirstPageTesting.setAge(Integer.valueOf(dcVal));
                            return;
                        }
                        if(dcName.equals("A21C")){
                            tFirstPageTesting.setMarriageCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A38C")){
                            tFirstPageTesting.setOccupationCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A22")){
                            tFirstPageTesting.setBirthAddrProvince(dcVal);
                            return;
                        }
                        if(dcName.equals("A23C")){
                            tFirstPageTesting.setNativeAddrProvince(dcVal);
                            return;
                        }
                        if(dcName.equals("A19C")){
                            tFirstPageTesting.setNationalityCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A15C")){
                            tFirstPageTesting.setCountry(dcVal);
                            return;
                        }
                        if(dcName.equals("A20")){
                            tFirstPageTesting.setIdNo(dcVal);
                            return;
                        }
                        if(dcName.equals("A26")){
                            tFirstPageTesting.setPresentAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("A27")){
                            tFirstPageTesting.setPresentAddrTel(dcVal);
                            return;
                        }
                        if(dcName.equals("A28C")){
                            tFirstPageTesting.setPresentAddrPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A29")){
                            tFirstPageTesting.setEmployerAddr(dcVal);
                            return;
                        }
                        if(dcName.equals("A30")){
                            tFirstPageTesting.setEmployerTel(dcVal);
                            return;
                        }
                        if(dcName.equals("A31C")){
                            tFirstPageTesting.setEmployerPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A24")){
                            tFirstPageTesting.setRegisterAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("A25C")){
                            tFirstPageTesting.setRegisterAddrPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("A32")){
                            tFirstPageTesting.setContactName(dcVal);
                            return;
                        }
                        if(dcName.equals("A33C")){
                            tFirstPageTesting.setContactRelationship(dcVal);
                            return;
                        }
                        if(dcName.equals("A34")){
                            tFirstPageTesting.setContactAddr(dcVal);
                            return;
                        }
                        if(dcName.equals("B11C")){
                            tFirstPageTesting.setInRoad(dcVal);
                            return;
                        }
                        if(dcName.equals("A35")){
                            tFirstPageTesting.setContactTel(dcVal);
                            return;
                        }
                        if(dcName.equals("B12")){
                            if(StringUtils.isNotBlank(dcVal)){
                                tFirstPageTesting.setInDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("B13C")){
                            tFirstPageTesting.setInDeptCode(dcVal);
                            return;
                        }
                        if(dcName.equals("B14")){
                            tFirstPageTesting.setInDeptRoom(dcVal);
                            return;
                        }
                        if(dcName.equals("B21C")){
                            tFirstPageTesting.setMoveDept(dcVal);
                            return;
                        }
                        if(dcName.equals("B15")){
                            if(StringUtils.isNotBlank(dcVal)) {
                                tFirstPageTesting.setOutDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("B16C")){
                            tFirstPageTesting.setOutDeptCode(dcVal);
                            return;
                        }
                        if(dcName.equals("B17")){
                            tFirstPageTesting.setOutDeptRoom(dcVal);
                            return;
                        }
                        if(dcName.equals("B20")){
                            tFirstPageTesting.setActualInDays(Long.valueOf(dcVal));
                            return;
                        }
                        if(dcName.equals("C01C")){
                            tFirstPageTesting.setOutpatDiagCode(dcVal);
                            return;
                        }
                        if(dcName.equals("C02")){
                            tFirstPageTesting.setOutpatDiagName(dcVal);
                            return;
                        }
                        if(dcName.equals("C25")){
                            tFirstPageTesting.setNameOfAllergicDrug(dcVal);
                            return;
                        }
                        if(dcName.equals("B22")){
                            tFirstPageTesting.setDeptDirector(dcVal);
                            return;
                        }
                        if(dcName.equals("B23")){
                            tFirstPageTesting.setChiefDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("B24")){
                            tFirstPageTesting.setInChargeDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("B25")){
                            tFirstPageTesting.setResidentDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("B26")){
                            tFirstPageTesting.setRespNurse(dcVal);
                            return;
                        }
                        if(dcName.equals("B27")){
                            tFirstPageTesting.setLearningDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("B28")){
                            tFirstPageTesting.setInternDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("B29")){
                            tFirstPageTesting.setCataloger(dcVal);
                            return;
                        }
                        if(dcName.equals("B30C")){
                            tFirstPageTesting.setCaseQuality(dcVal);
                            return;
                        }
                        if(dcName.equals("B31")){
                            tFirstPageTesting.setQcDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("B32")){
                            tFirstPageTesting.setQcNurse(dcVal);
                            return;
                        }
                        if(dcName.equals("C34C")){
                            tFirstPageTesting.setAutopsyMark(dcVal);
                            return;
                        }
                        if(dcName.equals("C26C")){
                            tFirstPageTesting.setAboCode(dcVal);
                            return;
                        }
                        if(dcName.equals("C27C")){
                            tFirstPageTesting.setRhCode9(dcVal);
                            return;
                        }
                        if(dcName.equals("A16")){
                            tFirstPageTesting.setAgeMonth(dcVal);
                            return;
                        }
                        if(dcName.equals("A18X01")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight(null);
                            }else {
                                tFirstPageTesting.setBirthWeight(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("A18X02")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight2(null);
                            }else {
                                tFirstPageTesting.setBirthWeight2(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("A18X03")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight3(null);
                            }else {
                                tFirstPageTesting.setBirthWeight3(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("A18X04")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight4(null);
                            }else {
                                tFirstPageTesting.setBirthWeight4(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("A18X05")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight5(null);
                            }else {
                                tFirstPageTesting.setBirthWeight5(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("A17")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setInWeight(null);
                            }else {
                                tFirstPageTesting.setInWeight(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("C28")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeDay(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeDay(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("C29")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeHour(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeHour(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("C30")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeMinute(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeMinute(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("C31")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeDay(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeDay(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("C32")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationAfterHour(null);
                            }else {
                                tFirstPageTesting.setComaDurationAfterHour(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("C33")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationAfterMinute(null);
                            }else {
                                tFirstPageTesting.setComaDurationAfterMinute(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("B36C")){
                            tFirstPageTesting.setRehospAfter31Mark(dcVal);
                            return;
                        }
                        if(dcName.equals("B37")){
                            tFirstPageTesting.setRehospAfter31Purpose(dcVal);
                            return;
                        }
                        if(dcName.equals("B34C")){
                            tFirstPageTesting.setDischargeClass(dcVal);
                            return;
                        }
                        if(dcName.equals("B35")){
                            tFirstPageTesting.setOrderReferralOrg(dcVal);
                            return;
                        }
                        if(dcName.equals("D01")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setFeeTotal(null);
                            }else {
                                tFirstPageTesting.setFeeTotal(new BigDecimal(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("D09")){
                            tFirstPageTesting.setFeeSelfPay(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D11")){
                            tFirstPageTesting.setFeeGeneralMedical(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D12")){
                            tFirstPageTesting.setFeeGeneralTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D13")){
                            tFirstPageTesting.setFeeTend(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D14")){
                            tFirstPageTesting.setFeeMedicalOther(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D15")){
                            tFirstPageTesting.setFeePathology(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D16")){
                            tFirstPageTesting.setFeeLaboratory(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D17")){
                            tFirstPageTesting.setFeeImaging(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D18")){
                            tFirstPageTesting.setFeeClinc(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D19")){
                            tFirstPageTesting.setFeeNonsurgicalTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D19X01")){
                            tFirstPageTesting.setFeeClinPhysical(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D20")){
                            tFirstPageTesting.setFeeSurgicalTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D20X01")){
                            tFirstPageTesting.setFeeAnaes(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D20X02")){
                            tFirstPageTesting.setFeeOperation(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D21")){
                            tFirstPageTesting.setFeeRecovery(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D22")){
                            tFirstPageTesting.setFeeCnTreatment(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D23")){
                            tFirstPageTesting.setFeeWesternMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D23X01")){
                            tFirstPageTesting.setFeeAntimicrobial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D24")){
                            tFirstPageTesting.setFeeCnMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D25")){
                            tFirstPageTesting.setFeeCnHerbalMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D26")){
                            tFirstPageTesting.setFeeBlood(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D27")){
                            tFirstPageTesting.setFeeAlbumin(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D28")){
                            tFirstPageTesting.setFeeGlobulin(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D29")){
                            tFirstPageTesting.setFeeBcf(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D30")){
                            tFirstPageTesting.setFeeCytokine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D31")){
                            tFirstPageTesting.setFeeCheckMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D32")){
                            tFirstPageTesting.setFeeTreatMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D33")){
                            tFirstPageTesting.setFeeOperMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("D34")){
                            tFirstPageTesting.setFeeOther(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("C24C")){
                            tFirstPageTesting.setDrugAllergyMark(dcVal);
                            return;
                        }
                        tFirstPageTesting.setCreateTime(new Date());
                        if(dlist.contains(dcName)){
                            outDiagMap.put(dcName,dcVal);
                            return;
                        }
                        if(olist.contains(dcName)){
                            outOperMap.put(dcName,dcVal);
                            return;
                        }
                    });
                }catch (Exception t){
                    System.out.println("出现异常的数据为"+o);
                }
                long nowDate = new Date().getTime();
                String sid = Integer.toHexString((int)nowDate);
                tFirstPageTesting.setTempSid(sid);
                tFirstPageTestingList.add(tFirstPageTesting);
                //转换诊断信息
                parseToDiagInfoForJXKH(outDiagMap,tFirstPageTesting,tFirstoutdiagTestingList);
                if(tFirstoutdiagTestingList.size()==0){
                    resultMap.put("diagInfo",null);
                }else {
                    resultMap.put("diagInfo",tFirstoutdiagTestingList);
                }
                //转换手术信息
                parseToOPerInfoForJXKH(outOperMap,tFirstPageTesting,tFirstoutoperTestingList);
                if(tFirstoutoperTestingList.size()==0){
                    resultMap.put("operInfo",null);
                }else {
                    resultMap.put("operInfo",tFirstoutoperTestingList);
                }
            });
            resultMap.put("firtPageInfo",tFirstPageTestingList);
        }catch (Exception e){
            System.out.println("出现异常:"+e.getMessage());
            e.printStackTrace();
        }
        return resultMap;
    }

    private static void parseToOPerInfoForWT(Map<String, Object> outOperMap, TFirstPageTesting tFirstPageTesting, List<TFirstoutoperTesting> tFirstoutoperTestingList) {
        try {
            if(!StringUtils.isBlank((String)outOperMap.get("SSJCZBM1"))){
                String[][] orderSTArr=new String[10][14];
                orderSTArr[0]=new String[]{ "SSJCZBM1","SSJCZRQ1","SSJB1","SSJCZMC1","SSCZBW1","SSCXSJ1","SZ1","YZ1","EZ1","MZFS1","MZFJ1","QKDJ1","QKYHLB1","MZYS1"};
                for(int i=1;i<10;i++){
                    String[] orderData=new String[14];
                    StringBuffer sb1=new StringBuffer();
                    StringBuffer sb2=new StringBuffer();
                    StringBuffer sb3=new StringBuffer();
                    StringBuffer sb4=new StringBuffer();
                    StringBuffer sb5=new StringBuffer();
                    StringBuffer sb6=new StringBuffer();
                    StringBuffer sb7=new StringBuffer();
                    StringBuffer sb8=new StringBuffer();
                    StringBuffer sb9=new StringBuffer();
                    StringBuffer sb10=new StringBuffer();
                    StringBuffer sb11=new StringBuffer();
                    StringBuffer sb12=new StringBuffer();
                    StringBuffer sb13=new StringBuffer();
                    StringBuffer sb14=new StringBuffer();
                    orderData[0]=sb1.append("SSJCZBM").append(i+1).toString();
                    orderData[1]=sb2.append("SSJCZRQ").append(i+1).toString();
                    orderData[2]=sb3.append("SSJB").append(i+1).toString();
                    orderData[3]=sb4.append("SSJCZMC").append(i+1).toString();
                    orderData[4]=sb5.append("SSCZBW").append(i+1).toString();
                    orderData[5]=sb6.append("SSCXSJ").append(i+1).toString();
                    orderData[6]=sb7.append("SZ").append(i+1).toString();
                    orderData[7]=sb8.append("YZ").append(i+1).toString();
                    orderData[8]=sb9.append("EZ").append(i+1).toString();
                    orderData[9]=sb10.append("MZFS").append(i+1).toString();
                    orderData[10]=sb11.append("MZFJ").append(i+1).toString();
                    orderData[11]=sb12.append("QKDJ").append(i+1).toString();
                    orderData[12]=sb13.append("QKYHLB").append(i+1).toString();
                    orderData[13]=sb14.append("MZYS").append(i+1).toString();
                    orderSTArr[i]=orderData;
                }
                Stream.of(orderSTArr).forEach(e->{
                    if(e[0].equals("SSJCZBM1")){
                        TFirstoutoperTesting t=new TFirstoutoperTesting();
                        t.setTempSid(tFirstPageTesting.getTempSid());
                        t.setOperationType("01");
                        t.setBatchId(tFirstPageTesting.getBatchId());
                        t.setCaseId(tFirstPageTesting.getCaseNo());
                        t.setOperationCode((String)outOperMap.get(e[0]));
                        t.setOperationDtime((String)outOperMap.get(e[1]));
                        t.setOperationLevel((String)outOperMap.get(e[2]));
                        t.setOperationName((String)outOperMap.get(e[3]));
                        t.setBodyPart((String)outOperMap.get(e[4]));
                        if(StringUtils.isNotBlank((String)outOperMap.get(e[5]))){
                            t.setOperDuration(new BigDecimal((String)outOperMap.get(e[5])));
                        }else {
                            t.setOperDuration(null);
                        }
                        t.setSurgeon((String)outOperMap.get(e[6]));
                        t.setAssistant1((String)outOperMap.get(e[7]));
                        t.setAssistant2((String)outOperMap.get(e[8]));
                        t.setAnesthesiaMode((String)outOperMap.get(e[9]));
                        t.setAnesthesiaLevel((String)outOperMap.get(e[10]));
                        t.setIncisionHealing((String)outOperMap.get(e[11]));
                        t.setIncisionHealingType((String)outOperMap.get(e[12]));
                        t.setAnesthesiologist((String)outOperMap.get(e[13]));
                        t.setOperationOrder(1);
                        tFirstoutoperTestingList.add(t);
                        return;
                    }else {
                        if(StringUtils.isNotBlank((String)outOperMap.get(e[0]))){
                            TFirstoutoperTesting t=new TFirstoutoperTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setOperationType("02");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setOperationCode((String)outOperMap.get(e[0]));
                            t.setOperationDtime((String)outOperMap.get(e[1]));
                            t.setOperationLevel((String)outOperMap.get(e[2]));
                            t.setOperationName((String)outOperMap.get(e[3]));
                            t.setBodyPart((String)outOperMap.get(e[4]));
                            if(StringUtils.isBlank((String)outOperMap.get(e[5]))){
                                t.setOperDuration(null);
                            }else {
                                t.setOperDuration(new BigDecimal((String)outOperMap.get(e[5])));
                            }
                            //t.setOperDuration(new BigDecimal((String)outOperMap.get(e[5])));
                            t.setSurgeon((String)outOperMap.get(e[6]));
                            t.setAssistant1((String)outOperMap.get(e[7]));
                            t.setAssistant2((String)outOperMap.get(e[8]));
                            t.setAnesthesiaMode((String)outOperMap.get(e[9]));
                            t.setAnesthesiaLevel((String)outOperMap.get(e[10]));
                            t.setIncisionHealing((String)outOperMap.get(e[11]));
                            t.setIncisionHealingType((String)outOperMap.get(e[12]));
                            t.setAnesthesiologist((String)outOperMap.get(e[13]));
                            if(e[0].length()==8){
                                t.setOperationOrder(Integer.valueOf(e[0].substring(e[0].length()-1)));
                            }else {
                                t.setOperationOrder(Integer.valueOf(e[0].substring(e[0].length()-2)));
                            }
                            tFirstoutoperTestingList.add(t);
                            return;
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void parseToDiagInfoForWT(Map<String, Object> outDiagMap, TFirstPageTesting tFirstPageTesting, List<TFirstoutdiagTesting> tFirstoutdiagTestingList) {
        try {
            if(!StringUtils.isBlank((String)outDiagMap.get("JBDM"))){
                String[][] orderSTArr=new String[22][4];
                orderSTArr[0]=new String[]{"JBDM","ZYZD","RYBQ","ZYZDCYQK"};
                orderSTArr[1]=new String[]{"JBMM","BLZD","BLH"};
                orderSTArr[2]=new String[]{"BLZDBM2","BLZDMC2","BLH2"};
                orderSTArr[3]=new String[]{"BLZDBM3","BLZDMC3","BLH3"};
                if(outDiagMap.containsKey("WBYYBM")){
                    orderSTArr[4]=new String[]{"WBYYBM","WBYY"};
                }else {
                    orderSTArr[4]=new String[]{"H23","WBYY"};
                }
                orderSTArr[5]=new String[]{"WBYSBM2","WBYSMC2"};
                orderSTArr[6]=new String[]{"WBYSBM3","WBYSMC3"};
                for(int i=1;i<=15;i++){
                    String[] orderData=new String[4];
                    StringBuffer sb1=new StringBuffer();
                    StringBuffer sb2=new StringBuffer();
                    StringBuffer sb3=new StringBuffer();
                    StringBuffer sb4=new StringBuffer();
                    orderData[0]=sb1.append("JBDM").append(i).toString();
                    orderData[1]=sb2.append("QTZD").append(i).toString();
                    orderData[2]=sb3.append("RYBQ").append(i).toString();
                    orderData[3]=sb4.append("QTZDCYQK").append(i).toString();
                    orderSTArr[i+6]=orderData;
                }
                Stream.of(orderSTArr).forEach(e->{
                    if(e[0].equals("JBDM")){
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("01");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setDiagOrder(1);
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setInCondition((String)outDiagMap.get(e[2]));
                            t.setDischargeStatus((String)outDiagMap.get(e[3]));
                            tFirstoutdiagTestingList.add(t);
                            return;
                        }
                    }else if(e[0].equals("JBMM")||e[0].equals("BLZDBM2")||e[0].equals("BLZDBM3")){
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("03");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            if(e[0].equals("JBMM")){
                                t.setDiagOrder(1);
                            }else if(e[0].equals("BLZDBM2")){
                                t.setDiagOrder(2);
                            }else {
                                t.setDiagOrder(3);
                            }
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setPathologicalNumber((String)outDiagMap.get(e[2]));
                            tFirstoutdiagTestingList.add(t);
                            return;
                        }
                    }else if(e[0].equals("WBYYBM")||e[0].equals("H23")||e[0].equals("WBYSBM2")||e[0].equals("WBYSBM3")){
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("04");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            if(e[0].equals("WBYYBM")||e[0].equals("H23")){
                                t.setDiagOrder(1);
                            }else if(e[0].equals("WBYSBM2")){
                                t.setDiagOrder(2);
                            }else {
                                t.setDiagOrder(3);
                            }
                            tFirstoutdiagTestingList.add(t);
                            return;
                        }
                    }else {
                        if(StringUtils.isNotBlank((String)outDiagMap.get(e[0]))){
                            TFirstoutdiagTesting t=new TFirstoutdiagTesting();
                            t.setTempSid(tFirstPageTesting.getTempSid());
                            t.setDiagType("02");
                            t.setBatchId(tFirstPageTesting.getBatchId());
                            t.setDiagnosisCode((String)outDiagMap.get(e[0]));
                            t.setDiagnosisName((String)outDiagMap.get(e[1]));
                            t.setDischargeStatus((String)outDiagMap.get(e[3]));
                            t.setCaseId(tFirstPageTesting.getCaseNo());
                            t.setInCondition((String)outDiagMap.get(e[2]));
                            if(e[0].length()==5){
                                t.setDiagOrder(Integer.valueOf(e[0].substring(e[0].length()-1)));
                            }else {
                                t.setDiagOrder(Integer.valueOf(e[0].substring(e[0].length()-2)));
                            }
                            tFirstoutdiagTestingList.add(t);
                            return;
                        }
                    }
                });
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createJXKHStandardList(String[] strings, FileAnalysisDto fileAnalysisDto) {
        try {
            fileAnalysisDto.setCellNameList(Stream.of(strings).collect(Collectors.toList()));
            //String standardLabel= StandardCodeEnum.getStateLabel(fileAnalysisDto.getStandardCode());
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("STANDARD_TYPE","JXKH");
            //queryMap.put("must_fill","是");
            List<TDatacleanStandard> tDatacleanStandardList = fileAnalysisDto.getTDatacleanStandardMapper().selectList(queryWrapper);
            List<TDatacleanStandard> totalStandardList=Lists.newArrayList();
            tDatacleanStandardList.stream().forEach(e->{
                if(!e.getDataColName().contains("至")){
                    totalStandardList.add(e);
                }else {
                    String[] chars=e.getDataColName().split("至");
                    String lastStr=null;
                    if(e.getDataColName().length()>6){
                        lastStr=e.getDataColName().substring(e.getDataColName().length()-1);
                    }
//                    if(e.getDataColName().startsWith("C37")){
//                        lastStr=null;
//                    }
                    for(int i=1;i<=40;i++){
                        StringBuffer sb=new StringBuffer();
                        if(i<10){
                            sb.append(chars[0].substring(0,4)).append("0").append(i);
                        }else {
                            sb.append(chars[0].substring(0,4)).append(i);
                        }
                        if(chars[0].length()>6){
                            sb.append(lastStr);
                        }
                        TDatacleanStandard t=new TDatacleanStandard();
                        BeanUtils.copyProperties(e,t);
                        t.setDataColName(sb.toString());
                        totalStandardList.add(t);
                    }
                }
            });
            fileAnalysisDto.setTDatacleanStandardList(totalStandardList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createWTStandardList(String[] strings, FileAnalysisDto fileAnalysisDto) {
        try {
            List<String> cellNameList=Stream.of(strings).collect(Collectors.toList());
            cellNameList.removeAll(Collections.singleton(null));
            fileAnalysisDto.setCellNameList(cellNameList);
            //String standardLabel= StandardCodeEnum.getStateLabel(fileAnalysisDto.getStandardCode());
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("STANDARD_TYPE","WT");
            //queryMap.put("must_fill","是");
            List<TDatacleanStandard> tDatacleanStandardList = fileAnalysisDto.getTDatacleanStandardMapper().selectList(queryWrapper);
            fileAnalysisDto.setTDatacleanStandardList(tDatacleanStandardList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Map<String, Object> getCaseListBeforeInsertForWT(List dataList,FileAnalysisDto fileAnalysisDto) {
        Map<String,Object> resultMap=Maps.newHashMap();
        try {
            List<TFirstPageTesting> tFirstPageTestingList=Lists.newArrayList();
            List<TFirstoutdiagTesting> tFirstoutdiagTestingList=Lists.newArrayList();
            List<TFirstoutoperTesting> tFirstoutoperTestingList=Lists.newArrayList();
            Map<String,Object> outDiagMap=Maps.newHashMap();
            //构造有序集合
            String[] diagInfoArr;
            boolean flag = true;
            for(String cellName : fileAnalysisDto.getCellNameList()){
                if(cellName.equals("H23")){
                    flag = false;
                    break;
                }
            }
            if(flag){
                diagInfoArr=new String[]{
                        "JBDM","ZYZD","RYBQ","ZYZDCYQK",
                        "JBMM","BLZD","BLH",
                        "BLZDBM2","BLZDMC2","BLH2",
                        "BLZDBM3","BLZDMC3","BLH3",
                        "WBYYBM","WBYY",
                        "WBYSBM2","WBYSMC2",
                        "WBYSBM3","WBYSBM3"
                };
            }else {
                diagInfoArr=new String[]{
                        "JBDM","ZYZD","RYBQ","ZYZDCYQK",
                        "JBMM","BLZD","BLH",
                        "BLZDBM2","BLZDMC2","BLH2",
                        "BLZDBM3","BLZDMC3","BLH3",
                        "H23","WBYY",
                        "WBYSBM2","WBYSMC2",
                        "WBYSBM3","WBYSBM3"
                };
            }
            List<String> dlist=Stream.of(diagInfoArr).collect(Collectors.toList());
            for(int i=1;i<=15;i++){
                StringBuffer sb1=new StringBuffer();
                StringBuffer sb2=new StringBuffer();
                StringBuffer sb3=new StringBuffer();
                StringBuffer sb4=new StringBuffer();
                sb1.append("JBDM").append(i);
                sb2.append("QTZD").append(i);
                sb3.append("RYBQ").append(i);
                sb4.append("QTZDCYQK").append(i);
                dlist.add(sb1.toString());
                dlist.add(sb2.toString());
                dlist.add(sb3.toString());
                dlist.add(sb4.toString());
            }
            Map<String,Object> outOperMap=Maps.newHashMap();
            //构造有序集合
            String[] operInfoArr=new String[]{
                    "SSJCZBM1","SSJCZRQ1","SSJB1","SSJCZMC1","SSCZBW1","SSCXSJ1","SZ1","YZ1","EZ1","MZFS1","MZFJ1","QKDJ1","QKYHLB1","MZYS1"
            };
            List<String> olist=Stream.of(operInfoArr).collect(Collectors.toList());
            for(int i=2;i<=10;i++){
                StringBuffer sb1=new StringBuffer();
                StringBuffer sb2=new StringBuffer();
                StringBuffer sb3=new StringBuffer();
                StringBuffer sb4=new StringBuffer();
                StringBuffer sb5=new StringBuffer();
                StringBuffer sb6=new StringBuffer();
                StringBuffer sb7=new StringBuffer();
                StringBuffer sb8=new StringBuffer();
                StringBuffer sb9=new StringBuffer();
                StringBuffer sb10=new StringBuffer();
                StringBuffer sb11=new StringBuffer();
                StringBuffer sb12=new StringBuffer();
                StringBuffer sb13=new StringBuffer();
                StringBuffer sb14=new StringBuffer();
                sb1.append("SSJCZBM").append(i);
                sb2.append("SSJCZRQ").append(i);
                sb3.append("SSJB").append(i);
                sb4.append("SSJCZMC").append(i);
                sb5.append("SSCZBW").append(i);
                sb6.append("SSCXSJ").append(i);
                sb7.append("SZ").append(i);
                sb8.append("YZ").append(i);
                sb9.append("EZ").append(i);
                sb10.append("MZFS").append(i);
                sb11.append("MZFJ").append(i);
                sb12.append("QKDJ").append(i);
                sb13.append("QKYHLB").append(i);
                sb14.append("MZYS").append(i);
                olist.add(sb1.toString());
                olist.add(sb2.toString());
                olist.add(sb3.toString());
                olist.add(sb4.toString());
                olist.add(sb5.toString());
                olist.add(sb6.toString());
                olist.add(sb7.toString());
                olist.add(sb8.toString());
                olist.add(sb9.toString());
                olist.add(sb10.toString());
                olist.add(sb11.toString());
                olist.add(sb12.toString());
                olist.add(sb13.toString());
                olist.add(sb14.toString());
            }
            dataList.stream().forEach(e->{
                //转换基本信息
                LinkedHashMap o=(LinkedHashMap)e;
                TFirstPageTesting tFirstPageTesting=new TFirstPageTesting();
                //continueout:
                o.forEach((k,v)->{
                    try {
                        String tInfo=(String) k;
                        String[] resultArr=tInfo.split("&");
                        String dcName;
                        String dcVal=null;
                        if(resultArr.length>2){
                            dcName=resultArr[1];
                            dcVal=resultArr[2].equals("null")?"":resultArr[2];
                        }else {
                            dcName=resultArr[1];
                        }
                        if(dcVal==null){
                            dcVal="";
                        }
                        //if(dcVal.equals("-")){
                        //    dcVal="";
                        //}
                        tFirstPageTesting.setBatchId(fileAnalysisDto.getBatchId());
                        tFirstPageTesting.setUpOrgId(fileAnalysisDto.getUpOrgId());
                        if(dcName.equals("USERNAME")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setOrgName(null);
                            }else {
                                tFirstPageTesting.setOrgName(dcVal);
                            }
                            return;
                        }
//                    if(dcName.equals("")){
//                        try {
//                            tFirstPageTesting.setOrgId(Long.valueOf(dcVal));
//                        }catch (Exception e1){
//                            System.out.println(e1.getMessage());
//                        }finally {
//                            tFirstPageTesting.setOrgId(null);
//                        }
//                        return;
//                    }
                        if(dcName.equals("YLBXSCKH")){
                            tFirstPageTesting.setMedicareCard(dcName);
                        }
                        if(dcName.equals("JKKH")){
                            tFirstPageTesting.setCardNo(dcVal);
                            return;
                        }
                        if(dcName.equals("YLFKFS")){
                            tFirstPageTesting.setPayWayCode(dcVal);
                            return;
                        }
                        if(dcName.equals("ZYCS")){
                            tFirstPageTesting.setInHospitalTimes(dcVal);
                            return;
                        }
                        if(dcName.equals("BAH")){
                            tFirstPageTesting.setCaseNo(dcVal);
                            return;
                        }
                        if(dcName.equals("XM")){
                            tFirstPageTesting.setFname(dcVal);
                            return;
                        }
                        if(dcName.equals("XB")){
                            tFirstPageTesting.setSexCode(dcVal);
                            return;
                        }
                        if(dcName.equals("CSRQ")){
                            tFirstPageTesting.setBirthDate(DateUtils.parse2DateByTimeStr(dcVal));
                            return;
                        }
                        if(dcName.equals("NL")){
                            tFirstPageTesting.setAge(Integer.valueOf(dcVal));
                            return;
                        }
                        if(dcName.equals("HY")){
                            tFirstPageTesting.setMarriageCode(dcVal);
                            return;
                        }
                        if(dcName.equals("ZY")){
                            tFirstPageTesting.setOccupationCode(dcVal);
                            return;
                        }
                        if(dcName.equals("CSD")){
                            tFirstPageTesting.setBirthAddrProvince(dcVal);
                            return;
                        }
                        if(dcName.equals("CSSF")){
                            tFirstPageTesting.setBirthAddrProvince(dcVal);
                            return;
                        }
                        if(dcName.equals("CSDS")){
                            tFirstPageTesting.setBirthAddrCity(dcVal);
                            return;
                        }
                        if(dcName.equals("CSDX")){
                            tFirstPageTesting.setBirthAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("GG")){
                            tFirstPageTesting.setNativeAddrProvince(dcVal);
                            return;
                        }
                        if(dcName.equals("MZ")){
                            tFirstPageTesting.setNationalityCode(dcVal);
                            return;
                        }
                        if(dcName.equals("GJ")){
                            tFirstPageTesting.setCountry(dcVal);
                            return;
                        }
                        if(dcName.equals("SFZH")){
                            tFirstPageTesting.setIdNo(dcVal);
                            return;
                        }
                        if(dcName.equals("XZZ")){
                            tFirstPageTesting.setPresentAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("DH")){
                            tFirstPageTesting.setPresentAddrTel(dcVal);
                            return;
                        }
                        if(dcName.equals("YB1")){
                            tFirstPageTesting.setPresentAddrPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("GZDWJDZ")){
                            tFirstPageTesting.setEmployerAddr(dcVal);
                            return;
                        }
                        if(dcName.equals("DWDH")){
                            tFirstPageTesting.setEmployerTel(dcVal);
                            return;
                        }
                        if(dcName.equals("YB3")){
                            tFirstPageTesting.setEmployerPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("HKDZ")){
                            tFirstPageTesting.setRegisterAddrCounty(dcVal);
                            return;
                        }
                        if(dcName.equals("YB2")){
                            tFirstPageTesting.setRegisterAddrPostalCode(dcVal);
                            return;
                        }
                        if(dcName.equals("LXRXM")){
                            tFirstPageTesting.setContactName(dcVal);
                            return;
                        }
                        if(dcName.equals("GX")){
                            tFirstPageTesting.setContactRelationship(dcVal);
                            return;
                        }
                        if(dcName.equals("DZ")){
                            tFirstPageTesting.setContactAddr(dcVal);
                            return;
                        }
                        if(dcName.equals("RYTJ")){
                            tFirstPageTesting.setInRoad(dcVal);
                            return;
                        }
                        if(dcName.equals("DH2")){
                            tFirstPageTesting.setContactTel(dcVal);
                            return;
                        }
                        if(dcName.equals("RYSJ")){
                            if(StringUtils.isNotBlank(dcVal)){
                                tFirstPageTesting.setInDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
//                        if(dcName.equals("RYSJS")){
//                            tFirstPageTesting.setContactTel(dcVal);
//                            return;
//                        }
                        if(dcName.equals("RYKB")){
                            tFirstPageTesting.setInDeptCode(dcVal);
                            return;
                        }
                        if(dcName.equals("RYBF")){
                            tFirstPageTesting.setInDeptRoom(dcVal);
                            return;
                        }
                        if(dcName.equals("ZKKB")){
                            tFirstPageTesting.setMoveDept(dcVal);
                            return;
                        }
                        if(dcName.equals("CYSJ")){
                            if(StringUtils.isNotBlank(dcVal)){
                                tFirstPageTesting.setOutDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
//                        if(dcName.equals("CYSJS")){
//                            tFirstPageTesting.setContactTel(dcVal);
//                            return;
//                        }
                        if(dcName.equals("CYKB")){
                            tFirstPageTesting.setOutDeptCode(dcVal);
                            return;
                        }
                        if(dcName.equals("CYBF")){
                            tFirstPageTesting.setOutDeptRoom(dcVal);
                            return;
                        }
                        if(dcName.equals("SJZYTS")){
                            tFirstPageTesting.setActualInDays(Long.valueOf(dcVal));
                            return;
                        }
                        if(dcName.equals("JBBM")){
                            tFirstPageTesting.setOutpatDiagCode(dcVal);
                            return;
                        }
                        if(dcName.equals("MZZD")){
                            tFirstPageTesting.setOutpatDiagName(dcVal);
                            return;
                        }
                        if(dcName.equals("RYSQK")){
                            tFirstPageTesting.setOnAdmission(dcVal);
                            return;
                        }
                        if(dcName.equals("RYZDDM")){
                            tFirstPageTesting.setInDiagCode(dcVal);
                            return;
                        }
                        if(dcName.equals("RYZDMS")){
                            tFirstPageTesting.setInDiagName(dcVal);
                            return;
                        }
                        if(dcName.equals("RYQZRQ")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setDateOfDiagnosis(null);
                            }else {
                                tFirstPageTesting.setDateOfDiagnosis(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("YYGRZCS")){
                            tFirstPageTesting.setTotalNumberOfInfections(dcVal);
                            return;
                        }
                        if(dcName.equals("GMY")){
                            tFirstPageTesting.setAllergySource(dcVal);
                            return;
                        }
                        if(dcName.equals("GMYW")){
                            tFirstPageTesting.setNameOfAllergicDrug(dcVal);
                            return;
                        }
                        if(dcName.equals("HBSAG")){
                            tFirstPageTesting.setHbsag(dcVal);
                            return;
                        }
                        if(dcName.equals("HCVAB")){
                            tFirstPageTesting.setHcvAb(dcVal);
                            return;
                        }
                        if(dcName.equals("HIVAB")){
                            tFirstPageTesting.setHivAb(dcVal);
                            return;
                        }
                        if(dcName.equals("MZYCYFHQK")){
                            tFirstPageTesting.setClinic2out(dcVal);
                            return;
                        }
                        if(dcName.equals("RYYCYFHQK")){
                            tFirstPageTesting.setIn2out(dcVal);
                            return;
                        }
                        if(dcName.equals("SQYSHFHQK")){
                            tFirstPageTesting.setPreOper2oper(dcVal);
                            return;
                        }
                        if(dcName.equals("LCYBLFHQK")){
                            tFirstPageTesting.setClinic2autopsy(dcVal);
                            return;
                        }
                        if(dcName.equals("FSYBLFHQK")){
                            tFirstPageTesting.setRadiation2pathology(dcVal);
                            return;
                        }
                        if(dcName.equals("QJCS")){
                            tFirstPageTesting.setRescueTimes(dcVal);
                            return;
                        }
                        if(dcName.equals("QJCGCS")){
                            tFirstPageTesting.setRescueSuccessTimes(dcVal);
                            return;
                        }
                        if(dcName.equals("ZGZDYJ")){
                            tFirstPageTesting.setHighestDiagnosticBasis(dcVal);
                            return;
                        }
//                    if(dcName.equals("P688")){
//                        tFirstPageTesting.setRescueSuccessTimes(dcVal);
//                        return;
//                    }
                        if(dcName.equals("KZR")){
                            tFirstPageTesting.setDeptDirector(dcVal);
                            return;
                        }
                        if(dcName.equals("ZRYS")){
                            tFirstPageTesting.setChiefDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("ZZYS")){
                            tFirstPageTesting.setInChargeDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("ZYYS")){
                            tFirstPageTesting.setResidentDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("ZRHS")){
                            tFirstPageTesting.setRespNurse(dcVal);
                            return;
                        }
                        if(dcName.equals("JXYS")){
                            tFirstPageTesting.setLearningDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("YJSSXYS")){
                            tFirstPageTesting.setPostgraduateIntern(dcVal);
                            return;
                        }
                        if(dcName.equals("SXYS")){
                            tFirstPageTesting.setInternDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("BMY")){
                            tFirstPageTesting.setCataloger(dcVal);
                            return;
                        }
                        if(dcName.equals("BAZL")){
                            tFirstPageTesting.setCaseQuality(dcVal);
                            return;
                        }
                        if(dcName.equals("ZKYS")){
                            tFirstPageTesting.setQcDoctor(dcVal);
                            return;
                        }
                        if(dcName.equals("ZKHS")){
                            tFirstPageTesting.setQcNurse(dcVal);
                            return;
                        }
                        if(dcName.equals("ZKRQ")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setQcDtime(null);
                            }else {
                                tFirstPageTesting.setQcDtime(DateUtils.parse2DateByTimeStr(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("SWHZSJ")){
                            tFirstPageTesting.setAutopsyMark(dcVal);
                            return;
                        }
                        if(dcName.equals("SFDYL")){
                            tFirstPageTesting.setFirstCase(dcVal);
                            return;
                        }
                        if(dcName.equals("SSHZLX")){
                            tFirstPageTesting.setSurgicalPatientType(dcVal);
                            return;
                        }
                        if(dcName.equals("SFSZ")){
                            tFirstPageTesting.setFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("SZZS")){
                            tFirstPageTesting.setWeeksOfFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("SZYS")){
                            tFirstPageTesting.setMonthsOfFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("SZNS")){
                            tFirstPageTesting.setYearsOfFollowUp(dcVal);
                            return;
                        }
                        if(dcName.equals("SJBL")){
                            tFirstPageTesting.setTeachingCases(dcVal);
                            return;
                        }
                        if(dcName.equals("XX")){
                            tFirstPageTesting.setAboCode(dcVal);
                            return;
                        }
                        if(dcName.equals("RH")){
                            tFirstPageTesting.setRhCode9(dcVal);
                            return;
                        }
                        if(dcName.equals("SXFY")){
                            tFirstPageTesting.setTransfusionReaction(dcVal);
                            return;
                        }
                        if(dcName.equals("HXB")){
                            tFirstPageTesting.setRedBloodCell(dcVal);
                            return;
                        }
                        if(dcName.equals("XXB")){
                            tFirstPageTesting.setPlatelet(dcVal);
                            return;
                        }
                        if(dcName.equals("XJ")){
                            tFirstPageTesting.setPlasma(dcVal);
                            return;
                        }
                        if(dcName.equals("QX")){
                            tFirstPageTesting.setWholeBlood(dcVal);
                            return;
                        }
                        if(dcName.equals("ZTHS")){
                            tFirstPageTesting.setSelfRecovery(dcVal);
                            return;
                        }
                        if(dcName.equals("QTSR")){
                            tFirstPageTesting.setOtherInputs(dcVal);
                            return;
                        }
                        if(dcName.equals("BZYZSNL")){
                            tFirstPageTesting.setAgeMonth(dcVal);
                            return;
                        }
                        if(dcName.equals("XSECSTZ")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight(null);
                            }else {
                                tFirstPageTesting.setBirthWeight(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("XSECSTZ2")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight2(null);
                            }else {
                                tFirstPageTesting.setBirthWeight2(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("XSECSTZ3")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight3(null);
                            }else {
                                tFirstPageTesting.setBirthWeight3(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("XSECSTZ4")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight4(null);
                            }else {
                                tFirstPageTesting.setBirthWeight4(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("XSECSTZ5")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setBirthWeight5(null);
                            }else {
                                tFirstPageTesting.setBirthWeight5(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("XSERYTZ")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setInWeight(null);
                            }else {
                                tFirstPageTesting.setInWeight(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("RYQ_T")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeDay(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeDay(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("RYQ_XS")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeHour(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeHour(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("RYQ_F")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeMinute(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeMinute(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("RYH_T")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationBeforeDay(null);
                            }else {
                                tFirstPageTesting.setComaDurationBeforeDay(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("RYH_XS")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationAfterHour(null);
                            }else {
                                tFirstPageTesting.setComaDurationAfterHour(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("RYH_F")){
                            if(StringUtils.isBlank(dcVal)||dcVal.equals("-")){
                                tFirstPageTesting.setComaDurationAfterMinute(null);
                            }else {
                                tFirstPageTesting.setComaDurationAfterMinute(Integer.valueOf(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("HXJSYSJ")){
                            tFirstPageTesting.setVentilatorUseTime(dcVal);
                            return;
                        }
                        if(dcName.equals("SFZZYJH")){
                            tFirstPageTesting.setRehospAfter31Mark(dcVal);
                            return;
                        }
                        if(dcName.equals("MD")){
                            tFirstPageTesting.setRehospAfter31Purpose(dcVal);
                            return;
                        }
                        if(dcName.equals("LYFS")){
                            tFirstPageTesting.setDischargeClass(dcVal);
                            return;
                        }
                        if(dcName.equals("YZZY_YLJG")){
                            tFirstPageTesting.setOrderReferralOrg(dcVal);
                            return;
                        }
                        if(dcName.equals("WSY_YLJG")){
                            tFirstPageTesting.setOrderReferralOrg(dcVal);
                            return;
                        }
                        if(dcName.equals("ZFY")){
                            if(StringUtils.isBlank(dcVal)){
                                tFirstPageTesting.setFeeTotal(null);
                            }else {
                                tFirstPageTesting.setFeeTotal(new BigDecimal(dcVal));
                            }
                            return;
                        }
                        if(dcName.equals("ZFJE")){
                            tFirstPageTesting.setFeeSelfPay(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("YLFUF")){
                            tFirstPageTesting.setFeeGeneralMedical(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("ZLCZF")){
                            tFirstPageTesting.setFeeGeneralTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("HLF")){
                            tFirstPageTesting.setFeeTend(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("QTFY")){
                            tFirstPageTesting.setFeeMedicalOther(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("BLZDF")){
                            tFirstPageTesting.setFeePathology(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("SYSZDF")){
                            tFirstPageTesting.setFeeLaboratory(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("YXXZDF")){
                            tFirstPageTesting.setFeeImaging(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("LCZDXMF")){
                            tFirstPageTesting.setFeeClinc(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("FSSZLXMF")){
                            tFirstPageTesting.setFeeNonsurgicalTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("WLZLF")){
                            tFirstPageTesting.setFeeClinPhysical(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("SSZLF")){
                            tFirstPageTesting.setFeeSurgicalTreat(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("MAF")){
                            tFirstPageTesting.setFeeAnaes(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("SSF")){
                            tFirstPageTesting.setFeeOperation(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("KFF")){
                            tFirstPageTesting.setFeeRecovery(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("ZYZLF")){
                            tFirstPageTesting.setFeeCnTreatment(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("XYF")){
                            tFirstPageTesting.setFeeWesternMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("KJYWF")){
                            tFirstPageTesting.setFeeAntimicrobial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("ZCYF")){
                            tFirstPageTesting.setFeeCnMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("ZCYF1")){
                            tFirstPageTesting.setFeeCnHerbalMedicine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("XF")){
                            tFirstPageTesting.setFeeBlood(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("BDBLZPF")){
                            tFirstPageTesting.setFeeAlbumin(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("QDBLZPF")){
                            tFirstPageTesting.setFeeGlobulin(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("NXYZLZPF")){
                            tFirstPageTesting.setFeeBcf(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("XBYZLZPF")){
                            tFirstPageTesting.setFeeCytokine(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("HCYYCLF")){
                            tFirstPageTesting.setFeeCheckMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("YYCLF")){
                            tFirstPageTesting.setFeeTreatMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("YCXYYCLF")){
                            tFirstPageTesting.setFeeOperMaterial(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("QTF")){
                            tFirstPageTesting.setFeeOther(new BigDecimal(dcVal));
                            return;
                        }
                        if(dcName.equals("YWGM")){
                            tFirstPageTesting.setDrugAllergyMark(dcVal);
                            return;
                        }
                        tFirstPageTesting.setCreateTime(new Date());
                        if(dlist.contains(dcName)){
                            outDiagMap.put(dcName,dcVal);
                            return;
                        }
                        if(olist.contains(dcName)){
                            outOperMap.put(dcName,dcVal);
                            return;
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                });
                long nowDate = new Date().getTime();
                String sid = Integer.toHexString((int)nowDate);
                tFirstPageTesting.setTempSid(sid);
                tFirstPageTestingList.add(tFirstPageTesting);
                //转换诊断信息
                parseToDiagInfoForWT(outDiagMap,tFirstPageTesting,tFirstoutdiagTestingList);
                if(tFirstoutdiagTestingList.size()==0){
                    resultMap.put("diagInfo",null);
                }else {
                    resultMap.put("diagInfo",tFirstoutdiagTestingList);
                }
                //转换手术信息
                parseToOPerInfoForWT(outOperMap,tFirstPageTesting,tFirstoutoperTestingList);
                if(tFirstoutoperTestingList.size()==0){
                    resultMap.put("operInfo",null);
                }else {
                    resultMap.put("operInfo",tFirstoutoperTestingList);
                }
            });
            resultMap.put("firtPageInfo",tFirstPageTestingList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    public static void trimStr(TDatacleanStandard t){
        try {
            if(t.getDataLength()!=null){
                String d=t.getDataLength();
                d.trim();
                d.replace(" ", "");
                t.setDataLength(d);
            }
        }catch (Exception e){
            System.out.println("去空格出错！");
        }finally {
            t.setRemark(null);
        }
    }

    public static void parseDataAndInsertData(FileAnalysisDto fileAnalysisDto,List resultList){
        //用map包含三种格式的list一并插入
        try {
            Map<String,Object> queryMap= com.google.common.collect.Maps.newHashMap();
            queryMap.put("MD5",fileAnalysisDto.getMd5().trim());
            FileUploadEntity fileUploadEntity=fileAnalysisDto.getFileUploadMapper().selectFileUpload(queryMap);
            fileAnalysisDto.setBatchId(fileUploadEntity.getBatchId());
            Map<String,Object> fianlResultMap=null;
            if(fileAnalysisDto.getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.HQMS))){
                fianlResultMap=DataCleanUtils.getCaseListBeforeInsertForHQMS(resultList,fileAnalysisDto);
            }else if(fileAnalysisDto.getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.JXKH))){
                fianlResultMap=DataCleanUtils.getCaseListBeforeInsertForJXKH(resultList,fileAnalysisDto);
            }else {
                fianlResultMap=DataCleanUtils.getCaseListBeforeInsertForWT(resultList,fileAnalysisDto);
            }
            //插入基本信息
            List<TFirstPageTesting> tFirstPageTestingList=(List<TFirstPageTesting>) fianlResultMap.get("firtPageInfo");
            if(tFirstPageTestingList==null){
                System.out.println("该批次出现病案信息为空的异常,开始数据为:"+resultList.get(0).toString());
            }
            if(tFirstPageTestingList!=null){
                //int i=fileAnalysisDto.getTFirstpageTestingMapper().batchInsertTFInfo(tFirstPageTestingList);
                for(TFirstPageTesting t : tFirstPageTestingList){
                    fileAnalysisDto.getTFirstpageTestingMapper().insert(t);
                }
                //System.out.println("该批次以已入基本信息个数为:"+i);
               // if(i>0){
                    //插入诊断信息
                    List<TFirstoutdiagTesting> tFirstoutdiagTestings=(List<TFirstoutdiagTesting>)fianlResultMap.get("diagInfo");
                    if(tFirstoutdiagTestings!=null){
                        if(tFirstoutdiagTestings.size()>0){
                            synchronized (lock1){
                                setTidBeforeInsertForDiag(tFirstPageTestingList,tFirstoutdiagTestings);
                            }
                            fileAnalysisDto.getTFirstoutdiagTestingMapper().batchInsertOutDiag(tFirstoutdiagTestings);
                        }
                    }
                    //插入手术信息
                    List<TFirstoutoperTesting> tFirstoutoperTestings=(List<TFirstoutoperTesting>)fianlResultMap.get("operInfo");
                    if(tFirstoutoperTestings!=null){
                        if(tFirstoutoperTestings.size()>0){
                            synchronized (lock2) {
                                setTidBeforeInsertForOper(tFirstPageTestingList, tFirstoutoperTestings);
                            }
                            fileAnalysisDto.getTFirstoutoperTestingMapper().batchInsertOutOper(tFirstoutoperTestings);
                        }
                    }
             //   }
            }
        }catch (Exception e){
            System.out.println("批量插入方法里失败！原因是："+e.getMessage());
        }
    }

    private static void setTidBeforeInsertForOper(List<TFirstPageTesting> tFirstPageTestingList, List<TFirstoutoperTesting> tFirstoutoperTestings) {
        for(TFirstPageTesting t:tFirstPageTestingList){
            for(TFirstoutoperTesting o:tFirstoutoperTestings){
                //if(t.getTempSid().equals(o.getTempSid())){
                if(t.getCaseNo().equals(o.getCaseId())){
                    o.setTFirstPageTestingId(t.getTFirstPageTestingId());
                }
            }
        }
    }

    private static void setTidBeforeInsertForDiag(List<TFirstPageTesting> tFirstPageTestingList, List<TFirstoutdiagTesting> tFirstoutdiagTestings) {
        for(TFirstPageTesting t:tFirstPageTestingList){
            for(TFirstoutdiagTesting d:tFirstoutdiagTestings){
                //if(t.getTempSid().equals(d.getTempSid())){
                if(t.getCaseNo().equals(d.getCaseId())){
                    d.setTFirstPageTestingId(t.getTFirstPageTestingId());
                }
            }
        }
    }

    public static boolean checkHeadLine(String[] strings, FileAnalysisDto fileAnalysisDto) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("STANDARD_TYPE",StandardCodeEnum.getStateLabel(fileAnalysisDto.getStandardCode()));
        List<TDatacleanStandard> tDatacleanStandards=fileAnalysisDto.getTDatacleanStandardMapper().selectList(queryWrapper);
        List<String> tds=null;
        if(!fileAnalysisDto.getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.JXKH))){
            tds=tDatacleanStandards.stream().map(TDatacleanStandard::getDataColName).collect(Collectors.toList());
        }else {
            List<TDatacleanStandard> totalStandardList=Lists.newArrayList();
            tDatacleanStandards.stream().forEach(e->{
                if(!e.getDataColName().contains("至")){
                    totalStandardList.add(e);
                }else {
                    String[] chars=e.getDataColName().split("至");
                    String lastStr=null;
                    if(e.getDataColName().length()>6){
                        lastStr=e.getDataColName().substring(e.getDataColName().length()-1);
                    }
                    for(int i=1;i<=40;i++){
                        StringBuffer sb=new StringBuffer();
                        if(i<10){
                            sb.append(chars[0].substring(0,4)).append("0").append(i);
                        }else {
                            sb.append(chars[0].substring(0,4)).append(i);
                        }
                        if(chars[0].length()>6){
                            sb.append(lastStr);
                        }
                        TDatacleanStandard t=new TDatacleanStandard();
                        BeanUtils.copyProperties(e,t);
                        t.setDataColName(sb.toString());
                        totalStandardList.add(t);
                    }
                }
            });
            tds=totalStandardList.stream().map(TDatacleanStandard::getDataColName).collect(Collectors.toList());
        }
        List<String> headCells=Lists.newArrayList();
        for(String s:strings){
            if(StringUtils.isNotEmpty(s)){
                headCells.add(s.toUpperCase());
            }
        }
        List<String> intersections=tds.stream().filter(t->headCells.contains(t)).collect(Collectors.toList());
        if(intersections==null){
            return false;
        }else { if(new BigDecimal((float)intersections.size()/headCells.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()>0.5){
                return true;
            }else {
                return false;
            }
        }
    }
}

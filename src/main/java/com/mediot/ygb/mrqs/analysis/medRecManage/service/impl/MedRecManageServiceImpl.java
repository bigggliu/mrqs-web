package com.mediot.ygb.mrqs.analysis.medRecManage.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import com.mediot.ygb.mrqs.analysis.medRecManage.dao.TDatacleanStandardMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckColMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckOrgMapper;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.dto.MedRecDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.dto.ThreadStateDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.service.MedRecordManageService;

import com.mediot.ygb.mrqs.analysis.medRecManage.thread.medCaseThread.UploadThreadOperator;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.InitInfoVo;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ThreadDataVo;
import com.mediot.ygb.mrqs.common.entity.excelModel.ErrorInfo;
import com.mediot.ygb.mrqs.common.enumcase.ResultCodeEnum;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.config.FileUploadConfig;
import com.mediot.ygb.mrqs.dict.dao.TBaseDictMapper;
import com.mediot.ygb.mrqs.dict.dao.TDataStandardMapper;
import com.mediot.ygb.mrqs.dict.dao.TDiagDictMapper;
import com.mediot.ygb.mrqs.dict.dao.TOperationDictMapper;

import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorDetailMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutdiagTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;

import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;


import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase.StateEnum;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao.FileUploadMapper;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class MedRecManageServiceImpl implements MedRecordManageService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static ConcurrentHashMap<String, ThreadStateDto> threadsMap=new ConcurrentHashMap<>();


    @Autowired
    private TDatacleanStandardMapper tDatacleanStandardMapper;

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TFirstoutdiagTestingMapper tFirstoutdiagTestingMapper;

    @Autowired
    private TFirstoutoperTestingMapper tFirstoutoperTestingMapper;

    @Autowired
    private TFirstpageTestingMapper tFirstpageTestingMapper;

    @Autowired
    private TBaseDictMapper tBaseDictMapper;

    @Autowired
    private TDiagDictMapper tDiagDictMapper;

    @Autowired
    private TOrgsMapper tOrgsMapper;

    @Autowired
    private TOperationDictMapper tOperationDictMapper;

    @Autowired
    private TCheckColMapper tCheckColMapper;

    @Autowired
    private TCheckOrgMapper tCheckOrgMapper;

    @Autowired
    private TErrorMapper tErrorMapper;

    @Autowired
    private TErrorDetailMapper tErrorDetailMapper;

    @Autowired
    private TDataStandardMapper tDataStandardMapper;


    private List<FileUploadEntity> createAnalyseInfo(StringBuffer sb,List<FileUploadEntity> fileUploadEntityList,InitInfoVo initInfoVo,String preKey){
        StringBuffer errorSb=new StringBuffer();
        StringBuffer startSb=new StringBuffer();
        StringBuffer compeletedSb=new StringBuffer();
        StringBuffer inAnalyseSb=new StringBuffer();
        StringBuffer errorIdsSb=new StringBuffer();
        StringBuffer startIdsSb=new StringBuffer();
        StringBuffer compeletedIdsSb=new StringBuffer();
        StringBuffer inAnalyseIdsSb=new StringBuffer();
        List<FileUploadEntity> filterFUEList=Lists.newArrayList();
        for(FileUploadEntity e:fileUploadEntityList){
            StringBuffer _sb=new StringBuffer();
            _sb.append(e.getFileId());
            _sb.append(preKey);
            Set<String> keys = redisTemplate.keys(_sb.toString());
            List<LinkedHashMap> redisResultList = redisTemplate.opsForValue().multiGet(keys);
            if(redisResultList.size()==0){
                startSb.append(e.getFileName()).append(",");
                startIdsSb.append(e.getFileId()).append(",");
                filterFUEList.add(e);
                continue;
            }
            List<ProgressVo> progressVoList=redisResultList.stream().map(p->{
                ProgressVo progressVo=new ProgressVo();
                ObjectMapper mapper = new ObjectMapper();
                String json= null;
                try {
                    json = mapper.writeValueAsString(p);
                    progressVo = mapper.readValue(json, ProgressVo.class);
                }catch (JsonProcessingException e1){
                    e1.printStackTrace();
                }catch (IOException e2){
                    e2.printStackTrace();
                }
                return progressVo;
            }).collect(Collectors.toList());
            int completedNum=0;
            for(ProgressVo progressVo:progressVoList){
                if(progressVo.getProgress()==1){
                    completedNum++;
                }
            }
            if(completedNum==progressVoList.size()){//是否已完成
                compeletedIdsSb.append(e.getFileId()).append(",");
                compeletedSb.append(e.getFileName()).append(",");
            }else {
                int errorNum=0;
                for(ProgressVo progressVo:progressVoList){//是否是错误
                    if(!progressVo.getAnalysisStatus()){
                        errorNum++;
                        break;
                    }
                }
                if(errorNum>0){
                    errorSb.append(e.getFileName()).append(",");
                    errorIdsSb.append(e.getFileId()).append(",");
                    StringBuffer __sb=new StringBuffer();
                    __sb.append(e.getFileId()).append(preKey);
                    Set<String> redisKeys = redisTemplate.keys(__sb.toString());
                    redisTemplate.delete(redisKeys);
                }else {
                    if(threadsMap.size()==0){//是否是进行中的检测
                        if(completedNum!=4){
                            startSb.append(e.getFileName()).append(",");
                            startIdsSb.append(e.getFileId()).append(",");
                            filterFUEList.add(e);
                        }else {
                            compeletedIdsSb.append(e.getFileId()).append(",");
                            compeletedSb.append(e.getFileName()).append(",");
                        }
                    }else {
                        for (String key : threadsMap.keySet()) {
                            if(key.substring(0,19).equals(String.valueOf(e.getFileId()))){
                                inAnalyseSb.append(e.getFileName()).append(",");
                                inAnalyseIdsSb.append(e.getFileId()).append(",");
                                break;
                            }else {
                                startSb.append(e.getFileName()).append(",");
                                startIdsSb.append(e.getFileId()).append(",");
                                filterFUEList.add(e);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(startSb.length()>0){
            initInfoVo.setStartFileIds(startIdsSb.substring(0,startIdsSb.length()-1));
            sb.append("文件").append(startSb.toString().substring(0,startSb.length()-1)).append("开启解析;");
        }
        if(inAnalyseSb.length()>0){
            initInfoVo.setInAnalyseFileIds(inAnalyseIdsSb.substring(0,inAnalyseIdsSb.length()-1));
            sb.append("文件").append(inAnalyseSb.toString().substring(0,inAnalyseSb.length()-1)).append("在解析中,不再重复开启;");
        }
        if(compeletedSb.length()>0){
            initInfoVo.setCompletedFileIds(compeletedIdsSb.substring(0,compeletedIdsSb.length()-1));
            sb.append("文件").append(compeletedSb.toString().substring(0,compeletedSb.length()-1)).append("已解析过,不再重复开启;");
        }
        if(errorSb.length()>0){
            initInfoVo.setErrorFileIds(errorIdsSb.substring(0,errorIdsSb.length()-1));
            sb.append("文件").append(errorSb.toString().substring(0,errorSb.length()-1)).append("存在错误,请勘误后再解析;");
        }
        return filterFUEList;
    }

    private void clearTempData(String[] fileIds) {
        Stream.of(fileIds).forEach(e->{
            for (String key : threadsMap.keySet()) {
                ThreadStateDto t = threadsMap.get(key);
                if(key.substring(0,19).equals(e)){
                    threadsMap.remove(key);
                    t.getThread().interrupt();
                }
            }
            StringBuffer sb=new StringBuffer();
            sb.append(e).append("_*");
            Set<String> keys = redisTemplate.keys(sb.toString());
            redisTemplate.delete(keys);
        });
    }


    public  void  initTmepDir(){
        String unZipPath=FileUploadConfig.basePath+FileUploadConfig.unZipPath;
        File file = new File(unZipPath);
        if(!file.exists()&&!file.isDirectory()) {
            file.mkdirs();
        }
    }

    public List<FileAnalysisDto> initializeUploadFile(List<FileUploadEntity> fileUploadEntityList){
        List<FileAnalysisDto> fileAnalysisDtoList=Lists.newArrayList();
        String baseDir=FileUploadConfig.basePath+ FileUploadConfig.tempPath;
        StringBuffer sb=new StringBuffer();
        fileUploadEntityList.stream().forEach(e->{
            sb.append("/").append(e.getFileName());
            String currentDir=baseDir+e.getMd5()+sb.toString();
            File file = new File(currentDir);
            if(file.exists()&&!file.isDirectory()){
                FileAnalysisDto fileAnalysisDto=new FileAnalysisDto();
                fileAnalysisDto.setRedisTemplate(redisTemplate);
                fileAnalysisDto.setTheadMaps(threadsMap);
                fileAnalysisDto.setFile(file);
                fileAnalysisDto.setUpOrgId(e.getOrgId());
                fileAnalysisDto.setMd5(e.getMd5());
                fileAnalysisDto.setFileId(String.valueOf(e.getFileId()));
                fileAnalysisDto.setFileName(e.getFileName());
                fileAnalysisDto.setSourceCode(e.getSourceCode());
                fileAnalysisDto.setTDatacleanStandardMapper(tDatacleanStandardMapper);
                fileAnalysisDto.setTFirstoutdiagTestingMapper(tFirstoutdiagTestingMapper);
                fileAnalysisDto.setTFirstoutoperTestingMapper(tFirstoutoperTestingMapper);
                fileAnalysisDto.setTFirstpageTestingMapper(tFirstpageTestingMapper);
                fileAnalysisDto.setTBaseDictMapper(tBaseDictMapper);
                fileAnalysisDto.setTDiagDictMapper(tDiagDictMapper);
                fileAnalysisDto.setFileUploadMapper(fileUploadMapper);
                fileAnalysisDto.setTOperationDictMapper(tOperationDictMapper);
                fileAnalysisDto.setStandardCode(e.getStandardCode());
                fileAnalysisDto.setTOrgsMapper(tOrgsMapper);
                fileAnalysisDtoList.add(fileAnalysisDto);
            }
            sb.setLength(0);
        });
        //
        return fileAnalysisDtoList;
    }


    @Override
    public ResultUtil suspendThreadsByFileId(MedRecDto medRecDto) {
        ResultUtil res=ResultUtil.build();
        try {
            for (String key : threadsMap.keySet()) {
                ThreadStateDto t = threadsMap.get(key);
                if(key.substring(0,19).equals(medRecDto.getFileId())){
                    t.setIsPause(true);
                    t.getThread().suspend();
                }
            }
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("线程已暂停");
        }catch (Throwable e){
            e.printStackTrace();
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("发生未知错误:"+e.getMessage());
        }
    }

    @Override
    public ResultUtil awakeThreadByFileId(MedRecDto medRecDto) {
        ResultUtil res=ResultUtil.build();
        try {
            for (String key : threadsMap.keySet()) {
                ThreadStateDto t = threadsMap.get(key);
                if(key.substring(0,19).equals(medRecDto.getFileId())){
                    t.getThread().resume();
                }
            }
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("线程已重启");
        }catch (Throwable e){
            e.printStackTrace();
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("发生未知错误:"+e.getMessage());
        }
    }

    private void  getTempDateByFileId(String[] arr,ResultUtil res,List<ThreadDataVo> resultList){
        Stream.of(arr).forEach(e->{
            //
            StringBuffer sb=new StringBuffer();
            sb.append(e).append("$*");
            Set<String> keys = redisTemplate.keys(sb.toString());
            List<LinkedHashMap> redisResultList = redisTemplate.opsForValue().multiGet(keys);
            List<ProgressVo> progressVoList=Lists.newArrayList();
            redisResultList.stream().forEach(p->{
                ProgressVo progressVo=new ProgressVo();
                ObjectMapper mapper = new ObjectMapper();
                String json= null;
                try {
                    json = mapper.writeValueAsString(p);
                    progressVo = mapper.readValue(json, ProgressVo.class);
                }catch (JsonProcessingException e1){
                    res.msg(e1.getMessage()).code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL));
                }catch (IOException e2){
                    res.msg(e2.getMessage()).code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL));
                }
                progressVoList.add(progressVo);
            });
            //获取暂停信息
            ThreadDataVo threadDataVo=new ThreadDataVo();
            try {
                for (String key : threadsMap.keySet()) {
                    ThreadStateDto t = threadsMap.get(key);
                    if(key.substring(0,19).equals(e)){
                        if(t.getIsPause()){
                            threadDataVo.setIsPause(true);
                            break;
                        }else {
                            threadDataVo.setIsPause(false);
                            break;
                        }
                    }
                }
            }catch (Throwable e3){
                e3.printStackTrace();
            }
            //数据进度展示vo
            threadDataVo.setFileId(e);
            threadDataVo.setFileName(progressVoList.get(0).getFileName());
            threadDataVo.setProgressVoList(progressVoList);
            resultList.add(threadDataVo);
        });
        //解析自身状态
        for(ThreadDataVo threadDataVo:resultList){
            for (ProgressVo progressVo:threadDataVo.getProgressVoList()){
                if(progressVo.getAnalysisStatus()!=null){
                    if(!progressVo.getAnalysisStatus()){
                        threadDataVo.setAnalysisStatus(false);
                        threadDataVo.setErrorMsg(progressVo.getErrorMsg());
                        break;
                    }else {
                        threadDataVo.setAnalysisStatus(true);
                    }
                }
            }
        }
    }

    @Override
    public ResultUtil getProgressByFileId(String fileIds) {
        ResultUtil res=ResultUtil.build();
        List<ThreadDataVo> resultList=Lists.newArrayList();
        //如果任何信息都不存在，返回等待中
        if(fileIds==null){
            getTempDateWithoutFileid(resultList,res);
            certainResCodeByResultList(res,resultList);
            return res;
        }else {
            String fileIdArr[]=fileIds.split(",");
            List<String> filteredRedisList=Lists.newArrayList();
            //redis查缓存并过滤
            Stream.of(fileIdArr).forEach(e-> {
                //
                StringBuffer sb = new StringBuffer();
                sb.append(e).append("$*");
                Set<String> keys = redisTemplate.keys(sb.toString());
                List<LinkedHashMap> redisResultList = redisTemplate.opsForValue().multiGet(keys);
                if(redisResultList.size()>0){
                    filteredRedisList.add(e);
                }
            });
            if(filteredRedisList.size()==0){
                return  res.code(ResultCodeEnum.getCode(ResultCodeEnum.NONE)).msg("请等待！");
            }else {
                getTempDateByFileId(filteredRedisList.stream().toArray(String[]::new),res,resultList);
                res.data(resultList);
            }
            certainResCodeByResultList(res,resultList);
            return res;
        }
    }

    private ResultUtil getTempDateWithoutFileid(List<ThreadDataVo> resultList,ResultUtil res) {
        StringBuffer sb=new StringBuffer();
        sb.append("*$*");
        Set<String> keys = redisTemplate.keys(sb.toString());
        List<LinkedHashMap> redisResultList = redisTemplate.opsForValue().multiGet(keys);
        if(redisResultList.size()==0){
            res.code(ResultCodeEnum.getCode(ResultCodeEnum.NONE)).msg("没有进度信息！");
        }else {
            String[] fileIds=keys.stream().map(e->e.substring(0,19)).collect(Collectors.toList()).stream().toArray(String[]::new);
            Set<String> fileIdSet= Sets.newHashSet();
            Stream.of(fileIds).forEach(e->{
                fileIdSet.add(e.substring(0,19));
            });
            getTempDateByFileId(fileIdSet.stream().toArray(String[]::new),res,resultList);
            resultList=removeCompletedRedisData(resultList);
            res.data(resultList);
        }
        return res;
    }

    private List<ThreadDataVo> removeCompletedRedisData(List<ThreadDataVo> resultList) {
        List<ThreadDataVo> unCompeletedList=Lists.newArrayList();
        resultList.stream().forEach(e->{
            int completedNum=0;
            for(ProgressVo progressVo:e.getProgressVoList()){
                if(progressVo.getProgress()==1){
                    completedNum++;
                }
            }
            if(completedNum!=4){
                unCompeletedList.add(e);
            }
        });
        return unCompeletedList;
    }

    private void certainResCodeByResultList(ResultUtil res, List<ThreadDataVo> resultList) {
        continueOut:
        for(ThreadDataVo threadDataVo:resultList){
            if(threadDataVo.getProgressVoList().size()==1){
                for(ProgressVo progressVo:threadDataVo.getProgressVoList()){
                    if(progressVo.getState()== AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING)){
                        if(progressVo.getProgress()==0){
                            res.code(ResultCodeEnum.getCode(ResultCodeEnum.WAIT)).msg("请等待！");
                            break  continueOut;
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResultUtil cancelThreadByIds(MedRecDto medRecDto) {
        ResultUtil res=ResultUtil.build();
        try {
            for (String key : threadsMap.keySet()) {
                ThreadStateDto t = threadsMap.get(key);
                if(key.substring(0,19).equals(medRecDto.getFileId())){
                    t.setIsPause(false);
                    t.getThread().interrupt();
                }
            }
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("线程已重启");
        }catch (Throwable e){
            e.printStackTrace();
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("发生未知错误:"+e.getMessage());
        }
    }

    public void clearAllTempData(String fileId){
        StringBuffer s=new StringBuffer();
        s.append("*$$$*");
        Set<String> keyss = redisTemplate.keys(s.toString());
        redisTemplate.delete(keyss);
        StringBuffer sb=new StringBuffer();
        sb.append("*$*");
        Set<String> keys = redisTemplate.keys(sb.toString());
        redisTemplate.delete(keys);
        if(fileId!=null){
            String key = "error_"+fileId;
            String rkey = "right_"+fileId;
            Set<String> rkeys = redisTemplate.keys("right_"+fileId+"*");
            try {
                System.out.println(redisTemplate.opsForZSet().remove("error_"+fileId));
            }catch (Exception e){
                e.printStackTrace();
            }
            redisTemplate.delete(key);
            redisTemplate.delete(rkey);
            redisTemplate.delete(rkeys);
        }
    }

    @Override
    public void importDataOfDataClean(String path) {
        DataCleanUtils.importDataOfDataClean(path, tDatacleanStandardMapper);
    }

    @Override
    public ResultUtil analyseMedRecords(MedRecDto medRecDto) {
        ResultUtil res=ResultUtil.build();
        //res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("正在开发中！");
        //判断该用户是否有机构
//        UserInfoDto userInfoDto = new UserInfoDto();
//        UserInfoVo userInfoVo = WebUtils.getSessionAttribute(CustomConst.LoginUser.SESSION_USER_INFO);
//        if(userInfoVo==null){
//            res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("未经授权的用户！");
//        }
//        if(userInfoVo.getOrgId()==null){
//            res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("该用户没有关联机构！");
//        }
//        if(tOrgsMapper.selectById(userInfoVo.getOrgId())==null){
//            res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("该用户所关联的机构不存在！");
//        }
        //文件是否是入库完成状态
        String fileIds[]=medRecDto.getFileIds().split(",");
        List<FileUploadEntity> fileUploadEntityList=fileUploadMapper.selectFileUploadList(fileIds);
        if(fileUploadEntityList.size()==0){
            return  res.msg("该文件不存在！").code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL));
        }else {
            StringBuffer rtMsg=new StringBuffer();
            fileUploadEntityList.stream().forEach(e->{
                if(!e.getState().equals(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE)))){
                    rtMsg.append(e.getFileName()).append(",");
                }
            });
            if(rtMsg.length()>0){
                return res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL))
                        .msg(rtMsg.toString().substring(0,rtMsg.length()-1)+"的数据没有完成入库或者已在解析中，不能进行解析以及重复解析！");
            }
        }
        //
        StringBuffer sb=new StringBuffer();
        InitInfoVo initInfoVo=new InitInfoVo();
        fileUploadEntityList=createAnalyseInfo(sb,fileUploadEntityList,initInfoVo,"$$$*");
        //
        if(fileUploadEntityList.size()!=0){
            List<FileAnalysisDto> fileAnalysisDtos=initDataAnalyseInfo(fileUploadEntityList);
            UploadThreadOperator uploadThreadOperator=new UploadThreadOperator();
            uploadThreadOperator.createThreadPool(5,Thread.currentThread().getName(),"fix");
            fileAnalysisDtos.stream().forEach(e->{
                //clearAllTempData(e.getFileId());
                uploadThreadOperator.startAnalysis(e);
            });
            uploadThreadOperator.releasePool();
        }else {
            return  res.msg(sb.toString()).code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL));
        }
        return  res;
    }

    private List<FileAnalysisDto> initDataAnalyseInfo(List<FileUploadEntity> list){
        List<FileAnalysisDto> fileAnalysisDtos=Lists.newArrayList();
        list.stream().forEach(e->{
            FileAnalysisDto fa=new FileAnalysisDto();
            fa.setFileId(String.valueOf(e.getFileId()));
            fa.setBatchId(e.getBatchId());
            fa.setTheadMaps(threadsMap);
            fa.setRedisTemplate(redisTemplate);
            fa.setTCheckColMapper(tCheckColMapper);
            fa.setTCheckOrgMapper(tCheckOrgMapper);
            fa.setTErrorMapper(tErrorMapper);
            fa.setTErrorDetailMapper(tErrorDetailMapper);
            fa.setTFirstpageTestingMapper(tFirstpageTestingMapper);
            fa.setStandardCode(e.getStandardCode());
            fa.setUpOrgId(e.getOrgId());
            fa.setFileUploadMapper(fileUploadMapper);
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("STANDARD_CODE",e.getStandardCode());
            fa.setTotalStandards(tDataStandardMapper.selectOne(queryWrapper).getTotalNumber());
            Map<String,Object> queryMap=Maps.newHashMap();
            queryMap.put("fileId",fa.getFileId());
            queryMap.put("batchId",fa.getBatchId());
            Integer total=tFirstpageTestingMapper.selectCountByMap(queryMap);
            fa.setTotalNumForCurrentBatchId(total);
            fileAnalysisDtos.add(fa);
        });
        return fileAnalysisDtos;
    }

    @Override
    public ResultUtil cleanAndAddMedRecords(MedRecDto medRecDto) {
        ResultUtil res=ResultUtil.build();
        LocalAssert.notNull(medRecDto.getFileIds(),"文件编号不能为空！");
        String fileIds[]=medRecDto.getFileIds().split(",");
        initTmepDir();
        //clearTempData(fileIds);
        List<FileUploadEntity> fileUploadEntityList=fileUploadMapper.selectFileUploadList(fileIds);
        if(fileUploadEntityList.size()==0){
            return  res.msg("该文件不存在！").code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL));
        }
        if(fileUploadEntityList.get(0).getState().equals(StateEnum.getValue(StateEnum.ClEANCOMPELETED))){
            return  res.msg("该文件已经清洗过！").code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL));
        }
        StringBuffer sb=new StringBuffer();
        InitInfoVo initInfoVo=new InitInfoVo();
        fileUploadEntityList=createAnalyseInfo(sb,fileUploadEntityList,initInfoVo,"$*");//todo 没有做持久化处理,遗留到三步解析完成后再持久化
        if(fileUploadEntityList.size()!=0){
            res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("开启清洗！");
            List<FileAnalysisDto> uploadFilesList=initializeUploadFile(fileUploadEntityList);
            UploadThreadOperator uploadThreadOperator=new UploadThreadOperator();
            uploadThreadOperator.createThreadPool(5,Thread.currentThread().getName(),"fix");
            //开始解析
            if(uploadFilesList.size()==0){
                return  res.msg("文件出现未知错误,请联系管理员！").code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL));
            }
            uploadFilesList.stream().forEach(e->{
                //clearAllTempData(e.getFileId());
                uploadThreadOperator.startDataClean(e);
            });
            ExecutorService es=uploadThreadOperator.getUploadThreadPool().getExecutorService();
            uploadThreadOperator.releasePool();
            es.shutdown();
        }else {
            res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg(sb.toString());
        }
        return  res;
    }

    @Override
    public void exportErrorInfoByFileId(HttpServletResponse response, String fileId,String fileName)  {
        LocalAssert.notNull(fileId,"文件id不能为空！");
        LocalAssert.notNull(fileName,"文件名不能为空！");
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fName = URLEncoder.encode(fileName, "UTF-8");
            //取缓存形成文件
            String key = "error_"+fileId;
            List<LinkedHashMap> redisResultList = redisTemplate.opsForList().range(key,0,-1);
            List<ErrorInfo> errorInfoList=Lists.newArrayList();
            redisResultList.stream().forEach(e->{
                StringBuffer sb=new StringBuffer();
                ErrorInfo errorInfo=new ErrorInfo();
                errorInfo.setColName((String)e.get("dataColName"));
                sb.append("第").append(e.get("currentRow")).append("行第").append(e.get("currentColIndex"))
                        .append("列存在错误，错误原因是：")
                        .append((String)e.get("errorMsg"));
                errorInfo.setErrorMsg(sb.toString());
                errorInfo.setCol((String)e.get("dataCol"));
                errorInfo.setVal((String)e.get("tempCellVal"));
                errorInfo.setFileName((String)e.get("currentFileName"));
                errorInfoList.add(errorInfo);
            });
            response.setHeader("Content-disposition", "attachment;filename=" + fName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ErrorInfo.class).sheet("模板").doWrite(errorInfoList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<String,Object> getDataCleanInfoByFileId(String fileId, int pageSize, int pageNum) {
        Map<String,Object> resultMap= Maps.newHashMap();
        List result = null;
        //Set<String> keys = redisTemplate.keys("error_"+fileId+"_*");
        String key="error_"+fileId;
        Long totalNum=redisTemplate.opsForList().size(key);
        try {
            if(((pageNum-1)*pageSize+pageSize)>totalNum){
                result= redisTemplate.opsForList().range(key,(pageNum-1)*pageSize,totalNum);
            }else {
                result= redisTemplate.opsForList().range(key,(pageNum-1)*pageSize,((pageNum-1)*pageSize+pageSize));
            }
            if(result==null){
                resultMap.put("data",null);//数据结果
                resultMap.put("total", totalNum);//获取数据总数
                resultMap.put("pageSize", pageSize);//获取长度
                resultMap.put("pageNum", pageNum);//获取当前页数
            }else {
                resultMap.put("data",result);//数据结果
                resultMap.put("total", totalNum);//获取数据总数
                resultMap.put("pageSize", pageSize);//获取长度
                resultMap.put("pageNum", pageNum);//获取当前页数
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public Map<String,Object> getRightInfoByFileId(String fileId, int pageSize, int pageNum) {
        Map<String,Object> resultMap= Maps.newHashMap();
        List result = null;
        //Set<String> keys = redisTemplate.keys("error_"+fileId+"_*");
        String key="right_"+fileId;
        int totalNum=redisTemplate.keys(key).size();
        try {
            if(((pageNum-1)*pageSize+pageSize)>totalNum){
                result= redisTemplate.opsForList().range(key,(pageNum-1)*pageSize,totalNum);
            }else {
                result= redisTemplate.opsForList().range(key,(pageNum-1)*pageSize,((pageNum-1)*pageSize+pageSize));
            }
            if(result==null){
                resultMap.put("data",null);//数据结果
                resultMap.put("total", totalNum);//获取数据总数
                resultMap.put("pageSize", pageSize);//获取长度
                resultMap.put("pageNum", pageNum);//获取当前页数
            }else {
                resultMap.put("data",result);//数据结果
                resultMap.put("total", totalNum);//获取数据总数
                resultMap.put("pageSize", pageSize);//获取长度
                resultMap.put("pageNum", pageNum);//获取当前页数
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

//    @Override
//    public void batchInsertResult(String fileId) {
//        String key="right_"+fileId;
//        FileAnalysisDto fileAnalysisDto=new FileAnalysisDto();
//        fileAnalysisDto.setRedisTemplate(redisTemplate);
//        fileAnalysisDto.setTheadMaps(threadsMap);
//        fileAnalysisDto.setMd5("ed142dae58ede74952a9b4d7ff047ca8");
//        fileAnalysisDto.setSourceCode("04");
//        fileAnalysisDto.setTDatacleanStandardMapper(tDatacleanStandardMapper);
//        fileAnalysisDto.setTFirstoutdiagTestingMapper(tFirstoutdiagTestingMapper);
//        fileAnalysisDto.setTFirstoutoperTestingMapper(tFirstoutoperTestingMapper);
//        fileAnalysisDto.setTFirstpageTestingMapper(tFirstpageTestingMapper);
//        fileAnalysisDto.setTBaseDictMapper(tBaseDictMapper);
//        fileAnalysisDto.setTDiagDictMapper(tDiagDictMapper);
//        fileAnalysisDto.setFileUploadMapper(fileUploadMapper);
//        fileAnalysisDto.setTOperationDictMapper(tOperationDictMapper);
//        fileAnalysisDto.setStandardCode("04");
//        fileAnalysisDto.setTOrgsMapper(tOrgsMapper);
//        CVSRowProcessors.batchInsertResult(key,Long.valueOf(32574),fileAnalysisDto);
//    }


}

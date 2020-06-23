package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.service.impl;

import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.DateUtils;
import com.mediot.ygb.mrqs.common.util.FileUtils;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorDetailMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


import com.mediot.ygb.mrqs.common.enumcase.ResultCodeEnum;

import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.common.util.ResultUtil;

import com.mediot.ygb.mrqs.config.FileUploadConfig;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorMapper;

import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutdiagTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dto.FileUploadDto;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase.StateEnum;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase.UploadStateEnum;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.vo.FileUploadVo;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao.FileUploadMapper;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.service.FileUploadService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


import java.io.File;


import java.io.InputStream;
import java.io.RandomAccessFile;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


@Service
public class FileUploadServiceImpl extends BaseServiceImpl<FileUploadMapper, FileUploadEntity> implements FileUploadService {

    @Autowired
    FileUploadMapper fileUploadMapper;

    @Autowired
    TErrorMapper tErrorMapper;

    @Autowired
    TFirstpageTestingMapper tFirstpageTestingMapper;

    @Autowired
    TFirstoutdiagTestingMapper tFirstoutdiagTestingMapper;

    @Autowired
    TFirstoutoperTestingMapper tFirstoutoperTestingMapper;

    @Autowired
    TErrorDetailMapper tErrorDetailMapper;

//    @Autowired
//    private RedisTemplate redisTemplate;

    private static  ConcurrentHashMap<String,Object> concurrentHashMap=new ConcurrentHashMap();

    private ReentrantLock reentrantLock=new ReentrantLock();


    @Override
    public ResultUtil uploadFile(FileUploadDto fileUploadDto) {
        LocalAssert.notNull(fileUploadDto.getMd5(),"MD5值不能为空");
        LocalAssert.notNull(fileUploadDto.getChunk(),"当前分片不能为空");
        LocalAssert.notNull(fileUploadDto.getChunks(),"总分片不能为空");
        LocalAssert.notNull(fileUploadDto.getSize(),"文件大小不能为空");
        LocalAssert.notNull(fileUploadDto.getFileName(),"文件名不能为空");
        initTmepDir();
        //存在文件.a,是否是断点续传还是秒传
        Map<String,Object> queryMap= Maps.newHashMap();
        queryMap.put("MD5",fileUploadDto.getMd5().trim());
        FileUploadEntity fileUploadEntity=fileUploadMapper.selectFileUpload(queryMap);
        if(fileUploadEntity==null){
            //新传（即是续传的起始）
            return uploadWithBlock(fileUploadDto);
        }else{
            if(fileUploadEntity.getState().equals(StateEnum.getValue(StateEnum.FAIL))){
                return uploadWithBlock(fileUploadDto);
            }
            return quickUpload(fileUploadEntity);
        }
    }

    public  void  initTmepDir(){
        String tmepDirPath=FileUploadConfig.basePath+FileUploadConfig.tempPath;
        File file = new File(tmepDirPath);
        if(!file.exists()&&!file.isDirectory()) {
            file.mkdirs();
        }
    }

    @Override
    public ResultUtil quickUpload(FileUploadEntity fileUploadEntity) {
        //FileUploadVo fileUploadVo=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(fileUploadDto),FileUploadVo.class);
        //传输状态更新
        fileUploadEntity.setState(StateEnum.getValue(StateEnum.SUCCESS));
        fileUploadMapper.updateById(fileUploadEntity);
        return ResultUtil.build().code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("秒传成功！");
    }


    public FileUploadVo parse2FileUploadVo( FileUploadDto fileUploadDto){
        FileUploadVo fileUploadVo=new FileUploadVo();
        fileUploadVo.setChunk(String.valueOf(fileUploadDto.getChunk()));
        fileUploadVo.setChunks(String.valueOf(fileUploadDto.getChunk()));
        fileUploadVo.setFileName(fileUploadDto.getFileName());
        fileUploadVo.setMd5(fileUploadDto.getMd5().trim());
        fileUploadVo.setChunkSize(fileUploadDto.getFile().getSize());
        return  fileUploadVo;
    };

    public ResultUtil uploadNewBlock(File uploadDir,String fileName,FileUploadDto fileUploadDto,StringBuffer sb){
        Long startTime=System.currentTimeMillis();
        logger.info("分片"+fileUploadDto.getChunk()+"进入传输！");
        try {
            FileUploadVo fileUploadVo=parse2FileUploadVo(fileUploadDto);
            Integer currChunk = fileUploadDto.getChunk();
            Integer allChunks = fileUploadDto.getChunks();
            long chunkSize = fileUploadDto.getFile().getSize();
            Long allSize = fileUploadDto.getSize();
            long startPos=0;//文件写入位置指针
            if(currChunk<(allChunks-1)){//整数分片
                startPos = chunkSize*currChunk;
            }else if(currChunk==(allChunks-1)){
                startPos= allSize-chunkSize;
            }else {
                return ResultUtil.build().code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("分片参数异常").data(fileUploadVo);
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(uploadDir.getAbsolutePath()+'/'+fileName,"rw");
            randomAccessFile.skipBytes(Integer.parseInt(String.valueOf(startPos)));
            InputStream inputStream=fileUploadDto.getFile().getInputStream();
            int len;
            int passedSize=0;
            byte[] buf = new byte[1024];
            while (-1 != (len =  inputStream.read(buf))) {
                randomAccessFile.write(buf,0,len);
                passedSize+=len;
                fileUploadVo.setPassedSize(passedSize);
                concurrentHashMap.put(sb.toString(),fileUploadVo);
            }
            randomAccessFile.close();
            Long endTime=System.currentTimeMillis();
            logger.info("分片"+fileUploadDto.getChunk()+"完成传输！用时"+(startTime-endTime)/1000);
            return ResultUtil.build().data(fileUploadVo).code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS))
                    .msg("分片"+fileUploadDto.getChunk()+"传输成功！");
        }catch (Throwable e){
            return ResultUtil.build().code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg(e.getMessage()).data(parse2FileUploadVo(fileUploadDto));
        }
    }

    public void skip(int skipPos,RandomAccessFile randomAccessFile,FileUploadDto fileUploadDto){
        try {
            while(skipPos > 0) {
                long amt = randomAccessFile.skipBytes(skipPos);
                if (amt == -1) {
                    throw new RuntimeException(fileUploadDto.getFileName() + ": unexpected EOF");
                }
                skipPos -= amt;
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public void skip(int skipPos,InputStream inputStream,FileUploadDto fileUploadDto){
        try {
            while(skipPos > 0) {
                long amt = inputStream.skip(skipPos);
                if (amt == -1) {
                    throw new RuntimeException(fileUploadDto.getFileName() + ": unexpected EOF");
                }
                skipPos -= amt;
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public ResultUtil uploadExistBlock(File uploadDir,String fileName,FileUploadDto fileUploadDto,StringBuffer sb) {
        Long startTime=System.currentTimeMillis();
        logger.info("分片"+fileUploadDto.getChunk()+"进入传输");
        FileUploadVo fileUploadVo=(FileUploadVo)concurrentHashMap.get(sb.toString());
        try {
            Integer currChunk = fileUploadDto.getChunk();
            Integer allChunks = fileUploadDto.getChunks();
            long chunkSize = fileUploadDto.getFile().getSize();
            Long allSize = fileUploadDto.getSize();
            long startPos=0;//文件写入位置指针
            if(currChunk<(allChunks-1)){//整数分片
                if(fileUploadVo.getPassedSize()>chunkSize){
                    startPos=chunkSize*currChunk;
                }else {
                    startPos = chunkSize*currChunk+fileUploadVo.getPassedSize();
                }
            }else if(currChunk==(allChunks-1)){
                startPos= (allSize-chunkSize)+fileUploadVo.getPassedSize();
            }else {
                return ResultUtil.build().code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("分片参数异常").data(fileUploadVo);
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(uploadDir.getAbsolutePath()+'/'+fileName,"rw");
            randomAccessFile.skipBytes(Integer.parseInt(String.valueOf(startPos)));
            InputStream inputStream=fileUploadDto.getFile().getInputStream();
            skip(fileUploadVo.getPassedSize(),inputStream,fileUploadDto);
            int len;
            int passedSize=fileUploadVo.getPassedSize();
            byte[] buf = new byte[1024];
            while (-1 != (len =  inputStream.read(buf))) {
                randomAccessFile.write(buf,0,len);
                passedSize+=len;
                fileUploadVo.setPassedSize(passedSize);
                concurrentHashMap.put(sb.toString(),fileUploadVo);
            }
            randomAccessFile.close();
            Long endTime=System.currentTimeMillis();
            logger.info("分片"+fileUploadDto.getChunk()+"完成传输！用时"+(endTime-startTime)/1000);
            return ResultUtil.build().data(fileUploadVo).code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS))
                        .msg("分片"+fileUploadDto.getChunk()+"传输成功！");
        }catch (Throwable e){
            return ResultUtil.build().code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg(e.getMessage()).data(fileUploadVo);
        }
    }

    public  ResultUtil uploadWithBlock(FileUploadDto fileUploadDto){
        //创建md5命名的文件
        try {
            String currentDir=FileUploadConfig.basePath+FileUploadConfig.tempPath+fileUploadDto.getMd5().trim();
            String fileName = fileUploadDto.getFileName();
            File uploadDir= new File(currentDir);
            if(!uploadDir.exists()&&!uploadDir.isDirectory()){
                uploadDir.mkdir();
            }
            StringBuffer sb=new StringBuffer();
            sb.append(fileUploadDto.getMd5()).append("_").append(fileUploadDto.getChunk());
            if(concurrentHashMap.get(sb.toString())==null){
                return uploadNewBlock(uploadDir,fileName,fileUploadDto,sb);
            }else {
                FileUploadVo fileUploadVo=(FileUploadVo)concurrentHashMap.get(sb.toString());
                if(fileUploadVo.getPassedSize()==0){
                    return uploadNewBlock(uploadDir,fileName,fileUploadDto,sb);
                }else if(fileUploadVo.getPassedSize()==fileUploadDto.getFile().getSize()){
                    return ResultUtil.build().data(fileUploadVo).code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS))
                            .msg("分片"+fileUploadDto.getChunk()+"传输成功！");
                }else {
                    return uploadExistBlock(uploadDir,fileName,fileUploadDto,sb);
                }
            }
        }catch (Throwable e){
            FileUploadVo fileUploadVo=parse2FileUploadVo(fileUploadDto);
            return ResultUtil.build().code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg(e.getMessage()).data(fileUploadVo);
        }
    }

    public void releaseUploadInfo(FileUploadDto fileUploadDto){
        if(concurrentHashMap.size()!=0&&concurrentHashMap!=null){
            for (String key : concurrentHashMap.keySet()) {
                //FileUploadVo fileUploadVo = (FileUploadVo)concurrentHashMap.get(key);
                if(key.substring(0,31)==fileUploadDto.getMd5()){
                    concurrentHashMap.remove(key);
                }
            }
        }
    }

    public ResultUtil getProgressInfo(FileUploadDto fileUploadDto){
        LocalAssert.notNull(fileUploadDto.getMd5(),"MD5不能为空");
        List<FileUploadVo> fileUploadVoList= Lists.newArrayList();
        //LocalAssert.notNull(fileUploadDto.getChunk(),"分片数不能为空");
        String currentDir=FileUploadConfig.basePath+FileUploadConfig.tempPath+fileUploadDto.getMd5()+"/"+fileUploadDto.getFileName();
        File file = new File(currentDir);
        if(file.exists()&&!file.isDirectory()) {
            Map<String, Object> queryMap=Maps.newHashMap();
            queryMap.put("MD5",fileUploadDto.getMd5());
            logger.info("MD5值是："+fileUploadDto.getMd5()+"参数值是："+queryMap.toString());
            FileUploadEntity fileUploadEntity=fileUploadMapper.selectFileUpload(queryMap);
            if(fileUploadEntity!=null){
                if(fileUploadEntity.getState().equals("1")){
                    return ResultUtil.build().code(UploadStateEnum.getValue(UploadStateEnum.REAPET_UPLOAD)).msg("请勿重复上传已经上传过的文件！");
                }
            }
        }
        if(concurrentHashMap.size()==0){
            Map<String, Object> queryMap=Maps.newHashMap();
            queryMap.put("MD5",fileUploadDto.getMd5());
            FileUploadEntity fileUploadEntity=fileUploadMapper.selectFileUpload(queryMap);
            if(fileUploadEntity==null){
                return ResultUtil.build().code(UploadStateEnum.getValue(UploadStateEnum.START_UPLOAD)).msg("该文件未上传任何分块，请开始上传！");
            }else {
                if(fileUploadEntity.getState().equals(StateEnum.getValue(StateEnum.SUCCESS))){
                    return ResultUtil.build().code(UploadStateEnum.getValue(UploadStateEnum.REAPET_UPLOAD)).msg("请勿重复上传已经上传过的文件！");
                }else {
                    return ResultUtil.build().code(UploadStateEnum.getValue(UploadStateEnum.START_UPLOAD)).msg("该文件未上传任何分块，请开始上传！");
                }
            }
        }else {
            for (String key : concurrentHashMap.keySet()) {
                FileUploadVo fileUploadVo = (FileUploadVo)concurrentHashMap.get(key.trim());
                if(key.trim().substring(0,32).equals(fileUploadDto.getMd5())){
                    if(fileUploadVo.getPassedSize()!=fileUploadVo.getChunkSize()&&fileUploadVo.getPassedSize()<fileUploadVo.getChunkSize()){
                        fileUploadVo.setStatus("pending");
                    }
                    fileUploadVoList.add(fileUploadVo);
                }
            }
            if(fileUploadVoList.size()==0){
                return ResultUtil.build().code(UploadStateEnum.getValue(UploadStateEnum.START_UPLOAD)).msg("该文件未上传任何分块，请开始上传！");
            }else {
                Map<String, Object> queryMap=Maps.newHashMap();
                queryMap.put("MD5",fileUploadDto.getMd5());
                FileUploadEntity fileUploadEntity=fileUploadMapper.selectFileUpload(queryMap);
                if(fileUploadEntity==null){
                    return ResultUtil.build().code(UploadStateEnum.getValue(UploadStateEnum.START_UPLOAD)).msg("该文件未上传任何分块，请开始上传！");
                }else{
                    return ResultUtil.build().code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).data(fileUploadVoList);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultUtil syncUploadBlokState(FileUploadDto fileUploadDto){
        //LocalAssert.notNull(fileUploadDto.getMd5(),"MD5不能为空");
        FileUploadEntity fileUploadEntity;
        if(fileUploadDto.getMd5()!=null){
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("MD5",fileUploadDto.getMd5().trim());
            fileUploadEntity=fileUploadMapper.selectFileUpload(queryMap);
        }else {
            fileUploadEntity=null;
        }
        //FileUploadVo fileUploadVo=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(fileUploadDto),FileUploadVo.class);
        FileUploadEntity fileUploadPOJO=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(fileUploadDto),FileUploadEntity.class);
        fileUploadPOJO.setMd5(fileUploadPOJO.getMd5().trim());
        ResultUtil res= ResultUtil.build();
        try {
            reentrantLock.lock();
            if(fileUploadEntity==null){
                TErrorEntity tErrorEntity=new TErrorEntity();
                tErrorMapper.insert(tErrorEntity);
                fileUploadPOJO.setBatchId(tErrorEntity.getBatchId());
                fileUploadPOJO.setUploadTime(new Date());
                fileUploadMapper.insert(fileUploadPOJO);
                res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).data(fileUploadPOJO);
            }else {
                //fileUploadPOJO.setBatchId(Long.valueOf(fileUploadDto.getBatchId()));
                fileUploadPOJO.setFileId(fileUploadEntity.getFileId());
                fileUploadPOJO.setBatchId(fileUploadEntity.getBatchId());
                fileUploadPOJO.setUpdateTime(new Date());
                fileUploadMapper.updateById(fileUploadPOJO);
                if(!fileUploadEntity.getMd5().equals(fileUploadDto.getMd5())){
                    String currentDir=FileUploadConfig.basePath+FileUploadConfig.tempPath+fileUploadEntity.getMd5();
                    File file = new File(currentDir);
                    FileUtils.delFile(file);
                }
                res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).data(fileUploadPOJO);
            }
            releaseUploadInfo(fileUploadDto);
        }catch (Throwable e){
            String currentDir=FileUploadConfig.basePath+FileUploadConfig.tempPath+fileUploadDto.getMd5();
            File file = new File(currentDir);
            FileUtils.delFile(file);
            res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("同步失败！请重新上传!").data(fileUploadPOJO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }finally {
            reentrantLock.unlock();
            return res;
        }
    }


    @Override
    public Map<String, Object> getUploadInfoByParam(FileUploadDto fileUploadDto) {
        LocalAssert.notNull(fileUploadDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(fileUploadDto.getPageSize(),"pageSize不能为空");
        Page page=PageHelper.startPage(fileUploadDto.getPageNum(),fileUploadDto.getPageSize());
        Map<String,Object> queryMap=Maps.newHashMap();
        if(StringUtils.isNotBlank(fileUploadDto.getQueryStr())){
            queryMap.put("queryStr",fileUploadDto.getQueryStr());
        }
        if(fileUploadDto.getState()!=null){
            queryMap.put("state",fileUploadDto.getState());
        }
        List<FileUploadEntity> fileUploadEntityList=fileUploadMapper.selectListByParam(queryMap);
        List<FileUploadVo> fileUploadVoList=Lists.newArrayList();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        fileUploadVoList = fileUploadEntityList.stream().map(e ->
            {
                FileUploadVo fileUploadVo  =JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), FileUploadVo.class);
                if(e.getUpdateTime()!=null){
                    fileUploadVo.setUpDateTime(DateUtils.convert(e.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                if(e.getUploadTime()!=null){
                    fileUploadVo.setUploadTime(DateUtils.convert(e.getUploadTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                return fileUploadVo;
            }
        ).collect(Collectors.toList());
        PageInfo<FileUploadVo> pageInfo = new PageInfo<>(fileUploadVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",fileUploadVoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil delUploadInfoByParam(FileUploadDto fileUploadDto) {
        LocalAssert.notNull(fileUploadDto.getMd5(),"MD5不能为空");
        QueryWrapper<FileUploadEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("MD5",fileUploadDto.getMd5());
        ResultUtil res=ResultUtil.build();
        if(fileUploadMapper.selectCount(queryWrapper)==0){
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("不存在这样的记录！");
        }else{
            String currentDir=FileUploadConfig.basePath+FileUploadConfig.tempPath+fileUploadDto.getMd5();
            File file = new File(currentDir);
            if(!file.exists()&&!file.isDirectory()) {
                return res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("删除失败:与记录不一致，请联系管理员！");
            }else {
                reentrantLock.lock();
                try {
                    Map<String,Object> queryMap= Maps.newHashMap();
                    queryMap.put("MD5",fileUploadDto.getMd5().trim());
                    FileUploadEntity fileUploadEntity=fileUploadMapper.selectFileUpload(queryMap);
                    QueryWrapper qw=new QueryWrapper();
                    qw.in("BATCH_ID",fileUploadEntity.getBatchId());
                    fileUploadMapper.delete(qw);
                    tErrorMapper.delete(qw);
                    tFirstpageTestingMapper.delete(qw);
                    tFirstoutdiagTestingMapper.delete(qw);
                    tFirstoutoperTestingMapper.delete(qw);
                    tErrorDetailMapper.delete(qw);
                    FileUtils.delFile(file);
                    res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("删除成功！");
                }catch (Throwable e){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("删除失败，发生未知错误！");
                }finally {
                    reentrantLock.unlock();
                    return res;
                }
            }
        }
    }

    @Override
    @Transactional
    public ResultUtil batchDelUploadInfoByParam(String fileIds) {
        ResultUtil res=ResultUtil.build();
        LocalAssert.notNull(fileIds,"主键id组不能为空");
        String fileIdsArr[]=fileIds.split(",");
        if(fileIdsArr.length==0){
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("不存在这些的记录！");
        }
        try {
            List<FileUploadEntity> fileUploadEntityList=fileUploadMapper.selectFileUploadList(fileIdsArr);
            if(fileUploadEntityList.size()==0){
                res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg("删除失败，删除的数据无一存在！");
            }else {
                List<Long> filteredIdsList=fileUploadEntityList.stream().map(FileUploadEntity::getFileId).collect(Collectors.toList());
                List<Long> filteredBIdsList=fileUploadEntityList.stream().map(FileUploadEntity::getBatchId).collect(Collectors.toList());
                fileUploadMapper.batchDeleteFile(filteredIdsList);
                tErrorMapper.batchDeleteTError(filteredBIdsList);
                QueryWrapper qw=new QueryWrapper();
                qw.in("BATCH_ID",filteredBIdsList);
                tFirstpageTestingMapper.delete(qw);
                tFirstoutdiagTestingMapper.delete(qw);
                tFirstoutoperTestingMapper.delete(qw);
                tErrorDetailMapper.delete(qw);
                String baseDir=FileUploadConfig.basePath+FileUploadConfig.tempPath;
                for(FileUploadEntity fileUploadEntity:fileUploadEntityList){
                    String currentDir=baseDir+fileUploadEntity.getMd5();
                    File file = new File(currentDir);
                    FileUtils.delFile(file);
                }
                res.code(ResultCodeEnum.getCode(ResultCodeEnum.SUCCESS)).msg("删除成功！");
            }
        }catch (Throwable e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).msg(e.getMessage());
        }
        return res;
    }

    /**
     */
//    public static void download(HttpServletRequest request, HttpServletResponse response, File proposeFile) {
//        LOGGER.debug("下载文件路径：" + proposeFile.getPath());
//        InputStream inputStream = null;
//        OutputStream bufferOut = null;
//        try {
//            // 设置响应报头
//            long fSize = proposeFile.length();
//            response.setContentType("application/x-download");
//            // Content-Disposition: attachment; filename=WebGoat-OWASP_Developer-5.2.zip
//            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(proposeFile.getName(), ENCODING));
//            // Accept-Ranges: bytes
//            response.setHeader("Accept-Ranges", "bytes");
//            long pos = 0, last = fSize - 1, sum = 0;//pos开始读取位置;  last最后读取位置;  sum记录总共已经读取了多少字节
//            if (null != request.getHeader("Range")) {
//                // 断点续传
//                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//                try {
//                    // 情景一：RANGE: bytes=2000070- 情景二：RANGE: bytes=2000070-2000970
//                    String numRang = request.getHeader("Range").replaceAll("bytes=", "");
//                    String[] strRange = numRang.split("-");
//                    if (strRange.length == 2) {
//                        pos = Long.parseLong(strRange[0].trim());
//                        last = Long.parseLong(strRange[1].trim());
//                    } else {
//                        pos = Long.parseLong(numRang.replaceAll("-", "").trim());
//                    }
//                } catch (NumberFormatException e) {
//                    LOGGER.error(request.getHeader("Range") + " is not Number!");
//                    pos = 0;
//                }
//            }
//            long rangLength = last - pos + 1;// 总共需要读取的字节
//            // Content-Range: bytes 10-1033/304974592
//            String contentRange = new StringBuffer("bytes ").append(pos).append("-").append(last).append("/").append(fSize).toString();
//            response.setHeader("Content-Range", contentRange);
//            // Content-Length: 1024
//            response.addHeader("Content-Length", String.valueOf(rangLength));
//
//            // 跳过已经下载的部分，进行后续下载
//            bufferOut = new BufferedOutputStream(response.getOutputStream());
//            inputStream = new BufferedInputStream(new FileInputStream(proposeFile));
//            inputStream.skip(pos);
//            byte[] buffer = new byte[1024];
//            int length = 0;
//            while (sum < rangLength) {
//                length = inputStream.read(buffer, 0, ((rangLength - sum) <= buffer.length ? ((int) (rangLength - sum)) : buffer.length));
//                sum = sum + length;
//                bufferOut.write(buffer, 0, length);
//            }
//        } catch (Throwable e) {
//            if (e instanceof ClientAbortException) {
//                // 浏览器点击取消
//                LOGGER.info("用户取消下载!");
//            } else {
//                LOGGER.info("下载文件失败....");
//                e.printStackTrace();
//            }
//        } finally {
//            try {
//                if (bufferOut != null) {
//                    bufferOut.close();
//                }
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

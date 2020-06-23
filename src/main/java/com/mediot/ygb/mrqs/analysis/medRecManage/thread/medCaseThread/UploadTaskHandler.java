package com.mediot.ygb.mrqs.analysis.medRecManage.thread.medCaseThread;

import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.checkheadLineThread.CheckHeadLineRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileHandle;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.fetchFileAnalyse.FetchFileRequest;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.common.util.DeCompressUtil;
import com.mediot.ygb.mrqs.config.FileUploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;



public class UploadTaskHandler implements Callable<String> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MedRecordRequest request;

    public UploadTaskHandler(MedRecordRequest request){
        this.request=request;
    }

    @Override
    public String call() {
        FileAnalysisDto fileAnalysisDto=request.getFileAnalysisDto();
        //解压缩,给予原始文件
        List<File> analysisFileList=UnzipFile(fileAnalysisDto);
        if(checkHeadLineIsInvalid(analysisFileList,fileAnalysisDto)){
            parseFile2DataListAndAnalysis(analysisFileList,fileAnalysisDto);
        }else {
            ProgressVo progressVo=new ProgressVo();
            progressVo.setAnalysisStatus(false);
            progressVo.setProgress(0.00);
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL));
            progressVo.setErrorMsg("清洗失败，存在文件表头与标准不一致的情况！");
            fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL),progressVo);
        }
        return "完成任务";
    }

    private boolean checkHeadLineIsInvalid(List<File> analysisFileList,FileAnalysisDto fileAnalysisDto) {
        CountDownLatch countDownLatch=new CountDownLatch(analysisFileList.size());
        analysisFileList.stream().forEach(e->{
            FutureTask<String> f=new FutureTask<>(new CheckHeadLineHandler(new CheckHeadLineRequest(e,countDownLatch,fileAnalysisDto)));
            Thread thread=new Thread(f);
            thread.start();
        });
        try {
            countDownLatch.await();
        }catch (Exception e){
            ProgressVo progressVo=new ProgressVo();
            progressVo.setAnalysisStatus(false);
            progressVo.setProgress(0.00);
            progressVo.setErrorMsg("清洗失败，文件未能正确打开！");
            fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL),progressVo);
        }
        if(fileAnalysisDto.getErrorHeadLine()>0){
            return false;
        }
        return true;
    }


    public void parseFile2DataListAndAnalysis(List<File> fileList,FileAnalysisDto fileAnalysisDto){
        fileList.stream().forEach(e->{
            FutureTask<String> f=new FutureTask<>(new FetchFileHandle(new FetchFileRequest(e,fileAnalysisDto)));
            Thread thread=new Thread(f);
            thread.start();
        });
    }

    public List<File> UnzipFile(FileAnalysisDto fileAnalysisDto){
        String fileName = fileAnalysisDto.getFile().getName();
        boolean isMatch= Pattern.matches("^.*\\.(rar|RAR|zip|ZIP)$",fileName);
        List<File> analysisFileList= Lists.newArrayList();
        try {
            ProgressVo progressVo=new ProgressVo();
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING));
            progressVo.setProgress(0.00);
            progressVo.setAnalysisStatus(true);
            progressVo.setFileName(fileAnalysisDto.getFileName());
//            //todo bulid模式重构
//            ProgressVo progressVo1=new ProgressVo();
//            progressVo1.setAnalysisStatus(true);
//            progressVo1.setFileName(fileAnalysisDto.getFileName());
//            progressVo1.setState(AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING));
//            progressVo1.setAnalysedDataNum(0);
//            double pg1 = new BigDecimal(0.00).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//            progressVo1.setProgress(pg1);
//            //
//            ProgressVo progressVo2=new ProgressVo();
//            progressVo2.setAnalysisStatus(true);
//            progressVo2.setFileName(fileAnalysisDto.getFileName());
//            progressVo2.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN));
//            progressVo2.setAnalysedDataNum(0);
//            double pg2 = new BigDecimal(0.00).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//            progressVo2.setProgress(pg2);
//            //
//            ProgressVo progressVo3=new ProgressVo();
//            progressVo3.setAnalysisStatus(true);
//            progressVo3.setFileName(fileAnalysisDto.getFileName());
//            progressVo3.setState(AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING));
//            progressVo3.setAnalysedDataNum(0);
//            double pg3 = new BigDecimal(0.00).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//            progressVo3.setProgress(pg3);
              fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo);
//            fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo1);
//            fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN),progressVo2);
//            fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo3);
            if(isMatch){
                try {
                    String sourceFile= FileUploadConfig.basePath+FileUploadConfig.tempPath+ fileAnalysisDto.getMd5() +"/"+fileName;
                    String unZipTempPath=FileUploadConfig.basePath+FileUploadConfig.unZipPath+ fileAnalysisDto.getMd5() +"/"+fileName;
                    Boolean UnzipFileFlag=DeCompressUtil.deCompress(sourceFile,unZipTempPath);
                    if(UnzipFileFlag){
                        logger.info("解压成功！");
                        File file = new File(unZipTempPath);
                        File[] tempList = file.listFiles();
                        analysisFileList= Arrays.asList(tempList);
                        progressVo.setProgress(1.00);
                        fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo);
                    }else {
                        progressVo.setAnalysisStatus(false);
                        progressVo.setErrorMsg("解压失败，发生未知错误！");
                        fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo);
                        logger.info("解压失败，发生未知错误！");
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }else {
                boolean isCorrectFile= Pattern.matches("^.*\\.(xls|csv|dbf|xlsx|XLS|CSV|DBF|XLSX)$",fileName);
                if(isCorrectFile){
                    analysisFileList.add(fileAnalysisDto.getFile());
                }else {
                    progressVo.setAnalysisStatus(false);
                    progressVo.setProgress(1.00);
                    progressVo.setErrorMsg("不能处理这种格式的文件！");
                    fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo);
                }
            }
            progressVo.setProgress(1.00);
            fileAnalysisDto.getRedisTemplate().opsForValue().set(fileAnalysisDto.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.FILE_WAITING),progressVo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return analysisFileList;
    }
}

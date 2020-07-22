package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;

import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.analysis.medRecManage.dto.ThreadStateDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.MyErrorDetiVo;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.MyErrorDetaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCheckHandler implements Callable<String> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DataAnalyseRequset dataAnalyseRequset;

    private CountDownLatch countDownLatch;

    private List<TCheckCol> tCheckColLists;

    private int currentBatchNum;

    private List<MyErrorDetaEntity> myErrorDetaEntityList;

    private AtomicInteger at;

    private int totalTcheckNum;

    private static Object object=new Object();

    public MyCheckHandler(DataAnalyseRequset dataAnalyseRequset,CountDownLatch countDownLatch,List<TCheckCol> tCheckColLists,int currentBatchNum,List<MyErrorDetaEntity> myErrorDetaEntityList,AtomicInteger at,int totalTcheckNum){
        this.dataAnalyseRequset=dataAnalyseRequset;
        this.countDownLatch = countDownLatch;
        this.tCheckColLists = tCheckColLists;
        this.currentBatchNum = currentBatchNum;
        this.myErrorDetaEntityList = myErrorDetaEntityList;
        this.at = at;
        this.totalTcheckNum = totalTcheckNum;
    }

    @Override
    public String call() throws Exception {
        try{
            StringBuffer tSb=new StringBuffer();
            tSb.append(dataAnalyseRequset.getFileAnalysisDto().getFileId()).append(":").append(Thread.currentThread().getName());
            if(Thread.currentThread().isInterrupted()){
                dataAnalyseRequset.getFileAnalysisDto().getTheadMaps().remove(tSb.toString());
                System.out.println("检测到中断状态");
                return Thread.currentThread().getName()+"解析中断！";
            }else {
                ThreadStateDto threadStateDto=new ThreadStateDto();
                threadStateDto.setIsPause(false);
                threadStateDto.setThread(Thread.currentThread());
                dataAnalyseRequset.getFileAnalysisDto().getTheadMaps().put(tSb.toString(),threadStateDto);
                for(TCheckCol tCheckCol : tCheckColLists){
                    //替换batchId
                    String sql1 = tCheckCol.getVerificationLogic().replace("batchId_tag",dataAnalyseRequset.getFileAnalysisDto().getBatchId()+"");
                    //替换StandardCode
                    String finalSQL = sql1.replace("standardCode_tag",dataAnalyseRequset.getFileAnalysisDto().getStandardCode());
                    setAnalysedInfo(finalSQL,tCheckCol);
                }
            }
        } catch (Exception e){
            logger.info("规则检测线程出错，原因是："+e.getMessage());
        }finally {
            countDownLatch.countDown();
            logger.info("进度任务数减1,其值为："+countDownLatch.getCount());
        }
        return null;
    }

    protected void setAnalysedInfo(String finalSQL,TCheckCol tCheckCol){
        List<MyErrorDetiVo> myErrorDetiVos=Lists.newArrayList();
       try{
           long s=System.currentTimeMillis();
           myErrorDetiVos= dataAnalyseRequset.getFileAnalysisDto().getMyErrorDetiVoMapper().findHitRecordByQueryStr(finalSQL);
           long e=System.currentTimeMillis();
           logger.info("批次" + currentBatchNum +" 该命中函数所花时间为："+(e-s)+"ms"+",其语句为: "+finalSQL);
       }catch (Exception e){
           logger.info("命中函数出错，原因是"+e.getMessage());
       }finally {
           synchronized (object){
               ProgressVo progressVo=new ProgressVo();
               progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE));
               double pg;
               Integer pn=at.incrementAndGet();
               logger.info("当前进度数为："+at.get());
               pg = (float)pn/(totalTcheckNum+1);
               progressVo.setAnalysisStatus(true);
               progressVo.setProgress(pg);
               dataAnalyseRequset.getFileAnalysisDto().getRedisTemplate().opsForValue().set(dataAnalyseRequset.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE),progressVo);
               dataAnalyseRequset.getFileAnalysisDto().setIndicators(totalTcheckNum);
               myErrorDetiVos.stream().forEach(e->{
                   MyErrorDetaEntity t=new MyErrorDetaEntity();
                   t.setBatchId(dataAnalyseRequset.getFileAnalysisDto().getBatchId());
                   t.setCaseId(e.getCaseNo());
                   t.setColName(tCheckCol.getColName());
                   t.setColComments(tCheckCol.getColComments());
                   t.setErrorMessage(tCheckCol.getRuleDescription());
                   t.setAnalyseType(dataAnalyseRequset.getFileAnalysisDto().getStandardCode());
                   t.setTFirstPageTestingId(e.getTFirstPageTestingId());
                   t.setTFirstoutoperTestingId(e.getTFirstoutoperTestingId());
                   t.setOperationType(e.getOperationType());
                   t.setOperationOrder(e.getOperationOrder());
                   t.setTFirstoutdiagTestingId(e.getTFirstoutdiagTestingId());
                   t.setDiagType(e.getDiagType());
                   t.setDiagOrder(e.getDiagOrder());
                   t.setInformationClass(tCheckCol.getInformationClass());
                   myErrorDetaEntityList.add(t);
               });
           }
       }
    }
}

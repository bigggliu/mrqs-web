package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;
import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.analysis.medRecManage.dto.ThreadStateDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.MyErrorDetiVo;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.entity.TCheckCol;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorDetailMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.MyErrorDetaEntity;
import com.mediot.ygb.mrqs.index.errorInfoManage.entity.TErrorDetailEntity;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CaseOfBatchHandler implements Callable<String> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CaseOfBatchRequest caseOfBatchRequest;

    private static Object object=new Object();


    public CaseOfBatchHandler(CaseOfBatchRequest caseOfBatchRequest){
        this.caseOfBatchRequest=caseOfBatchRequest;
    }

    @Override
    public String call() throws Exception {
        try {
            //
            StringBuffer tSb=new StringBuffer();
            tSb.append(caseOfBatchRequest.getDataAnalyseRequset().getFileAnalysisDto().getFileId()).append(":").append(Thread.currentThread().getName());
            if(Thread.currentThread().isInterrupted()){
                caseOfBatchRequest.getDataAnalyseRequset().getFileAnalysisDto().getTheadMaps().remove(tSb.toString());
                System.out.println("检测到中断状态");
                return Thread.currentThread().getName()+"解析中断！";
            }else {
                //
                ThreadStateDto threadStateDto=new ThreadStateDto();
                threadStateDto.setIsPause(false);
                threadStateDto.setThread(Thread.currentThread());
                caseOfBatchRequest.getDataAnalyseRequset().getFileAnalysisDto().getTheadMaps().put(tSb.toString(),threadStateDto);
                //找数据
                FileAnalysisDto f=caseOfBatchRequest
                        .getDataAnalyseRequset().getFileAnalysisDto();
                Map<String,Object> queryMap=new HashMap<>();
                //queryMap.put("orgId","1266216344326770690");
                queryMap.put("orgId",caseOfBatchRequest.getDataAnalyseRequset().getFileAnalysisDto().getUpOrgId());
                List<TCheckCol> tCheckCols=f.getTCheckColMapper().selectTCheckColsByOrgId(queryMap);
                //首页id分页起始值
                int pageSet = caseOfBatchRequest.getCurrentNum()*caseOfBatchRequest.getOnceNum()+1;
                //首页id分页结束值
                int pageOff = 0;
                if(caseOfBatchRequest.getCurrentNum()==caseOfBatchRequest.getBatchNum()-1){
                    pageOff = f.getTotalNumForCurrentBatchId();
                }else {
                    pageOff = (caseOfBatchRequest.getCurrentNum()+1)*caseOfBatchRequest.getOnceNum();
                }
                int finalPageOff = pageOff;
                long start = System.currentTimeMillis();
                tCheckCols.forEach(e->{
                    //拼接sql语句
                    try {
                        //标签值替换
                        String step1 = e.getVerificationLogic().replace("batchId_tag",f.getBatchId()+"");
                        String step2 = step1.replace("pageSet_tag",pageSet+"");
                        String step3 = step2.replace("pageOff_tag", finalPageOff +"");
                        String finalSQL = step3.replace("standardCode_tag",f.getStandardCode());
                        setAnalysedInfo(f,new StringBuffer(finalSQL),tCheckCols,caseOfBatchRequest,e);
                    }catch (Exception ex){
                        logger.info("逐条检测方法出错，原因是："+ex.getMessage());
                    }
                });
                long end = System.currentTimeMillis();
                logger.info("批次" + (caseOfBatchRequest.getCurrentNum()+1) + "所有检测条件执行完花费时间："+(end-start)+"ms");
            }
        }catch (Exception e){
            logger.info("逐条检测线程出错，原因是："+e.getMessage());
        }finally {
            caseOfBatchRequest.getCountDownLatch().countDown();
            logger.info("进度任务数减1,其值为："+caseOfBatchRequest.getCountDownLatch().getCount());
        }
        return null;
    }

    protected void setAnalysedInfo(FileAnalysisDto f,StringBuffer fetchStr,List<TCheckCol> tCheckCols,CaseOfBatchRequest caseOfBatchRequest,TCheckCol tCol){
        List<MyErrorDetiVo> myErrorDetiVos=Lists.newArrayList();
        try {
            long s=System.currentTimeMillis();
            logger.info("执行的完整sql："+ fetchStr);
            myErrorDetiVos= f.getMyErrorDetiVoMapper().findHitRecordByQueryStr(fetchStr.toString());
            long e=System.currentTimeMillis();
            logger.info("批次" + (caseOfBatchRequest.getCurrentNum()+1) +"该命中函数所花时间为："+(e-s)+"ms"+",其语句为"+fetchStr.toString());
        }catch (Exception e){
            logger.info("命中函数出错，原因是"+e.getMessage());
        }finally {
            List<MyErrorDetaEntity> myErrorDetaEntityList= Lists.newArrayList();
            synchronized (object){
                //
                ProgressVo progressVo=new ProgressVo();
                progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE));
                double pg;
                //caseOfBatchRequest.setCurrentNum(caseOfBatchRequest.getCurrentNum()+1);
                Integer pn=caseOfBatchRequest.getAt().incrementAndGet();
                //logger.info("批次数为"+caseOfBatchRequest.getBatchNum()+"命中函数数量为："+tCheckCols.size()+"当前进度数为："+caseOfBatchRequest.getAt().get());
                logger.info("批次数为"+(caseOfBatchRequest.getCurrentNum()+1)+"命中函数数量为："+tCheckCols.size()+"当前进度数为："+caseOfBatchRequest.getAt().get());
                pg = (float)pn/(caseOfBatchRequest.getBatchNum()*tCheckCols.size());
                progressVo.setAnalysisStatus(true);
                progressVo.setProgress(pg);
                f.getRedisTemplate().opsForValue().set(f.getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_ANALYSE),progressVo);
                //
                f.setIndicators(tCheckCols.size());
                //logger.info("累积后命中数目为："+f.getAnalysedNum());
                myErrorDetiVos.stream().forEach(e->{
                    MyErrorDetaEntity t=new MyErrorDetaEntity();
                    t.setBatchId(f.getBatchId());
                    t.setCaseId(e.getCaseNo());
                    t.setColName(tCol.getColName());
                    t.setColComments(tCol.getColComments());
                    t.setErrorMessage(tCol.getRuleDescription());
                    t.setAnalyseType(f.getStandardCode());
                    t.setTFirstPageTestingId(e.getTFirstPageTestingId());
                    t.setTFirstoutoperTestingId(e.getTFirstoutoperTestingId());
                    t.setOperationType(e.getOperationType());
                    t.setOperationOrder(e.getOperationOrder());
                    t.setTFirstoutdiagTestingId(e.getTFirstoutdiagTestingId());
                    t.setDiagType(e.getDiagType());
                    t.setDiagOrder(e.getDiagOrder());
                    myErrorDetaEntityList.add(t);
                });
            }
            int num=0;
            if(myErrorDetaEntityList.size()>0){
                num=f.getMyErrorDetaMapper().batchInsertTErrorDetails(myErrorDetaEntityList);
            }
            logger.info("该批次下错误详细数目为："+num);
            //logger.info("当前批次为："+caseOfBatchRequest.getCountDownLatch().getCount());
        }
    }

}

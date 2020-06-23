package com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataAnalyse;

import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.rowProcessors.CVSRowProcessors;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class EntryStoreHandler implements Callable<String> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private EntryStoreRequest entryStoreRequest;

    private static Object lock=new Object();



    public EntryStoreHandler(EntryStoreRequest entryStoreRequest){
        this.entryStoreRequest=entryStoreRequest;
    }

    @Override
    public String call() throws Exception {
        StringBuffer tSb=new StringBuffer();
        tSb.append(entryStoreRequest.getFileAnalysisDto().getFileId()).append(":").append(Thread.currentThread().getName());
        if(Thread.currentThread().isInterrupted()){
            entryStoreRequest.getFileAnalysisDto().getTheadMaps().remove(tSb.toString());
            System.out.println("检测到中断状态");
            return Thread.currentThread().getName()+"解析中断！";
        }else {
            DataCleanUtils.parseDataAndInsertData(entryStoreRequest.getFileAnalysisDto(),entryStoreRequest.getResultList());
            synchronized (lock){
                try {
                    ProgressVo progressVo=new ProgressVo();
                    progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.IN_ENTRYSTORE));
                    float t=(float)entryStoreRequest.getFileAnalysisDto().getRowNum();
                    float e=(float)(entryStoreRequest.getFileAnalysisDto().getEntryStoreNum()+entryStoreRequest.getTotalNum().intValue());
                    float p=(e/t);
                    entryStoreRequest.getFileAnalysisDto().setEntryStoreNum((int)e);
                    Double d=new BigDecimal(p).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                    progressVo.setProgress(d);
                    progressVo.setAnalysedDataNum((int)e);
                    logger.info(entryStoreRequest.getFileAnalysisDto().getFileName()+"入库进度为："+progressVo.getProgress());
                    progressVo.setAnalysisStatus(true);
                    entryStoreRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().set(entryStoreRequest.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.IN_ENTRYSTORE),progressVo);
                    if(progressVo.getProgress()>=1.0){
                        //入库成功！
                        ProgressVo pv=new ProgressVo();
                        pv.setState(AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE));
                        pv.setProgress(1.00);
                        pv.setAnalysisStatus(true);
                        entryStoreRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().set(entryStoreRequest.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE),pv);
                        Map<String,Object> m= com.google.common.collect.Maps.newHashMap();
                        m.put("fileId",entryStoreRequest.getFileAnalysisDto().getFileId());
                        FileUploadEntity f=entryStoreRequest.getFileAnalysisDto().getFileUploadMapper().selectFileUpload(m);
                        f.setState(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.ENTRYSTORE)));
                        entryStoreRequest.getFileAnalysisDto().getFileUploadMapper().updateById(f);
                        ConcurrentHashMap<String,Object> threadsMap=entryStoreRequest.getFileAnalysisDto().getTheadMaps();
                        for (String key:threadsMap.keySet()) {
                            if(key.substring(0,19).equals(String.valueOf(f.getFileId()))){
                                threadsMap.remove(key);
                            }
                        }
                        Set<String> rkeys = entryStoreRequest.getFileAnalysisDto().getRedisTemplate().keys("right_"+f.getFileId()+"*");
                        logger.info("入库成功！");
                        entryStoreRequest.getResultList().clear();
                        entryStoreRequest.getFileAnalysisDto().getRedisTemplate().delete(rkeys);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

package com.mediot.ygb.mrqs.analysis.medRecManage.chain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.chain.abstractHandler.ImportHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;

import java.io.IOException;
import java.math.BigDecimal;


public class DataDetectionHandler extends ImportHandler {

    public void handelReq(DataCleanRequest dataAnalysisRequest) {
        if(!dataAnalysisRequest.getIsSheet()){
            System.out.println("我已进入步骤3");
            //处理请求
            for(int i=0;i<5;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("步骤3现在读秒为"+i);
            }
            //reids同步
            setFileAnalysedProgress(dataAnalysisRequest);
        }
        //传递职责
        if (getNextHandler() != null) {
            getNextHandler().handelReq(dataAnalysisRequest);;
        }
    }

    protected synchronized ProgressVo setFileAnalysedProgress(DataCleanRequest dataAnalysisRequest){
        //处理同步信息
        dataAnalysisRequest.getFileAnalysisDto().getAiDDH().incrementAndGet();
        StringBuilder sb=new StringBuilder();
        sb.append(dataAnalysisRequest.getFileAnalysisDto().getFileId()).append("$").append(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN));
        //从redis获取既有数据，做进度分析
        dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().get(sb.toString());
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        ProgressVo progressVo=null;
        try {
            json = mapper.writeValueAsString(dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().get(sb.toString()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            progressVo = mapper.readValue(json, ProgressVo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(progressVo==null){
            progressVo=new ProgressVo();
            progressVo.setAnalysisStatus(true);
            progressVo.setAnalysedDataNum(dataAnalysisRequest.getFileAnalysisDto().getAiDDH().get());
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN));
            progressVo.setFileName(dataAnalysisRequest.getFileAnalysisDto().getFileName());
            double pg = new BigDecimal((float)(dataAnalysisRequest.getFileAnalysisDto().getAiDDH().get()/dataAnalysisRequest.getFileAnalysisDto().getRowNum())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            progressVo.setProgress(pg);
        }else {
            progressVo.setAnalysedDataNum(dataAnalysisRequest.getFileAnalysisDto().getAiDDH().get());
            progressVo.setFileName(dataAnalysisRequest.getFileAnalysisDto().getFileName());
            double pg = new BigDecimal((double) (progressVo.getAnalysedDataNum())/(float)dataAnalysisRequest.getFileAnalysisDto().getRowNum()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            progressVo.setProgress(pg);
            System.out.println("文件名为"+dataAnalysisRequest.getFileAnalysisDto().getFileName()+",进度为3的任务目前执行完毕的数目为"+progressVo.getAnalysedDataNum());
        }
        //记录到redis
        dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().set(sb.toString(),
                progressVo);
        return progressVo;
    }
}

package com.mediot.ygb.mrqs.analysis.medRecManage.chain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.chain.abstractHandler.ImportHandler;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;

import java.io.IOException;

public class FileAnalysisHandler extends ImportHandler {


    @Override
    public void handelReq(DataCleanRequest dataAnalysisRequest) {
        if(!dataAnalysisRequest.getIsSheet()){
            //设置步数
            System.out.println("我已进入步骤1");
            if(!dataAnalysisRequest.getIsSheet()){
                //处理请求
                //long start=System.currentTimeMillis();
                DataCleanUtils.cleanCaseDataBySC(dataAnalysisRequest);
                //long end=System.currentTimeMillis();
                //System.out.println("所花时间为："+(end-start)/1000);
                //处理同步信息
                setFileAnalysedProgress(dataAnalysisRequest);
                //传递职责
                if (getNextHandler() != null) {
                    getNextHandler().handelReq(dataAnalysisRequest);;
                }
            }
        }
    }

    protected synchronized ProgressVo setFileAnalysedProgress(DataCleanRequest dataAnalysisRequest){
        //处理同步信息
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
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN));
            progressVo.setFileName(dataAnalysisRequest.getFileAnalysisDto().getFileName());
            progressVo.setAnalysedDataNum(dataAnalysisRequest.getFileAnalysisDto().getAiDDH().incrementAndGet());
            double pg = (float)dataAnalysisRequest.getFileAnalysisDto().getAiDCH().get()/dataAnalysisRequest.getFileAnalysisDto().getRowNum();
            progressVo.setProgress(pg);
        }else {
            //System.out.println("文件名为"+dataAnalysisRequest.getFileAnalysisDto().getFileName()+",进度为2的任务目前执行完毕的数目为"+progressVo.getAnalysedDataNum());
            progressVo.setAnalysedDataNum(dataAnalysisRequest.getFileAnalysisDto().getAiDDH().incrementAndGet());
            progressVo.setFileName(dataAnalysisRequest.getFileAnalysisDto().getFileName());
            double pg = (float)(progressVo.getAnalysedDataNum())/dataAnalysisRequest.getFileAnalysisDto().getRowNum();
            progressVo.setProgress(pg);
        }
        //记录到redis
        dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().set(sb.toString(),
                progressVo);
        return progressVo;
    }
}

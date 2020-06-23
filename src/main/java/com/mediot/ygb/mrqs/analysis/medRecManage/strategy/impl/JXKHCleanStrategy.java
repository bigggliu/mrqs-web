package com.mediot.ygb.mrqs.analysis.medRecManage.strategy.impl;

import com.google.common.collect.Maps;

import com.mediot.ygb.mrqs.analysis.medRecManage.dto.ParseDataDto;
import com.mediot.ygb.mrqs.analysis.medRecManage.entity.TDatacleanStandard;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.AnalysisEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.enumcase.StandardCodeEnum;
import com.mediot.ygb.mrqs.analysis.medRecManage.strategy.DataCleanStrategy;
import com.mediot.ygb.mrqs.analysis.medRecManage.thread.dataCleanThread.DataCleanRequest;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.ProgressVo;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.entity.dto.FileAnalysisDto;
import com.mediot.ygb.mrqs.common.util.DataCleanUtils;
import com.mediot.ygb.mrqs.common.util.DateUtils;
import com.mediot.ygb.mrqs.common.util.SerializingUtil;
import com.mediot.ygb.mrqs.dict.entity.TBaseDict;
import com.mediot.ygb.mrqs.dict.entity.TDiagDict;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.entity.FileUploadEntity;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase.StateEnum;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JXKHCleanStrategy implements DataCleanStrategy {

    private DataCleanRequest dataAnalysisRequest;

    public JXKHCleanStrategy(DataCleanRequest dataAnalysisRequest){
        this.dataAnalysisRequest=dataAnalysisRequest;
    }

    private static final Object lock=new Object();

    @Override
    public void startDataClean() {
        try {
            ParseDataDto parseDataDto=(ParseDataDto)dataAnalysisRequest.getExcelData();
            FileAnalysisDto fileAnalysisDto=dataAnalysisRequest.getFileAnalysisDto();
            String[] data=(String[])(parseDataDto.getBaseData());
            //组装数据
            Map<String,Object> resultMap= Maps.newHashMap();
            synchronized (lock){
                for(int i=0;i<fileAnalysisDto.getCellNameList().size();i++){
                    for(int j=0;j<fileAnalysisDto.getTDatacleanStandardList().size();j++){
                        String cellName=fileAnalysisDto.getCellNameList().get(i);
                        String colName=fileAnalysisDto.getTDatacleanStandardList().get(j).getDataColName();
                        if(fileAnalysisDto.getStandardCode().equals(StandardCodeEnum.getValue(StandardCodeEnum.JXKH))){
                            cellName=cellName.toUpperCase();
                            colName=colName.toUpperCase();
                        }
                        if(cellName.equals(colName)){
                            TDatacleanStandard t=fileAnalysisDto.getTDatacleanStandardList().get(j);
                            t.setTempCellVal(data[i]);
                            t.setCurrentRow(dataAnalysisRequest.getCurrentRow());
                            t.setCurrentColIndex(new Long((long)j));
                            t.setCurrentFileName(dataAnalysisRequest.getFetchFileRequest().getE().getName());
                            StringBuffer sb=new StringBuffer();
                            sb.append(fileAnalysisDto.getFileId())
                                    .append("_")
                                    .append(t.getCurrentRow())
                                    .append("_")
                                    .append(t.getCurrentColIndex())
                                    .append("&")
                                    .append(t.getDataColName())
                                    .append("&")
                                    .append(t.getTempCellVal());
                            DataCleanUtils.trimStr(t);
                            resultMap.put(sb.toString()
                                    , t);
                            break;
                        }
                    }
                }
                validateMapOfData(resultMap,dataAnalysisRequest);
            }
        }catch (Exception e){
            Map<String,Object> queryMap= Maps.newHashMap();
            queryMap.put("MD5",dataAnalysisRequest.getFileAnalysisDto().getMd5());
            FileUploadEntity f=dataAnalysisRequest.getFileAnalysisDto().getFileUploadMapper().selectFileUpload(queryMap);
            f.setState(String.valueOf(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL)));
            dataAnalysisRequest.getFileAnalysisDto().getFileUploadMapper().updateById(f);
            //
            ProgressVo progressVo=new ProgressVo();
            progressVo.setState(AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL));
            progressVo.setProgress(0.00);
            progressVo.setAnalysisStatus(false);
            progressVo.setErrorMsg(e.getMessage());
            dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate().opsForValue().set(dataAnalysisRequest.getFileAnalysisDto().getFileId()+"$"+AnalysisEnum.getValue(AnalysisEnum.DATA_CLEAN_FAIL),progressVo);
        }
    }

    public void validateDataLength(String k,TDatacleanStandard t,StringBuffer errorMsg){
        if(t.getTempCellVal().length()>Integer.valueOf(t.getDataLength())){
            createErrorInfo(dataAnalysisRequest,
                    errorMsg.append("字符长度超过").append(t.getDataLength()).append("的限制!")
                    ,k,t);
        }
    }

    public TBaseDict findTBaseDictByMap(TDatacleanStandard t,String dcode){
        String basecode=dataAnalysisRequest.getFileAnalysisDto().getStandardCode();
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("dCode",dcode);
        queryMap.put("baseCode",basecode);
        queryMap.put("dictCode",t.getTempCellVal());
        return  dataAnalysisRequest.getFileAnalysisDto().getTBaseDictMapper().selectTBaseDictByMap(queryMap);
    }

    public void validateDataRemark(String k,TDatacleanStandard t,StringBuffer errorMsg){
        if(t.getRemark()!=null){
            if(t.getRemark().contains("RC")&&!t.getRemark().contains("ICD")){
                String dcode=t.getRemark().substring(t.getRemark().indexOf("R"),t.getRemark().indexOf("R")+5);
                TBaseDict tBaseDict=findTBaseDictByMap(t,dcode);
                if(tBaseDict==null){
                    createErrorInfo(dataAnalysisRequest,
                            errorMsg.append("该值不在").append(dcode).append("标准里!")
                            ,k,t);
                }
            }else if(t.getRemark().contains("ICD")){
                Map<String,Object> queryMap=Maps.newHashMap();
                queryMap.put("dictCode",t.getTempCellVal());
                TDiagDict tDiagDict=dataAnalysisRequest.getFileAnalysisDto().getTDiagDictMapper().selectTDiagDictByMap(queryMap);
                if(t.getDataColName().equals("P321")) {
                    if(tDiagDict==null&&!t.getTempCellVal().equals("NA")){
                        createErrorInfo(dataAnalysisRequest,
                                errorMsg.append("该值不在诊断或形态学编码里!")
                                ,k,t);
                    }
                }else {
                    if(tDiagDict==null){
                        createErrorInfo(dataAnalysisRequest,
                                errorMsg.append("该值不在诊断或形态学编码里!")
                                ,k,t);
                    }
                }
            };
        }
    }

    public void validateSetCol(StringBuffer errorMsg,String k,TDatacleanStandard t){
        String[] valArr=t.getTempCellVal().split(",");
        if(valArr.length==0){
            createErrorInfo(dataAnalysisRequest,
                    errorMsg.append("该集合没有按照英文逗号分割!")
                    ,k,t);
        }else {
            String dcode=t.getRemark().substring(t.getRemark().indexOf("R"),t.getRemark().indexOf("R")+5);
            String basecode=dataAnalysisRequest.getFileAnalysisDto().getStandardCode();
            Map<String,Object> queryMap=Maps.newHashMap();
            queryMap.put("dCode",dcode);
            queryMap.put("baseCode",basecode);
            List<String> valArrList= Stream.of(valArr).collect(Collectors.toList());
            List<TBaseDict> tBaseDictList=dataAnalysisRequest.getFileAnalysisDto().getTBaseDictMapper()
                    .selectTBaseDictListByMap(queryMap);
            //过滤
            List<TBaseDict> filteredBaseDictList=tBaseDictList.stream().filter(e->valArrList.contains(e.getDictCode()))
                    .collect(Collectors.toList());
            if(filteredBaseDictList.size()!=valArrList.size()){
                //求差集
                List<TBaseDict> reduceList=filteredBaseDictList.stream().filter(e->!valArrList.contains(e)).collect(Collectors.toList());
                StringBuffer errorVal=new StringBuffer();
                reduceList.stream().forEach(e->{
                    errorVal.append(e).append(",");
                });
                createErrorInfo(dataAnalysisRequest,
                        errorMsg.append("该集合中").append(errorVal.substring(0,errorVal.length()-1))
                                .append(dcode).append("标准内")
                        ,k,t);
            }
        }
    }

    public static String deleteString(String str, char delChar) {
        String delStr = "";
        char[] Bytes = str.toCharArray();
        int iSize = Bytes.length;
        for (int i = Bytes.length - 1; i >= 0; i--) {
            if (Bytes[i] == delChar) {
                for (int j = i; j < iSize - 1; j++) {
                    Bytes[j] = Bytes[j + 1];
                }
                iSize--;
            }
        }
        for (int i = 0; i < iSize; i++) {
            delStr += Bytes[i];
        }
        return delStr;
    }

    public void validateDataForNum(StringBuffer errorMsg,String k,TDatacleanStandard t){
        //数字验证(非负整数，和非负浮点数)
        Pattern pattern = Pattern.compile("^[1-9]\\d*|0$");
        Pattern _pattern = Pattern.compile("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$");
        Matcher isNum = pattern.matcher(t.getTempCellVal());
        Matcher isFloat = _pattern.matcher(t.getTempCellVal());
        if(!t.getTempCellVal().equals("0")
                &&!t.getTempCellVal().equals("0.0")
                &&!t.getTempCellVal().equals("0.00")
                &&!t.getTempCellVal().equals("0.000")
                &&!t.getTempCellVal().equals("0.0000")){
            if(!isNum.matches()){
                if(!isFloat.matches()){
                    createErrorInfo(dataAnalysisRequest,
                            errorMsg.append("该值不是一个数字！")
                            ,k,t);
                }
            }
        }
        //长度验证
        if(t.getDataLength().contains(",")){
            //小数验证
            char delChar1 = '(';
            char delChar2 = ')';
            t.setDataLength(deleteString(t.getDataLength(),delChar1));
            t.setDataLength(deleteString(t.getDataLength(),delChar2));
            String[] rangeArr=t.getDataLength().split(",");
            String reg="^\\d{1,"+rangeArr[0]+"}(\\.\\d{1,"+rangeArr[1]+"})?$";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(t.getTempCellVal());
            if(!m.matches()){
                createErrorInfo(dataAnalysisRequest,
                        errorMsg.append("该值不在(").append(rangeArr[0]).append(",").append(rangeArr[1]).append(")")
                        .append("范围内")
                        ,k,t);
            }
        }else {
            if(t.getTempCellVal().length()>Integer.valueOf(t.getDataLength())){
                createErrorInfo(dataAnalysisRequest,
                        errorMsg.append("长度超过").append(t.getDataLength()).append("的范围！")
                        ,k,t);
            }
        }
    }

    public void createErrorInfo(DataCleanRequest dataAnalysisRequest, StringBuffer errorMsg, String k, TDatacleanStandard t){
        dataAnalysisRequest.getDataErrorNum().incrementAndGet();
        t.setErrorMsg(errorMsg.toString());
        StringBuffer errorKey=new StringBuffer();
        errorKey.append("error_").append(dataAnalysisRequest.getFileAnalysisDto().getFileId());
        //String errorKey="error_"+dataAnalysisRequest.getFileAnalysisDto().getFileId();//+"_"+t.getCurrentRow()+"_"+t.getDataColName();
        t.setCurrentRow(dataAnalysisRequest.getCurrentRow());
        t.setCurrentRow(dataAnalysisRequest.getCurrentRow());
        t.setCurrentFileName(dataAnalysisRequest.getFetchFileRequest().getE().getName());
        dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate().opsForList().rightPush(errorKey.toString(),t);
        //dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate().expire(errorKey.toString(),24,TimeUnit.HOURS);
    }

    public void validateMapOfData(Map<String,Object> resultMap, DataCleanRequest dataAnalysisRequest){
        try {
            RedisTemplate redisTemplate=dataAnalysisRequest.getFileAnalysisDto().getRedisTemplate();
            resultMap.forEach((k,v)->{
                StringBuffer errorMsg=new StringBuffer();
                TDatacleanStandard t=((TDatacleanStandard)v);
                if(t.getDataType().contains("字符")){
                    if(StringUtils.isNotBlank(t.getTempCellVal())){
                        validateDataLength(k,t,errorMsg);
                    }
                }else if(t.getDataType().contains("数字")){
                    if(StringUtils.isNotBlank(t.getTempCellVal())){
                        if(!t.getTempCellVal().contains("-")){
                            validateDataForNum(errorMsg,k,t);
                        }
                    }
                }else if(t.getDataType().contains("集合")){
                    if(StringUtils.isNotBlank(t.getTempCellVal())){
                        String[] setArr=t.getTempCellVal().split(",");
                        if(setArr.length==0){
                            createErrorInfo(dataAnalysisRequest,
                                    errorMsg.append("不符合英文逗号分隔的规范！")
                                    ,k,t);
                        }
                    }
                }
                resultMap.put(k," ");
            });
            StringBuffer rightKey=new StringBuffer();
            rightKey.append("right_").append(dataAnalysisRequest.getFileAnalysisDto().getFileId()).append("_")
                    .append(dataAnalysisRequest.getFetchFileRequest().getE().getName());
            redisTemplate.opsForList().rightPush(rightKey.toString(),resultMap);
            redisTemplate.expire("right_"+dataAnalysisRequest.getFileAnalysisDto().getFileId(),24,TimeUnit.HOURS);
            StringBuffer errorKey=new StringBuffer();
            errorKey.append("error_").append(dataAnalysisRequest.getFileAnalysisDto().getFileId());
            redisTemplate.expire(errorKey.toString(),24,TimeUnit.HOURS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String k="626.4";
        Pattern pattern = Pattern.compile("^[1-9]\\d*|0$");
        Pattern _pattern = Pattern.compile("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$");
        Matcher isNum = pattern.matcher(k);
        Matcher isFloat = _pattern.matcher(k);
        if(!k.equals("0")&&!k.equals("0.0")){
            if(!isNum.matches()){
                if(!isFloat.matches()){
                   System.out.println("该值不是一个数字！");
                }
            }
        }

    }
}

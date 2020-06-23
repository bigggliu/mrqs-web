package com.mediot.ygb.mrqs.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.common.entity.excelModel.HQMSData;
import com.mediot.ygb.mrqs.common.entity.excelModel.JXKHData;
import com.mediot.ygb.mrqs.common.entity.excelModel.WTData;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutdiagTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutdiagTesting;
import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstoutoperTesting;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.List;


public class DataAnalyseUtils {


    public static JXKHData parse2JXKHData(TFirstPageTesting e,TFirstoutoperTestingMapper tdm,TFirstoutdiagTestingMapper tom) {
        JXKHData j=new JXKHData();
        BeanUtils.copyProperties(e,j);
        //获取诊断信息
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("BATCH_ID",e.getBatchId());
        queryWrapper.eq("CASE_ID",e.getCaseNo());
        List<TFirstoutoperTesting> tFirstoutoperTestings=tdm.selectList(queryWrapper);
        List<TFirstoutoperTesting> tOOfOther= Lists.newArrayList();
        //反射注入属性和属性值
        if(tFirstoutoperTestings.size()>0){
            tFirstoutoperTestings.stream().forEach(t->{
                if(t.getOperationType().equals("01")){
                    j.setC14X01C(t.getOperationCode());
                    j.setC16X01(t.getOperationDtime());
                    j.setC17X01(t.getOperationLevel());
                    j.setC18x01(t.getSurgeon());
                    j.setC19X01(t.getAssistant1());
                    j.setC20X01(t.getAssistant2());
                    j.setC21X01C(t.getIncisionHealing());
                    j.setC22X01C(t.getAnesthesiaMode());
                    j.setC23X01(t.getAnesthesiologist());
                    j.setC15X01N(t.getOperationName());
                }else if(t.getOperationType().equals("02")){
                    tOOfOther.add(t);
                }
                return;
            });
        }
        if(tOOfOther.size()>0){
            for(int i=0;i<tOOfOther.size();i++){
                Class cls = j.getClass();
                Field[] fields = cls.getDeclaredFields();
                for(int k=0;k<fields.length;k++){
                    try{
                        Field field = fields[k];
                        field.setAccessible(true);
                        String name = field.getName();
                        String oOC="";
                        String oOD="";
                        String oOL="";
                        String oON="";
                        String oS="";
                        String oA1="";
                        String oA2="";
                        String oAM="";
                        String oIH="";
                        String oAn="";
                        if(i>=10){
                            oOC="C35X0"+i+"C";
                            oOD="C37X0"+i;
                            oOL="C38X0"+i+"C";
                            oON="C36X0"+i+"N";
                            oS="C39X0"+i;
                            oA1="C40X0"+i;
                            oA2="C41X0"+i;
                            oAM="C43X0"+i+"C";
                            oIH="C42X0"+i+"C";
                            oAn="C44X0"+i;
                        }else {
                            oOC="C35X"+(i+1)+"C";
                            oOD="C37X"+(i+1);
                            oOL="C38X"+(i+1)+"C";
                            oON="C36X"+(i+1)+"N";
                            oS="C39X"+(i+1);
                            oA1="C40X"+(i+1);
                            oA2="C41X"+(i+1);
                            oAM="C43X"+(i+1)+"C";
                            oIH="C42X"+(i+1)+"C";
                            oAn="C44X"+(i+1);
                        }
                        if(name.toUpperCase().equals(oOC)){
                            field.set(j,tOOfOther.get(i).getOperationCode());
                            break;
                        }
                        if(name.toUpperCase().equals(oOD)){
                            field.set(j,tOOfOther.get(i).getOperationDtime());
                            break;
                        }
                        if(name.toUpperCase().equals(oOL)){
                            field.set(j,tOOfOther.get(i).getOperationLevel());
                            break;
                        }
                        if(name.toUpperCase().equals(oON)){
                            field.set(j,tOOfOther.get(i).getOperationName());
                            break;
                        }
                        if(name.toUpperCase().equals(oS)){
                            field.set(j,tOOfOther.get(i).getSurgeon());
                            break;
                        }
                        if(name.toUpperCase().equals(oA1)){
                            field.set(j,tOOfOther.get(i).getAssistant1());
                            break;
                        }
                        if(name.toUpperCase().equals(oA2)){
                            field.set(j,tOOfOther.get(i).getAssistant2());
                            break;
                        }
                        if(name.toUpperCase().equals(oAM)){
                            field.set(j,tOOfOther.get(i).getAnesthesiaMode());
                            break;
                        }
                        if(name.toUpperCase().equals(oIH)){
                            field.set(j,tOOfOther.get(i).getIncisionHealing());
                            break;
                        }
                        if(name.toUpperCase().equals(oAn)){
                            field.set(j,tOOfOther.get(i).getAnesthesiologist());
                            break;
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        List<TFirstoutdiagTesting> tFirstoutdiagTestings=tom.selectList(queryWrapper);
        List<TFirstoutdiagTesting> tfOfOther= Lists.newArrayList();
        if(tFirstoutdiagTestings.size()>0){
            tFirstoutdiagTestings.stream().forEach(t->{
                if(t.getDiagType().equals("01")){
                    j.setC03C(t.getDiagnosisCode());
                    j.setC04N(t.getDiagnosisName());
                    j.setC05C(t.getInCondition());
                }
                if(t.getDiagType().equals("03")){
                    j.setC09C(t.getDiagnosisCode());
                    j.setC10N(t.getDiagnosisName());
                    j.setC11(t.getPathologicalNumber());
                }
                if(t.getDiagType().equals("04")){
                    j.setC12C(t.getDiagnosisCode());
                    j.setC13N(t.getDiagnosisName());
                }
                if(t.getDiagType().equals("02")){
                    tfOfOther.add(t);
                }
            });
            if(tfOfOther.size()>0){
                for(int i=0;i<tfOfOther.size();i++){
                    Class cls = j.getClass();
                    Field[] fields = cls.getDeclaredFields();
                    for(int k=0;k<fields.length;k++){
                        try{
                            Field field = fields[k];
                            field.setAccessible(true);
                            String name = field.getName();
                            String oDC="";
                            String oDN="";
                            String oPN="";
                            if(i>=10){
                                oDC="C06X"+i+"C";
                                oDN="C07X"+i+"N";
                                oPN="C08X"+i+"C";
                            }else {
                                oDC="C06X0"+(i+1)+"C";
                                oDN="C07X0"+(i+1)+"N";
                                oPN="C08X0"+(i+1)+"C";
                            }
                            if(name.toUpperCase().equals(oDC)){
                                field.set(j,tfOfOther.get(i).getDiagnosisCode());
                                break;
                            }
                            if(name.toUpperCase().equals(oDN)){
                                field.set(j,tfOfOther.get(i).getDiagnosisName());
                                break;
                            }
                            if(name.toUpperCase().equals(oPN)){
                                field.set(j,tfOfOther.get(i).getPathologicalNumber());
                                break;
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        return j;
    }

    public static WTData parse2WTData(TFirstPageTesting e,TFirstoutoperTestingMapper tdm,TFirstoutdiagTestingMapper tom) {
        WTData w=new WTData();
        BeanUtils.copyProperties(e,w);
        return w;
    }

    public static HQMSData parse2HQMSData(TFirstPageTesting e,TFirstoutoperTestingMapper tdm,TFirstoutdiagTestingMapper tom) {
        HQMSData h=new HQMSData();
        BeanUtils.copyProperties(e,h);
        return h;
    }
}

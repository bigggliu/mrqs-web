package com.mediot.ygb.mrqs.common.excelListener;



import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.mediot.ygb.mrqs.analysis.medRecManage.dao.TDatacleanStandardMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.entity.TDatacleanStandard;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;

import java.util.LinkedHashMap;


public class DataCleanListener extends AnalysisEventListener {

    private TDatacleanStandardMapper tDatacleanStandardMapper;

    public DataCleanListener(TDatacleanStandardMapper tDatacleanStandardMapper) {
        this.tDatacleanStandardMapper = tDatacleanStandardMapper;
    }


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        saveData(o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    private void saveData(Object o) {
        //LOGGER.info("{}条数据，开始存储数据库！
        LinkedHashMap linkedHashMap=(LinkedHashMap)o;
        TDatacleanStandard tDatacleanStandard =new TDatacleanStandard();
        if(tDatacleanStandard.getStandardType().equals("HQMS")){
            tDatacleanStandard.setDataCol((String) linkedHashMap.get(1));
            tDatacleanStandard.setDataColName(((String) linkedHashMap.get(2)).toUpperCase());
            tDatacleanStandard.setDataType((String) linkedHashMap.get(3));
            tDatacleanStandard.setDataLength((String) linkedHashMap.get(4));
            tDatacleanStandard.setMustfill((String) linkedHashMap.get(5));
            tDatacleanStandard.setRemark((String) linkedHashMap.get(8));
            tDatacleanStandardMapper.insert(tDatacleanStandard);
        }else if(tDatacleanStandard.getStandardType().equals("JXKH")){
            tDatacleanStandard.setDataCol((String) linkedHashMap.get(1));
            tDatacleanStandard.setDataColName(((String) linkedHashMap.get(2)).toUpperCase());
            tDatacleanStandard.setDataType((String) linkedHashMap.get(5));
            tDatacleanStandard.setDataLength((String) linkedHashMap.get(6));
            tDatacleanStandard.setMustfill((String) linkedHashMap.get(7));
            tDatacleanStandard.setRemark((String) linkedHashMap.get(8));
            tDatacleanStandardMapper.insert(tDatacleanStandard);
        }else {
            tDatacleanStandard.setDataCol((String) linkedHashMap.get(3));
            tDatacleanStandard.setDataColName(((String) linkedHashMap.get(0)).toUpperCase());
            if(StringUtils.isNotBlank((String) linkedHashMap.get(1))){
                String str=(String) linkedHashMap.get(1);
                int s=str.indexOf("(");
                int e=str.indexOf(")");
                tDatacleanStandard.setDataLength(str.substring(s+1,e));
            }else {
                tDatacleanStandard.setDataLength(null);
            }
            tDatacleanStandard.setDataType((String) linkedHashMap.get(1));
            tDatacleanStandard.setMustfill((String) linkedHashMap.get(2));
            tDatacleanStandard.setRemark((String) linkedHashMap.get(4));
            tDatacleanStandardMapper.insert(tDatacleanStandard);
        }
    }


}

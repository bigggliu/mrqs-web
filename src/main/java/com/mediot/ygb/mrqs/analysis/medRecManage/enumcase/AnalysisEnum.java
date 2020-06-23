package com.mediot.ygb.mrqs.analysis.medRecManage.enumcase;

public enum AnalysisEnum {
    FILE_WAITING("文件准备中",0),
    DATA_CLEAN("数据清洗",1),
    DATA_CLEAN_FAIL("清洗失败",3),
    ENTRYSTORE("入库成功",11),
    ENTRYSTORE_FAIL("入库失败",4),
    DATA_ANALYSE("解析中",5),
    DATA_ANALYSE_FAIL("解析失败",6),
    IN_ENTRYSTORE("入库中",2),
    DATA_ANALYSE_SUCCESS("解析成功",7);


    private String stateLabel;
    private int value;

    AnalysisEnum(String stateLabel, int value){
        this.stateLabel=stateLabel;
        this.value=value;
    }

    public static String getStateLabel(int value) {
        for (AnalysisEnum a : AnalysisEnum.values()) {
            if (a.getValue()==value) {
                return a.stateLabel;
            }
        }
        return null;
    }

    public static int getValue(AnalysisEnum stateEnumEnum) {
        for (AnalysisEnum stateEnum : AnalysisEnum.values()) {
            if (stateEnum.name().equals(stateEnumEnum.name())) {
                return stateEnum.value;
            }
        }
        return 0;
    }

    public String getStateLabel() {
        return stateLabel;
    }

    public int getValue() {
        return value;
    }
}

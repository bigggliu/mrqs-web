package com.mediot.ygb.mrqs.analysis.medRecManage.enumcase;

public enum StandardCodeEnum {
    BASY("BASY","01"),
    HQMS("HQMS","02"),
    WT("WT","03"),
    JXKH("JXKH","04");

    private String stateLabel;
    private String value;

    StandardCodeEnum(String stateLabel, String value){
        this.stateLabel=stateLabel;
        this.value=value;
    }

    public static String getStateLabel(String value) {
        for (StandardCodeEnum a : StandardCodeEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.stateLabel;
            }
        }
        return null;
    }

    public static String getValue(StandardCodeEnum stateEnumEnum) {
        for (StandardCodeEnum stateEnum : StandardCodeEnum.values()) {
            if (stateEnum.name().equals(stateEnumEnum.name())) {
                return stateEnum.value;
            }
        }
        return "";
    }

    public String getStateLabel() {
        return stateLabel;
    }

    public String getValue() {
        return value;
    }
}

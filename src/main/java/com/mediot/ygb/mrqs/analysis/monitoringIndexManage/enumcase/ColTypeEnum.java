package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.enumcase;

public enum ColTypeEnum {

    integrality("完整性","1"),
    normalization("规范性","2"),
    logicality("逻辑性","3");

    private String colTypeLabel;

    private String value;

    ColTypeEnum(String colTypeLabel,String value){
        this.colTypeLabel=colTypeLabel;
        this.value=value;
    }

    public static String colTypeLabel(String value) {
        for (ColTypeEnum a : ColTypeEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.colTypeLabel;
            }
        }
        return null;
    }

    public String getColTypeLabel() {
        return colTypeLabel;
    }

    public String getValue() {
        return value;
    }
}

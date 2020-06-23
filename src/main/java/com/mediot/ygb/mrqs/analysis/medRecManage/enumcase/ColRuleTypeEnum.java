package com.mediot.ygb.mrqs.analysis.medRecManage.enumcase;

public enum ColRuleTypeEnum {

    LOGIC("逻辑性","1"),
    INTEGRITY("完整性","2"),
    NORMALIZATION("规范性","3");

    private String typeLabel;
    private String value;

    ColRuleTypeEnum(String typeLabel, String value){
        this.typeLabel=typeLabel;
        this.value=value;
    }

    public static String getTypeLabel(String value) {
        for (ColRuleTypeEnum a : ColRuleTypeEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.typeLabel;
            }
        }
        return null;
    }

    public static String getValue(ColRuleTypeEnum colRuleTypeEnum) {
        for (ColRuleTypeEnum ce : ColRuleTypeEnum.values()) {
            if (ce.name().equals(colRuleTypeEnum.name())) {
                return colRuleTypeEnum.value;
            }
        }
        return "";
    }
    public String getValue() {
        return value;
    }

}

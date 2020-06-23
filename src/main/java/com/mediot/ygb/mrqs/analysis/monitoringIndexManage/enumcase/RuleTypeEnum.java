package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.enumcase;

public enum  RuleTypeEnum {

    SQL("SQL语句","1"),
    storeProcedure("存储过程","2");

    private String ruleTypeLabel;

    private String value;

    RuleTypeEnum(String ruleTypeLabel,String value){
        this.ruleTypeLabel=ruleTypeLabel;
        this.value=value;
    }

    public static String getRuleTypeLabel(String value) {
        for (RuleTypeEnum a : RuleTypeEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.ruleTypeLabel;
            }
        }
        return null;
    }

    public String getRuleTypeLabel() {
        return ruleTypeLabel;
    }

    public String getValue() {
        return value;
    }
}

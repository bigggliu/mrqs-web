package com.mediot.ygb.mrqs.analysis.reportManage.enumcase;

public enum DwStatusEnum {

    generate("生成中",1),
    canDw("可下载",2),
    cantDW("未生成",0);

    private String dwStatusLabel;

    private int value;

    DwStatusEnum(String colTypeLabel, int value){
        this.dwStatusLabel=colTypeLabel;
        this.value=value;
    }

    public static int getValue(DwStatusEnum dwStatusEnum) {
        for (DwStatusEnum dw : DwStatusEnum.values()) {
            if (dw.name().equals(dwStatusEnum.name())) {
                return dw.value;
            }
        }
        return 0;
    }

    public int getValue() {
        return value;
    }
}

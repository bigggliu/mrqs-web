package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase;

public enum UploadStateEnum {
    REAPET_UPLOAD("重复上传",201),
    START_UPLOAD("初始化上传",501);


    private String stateLabel;
    private int value;

    UploadStateEnum(String stateLabel, int value){
        this.stateLabel=stateLabel;
        this.value=value;
    }

    public static String getStateLabel(int value) {
        for (UploadStateEnum a : UploadStateEnum.values()) {
            if (a.getValue()==value) {
                return a.stateLabel;
            }
        }
        return null;
    }

    public static int getValue(UploadStateEnum stateEnumEnum) {
        for (UploadStateEnum stateEnum : UploadStateEnum.values()) {
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

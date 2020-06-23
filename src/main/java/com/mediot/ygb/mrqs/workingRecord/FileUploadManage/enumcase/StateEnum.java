package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.enumcase;

public enum StateEnum {
    SUCCESS("上传成功","8"),
    FAIL("上传失败","9"),
    ClEANCOMPELETED("清洗完成","10");


    private String stateLabel;
    private String value;

    StateEnum(String stateLabel,String value){
        this.stateLabel=stateLabel;
        this.value=value;
    }

    public static String getStateLabel(String value) {
        for (StateEnum a : StateEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.stateLabel;
            }
        }
        return null;
    }

    public static String getValue(StateEnum stateEnumEnum) {
        for (StateEnum stateEnum : StateEnum.values()) {
            if (stateEnum.name().equals(stateEnumEnum.name())) {
                return stateEnum.value;
            }
        }
        return null;
    }

    public String getStateLabel() {
        return stateLabel;
    }

    public String getValue() {
        return value;
    }
}

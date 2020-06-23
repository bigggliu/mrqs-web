package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.enumcase;


public enum AvailableEnum {

    available("是","1"),
    unavailable("否","0");

    private String state;

    private String name;

    AvailableEnum(String name,String state){
        this.state=state;
        this.name=name;
    }

    public static String getName(String state) {
        for (AvailableEnum a : AvailableEnum.values()) {
            if (a.getState().equals(state)) {
                return a.name;
            }
        }
        return null;
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}

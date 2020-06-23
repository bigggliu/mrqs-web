package com.mediot.ygb.mrqs.common.enumcase;

public enum ResultCodeEnum {
    /**
     * 调用成功
     */
    SUCCESS(200, "成功"),

    /**
     * 调用失败
     */
    FAIL(500, "失败"),


    WAIT(201, "等待处理"),


    NONE(202, "无信息");
    /**
     * 返回编码
     */
    private Integer code;

    /**
     * 编码对应的消息
     */
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取枚举类型的编码值
     */
    public Integer code() {
        return this.code;
    }

    /**
     * 获取枚举类型的编码含义
     */
    public String msg() {
        return this.msg;
    }

    /**
     * 根据枚举名称--获取枚举编码
     */
    public static Integer getCode(ResultCodeEnum resultCodeEnum) {
        for (ResultCodeEnum resultCode : ResultCodeEnum.values()) {
            if (resultCode.name().equals(resultCodeEnum.name())) {
                return resultCode.code;
            }
        }
        return null;
    }
}
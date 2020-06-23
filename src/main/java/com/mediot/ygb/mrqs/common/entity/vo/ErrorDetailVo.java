package com.mediot.ygb.mrqs.common.entity.vo;


import lombok.Data;

@Data
public class ErrorDetailVo {

    private String sort;

    private String colName;

    private String colComments;

    private String errorMessage;

    private String total;

}

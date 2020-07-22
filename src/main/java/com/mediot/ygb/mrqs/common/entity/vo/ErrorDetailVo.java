package com.mediot.ygb.mrqs.common.entity.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ErrorDetailVo {

    private String sort;

    private String colName;

    private String colComments;

    private String errorMessage;

    private String total;

    private String diagType;

    private Integer diagOrder;

    private String operationType;

    private Integer operationOrder;

    private String informationClass;
}

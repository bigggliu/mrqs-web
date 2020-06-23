package com.mediot.ygb.mrqs.dict.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * T_OPERATION_DICT
 * @author 
 */
@Data
public class TOperationDict implements Serializable {

    private static final long serialVersionUID = 6233749899805782853L;

    private Long tOperationDictId;

    private String baseCode;

    private String dictCode;

    private String dictName;

    private String fqun;

    private String remark;

    private String fprop;

    private String nonReOp;

    private String opFlag;

    private String partName;

    private String opFlag5;

    private String checkCode;

    private String entryOptions;


}
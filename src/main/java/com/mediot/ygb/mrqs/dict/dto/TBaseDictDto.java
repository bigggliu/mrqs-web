package com.mediot.ygb.mrqs.dict.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TBaseDictDto implements Serializable {

    private static long serialVersionUID= 2696373793416589136L;

    private String baseCode;

    private String dictCode;

    private String fQun;

    private String dictName;

    private String remark;

    private String fState;

    private Integer pageNum;

    private Integer pageSize;

    private String queryStr;

}

package com.mediot.ygb.mrqs.dict.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class TBaseDictVo implements Serializable {

    private static final long serialVersionUID= -5293606728518734921L;

    private String baseCode;

    private String dictCode;

    private String fQun;

    private String dictName;

    private String remark;

    private String fState;

}

package com.mediot.ygb.mrqs.dict.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class TBaseVo implements Serializable {

    private static final long serialVersionUID= 6965091190932825562L;

    private String baseCode;

    private String baseName;

    private String parentCode;
}

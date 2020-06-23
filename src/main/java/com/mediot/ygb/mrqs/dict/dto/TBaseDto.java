package com.mediot.ygb.mrqs.dict.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class TBaseDto implements Serializable {

    public static final long serialVersionUID= -765902366199475714L;

    private String baseCode;

    private String baseName;

    private String parentCode;

    private Integer pageSize;

    private Integer pageNum;

}

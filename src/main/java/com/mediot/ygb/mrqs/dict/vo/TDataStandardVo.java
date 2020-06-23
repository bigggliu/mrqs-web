package com.mediot.ygb.mrqs.dict.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class TDataStandardVo implements Serializable {

    public static final long serialVersionUID= 7558223102320399019L;

    private String standardCode;

    private String standardName;

    private String fState;

    private String fQun;
}

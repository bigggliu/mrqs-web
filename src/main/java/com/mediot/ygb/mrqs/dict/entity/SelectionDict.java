package com.mediot.ygb.mrqs.dict.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;


@Data
public class SelectionDict implements Serializable {

    private static final long serialVersionUID = -5844107391403801724L;

    private String standardCode;

    private String standardName;

    private String fQun;

    private Map<String,Object> tBaseMap;
}

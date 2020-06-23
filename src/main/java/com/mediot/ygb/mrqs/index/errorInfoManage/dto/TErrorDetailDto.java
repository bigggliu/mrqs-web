package com.mediot.ygb.mrqs.index.errorInfoManage.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class TErrorDetailDto implements Serializable {

    private static final long serialVersionUID = -2108726850366205159L;

    private String batchId;

    private int pageSize;

    private int pageNum;

    private Integer analyseType;

}

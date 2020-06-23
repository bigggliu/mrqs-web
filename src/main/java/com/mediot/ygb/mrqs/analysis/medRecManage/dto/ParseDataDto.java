package com.mediot.ygb.mrqs.analysis.medRecManage.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ParseDataDto implements Serializable {

    private static long serialVersionUID= -8672753913656602443L;

    private Object baseData;

    private Boolean isSheet;

    private Long currentRow;


}

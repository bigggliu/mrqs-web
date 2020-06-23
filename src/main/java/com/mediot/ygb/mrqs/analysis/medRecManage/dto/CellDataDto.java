package com.mediot.ygb.mrqs.analysis.medRecManage.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CellDataDto implements Serializable {

    private static long serialVersionUID= -6995255577865077030L;

    private Object baseData;

    private Long currentRow;

    private Long currentColIndex;

}

package com.mediot.ygb.mrqs.index.errorInfoManage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;


@Data
public class TErrorDetailVo implements Serializable {

    private static final long serialVersionUID = 2648908673604791280L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long batchId;

    private String caseId;

    private String colName;

    private String colComments;

    private String errorMessage;

    private Integer score;

    private Integer mustFill;

    private String analyseType;

    private Integer remark;

}

package com.mediot.ygb.mrqs.analysis.monitoringIndexManage.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class TCheckOrgVo implements Serializable {

    private static final long serialVersionUID = -627048014043362488L;

    private String orgId;

    private String colName;

    private String colComments;

    private String fSort;

    private String score;

    private String mustFill;

    private String cmisChoice;

    private String emrChoice;

    private String ruleType;

}

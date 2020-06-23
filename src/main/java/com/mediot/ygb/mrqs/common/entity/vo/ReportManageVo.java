package com.mediot.ygb.mrqs.common.entity.vo;


import lombok.Data;

@Data
public class ReportManageVo {

    private String year;

    private String startTime;

    private String endTime;

    private String totalNumForCurrentYear;

    private String totalErrorNumForCurrentYear;

    private String totalStandards;

    private String errorFileds;

    private String proportionOfError;

}

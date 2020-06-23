package com.mediot.ygb.mrqs.analysis.medRecManage.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class ProgressVo implements Serializable {

    private static long serialVersionUID= -2902138495510850359L;

    private int state;

    private Double progress;

    private Boolean analysisStatus;

    private String errorMsg;

    private int analysedDataNum;

    private String fileName;

    private Boolean isPause;


}

package com.mediot.ygb.mrqs.analysis.medRecManage.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ThreadStateDto implements Serializable {

    private static long serialVersionUID= 970860311757056772L;

    private Thread thread;

    private Boolean isPause;

}

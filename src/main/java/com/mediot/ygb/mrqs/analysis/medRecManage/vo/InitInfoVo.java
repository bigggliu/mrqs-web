package com.mediot.ygb.mrqs.analysis.medRecManage.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class InitInfoVo implements Serializable {

    private static long serialVersionUID= 1265279807774335316L;

    private String startFileIds;

    private String completedFileIds;

    private String inAnalyseFileIds;

    private String errorFileIds;

}

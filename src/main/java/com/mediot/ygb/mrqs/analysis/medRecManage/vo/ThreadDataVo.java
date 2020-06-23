package com.mediot.ygb.mrqs.analysis.medRecManage.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ThreadDataVo implements Serializable {

    private static long serialVersionUID= -5706814487232574676L;

    private String fileId;

    private String fileName;

    private Boolean isPause;

    private List<ProgressVo> progressVoList;

    private Boolean analysisStatus;

    private String errorMsg;

}

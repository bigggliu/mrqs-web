package com.mediot.ygb.mrqs.analysis.medRecManage.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class MedRecDto implements Serializable {

    private static long serialVersionUID= 5746942270779378714L;

    private String fileIds;

    private String fileId;

    private Long batchId;
}

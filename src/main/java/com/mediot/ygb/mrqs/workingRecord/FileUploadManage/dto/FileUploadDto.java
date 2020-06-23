package com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dto;



import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class FileUploadDto implements Serializable {

    private static final long serialVersionUID = 909778113632093890L;

    private String fileId;

    private String fileName;

    private String md5;

    private Integer chunks;

    private Integer chunk;

    private Long size;

    private MultipartFile file;

    private Integer state;

    private Integer pageSize;

    private Integer pageNum;

    private String queryStr;

    private String standardCode;

    private String actualYear;

    private String sourceCode;

    private String batchId;

    private String orgId;

}

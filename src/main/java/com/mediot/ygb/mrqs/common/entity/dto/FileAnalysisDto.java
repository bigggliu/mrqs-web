package com.mediot.ygb.mrqs.common.entity.dto;

import com.mediot.ygb.mrqs.analysis.medRecManage.dao.MyErrorDetiVoMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.dao.TDatacleanStandardMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.entity.TDatacleanStandard;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckColMapper;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckOrgMapper;
import com.mediot.ygb.mrqs.dict.dao.TBaseDictMapper;
import com.mediot.ygb.mrqs.dict.dao.TDiagDictMapper;
import com.mediot.ygb.mrqs.dict.dao.TOperationDictMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.MyErrorDetaMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorDetailMapper;
import com.mediot.ygb.mrqs.index.errorInfoManage.dao.TErrorMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutdiagTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstoutoperTestingMapper;
import com.mediot.ygb.mrqs.index.indexInfoManage.dao.TFirstpageTestingMapper;
import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao.FileUploadMapper;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class FileAnalysisDto implements Serializable {

    private static long serialVersionUID= -7960510133784893669L;

    private File file;

    private String md5;

    private String fileId;

    private String fileName;

    private String sourceCode;

    private String upOrgId;

    private int sheetNum;

    private int rowNum;

    private Long batchId;

    private RedisTemplate redisTemplate;

    private String standardCode;

    private ConcurrentHashMap theadMaps;

    private TDatacleanStandardMapper tDatacleanStandardMapper;

    private MyErrorDetiVoMapper myErrorDetiVoMapper;

    private MyErrorDetaMapper myErrorDetaMapper;

    private TFirstoutdiagTestingMapper tFirstoutdiagTestingMapper;

    private TFirstoutoperTestingMapper tFirstoutoperTestingMapper;

    private TFirstpageTestingMapper tFirstpageTestingMapper;

    private TBaseDictMapper tBaseDictMapper;

    private TDiagDictMapper tDiagDictMapper;

    private FileUploadMapper fileUploadMapper;

    private TOrgsMapper tOrgsMapper;

    private TOperationDictMapper tOperationDictMapper;

    private List<String> cellNameList;

    List<TDatacleanStandard>  tDatacleanStandardList;

    private TCheckColMapper tCheckColMapper;

    private TCheckOrgMapper tCheckOrgMapper;

    private TErrorMapper tErrorMapper;

    private TErrorDetailMapper tErrorDetailMapper;

    private Integer totalNumForCurrentBatchId;

    private Integer analysedNum=0;

    private Integer currentNum=0;

    private Integer indicators;

    private String totalStandards;

    private Integer entryStoreNum=0;

    private Integer errorHeadLine=0;

    private AtomicInteger aiDCH=new AtomicInteger(0);

    private AtomicInteger aiDDH=new AtomicInteger(0);
}

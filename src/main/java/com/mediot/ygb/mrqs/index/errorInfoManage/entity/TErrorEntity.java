package com.mediot.ygb.mrqs.index.errorInfoManage.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_ERROR")
public class TErrorEntity implements Serializable {

    private static final long serialVersionUID = 1794726581792692114L;

    @TableId("BATCH_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long batchId;

//    @TableId("ERROR_ID")
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private Long errorId;

    @TableField("INTERVAL_PARTITION")
    private String intervalPartiton;

    @TableField("START_MONTH")
    private String startMonth;

    @TableField("END_MONTH")
    private String endMonth;

    @TableField("TOTAL")
    private String total;

    @TableField("TOTAL_NUMBER_OF_FIELDS")
    private String totalNumberOfFields;

    @TableField("NUMBER_OF_REQUIRED_FIELDS")
    private String numberOfRequiredFields;

    @TableField("HIT_DATA_VOLUME")
    private String hitDataVolume;

    @TableField("NUMBER_OF_TEST_INDICATORS")
    private String numberOfTestIndicators;

    @TableField("ERROR_FILEDS")
    private String errorFields;

    @TableField("PROPORTIONOFERROR")
    private String proportionOfError;

}

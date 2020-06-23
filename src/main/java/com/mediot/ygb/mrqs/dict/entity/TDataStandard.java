package com.mediot.ygb.mrqs.dict.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_DATA_STANDARD")
public class TDataStandard {

    private static final long serialVersionUID = 4781909350005949692L;

    @TableId("STANDARD_CODE")
    private String standardCode;

    @TableField("STANDARD_NAME")
    private String standardName;

    @TableField("FQUN")
    private String fQun;

    @TableField("REMARK")
    private String remark;

    @TableField("FSTATE")
    private String fState;

    @TableField("TOTAL_NUMBER")
    private String totalNumber;

}

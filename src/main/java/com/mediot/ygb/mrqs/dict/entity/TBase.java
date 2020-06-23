package com.mediot.ygb.mrqs.dict.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_BASE")
public class TBase implements Serializable {

    private static final long serialVersionUID = 228509220940438948L;

    @TableId("BASE_CODE")
    private String baseCode;

    @TableField("BASE_NAME")
    private String baseName;

    @TableField("FQUN")
    private String fQun;

    @TableField("PARENT_CODE")
    private String parentCode;

    @TableField("REMARK")
    private String remark;

    @TableField("FSTATE")
    private String fState;

    @TableField("VERSION")
    private String version;

    @TableField("DCODE")
    private String dCode;

}

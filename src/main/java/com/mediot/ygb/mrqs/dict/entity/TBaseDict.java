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
@TableName("T_BASE_DICT")
public class TBaseDict implements Serializable {

    private static final long serialVersionUID = 7725014437893020643L;

    @TableId("BASE_CODE")
    private String baseCode;

    @TableField("DICT_CODE")
    private String dictCode;

    @TableField("FQUN")
    private String fQun;

    @TableField("DICT_NAME")
    private String dictName;

    @TableField("REMARK")
    private String remark;

    @TableField("FSTATE")
    private String fState;


}

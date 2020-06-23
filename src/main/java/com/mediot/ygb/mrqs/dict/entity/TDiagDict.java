package com.mediot.ygb.mrqs.dict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * T_DIAG_DICT
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_DIAG_DICT")
public class TDiagDict implements Serializable {

    private static final long serialVersionUID = 2387955739924788457L;

    private Long tDiagDictId;

    private String baseCode;

    private String dictCode;

    private String dictName;

    private String fqun;

    private String remark;

    private String bodyPart;

    private String nonReHop;

    private String codingCategory;

    private String checkCode;

}
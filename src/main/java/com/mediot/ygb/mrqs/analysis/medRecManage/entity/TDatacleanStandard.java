package com.mediot.ygb.mrqs.analysis.medRecManage.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * T_DATACLEAN_STAMDRAD
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_DATACLEAN_STANDRAD")
public class TDatacleanStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "DATACLEAN_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long datacleanId;

    @ExcelProperty("数据采集项")
    private String dataCol;

    @ExcelProperty("字段名称")
    private String dataColName;

    @ExcelProperty("数据类型")
    private String dataType;

    @ExcelProperty("长度")
    private String dataLength;

    @ExcelProperty("上传时不能为空")
    private String mustfill;

    private String dataStandard;

    private String standardType="WT";

    private String remark;

    @TableField(exist = false)
    private Long currentRow;

    @TableField(exist = false)
    private Long currentColIndex;

    @TableField(exist = false)
    private String tempCellVal;

    @TableField(exist = false)
    private String errorMsg;

    @TableField(exist = false)
    private String currentFileName;

}
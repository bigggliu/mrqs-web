package com.mediot.ygb.mrqs.common.entity.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data

public class ErrorInfo extends BaseRowModel {

    @ExcelProperty(value = "指标", index = 0)
    private String colName;

    @ExcelProperty(value = "指标名称",index = 1)
    private String col;

    @ExcelProperty(value = "指标值",index = 2)
    private String val;

    @ExcelProperty(value = "错误信息",index = 3)
    private String errorMsg;

    @ExcelProperty(value = "所属文件",index =4)
    private String fileName;


}

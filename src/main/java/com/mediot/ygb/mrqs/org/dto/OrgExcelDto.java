package com.mediot.ygb.mrqs.org.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class OrgExcelDto implements Serializable {

    @ExcelProperty(value = "机构名称", index = 0)
    private String orgName;

    @ExcelProperty(value = "组织机构代码", index = 1)
    private String orgCode;

    @ExcelProperty(value = "所属行政区域", index = 2)
    private String area;

    @ExcelProperty(value = "地址", index = 3)
    private String addr;

    @ExcelProperty(value = "医院等级", index = 4)
    private String orgGrade;

    @ExcelProperty(value = "医院类型", index = 5)
    private String orgType;

    @ExcelProperty(value = "可选对接方案", index = 6)
    private String dockingScheme;

    @ExcelProperty(value = "省份", index = 7)
    private String province;

    @ExcelProperty(value = "默认对接方案", index = 8)
    private String defaultDockingScheme;

    @ExcelProperty(value = "邮箱地址", index = 9)
    private String email;

    @ExcelProperty(value = "联系方式", index = 10)
    private String phone;

    @ExcelProperty(value = "查询码", index = 11)
    private String queryCode;

    @ExcelProperty(value = "类别代码", index = 12)
    private String categoryCode;

    @ExcelProperty(value = "数据标准", index = 13)
    private String fqun;

}

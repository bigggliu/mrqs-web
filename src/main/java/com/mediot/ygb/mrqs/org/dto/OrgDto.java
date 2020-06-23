package com.mediot.ygb.mrqs.org.dto;



import com.mediot.ygb.mrqs.common.excel.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;


@Data
public class OrgDto implements Serializable {

    private static long serialVersionUID= 9052619805234351130L;

    @ExcelField(title="机构Id", align=2, sort=1)
    private Long orgId;

    //@NotBlank(message="机构代码不能为空")
    @ExcelField(title="机构代码", align=2, sort=2)
    private String orgCode;

    //@NotBlank(message="机构名称不能为空")
    @ExcelField(title="机构名称", align=2, sort=3)
    private String orgName;

    private String fqun;

    private String orgAlias;

    //@NotBlank(message="所属行政区域不能为空")
    @ExcelField(title="所属行政区域", align=2, sort=4)
    private String area;

    //@NotBlank(message="地址不能为空")
    @ExcelField(title="地址", align=2, sort=5)
    private String addr;

    private String parentId;

    //@NotBlank(message="医院等级不能为空")
    @ExcelField(title="医院等级", align=2, sort=6)
    private String orgGrade;

    //@NotBlank(message="医院类型不能为空")
    @ExcelField(title="医院类型", align=2, sort=7)
    private String orgType;

    private String diseaseCoding;

    private String operativeCode;

    private String dataFormat;

    //@NotBlank(message="可选对接方案不能为空")
    @ExcelField(title="可选对接方案", align=2, sort=8)
    private String dockingScheme;

    private String ststMode;

    private String logo;

    private String fState;

    //@NotBlank(message="省份不能为空")
    @ExcelField(title="省份", align=2, sort=9)
    private String province;

    private String city;

    private String country;

    private String evaluationCriteria;

    //@NotBlank(message="默认对接方案不能为空")
    @ExcelField(title="默认对接方案", align=2, sort=10)
    private String defaultDockingScheme;

    //@NotBlank(message="邮箱地址不能为空")
    @ExcelField(title="邮箱地址", align=2, sort=11)
    private String email;

    //@NotBlank(message="联系方式不能为空")
    @ExcelField(title="联系方式", align=2, sort=12)
    private String phone;

    //@NotBlank(message="查询码不能为空")
    @ExcelField(title="查询码", align=2, sort=13)
    private String queryCode;

    //@NotBlank(message="类别代码不能为空")
    @ExcelField(title="类别代码", align=2, sort=14)
    private String categoryCode;

    //@NotBlank(message="应用编码不能为空")
    @ExcelField(title="应用编码", align=2, sort=15)
    private String appCode;

    private Integer pageNum;

    private Integer pageSize;

    private String queryStr;

}

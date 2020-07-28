package com.mediot.ygb.mrqs.system.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("SYS_ORG")
public class Org implements Serializable {

    private static final long serialVersionUID = 9168856698053321518L;
    @TableId("ORG_ID")
    private Long orgId;
    private String orgName;
    private Long parentId;
    private String orgCode;
    private String orgProvince;
    private String standardCode;
    private String sourceCode;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private Integer state;
}

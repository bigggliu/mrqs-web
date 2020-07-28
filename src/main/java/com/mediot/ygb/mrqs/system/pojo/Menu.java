package com.mediot.ygb.mrqs.system.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("SYS_MENU")
public class Menu implements Serializable {

    private static final long serialVersionUID = -6160608856541879274L;
    @TableId("MENU_ID")
    private Long menuId;
    private String menuName;
    private String menuUrl;
    private Long parentId;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private String remark;
    private Integer state;
}

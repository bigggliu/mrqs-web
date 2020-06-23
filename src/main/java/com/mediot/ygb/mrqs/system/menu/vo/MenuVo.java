package com.mediot.ygb.mrqs.system.menu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuVo implements Serializable {

    private static final long serialVersionUID = -5280959594036762175L;

    /**
     * 上级菜单id
     */
    private String parentCode;

    /**
     * 菜单层级
     */
    private Integer nodeLevel;

    /**
     * 菜单编码（唯一）
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 路径
     */
    private String url;

    /**
     * 排序
     */
    private Integer fsort;

    /**
     * 状态
     */
    private Integer fstate;

    /**
     * 说明
     */
    private String remark;

    private List<MenuVo> children;


    private Integer isSelected;


}

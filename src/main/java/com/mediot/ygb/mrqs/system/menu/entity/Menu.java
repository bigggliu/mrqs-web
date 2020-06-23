package com.mediot.ygb.mrqs.system.menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("ts_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1707602000214412169L;

    /**
     * 上级菜单id
     */
    @TableField(value = "parent_code")
    private String parentCode;

    /**
     * 菜单层级
     */
    @TableField(value = "node_level")
    private Integer nodeLevel;

    /**
     * 菜单编码（唯一）
     */
    @TableId(value = "menu_code" , type = IdType.UUID)
    private String menuCode;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * 排序
     */
    @TableField(value = "fsort")
    private Integer fsort;

    /**
     * 状态
     */
    @TableField(value = "fstate")
        private Integer fstate;

    /**
     * 说明
     */
    @TableField(value = "remark")
    private String remark;


}

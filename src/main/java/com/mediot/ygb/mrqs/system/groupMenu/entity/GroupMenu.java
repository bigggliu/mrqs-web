package com.mediot.ygb.mrqs.system.groupMenu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ts_group_menus")
public class GroupMenu implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField(value = "group_id")
    private String groupId;

    @TableField(value = "menu_code")
    private String menuCode;

    /**
     * 关系id
     */
    @TableId(value = "group_menu_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long groupMenuId;


}

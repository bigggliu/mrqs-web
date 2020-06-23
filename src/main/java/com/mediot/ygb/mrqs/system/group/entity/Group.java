package com.mediot.ygb.mrqs.system.group.entity;


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
 * 角色表
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ts_groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组id
     */
    @TableId(value = "group_id" )
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long groupId;

    /**
     * 平台系统id
     */
    @TableField(value = "platform_system_id")
    private String platformSystemId;

    /**
     * 组名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;


}

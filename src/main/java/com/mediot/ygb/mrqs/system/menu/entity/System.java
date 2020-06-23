package com.mediot.ygb.mrqs.system.menu.entity;

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
 * 系统表
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ts_system")
public class System implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统编码
     */
    @TableId(value = "system_code")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long systemCode;


    /**
     * 系统名称
     */
    @TableField(value = "system_name")
    private String systemName;

    /**
     * 排序
     */
    @TableField(value = "fsort")
    private Integer fsort;

    /**
     * 说明
     */
    @TableField(value = "remark")
    private String remark;


    /**
     * 系统类别
     * 10:机构管理员系统 20:其他
     */
    @TableField(value = "system_type")
    private String systemType;

    /**
     * 系统类别
     * 10:机构管理员系统 20:其他
     */
    @TableField(value = "parent_system_code")
    private String parentSystemcode;
}

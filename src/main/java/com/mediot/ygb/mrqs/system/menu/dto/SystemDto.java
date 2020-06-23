package com.mediot.ygb.mrqs.system.menu.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class SystemDto{

    private String type;

    /**
     * 系统编码
     */
    private String systemCode;


    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 排序
     */
    private Integer fsort;

    /**
     * 说明
     */
    private String remark;


    /**
     * 系统类别
     * 10:机构管理员系统 20:其他
     */
    private String systemType;

    /**
     * 系统类别
     * 10:机构管理员系统 20:其他
     */
    private String parentSystemcode;
}

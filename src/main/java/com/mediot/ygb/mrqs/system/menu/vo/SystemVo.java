package com.mediot.ygb.mrqs.system.menu.vo;

import lombok.Data;

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
public class SystemVo implements Serializable {

    private static final long serialVersionUID = 3066623004099344353L;

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

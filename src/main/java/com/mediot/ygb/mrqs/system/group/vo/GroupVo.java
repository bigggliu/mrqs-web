package com.mediot.ygb.mrqs.system.group.vo;


import lombok.Data;

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

public class GroupVo implements Serializable {

    private static final long serialVersionUID = 3594827833003680563L;

    /**
     * 组id
     */
    private String groupId;

    /**
     * 平台系统id
     */
    private String platformSystemId;

    /**
     * 组名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


}

package com.mediot.ygb.mrqs.system.orgManage.entity;

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
@TableName("T_ORG_REF_SYSTEM")
public class OrgRefSystem implements Serializable {

    private static final long serialVersionUID = -3467103053361603432L;

    /**
     * 主键
     */
    @TableId(value = "REF_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refId;

    /**
     * 系统编码
     */
    @TableField(value = "SYSTEM_CODE")
    private String systemCode;

    /**
     * 机构名称
     */
    @TableField(value = "ORG_ID")
    private String orgId;

}

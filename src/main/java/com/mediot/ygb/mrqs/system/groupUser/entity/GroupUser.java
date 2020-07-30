package com.mediot.ygb.mrqs.system.groupUser.entity;


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
 * 角色用户关系表
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("TS_GROUP_USERS")
public class GroupUser implements Serializable {

    private static final long serialVersionUID = -286441750470552964L;

    /**
     * 关系id
     */
    @TableId(value = "group_user_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long groupUserId;

    @TableField(value = "group_id")
    private Long groupId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String userNo;

}

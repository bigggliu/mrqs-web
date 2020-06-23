package com.mediot.ygb.mrqs.system.menu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统菜单关系表
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ts_system_menu")
public class SystemMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "system_code")
    private Long systemCode;

    @TableId(value = "menu_code")
    private Long menuCode;


}

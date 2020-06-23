package com.mediot.ygb.mrqs.system.menu.dto;

import com.mediot.ygb.mrqs.system.menu.entity.Menu;

/**
 * @author
 * @since
 */
public class SystemMenuDto extends Menu {

    private String systemCode;

    private String type;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

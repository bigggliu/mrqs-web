package com.mediot.ygb.mrqs.system.group.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.system.groupMenu.entity.GroupMenu;


import java.util.Set;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author
 * @since
 */
public interface GroupMenuService extends BaseService<GroupMenu> {

    /**
     * 根据用户获取平台系统列表
     * @param userId
     * @return
     */
    //List<Map<String,Object>> getPlatformSystemByUserId(String userId);
    
    /**
     * 精细化平台系统列表查询
     * @param userId
     * @return
     */
    //List<Map<String,Object>> getDepartPlatformSystemByUserId(String userId);

    /**
     * 查询某个“系统操作员”的菜单权限集合
     * @param userId
     * @param platformSystemId
     * @return Set<String>
     * @author
     *      */
    public Set<String> findMenuCodesByOperator(String userId, String platformSystemId);

}

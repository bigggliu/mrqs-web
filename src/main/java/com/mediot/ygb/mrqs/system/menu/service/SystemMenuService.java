package com.mediot.ygb.mrqs.system.menu.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.system.menu.dto.SystemMenuDto;
import com.mediot.ygb.mrqs.system.menu.entity.SystemMenu;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 系统菜单关系表 服务类
 * </p>
 *
 * @author
 * @since
 */
public interface SystemMenuService extends BaseService<SystemMenu> {

    /**
     * 查找一级菜单(下拉框)
     * @param pager
     * @return
     */
    Map<String,Object> searchTopMenuBySystemId(Map<String,Object> queryMap);

    /**
     * 验证“菜单名称”是否重复
     * @param pager
     * @return
     */
    int selectCountMenuName(Map<String, Object> queryMap);

    /**
     * 新增或编辑菜单
     * @param menu
     */
    Long addOrUpdateSystemMenu(SystemMenuDto menu);

    /**
     * 查询某个“系统管理员”的菜单权限集合
     * @param userId
     * @param platformSystemId
     * @return Set
     * @author
     *      */
    Set<String> findMeueCodesBygbsAdmin(String userId, String platformSystemId);

    List<MenuVo> selectMenuVoList(Map<String, Object> queryMap);

    List<MenuVo> selectListByArray(String[] systemCodeArr);
}

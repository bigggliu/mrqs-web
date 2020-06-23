package com.mediot.ygb.mrqs.system.group.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.groupMenu.entity.GroupMenu;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author
 * @since
 */
public interface GroupMenuMapper extends BaseMapper<GroupMenu> {

    /**
     * 查询组菜单权限
     * @param groupId
     * @param platformSystemId
     * @return
     */
    List<MenuVo> getGroupMenuList(@Param("groupId") String groupId);

    /*List<Map<String, Object>> selectSubMenusByParentId(Pager pager);*/

    /**
     * 根据用户获取平台系统列表
     * @param userId
     * @return List
     */
    List<Map<String,Object>> getPlatformSystemByUserId(@Param("userId") String userId, @Param("platformType") Integer platformType);

    /**
     * 获取当前用户在某系统下的菜单列表
     * @param userId
     * @param platformSystemId
     * @return
     */
    List<Map<String, Object>> selectSysMenus(@Param("userId") String userId, @Param("platformSystemId") String platformSystemId);

    /**
     * 获取当前用户在某系统下的所有菜单列表
     * @param platformSystemId
     * @param systemCode
     * @return
     */
    List<Map<String, Object>> selectAllSysMenus(@Param("platformSystemId") String platformSystemId, @Param("systemCode") String systemCode);


    List<Map<String, Object>> selectDepartAllSysMenus(@Param("platformSystemId") String platformSystemId, @Param("systemCode") String systemCode);

    /**
     * 查询某个“系统操作员”的菜单权限集合
     * @param userId
     * @param platformSystemId
     * @return Set<String>
     * @author
     *      */
    Set<String> findMenuCodesByOperator(@Param("userId") String userId, @Param("platformSystemId") String platformSystemId);

}

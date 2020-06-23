package com.mediot.ygb.mrqs.system.group.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.system.group.entity.Group;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author
 * @since
 */
public interface GroupService extends BaseService<Group> {

    /**
     * 添加或编辑用户组
     * @param group
     */
    void addOrUpdatePlatformSystemGroup(Group group);

    /**
     * 删除用户组
     * @param groupId
     */
    void deletePlatformSystemGroup(String groupId);

    /**
     * 查询组成员列表
     * @param pager
     * @return
     */
    Map<String,Object> getGroupUserList(String groupId,
                                               String searchName,
                                               Integer pageNum,
                                               Integer pageSize);

    /**
     * 查询待添加进用户组的用户列表
     * @param pager
     * @return
     */
    Map<String, Object> getUnGroupUserList(Map<String, Object> queryMap,Integer pageSize);

    /**
     * 添加组成员
     * @param groupId
     * @param userIds
     */
    void addGroupUsers(String groupId, String[] userIds);

    /**
     * 移除组成员
     * @param groupUserId
     */
    void deleteGroupUser(String groupUserId);

    /**
     * 建立副本
     * @param group
     */
    void addGroupMenusClone(Group group);

    /**
     * 查询组菜单
     * @param groupId
     * @param platformSystemId
     * @return
     */
    List<MenuVo> getGroupMenuList(String groupId);

    /**
     * 保存组菜单权限配置
     * @param groupId
     * @param menuCodeList
     */
    void insertGroupMenus(String groupId, Set<String> menuCodeList);

    Map<String, Object> findPageListByParam(String name, Integer pageNum, Integer pageSize);
}

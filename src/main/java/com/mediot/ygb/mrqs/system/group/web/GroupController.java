package com.mediot.ygb.mrqs.system.group.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;

import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.system.group.entity.Group;
import com.mediot.ygb.mrqs.system.group.service.GroupMenuService;
import com.mediot.ygb.mrqs.system.group.service.GroupService;
import com.mediot.ygb.mrqs.system.group.service.GroupUserService;
import com.mediot.ygb.mrqs.system.menu.service.MenuService;

import com.mediot.ygb.mrqs.system.groupUser.entity.GroupUser;
import com.mediot.ygb.mrqs.system.menu.entity.Menu;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;

import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 系统表 前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/groupMgr")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private GroupMenuService groupMenuService;

    /**
     * 获取平台系统用户组列表
     * @param platformSystemId
     * @param name
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/getPlatformSystemGroupList")
    public Map<String, Object> getPlatformSystemGroupList(
            String name ,Integer pageNum ,Integer pageSize){
        return groupService.findPageListByParam(name ,pageNum ,pageSize);
    }

    /**
     * 添加或编辑组
     * @param group
     * @param request
     */
    @RequestMapping("/addOrUpdatePlatformSystemGroup")
    public void addOrUpdatePlatformSystemGroup(
            Group group , HttpServletRequest request){
        LocalAssert.notNull(group,"用户组信息为空，请填写用户组信息！");
        LocalAssert.notBlank(group.getName(), "组名，不能为空！");
        LocalAssert.intLessEqual(group.getName().length(), 25, "组名不能超过25个字符！");
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",group.getName());
        if(group.getGroupId()!=null){ //编辑
            queryWrapper.ne("group_id",group.getGroupId());
        }
        int count = groupService.selectCount(queryWrapper);//验证“用户组”是否重复
        LocalAssert.intLessEqual(count, 0, "组名不能重复！");
        if(StringUtils.isNotBlank(group.getRemark())){
            LocalAssert.intLessEqual(group.getRemark().length(), 127, "备注不能超过127个字符！");
        }
        groupService.addOrUpdatePlatformSystemGroup(group);
    }

    /**
     * 删除用户组信息
     * @param groupId
     * @param request
     */
    @RequestMapping("/deletePlatformSystemGroup")
    public void deletePlatformSystemGroup(
            String groupId , HttpServletRequest request){
        LocalAssert.notBlank(groupId, "用户组主键，不能为空！");
        //查询用户组内是否有用户
        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        int count = groupUserService.selectCount(queryWrapper);
        LocalAssert.intLessEqual(count, 0, "用户组内有用户不能删除组！");
        groupService.deletePlatformSystemGroup(groupId);
    }

    /**
     * 查询组成员列表
     * @param groupId
     * @param searchName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/getGroupUserList")
    public Map<String,Object> getGroupUserList(String groupId,
                                                     String searchName,
                                                     Integer pageNum,
                                                     Integer pageSize) {
        LocalAssert.notBlank(groupId, "用户组主键，不能为空！");
        return groupService.getGroupUserList(groupId,searchName,pageNum,pageSize);
    }

    /**
     * 查询待添加进用户组的用户列表
     * @param groupId
     * @param searchName
     * @param pageSize
     * @return
     */
    @RequestMapping("/getUnGroupUserList")
    public Map<String, Object> getUnGroupUserList(String groupId,
                                                      String searchName,
                                                      Integer pageSize) {
        LocalAssert.notBlank(groupId, "用户组主键，不能为空！");
        pageSize = (pageSize==null||pageSize>50)?50:pageSize;
        Map<String,Object> queryMap= Maps.newHashMap();
        //查询条件
        queryMap.put("groupId", groupId);
        queryMap.put("searchName", searchName);
        return groupService.getUnGroupUserList(queryMap,pageSize);
    }

    /**
     * 添加组成员
     * @param groupId
     * @param userIds
     * @param request
     */
    @RequestMapping("/addGroupUsers")
    public void addGroupUsers(
            String groupId , String[] userIds,HttpServletRequest request){
        LocalAssert.notBlank(groupId, "用户组主键，不能为空！");
        if(userIds==null || userIds.length==0 || StringUtils.isBlank(userIds[0])){
            throw new ValidationException("请勾选要选入的用户！");
        }
        groupService.addGroupUsers(groupId,userIds);
    }

    /**
     * 移除组成员
     * @param groupUserId
     * @param request
     */
    @RequestMapping("/deleteGroupUser")
    public void deleteGroupUser(
            String groupUserId , HttpServletRequest request){
        LocalAssert.notBlank(groupUserId, "用户组主键，不能为空！");
        groupService.deleteGroupUser(groupUserId);
    }

    /**
     * 建立副本
     * @param group
     * @param request
     */
    @RequestMapping("/addGroupMenusClone")
    public void addGroupMenusClone(
            Group group , HttpServletRequest request){
        LocalAssert.notNull(group,"用户组信息为空，请填写用户组信息！");
        LocalAssert.notBlank(String.valueOf(group.getGroupId()), "请选择要克隆的用户组信息！");
        LocalAssert.notBlank(group.getName(), "组名，不能为空！");
        LocalAssert.intLessEqual(group.getName().length(), 25, "组名不能超过25个字符！");
        Group entity = groupService.selectById(group.getGroupId());
        LocalAssert.notNull(entity,"选择的用户组信息不存在，请重新选择！");
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",group.getName());
        int count = groupService.selectCount(queryWrapper);//验证“用户组”是否重复
        LocalAssert.intLessEqual(count, 0, "组名不能重复！");
        if(StringUtils.isNotBlank(group.getRemark())){
            LocalAssert.intLessEqual(group.getRemark().length(), 127, "备注不能超过127个字符！");
        }
        groupService.addGroupMenusClone(group);
    }

    /**
     * 查询组菜单
     * @param groupId
     * @param platformSystemId
     * @param request
     * @return
     */
    @RequestMapping("/getGroupMenuList")
    public List<MenuVo> getGroupMenuList(
            String groupId , HttpServletRequest request){
        LocalAssert.notBlank(groupId, "请选择用户组！");
        List<MenuVo> list = groupService.getGroupMenuList(groupId);
        return list;
    }

    /**
     * 保存组菜单权限配置
     * @param groupId
     * @param menuCodes
     * @param request
     */
    @RequestMapping("/addGroupMenus")
    public void addGroupMenus(
            String groupId , String[] menuCodes,HttpServletRequest request){
        LocalAssert.notBlank(groupId, "用户组主键，不能为空！");
        Set<String> menuCodeList = new HashSet<String>();
        if(menuCodes!=null && menuCodes.length>0 && StringUtils.isNotBlank(menuCodes[0])){
            menuCodeList = new HashSet<>(Arrays.asList(menuCodes));
        }
        if(CollectionUtils.isNotEmpty(menuCodeList)){
            //循环找出上级菜单ID
            for(String menuCode:menuCodes){
                LocalAssert.notBlank(menuCode, "菜单为空，请重新勾选！");
                Menu menu = menuService.selectById(menuCode);
                LocalAssert.notNull(menu,"勾选的菜单不存在，请重新勾选！");
                if(StringUtils.isNotBlank(menu.getParentCode()) && !"0".equals(menu.getParentCode())){
                    menuCodeList.add(menu.getParentCode());
                }
            }
        }
        groupService.insertGroupMenus(groupId,menuCodeList);
    }

}

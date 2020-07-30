package com.mediot.ygb.mrqs.system.group.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;

import com.mediot.ygb.mrqs.system.group.entity.Group;
import com.mediot.ygb.mrqs.system.group.dao.GroupMapper;
import com.mediot.ygb.mrqs.system.group.dao.GroupMenuMapper;
import com.mediot.ygb.mrqs.system.group.dao.GroupUserMapper;
import com.mediot.ygb.mrqs.system.group.service.GroupService;
import com.mediot.ygb.mrqs.system.group.vo.GroupVo;
import com.mediot.ygb.mrqs.system.groupUser.vo.GroupUserVo;
import com.mediot.ygb.mrqs.system.menu.service.SystemService;

import com.mediot.ygb.mrqs.system.groupMenu.entity.GroupMenu;
import com.mediot.ygb.mrqs.system.groupUser.entity.GroupUser;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;

import com.mediot.ygb.mrqs.system.user.entity.UserInfo;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupMapper, Group> implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupMenuMapper groupMenuMapper;
    @Autowired
    private GroupUserMapper groupUserMapper;
    @Autowired
    private SystemService systemService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdatePlatformSystemGroup(Group group) {
        LocalAssert.notNull(group,"用户组信息为空，请填写用户组信息！");
        LocalAssert.notBlank(group.getName(), "组名，不能为空！");
        if(group.getGroupId()==null){//新增
            groupMapper.insert(group);
        }else{//编辑
            Group entity = new Group();
            entity.setGroupId(group.getGroupId());
            entity.setName(group.getName());
            entity.setRemark(group.getRemark());
            groupMapper.updateById(entity);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePlatformSystemGroup(String groupId) {
        LocalAssert.notBlank(groupId, "用户组主键，不能为空！");
        //删除用户权限
        QueryWrapper<GroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        groupMenuMapper.delete(queryWrapper);
        //删除用户组
        groupMapper.deleteById(groupId);
    }

    @Override
    public Map<String,Object> getGroupUserList(String groupId, String searchName, Integer pageNum, Integer pageSize) {
        LocalAssert.notNull(pageNum,"pageNum不能为空");
        LocalAssert.notNull(pageSize,"pageSize不能为空");
        Page page=PageHelper.startPage(pageNum,pageSize);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String,Object> paramMap= Maps.newHashMap();
        paramMap.put("searchName",searchName);
        paramMap.put("groupId",groupId);
        List<GroupUser> groupUserList=groupUserMapper.getGroupUserList(paramMap);
        if(groupUserList.size()==0){
            jsonMap.put("data",null);//数据结果
            jsonMap.put("total", 0);//获取数据总数
            jsonMap.put("pageSize",pageSize);//获取长度
            jsonMap.put("pageNum", pageNum);//获取当前页数
            return jsonMap;
        }
        List<GroupUserVo> groupUserVoList= Lists.newArrayList();
        if(groupUserList.size()>0){
            groupUserVoList = groupUserList.stream().map(e -> JsonUtil.
            getJsonToBean(JsonUtil.getBeanToJson(e), GroupUserVo.class)).
            collect(Collectors.toList());
        }
        PageInfo<GroupUserVo> pageInfo = new PageInfo<>(groupUserVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",groupUserVoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

    @Override
    public Map<String, Object> getUnGroupUserList(Map<String, Object> queryMap,Integer pageSize,Integer pageNum) {
        Page page=PageHelper.startPage(pageNum,pageSize);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<UserInfo> userInfoList= groupUserMapper.getUnGroupUserList(queryMap);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
        //pageInfo.setPages(page.getPages());//总页数
        //pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",userInfoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addGroupUsers(String groupId, String[] userIds) {
        for (String userId:userIds){
            LocalAssert.notBlank(userId, "用户，不能为空！");
            //检查该用户是否已存在用户组内
            QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("group_id",groupId);
            queryWrapper.eq("user_id",userId);
            int count = groupUserMapper.selectCount(queryWrapper);
            if(count ==0){
                GroupUser groupUser = new GroupUser();
                groupUser.setGroupId(Long.valueOf(groupId));
                groupUser.setUserId(Long.valueOf(userId));
                groupUserMapper.insert(groupUser);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGroupUser(String groupUserId) {
        groupUserMapper.deleteById(groupUserId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addGroupMenusClone(Group group) {
        LocalAssert.notNull(group,"用户组信息为空，请填写用户组信息！");
        LocalAssert.notBlank(String.valueOf(group.getGroupId()), "请选择要克隆的用户组信息！");
        //新增用户组信息
        Group entity = new Group();
        entity.setRemark(group.getRemark());
        entity.setName(group.getName());
        groupMapper.insert(entity);
        //开始克隆权限
        QueryWrapper<GroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",group.getGroupId());
        List<GroupMenu> list = groupMenuMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            for(GroupMenu gm:list){
                //克隆权限
                GroupMenu groupMenu = new GroupMenu();
                groupMenu.setGroupId(String.valueOf(group.getGroupId()));
                groupMenu.setMenuCode(gm.getMenuCode());
                groupMenuMapper.insert(groupMenu);
            }
        }
    }

    @Override
    public List<MenuVo> getGroupMenuList(String userId,String platformSystemId) {
        List<MenuVo> list=groupMenuMapper.getGroupMenuList(userId,platformSystemId);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertGroupMenus(String groupId, Set<String> menuCodeList) {
        LocalAssert.notBlank(groupId, "请选择用户组！");
     //   LocalAssert.notEmpty(menuCodeList,"请至少勾选一个菜单！");
        //先删除用户组权限
        QueryWrapper<GroupMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        groupMenuMapper.delete(queryWrapper);
        //再重新添加用户组权限
        if(CollectionUtils.isNotEmpty(menuCodeList)){
            for(String menuCode:menuCodeList){
                LocalAssert.notBlank(menuCode, "菜单为空，请重新勾选！");
                GroupMenu groupMenu = new GroupMenu();
                groupMenu.setGroupId(groupId);
                groupMenu.setMenuCode(menuCode);
                groupMenuMapper.insert(groupMenu);
            }
        }
    }

    @Override
    public Map<String, Object> findPageListByParam(String name, Integer pageNum, Integer pageSize) {
        LocalAssert.notNull(pageNum,"pageNum不能为空");
        LocalAssert.notNull(pageSize,"pageSize不能为空");
        Page page=PageHelper.startPage(pageNum,pageSize);
        List<GroupVo> groupVoList= Lists.newArrayList();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like("NAME",name);
        }
        queryWrapper.orderByAsc("NAME");
        List<Group> groupList= groupMapper.selectList(queryWrapper);
        groupVoList=groupList.stream().map(
                e-> JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), GroupVo.class)
        ).collect(Collectors.toList());
        PageInfo<GroupVo> pageInfo = new PageInfo<>(groupVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("rows", groupVoList);//数据结果
        jsonMap.put("count", pageInfo.getTotal());//获取数据总数
        jsonMap.put("size", pageInfo.getPageSize());//获取长度
        jsonMap.put("pages",pageInfo.getPages());//获取当前页数
        return jsonMap;
    }

    @Override
    public List<MenuVo> getGroupMenuListIsSelect(GroupVo groupVo) {
        List<MenuVo> list=groupMenuMapper.getGroupMenuListIsSelect(groupVo.getGroupId(),groupVo.getPlatformSystemId());
        return systemService.treeList(list, "0",groupVo.getGroupId());
    }
}

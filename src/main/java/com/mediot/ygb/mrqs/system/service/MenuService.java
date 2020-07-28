package com.mediot.ygb.mrqs.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.system.vo.MenuTree;
import com.mediot.ygb.mrqs.system.dao.MenuDao;
import com.mediot.ygb.mrqs.system.dao.RoleMenuDao;
import com.mediot.ygb.mrqs.system.pojo.Menu;
import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    public int insert(Menu menu) throws Exception {
        validationMenu(menu);
        checkMenuExist(menu);
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        return menuDao.insert(menu);
    }

    public int update(Menu menu) throws Exception {
        validationMenu(menu);
        Menu tempMenu = menuDao.selectById(menu.getMenuId());
        if(!tempMenu.getMenuName().equals(menu.getMenuName())){
            checkMenuExist(menu);
        }
        menu.setUpdateTime(new Date());
        return menuDao.updateById(menu);
    }

    public int delete(Menu menu) throws Exception {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("PARENT_ID",menu.getMenuId());
        if(menuDao.selectByMap(queryMap).size() > 0){
          throw new Exception("无法删除，该菜单下还有子菜单");
        }
        //删除角色菜单关系
        Map<String,Object> queryMap1 = new HashMap<>();
        queryMap1.put("MENU_ID",menu.getMenuId());
        roleMenuDao.deleteByMap(queryMap1);
        return menuDao.deleteById(menu);
    }

    public List<Menu> menuListFuzzyQuery(String queryStr){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("UPDATE_TIME");
        if(StringUtils.isNotBlank(queryStr)) {
            queryWrapper.like("MENU_NAME", queryStr).or().like("MENU_URL",queryStr);
        }
        return menuDao.selectList(queryWrapper);
    }

    public Menu selectById(Menu menu){
        LocalAssert.notNull(menu.getMenuId(),"菜单ID不能为空");
        return menuDao.selectById(menu);
    }

    public List<Menu> selectMenuListByRoleId(Role role){
        return menuDao.selectMenuListByRoleId(role.getRoleId());
    }

    /**
     * 查询菜单树
     */
    public List<MenuTree> getMenuTree(){
        //获取顶级列表
        QueryWrapper<Menu> queryWrapper = new QueryWrapper();
        queryWrapper.eq("PARENT_ID",0).orderByAsc("SORT");
        List<Menu> topList = menuDao.selectList(queryWrapper);
        List<MenuTree> menuTrees = new ArrayList<>();
        menuTrees = getTree(topList,menuTrees,null);
        return menuTrees;
    }

    /**
     * 迭代获得菜单树
     */
    public List<MenuTree> getTree(List<Menu> topList,List<MenuTree> menuTrees,User user){
        for(Menu menu : topList){
            MenuTree menuTree = new MenuTree();
            menuTree.setMenuId(menu.getMenuId());
            menuTree.setMenuName(menu.getMenuName());
            menuTree.setMenuUrl(menu.getMenuUrl());
            menuTree.setSort(menu.getSort());
            menuTree.setRemark(menu.getRemark());
            menuTree.setState(menu.getState());
            List<MenuTree> tempMenuTrees = new ArrayList<>();
            List<Menu> tempTopList = new ArrayList<>();
            if(user != null){
                Map<String,Object> queryMap = new HashMap<>();
                queryMap.put("parentId",menu.getMenuId());
                queryMap.put("userId",user.getUserId());
                tempTopList= menuDao.selectMenuListByParentIdAndUserId(queryMap);
            }else {
                QueryWrapper<Menu> queryWrapper = new QueryWrapper();
                queryWrapper.eq("PARENT_ID",menu.getMenuId()).orderByAsc("SORT");
                tempTopList = menuDao.selectList(queryWrapper);
            }
            getTree(tempTopList,tempMenuTrees,user);
            menuTree.setChildren(tempMenuTrees);
            menuTrees.add(menuTree);
        }
        return menuTrees;
    }

    /**
     * 根据用户ID查询菜单树
     */
    public List<MenuTree> getMenuTreeByUser(User user){
        //根据用户ID获取顶级列表
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("parentId",0);
        queryMap.put("userId",user.getUserId());
        List<Menu> topList = menuDao.selectMenuListByParentIdAndUserId(queryMap);
        List<MenuTree> menuTrees = new ArrayList<>();
        menuTrees = getTree(topList,menuTrees,user);
        return menuTrees;
    }

    public void validationMenu(Menu menu){
        LocalAssert.notNull(menu.getMenuName(),"菜单名称不能为空");
        LocalAssert.notNull(menu.getParentId(),"父级菜单不能为空");
        LocalAssert.notNull(menu.getSort(),"排序号不能为空");
    }

    public void checkMenuExist(Menu menu) throws Exception {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("MENU_NAME",menu.getMenuName());
        queryMap.put("PARENT_ID",menu.getParentId());
        if(menuDao.selectByMap(queryMap).size() > 0){
            throw new Exception("菜单名称已存在");
        }
    }
}

package com.mediot.ygb.mrqs.system.controller;

import com.mediot.ygb.mrqs.system.vo.MenuTree;
import com.mediot.ygb.mrqs.system.pojo.Menu;
import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/menuListFuzzyQuery")
    public List<Menu> menuListFuzzyQuery(String queryStr){
        return menuService.menuListFuzzyQuery(queryStr);
    }

    @RequestMapping("/insert")
    public int insert(Menu menu) throws Exception{
        return menuService.insert(menu);
    }

    @RequestMapping("/update")
    public int update(Menu menu) throws Exception {
        return menuService.update(menu);
    }

    @RequestMapping("/delete")
    public int delete(Menu menu) throws Exception {
        return menuService.delete(menu);
    }

    @RequestMapping("/selectById")
    public Menu selectById(Menu menu){
        return menuService.selectById(menu);
    }

    @RequestMapping("/queryMenuTree")
    public List<MenuTree> queryMenuTree(){
        return menuService.getMenuTree();
    }

    @RequestMapping("/selectMenuListByRoleId")
    public List<Menu> selectMenuListByRoleId(Role role){
        return menuService.selectMenuListByRoleId(role);
    }
}

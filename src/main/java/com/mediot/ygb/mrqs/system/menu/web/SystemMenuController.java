package com.mediot.ygb.mrqs.system.menu.web;

import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.system.menu.service.MenuService;
import com.mediot.ygb.mrqs.system.menu.service.SystemMenuService;
import com.mediot.ygb.mrqs.system.menu.dto.SystemMenuDto;
import com.mediot.ygb.mrqs.system.menu.entity.Menu;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单关系表 前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/systemMenu")
public class SystemMenuController {
    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private MenuService menuService;

    /**
     * 查找一级菜单(下拉框)
     * @param systemCode
     * @param request
     * @return
     */
    @RequestMapping("/searchTopMenuBySystemId")
    public Map<String,Object> searchTopMenuBySystemId(
            String systemCode , HttpServletRequest request){
        LocalAssert.notBlank(systemCode, "请选择系统！");
        Map<String,Object> queryMap= Maps.newHashMap();
        queryMap.put("systemCode", systemCode);
        //request.setAttribute(ResResultInterceptor.IGNORE_STD_RESULT, true);//忽略标准结果
        return systemMenuService.searchTopMenuBySystemId(queryMap);
    }

    /**
     * 新增或编辑菜单
     * @param menu
     * @param request
     */
    @RequestMapping("/addOrUpdateSystemMenu")
    public String addOrUpdateSystemMenu(
            SystemMenuDto menu , HttpServletRequest request){
        LocalAssert.notNull(menu,"菜单信息为空，请填写菜单信息！");
        LocalAssert.notBlank(menu.getSystemCode(), "请选择系统！");
        //LocalAssert.notBlank(menu.getParentCode(), "请选择上级菜单！");
        LocalAssert.notBlank(menu.getMenuCode(), "菜单编码，不能为空！");
        LocalAssert.notBlank(menu.getType(), "请明确当前操作方式！");
        if(!"0".equals(menu.getParentCode())){//二级菜单，路径必填
            LocalAssert.notBlank(menu.getUrl(), "请填写菜单路径！");
            LocalAssert.intLessEqual(menu.getUrl().length(), 250, "菜单路径不能超过250个字符！");
            //验证菜单是否只有2层
            Menu entity = menuService.selectById(menu.getParentCode());
            LocalAssert.notNull(entity,"上级菜单不存在，请重新选择！");
            LocalAssert.equalsTrim("0", entity.getParentCode(), "菜单只允许出现2级！");
        }
        LocalAssert.notBlank(menu.getMenuName(), "请输入菜单名称！");
        LocalAssert.intLessEqual(menu.getMenuName().length(), 10, "菜单名称不能超过10个字符！");
        Map<String,Object> queryMap=Maps.newHashMap();
        queryMap.put("menuName", menu.getMenuName());
        queryMap.put("parentCode", menu.getParentCode());
        queryMap.put("systemCode", menu.getSystemCode());
        if(CustomConst.OperateType.UPDATE.equals(menu.getType())){ //编辑
            queryMap.put("menuCode", menu.getMenuCode());
            //验证菜单是否存在
            Menu entity = menuService.selectById(menu.getMenuCode());
            LocalAssert.notNull(entity,"您编辑的菜单不存在，请确认后操作！");
        }
        int count = systemMenuService.selectCountMenuName(queryMap);//验证“菜单名称”是否重复
        LocalAssert.intLessEqual(count, 0, "菜单名称不能重复！");
        LocalAssert.notNull(menu.getFsort(),"请填写排序！");
        LocalAssert.intLessEqual(menu.getFsort().toString().length(), 3, "排序不能超过3位数！");
        LocalAssert.notNull(menu.getFstate(), "请选择菜单状态！");
        if(StringUtils.isNotBlank(menu.getRemark())){
            LocalAssert.intLessEqual(menu.getRemark().length(), 127, "备注不能超过127个字符！");
        }
        return systemMenuService.addOrUpdateSystemMenu(menu);
    }
}

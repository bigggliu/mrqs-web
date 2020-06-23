package com.mediot.ygb.mrqs.system.menu.service;


import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.system.menu.dto.SystemDto;
import com.mediot.ygb.mrqs.system.menu.entity.System;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统表 服务类
 * </p>
 *
 * @author
 * @since
 */
public interface SystemService extends BaseService<System> {

    /**
     * 查询系统列表
     * @param pager
     */
    Map<String,Object> querySystemList(Map<String,Object> queryMap,Integer pageSize,Integer pageNum);

    /**
     * 新增或编辑系统
     * @param system
     */
    void addOrUpdateSystem(SystemDto system);

    /**
     * 查询某一系统下的菜单列表
     * @param pager
     * @return
     */
    Map<String, Object> querySystemMenuList(Map<String, Object> queryMap,Integer pageSize,Integer pageNum);

    List<MenuVo> treeList(List<MenuVo> list, String parentCode, String groupId);

    /**
     * 递归-菜单树
     * @param list
     * @param parentCode
     * @return
     */
    List<MenuVo> treeList(List<MenuVo> list, String parentCode);
}

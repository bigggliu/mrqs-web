package com.mediot.ygb.mrqs.system.group.service.impl;


import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.system.group.dao.GroupMenuMapper;
import com.mediot.ygb.mrqs.system.group.service.GroupMenuService;
import com.mediot.ygb.mrqs.system.menu.service.SystemService;
import com.mediot.ygb.mrqs.system.groupMenu.entity.GroupMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 * @author
 * @since
 */
@Service
public class GroupMenuServiceImpl extends BaseServiceImpl<GroupMenuMapper, GroupMenu> implements GroupMenuService {

    @Autowired
    private GroupMenuMapper groupMenuMapper;
    @Autowired
    private SystemService systemService;

    /**
     * 获取用户的子系统列表
     * @param userId
     * @return List
     */
//    @Override
//    public List<Map<String, Object>> getPlatformSystemByUserId(String userId) {
//        List<Map<String, Object>> list=groupMenuMapper.getPlatformSystemByUserId(userId, null);
//        if(CollectionUtils.isNotEmpty(list)){
//            for(Map<String, Object> sysMap:list){
//                if(sysMap!=null){
//                    LocalAssert.notNull(sysMap.get("platformSystemId"),"数据问题：子系统为空！");
//                    List<Map<String, Object>> menuList= null;
//                    //如果是超级管理员,查询该子系统下的所有菜单
//                    if(CustomConst.UserLevel.SYS_ADMIN.equals(sysMap.get("level"))){
//                        menuList=groupMenuMapper.selectAllSysMenus(sysMap.get("platformSystemId").toString(),sysMap.get("systemCode").toString());
//                    }else if(CustomConst.UserLevel.ORG_USER.equals(sysMap.get("level"))){
//                         menuList=groupMenuMapper.selectSysMenus(userId,sysMap.get("platformSystemId").toString());
//                    }
//                    List<Map<String, Object>> sysMenus=systemService.treeList(menuList, "0");
//                    if(CollectionUtils.isNotEmpty(sysMenus)){
//                        sysMap.put("sysMenus",sysMenus);
//                    }
//                }
//            }
//        }
//        return list;
//    }

    /**
     * 查询某个“系统操作员”的菜单权限集合
     * @param userId
     * @param platformSystemId
     * @return Set<String>
     * @author
     *      */
    @Override
    public Set<String> findMenuCodesByOperator(String userId, String platformSystemId) {
        return groupMenuMapper.findMenuCodesByOperator(userId, platformSystemId);
    }

    /**
     * 获取用户的子系统列表（医院精细化管理平台专用）
     * @param userId
     * @return List
     */
//	@Override
//	public List<Map<String, Object>> getDepartPlatformSystemByUserId(String userId) {
//		List<Map<String, Object>> list=groupMenuMapper.getPlatformSystemByUserId(userId, PlatformType.HSCM);
//        if(CollectionUtils.isNotEmpty(list)){
//            for(Map<String, Object> sysMap:list){
//                if(sysMap!=null){
//                    LocalAssert.notNull(sysMap.get("platformSystemId"),"数据问题：子系统为空！");
//                    List<Map<String, Object>> menuList= null;
//                    //如果是超级管理员,查询该子系统下的所有菜单
//                    if(CustomConst.UserLevel.SYS_ADMIN.equals(sysMap.get("level"))){
//                    	//如果是科室工作站
//                    	if("50".equals(sysMap.get("systemType"))) {
//                    		menuList=groupMenuMapper.selectDepartAllSysMenus(sysMap.get("platformSystemId").toString(),sysMap.get("systemCode").toString());
//                    	}else {
//                    		//如果是其他子系统
//                    		menuList=groupMenuMapper.selectAllSysMenus(sysMap.get("platformSystemId").toString(),sysMap.get("systemCode").toString());
//                    	}
//                    }else if(CustomConst.UserLevel.ORG_USER.equals(sysMap.get("level"))){
//                         menuList=groupMenuMapper.selectSysMenus(userId,sysMap.get("platformSystemId").toString());
//                    }
//                    List<Map<String, Object>> sysMenus=systemService.treeList(menuList, "0");
//                    if(CollectionUtils.isNotEmpty(sysMenus)){
//                        sysMap.put("sysMenus",sysMenus);
//                    }
//                }
//            }
//        }
//        return list;
//	}

}

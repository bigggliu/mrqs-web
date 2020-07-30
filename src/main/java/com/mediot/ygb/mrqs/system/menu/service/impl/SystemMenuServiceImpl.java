package com.mediot.ygb.mrqs.system.menu.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.system.menu.dao.MenuMapper;

import com.mediot.ygb.mrqs.system.menu.dao.SystemMenuMapper;
import com.mediot.ygb.mrqs.system.menu.service.SystemMenuService;

import com.mediot.ygb.mrqs.system.menu.dto.SystemMenuDto;
import com.mediot.ygb.mrqs.system.menu.entity.Menu;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;
import com.mediot.ygb.mrqs.system.menu.vo.SystemLabelVo;

import com.mediot.ygb.mrqs.system.menu.entity.SystemMenu;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * <p>
 * 系统菜单关系表 服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {
    @Autowired
    private SystemMenuMapper systemMenuMapper;
    @Autowired
    private MenuMapper menuMapper;


    @Override
    public Map<String, Object> searchTopMenuBySystemId(Map<String,Object> queryMap) {
        Page page= PageHelper.startPage(1,15);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<SystemLabelVo> systemLabelVoList= Lists.newArrayList();
        SystemLabelVo systemLabelVo=new SystemLabelVo();
        systemLabelVo.setText("无");
        systemLabelVo.setValue("0");
        systemLabelVoList.add(systemLabelVo);
        List<SystemLabelVo> top = systemMenuMapper.searchTopMenuBySystemId(queryMap);
        if(CollectionUtils.isNotEmpty(top)){
            systemLabelVoList.addAll(top);
        }
        PageInfo<SystemLabelVo> pageInfo = new PageInfo<>(systemLabelVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",systemLabelVoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

    @Override
    public int selectCountMenuName(Map<String, Object> queryMap) {
        return systemMenuMapper.selectCountMenuName(queryMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addOrUpdateSystemMenu(SystemMenuDto menu) {
        Long menuCode = 0L;
        if(CustomConst.OperateType.INSERT.equals(menu.getType())){//新增菜单
            Menu entity = new Menu();
            entity.setParentCode(menu.getParentCode());
            entity.setNodeLevel("0".equals(menu.getParentCode())?1:2);
            entity.setMenuName(menu.getMenuName());
            entity.setUrl(menu.getUrl());
            entity.setFsort(menu.getFsort());
            entity.setFstate(menu.getFstate());
            entity.setRemark(menu.getRemark());
            menuMapper.insert(entity);//新增菜单
            SystemMenu systemMenu = new SystemMenu();
            systemMenu.setSystemCode(Long.valueOf(menu.getSystemCode()));
            systemMenu.setMenuCode(Long.valueOf(entity.getMenuCode()));
            systemMenuMapper.insert(systemMenu);
            menuCode = entity.getMenuCode();
        }else{//编辑菜单
            Menu bean = new Menu();
            bean.setMenuCode(menu.getMenuCode());
            bean.setMenuName(menu.getMenuName());
            bean.setFsort(menu.getFsort());
            bean.setFstate(menu.getFstate());
            bean.setUrl(menu.getUrl());
            bean.setRemark(menu.getRemark());
            menuMapper.updateById(bean);
//            if(CustomConst.Fstate.DISABLE == bean.getFstate()){
//                //修改值
//                PlatformSystemMenu platformSystemMenu = new PlatformSystemMenu();
//                platformSystemMenu.setFstate(CustomConst.Fstate.DISABLE);
//                //修改条件
//                UpdateWrapper<PlatformSystemMenu> wrapper = new UpdateWrapper<>();
//                wrapper.eq("menu_code", menu.getMenuCode());
//                platformSystemMenuMapper.update(platformSystemMenu,wrapper);
//            }
            menuCode = menu.getMenuCode();
        }
        return menuCode;
    }

    /**
     * 自动生成系统编码
     * @return
     */
//    public String getMenuCode(String systemCode,String parentCode) {
//        String beforeCode = systemMenuMapper.getMenuCode(systemCode,parentCode);
//        LocalAssert.notBlank(beforeCode, "数据错误：系统编码生成错误！");
//        String afterCode = null;
//        if("0".equals(parentCode)){
//            afterCode = String.format("%03d", new BigDecimal(beforeCode).add(new BigDecimal("1")).intValue());
//            LocalAssert.notBlank(afterCode, "数据错误：菜单编码无法自动生成！");
//            LocalAssert.intEqual(afterCode.length(), 3, "数据错误：一级菜单编码为3位数，生成错误！");
//        }else{
//            Menu entity = menuMapper.selectById(parentCode);
//            LocalAssert.notNull(entity,"上级菜单不存在，请重新选择！");
//            String exCode = entity.getMenuCode();//上级菜单的编码
//            LocalAssert.notBlank(exCode, "数据错误：上级菜单没有编码！");
//            if(beforeCode.length()>3){
//                beforeCode = beforeCode.substring(3);
//            }
//            String newCode = String.format("%03d", new BigDecimal(beforeCode).add(new BigDecimal("1")).intValue());
//            LocalAssert.notBlank(newCode, "数据错误：无法生成菜单编码！");
//            afterCode = new StringBuffer(exCode).append(newCode).toString();
//        }
//        LocalAssert.notBlank(afterCode, "数据错误：菜单编码无法自动生成！");
//        LocalAssert.intLessEqual(afterCode.length(), 6, "数据错误：菜单编码最长不超过6位数，生成错误！");
//        //校验新生成的菜单编码在数据库中是否重复
//        int count = systemMenuMapper.selectMenuCodeCount(afterCode,systemCode,parentCode);
//        LocalAssert.intLessEqual(count, 0, "生成的系统编码重复了，请重新操作！");
//        return afterCode;
//    }

    /**
     * 查询某个“系统管理员”的菜单权限集合
     * @param userId
     * @param platformSystemId
     * @return Set
     * @author
     *      */
    @Override
    public Set<String> findMeueCodesBygbsAdmin(String userId, String platformSystemId) {
        return baseMapper.findMeueCodesBygbsAdmin(userId, platformSystemId);
    }

    @Override
    public List<MenuVo> selectMenuVoList(Map<String, Object> queryMap) {
        return systemMenuMapper.querySystemMenuList(queryMap);
    }

    @Override
    public List<MenuVo> selectListByArray(String[] systemCodeArr) {
        return systemMenuMapper.querySystemMenuByArray(systemCodeArr);
    }

}

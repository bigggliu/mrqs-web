package com.mediot.ygb.mrqs.system.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.system.group.dao.GroupMenuMapper;
import com.mediot.ygb.mrqs.system.groupMenu.entity.GroupMenu;
import com.mediot.ygb.mrqs.system.menu.dao.SystemMapper;
import com.mediot.ygb.mrqs.system.menu.dao.SystemMenuMapper;
import com.mediot.ygb.mrqs.system.menu.entity.Menu;
import com.mediot.ygb.mrqs.system.menu.service.SystemService;
import com.mediot.ygb.mrqs.system.menu.dto.SystemDto;
import com.mediot.ygb.mrqs.system.menu.entity.System;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;
import com.mediot.ygb.mrqs.system.menu.vo.SystemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统表 服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl<SystemMapper, System> implements SystemService {
    @Autowired
    private SystemMapper systemMapper;
    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Autowired
    private GroupMenuMapper groupMenuMapper;

    @Override
    public Map<String,Object> querySystemList(Map<String,Object> queryMap,Integer pageSize,Integer pageNum) {
        LocalAssert.notNull(pageNum,"pageNum不能为空");
        LocalAssert.notNull(pageSize,"pageSize不能为空");
        List<System> systemList=systemMapper.querySystemList(queryMap);
        Page page= PageHelper.startPage(pageNum,pageSize);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if(systemList.size()==0){
            jsonMap.put("data",null);//数据结果
            jsonMap.put("total", 0);//获取数据总数
            jsonMap.put("pageSize",1);//获取长度
            jsonMap.put("pageNum", 15);//获取当前页数
            return jsonMap;
        }
        if(systemList.size()>0){
            List<SystemVo> systemVoList = systemList.stream().map(e -> JsonUtil.
                    getJsonToBean(JsonUtil.getBeanToJson(e), SystemVo.class)).
                    collect(Collectors.toList());
            PageInfo<SystemVo> pageInfo = new PageInfo<>(systemVoList);
            pageInfo.setPages(page.getPages());//总页数
            pageInfo.setTotal(page.getTotal());//总条数
            jsonMap.put("data",systemVoList);//数据结果
            jsonMap.put("total", pageInfo.getTotal());//获取数据总数
            jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
            jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        }else {
            jsonMap.put("data",null);//数据结果
            jsonMap.put("total", 0);//获取数据总数
            jsonMap.put("pageSize",1);//获取长度
            jsonMap.put("pageNum", 15);//获取当前页数
        }
        return jsonMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateSystem(SystemDto system) {
        LocalAssert.notNull(system,"系统信息为空，请填写系统的信息！");
        LocalAssert.notBlank(system.getType(), "请明确当前操作方式！");
        LocalAssert.notBlank(String.valueOf(system.getSystemCode()), "系统编码为空，请输入3位纯数字系统编码！");
        if(CustomConst.OperateType.INSERT.equals(system.getType())){//新增
            System s=JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(system),System.class);
            systemMapper.insert(s);
        }else{//编辑
            System entity = new System();
            entity.setSystemCode(Long.parseLong(system.getSystemCode()));
            entity.setSystemName(system.getSystemName());
            entity.setFsort(system.getFsort());
            systemMapper.updateById(entity);
        }
    }

    @Override
    public Map<String, Object> querySystemMenuList(Map<String, Object> queryMap,Integer pageSize,Integer pageNum) {
        LocalAssert.notNull(pageNum,"pageNum不能为空");
        LocalAssert.notNull(pageSize,"pageSize不能为空");
        Page page= PageHelper.startPage(pageNum,pageSize);
        List<MenuVo> list=systemMenuMapper.querySystemMenuList(queryMap);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<MenuVo> menuVoList=list.stream().map(e -> JsonUtil.
                getJsonToBean(JsonUtil.getBeanToJson(e), MenuVo.class)).
                collect(Collectors.toList()); ;
        List<MenuVo> finalList=treeList(menuVoList, "0");
        //
        PageInfo<MenuVo> pageInfo = new PageInfo<>(finalList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",finalList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

    @Override
    public List<MenuVo> treeList(List<MenuVo> list, String parentCode, String groupId) {
        List<MenuVo> children = new ArrayList<>();
        for (MenuVo menuVo : list) {
            String id = menuVo.getMenuCode();
            String pid = menuVo.getParentCode();
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("MENU_CODE",id);
            queryWrapper.eq("GROUP_ID",groupId);
            GroupMenu groupMenu=groupMenuMapper.selectOne(queryWrapper);
            if(groupMenu!=null){
                menuVo.setIsSelected(1);
            }else {
                menuVo.setIsSelected(0);
            }
            if (parentCode.equals(pid)) {
                List<MenuVo> childNode = treeList(list, id);
                if (childNode.size() != 0) {
                    menuVo.setChildren(Lists.newArrayList());
                    menuVo.getChildren().addAll(childNode);
                }
                children.add(menuVo);
            }
        }
        return children;
    }

    @Override
    public List<MenuVo> treeList(List<MenuVo> list, String parentCode) {
        List<MenuVo> children = new ArrayList<>();
        for (MenuVo menuVo : list) {
            String id = menuVo.getMenuCode();
            String pid = menuVo.getParentCode();
            if (parentCode.equals(pid)) {
                List<MenuVo> childNode = treeList(list, id);
                if (childNode.size() != 0) {
                    menuVo.setChildren(Lists.newArrayList());
                    menuVo.getChildren().addAll(childNode);
                }
                children.add(menuVo);
            }
        }
        return children;
    }

    /**
     * 自动生成系统编码
     * @return
     */
    public String getSystemCode() {
        String beforeCode = systemMapper.getSystemMaxCode();
        LocalAssert.notBlank(beforeCode, "数据错误：系统编码生成错误！");
        String afterCode = String.format("%03d", new BigDecimal(beforeCode).add(new BigDecimal("1")).intValue());
        LocalAssert.notBlank(afterCode, "数据错误：系统编码无法自动生成！");
        LocalAssert.intEqual(afterCode.length(), 3, "数据错误：系统编码位3位数，生成错误！");
        //校验新生成的系统编码在数据库中是否重复
        QueryWrapper<System> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_code",afterCode);
        int count = super.selectCount(queryWrapper);
        LocalAssert.intLessEqual(count, 0, "生成的系统编码重复了，请重新操作！");
        return afterCode;
    }
}

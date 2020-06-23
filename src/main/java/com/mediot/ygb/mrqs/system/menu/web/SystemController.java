package com.mediot.ygb.mrqs.system.menu.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.exception.MediotException;
import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.system.menu.service.SystemService;
import com.mediot.ygb.mrqs.system.menu.dto.SystemDto;
import com.mediot.ygb.mrqs.system.menu.entity.System;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 * 系统表 前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/systemMgr")
public class SystemController {
    @Autowired
    private SystemService systemService;

    /**
     * 查询系统列表
     * @param systemName
     * @param platformType
     * @param systemType
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/querySystemList")
    public Map<String,Object> querygbstemList(Integer pageSize,Integer pageNum,Integer queryType,String parentSystemcode,
                                              String systemName , String platformType, String systemType, HttpServletRequest request , HttpSession session){
        Map<String,Object> queryMap= Maps.newHashMap();
        queryMap.put("systemName", systemName);
        //queryMap.put("platformType", platformType);
        queryMap.put("systemType", systemType);
        queryMap.put("parentSystemcode", parentSystemcode);
        //queryType=1，过滤掉已经绑定的供应商业务系统
        if(null != queryType){
            queryMap.put("queryType", queryType);
        }
        return systemService.querySystemList(queryMap,pageSize,pageNum);
    }

    /**
     * 添加
     * @param system
     * @param request
     */
    @RequestMapping("/addOrUpdateSystem")
    public void addOrUpdateSystem(
            SystemDto system , HttpServletRequest request){
        LocalAssert.notNull(system,"系统信息为空，请填写系统的信息！");
        LocalAssert.notBlank(system.getType(), "请明确当前操作方式！");
        LocalAssert.notBlank(String.valueOf(system.getSystemCode()), "系统编码为空，请输入3位纯数字系统编码(不能以0或00开始)！");
        if(CustomConst.OperateType.INSERT.equals(system.getType())){//新增
            if(!system.getSystemCode().matches("^[1-9]\\d{2}$")){
                throw new ValidationException("系统编码格式不正确，请输入3位纯数字系统编码(不能以0或00开始)！");
            }
            System system1=systemService.selectById(system.getSystemCode());
            if(system1!=null){
                throw new ValidationException("系统编码已经存在,请更换重试！");
            }
        }
        LocalAssert.notBlank(system.getSystemName(), "请输入系统名称！");
        LocalAssert.intLessEqual(system.getSystemName().length(), 25, "系统名称不能超过25个字符！");
        QueryWrapper<System> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_name",system.getSystemName()); // system_name = system.getSystemName
        if(CustomConst.OperateType.UPDATE.equals(system.getType())){ //编辑
            queryWrapper.ne("system_code",system.getSystemCode()); // system_code <> system.getSystemCode
            System entity = systemService.selectById(system.getSystemCode());
            LocalAssert.notNull(entity,"您编辑的系统不存在，请确认后操作！");
        }
        int count = systemService.selectCount(queryWrapper);//验证“系统名称”是否重复
        LocalAssert.intLessEqual(count, 0, "系统名称不能重复！");
        LocalAssert.notNull(system.getFsort(),"请填写排序！");
        LocalAssert.intLessEqual(system.getFsort().toString().length(), 2, "排序不能超过2位数！");
        systemService.addOrUpdateSystem(system);
    }

    /**
     * 查询某一系统下的菜单列表
     * @param systemCode
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/querySystemMenuList")
    public Map<String, Object> querygbstemMenuList(
            String systemCode ,Integer pageSize,Integer pageNum,HttpServletRequest request , HttpSession session){
        if(!StringUtils.isNotBlank(systemCode)){
            new MediotException("请选择具体查询哪一系统下的菜单列表！");
        }
        Map<String, Object> queryMap=Maps.newHashMap();
        queryMap.put("systemCode", systemCode);
        return systemService.querySystemMenuList(queryMap,pageSize,pageNum);
    }
}

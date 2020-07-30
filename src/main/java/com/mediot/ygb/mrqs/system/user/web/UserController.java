package com.mediot.ygb.mrqs.system.user.web;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;

import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.exception.MediotException;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.WebUtils;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.org.service.TOrgsService;
import com.mediot.ygb.mrqs.system.group.service.GroupService;
import com.mediot.ygb.mrqs.system.menu.service.SystemMenuService;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;
import com.mediot.ygb.mrqs.system.user.entity.UserInfo;
import com.mediot.ygb.mrqs.system.user.service.UserService;
import com.mediot.ygb.mrqs.system.user.dto.UserInfoDto;
import com.mediot.ygb.mrqs.system.user.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @Autowired
    SystemMenuService systemMenuService;

    @Autowired
    TOrgsService tOrgsService;

    @Autowired
    GroupService groupService;

    /**
     * 分页查询用户信息
     * @param userInfoDto
     * @return
     */
    @RequestMapping("list")
    public Map<String, Object> getTBasePageList(UserInfoDto userInfoDto){
        return userService.findPageListByParam(userInfoDto);
    }

    @RequestMapping(value = {"addAndModifyUser"},method = RequestMethod.POST)
    public int addAndModifyOrg(UserInfoDto userInfoDto) {
        return userService.insertAndUpdateUser(userInfoDto,false);
    }

    @RequestMapping("resetPwd")
    public void resetPwd(UserInfo userInfo){
        userService.updatePwd(userInfo);
    }

    @RequestMapping("modifyPwd")
    public void modifyPwd(UserInfo userInfo){
        userService.modifyPwd(userInfo);
    }


    /**
     * 查询当前登录用户信息
     * @return
     */
    @RequestMapping("currentUser")
    public UserInfoVo curentUser(){
        UserInfoDto userInfoDto = new UserInfoDto();
        UserInfoVo userInfoVo = WebUtils.getSessionAttribute(CustomConst.LoginUser.SESSION_USER_INFO);
        userInfoDto.setUserId(userInfoVo.getUserId());
        QueryWrapper queryWrapper = new QueryWrapper<UserInfo>();
        queryWrapper.eq("user_id", userInfoDto.getUserId());

        UserInfo userInfo =  userService.selectOne(queryWrapper);
        if(null != userInfo){
            BeanUtils.copyProperties(userInfo,userInfoVo);
            if(StringUtils.isNotBlank(Long.toString(userInfo.getOrgId()))){
                userInfoVo.setOrgId(String.valueOf(userInfo.getOrgId()));
                //查找机构
                TOrgsEntity tOrgsEntity=tOrgsService.selectById(userInfo.getOrgId());
                userInfoVo.setOrgName(tOrgsEntity.getOrgName());
                userInfoVo.setAppCode(tOrgsEntity.getAppCode());
                userInfoVo.setFqun(tOrgsEntity.getFqun());
            }
            if(StringUtils.isBlank(userInfoVo.getSystemCodes())){
                userInfoVo.setCurrentSystemCode(userInfo.getSystemCodes().split(",")[0]);
            }
        }
        return userInfoVo;
    }

    /**
     * 切换子系统
     * @param
     * @return
     */
    @RequestMapping("/changeSystem")
    public UserInfoVo changeSystem(String systemCode) {
        LocalAssert.notBlank(systemCode, "systemCode不能为空！");
        UserInfoVo sessionUser =  WebUtils.getSessionUser(CustomConst.LoginUser.SESSION_USER_INFO);
        String[] systemCodeArr=sessionUser.getSystemCodes().split(",");
        List<String> systemCodeList = Arrays.asList(systemCodeArr);
        if(null != sessionUser){
            //判断用户有没有这个系统
            if(systemCodeList.contains(systemCode)){
                new MediotException("该用户不存在这样的系统!");
            };
            sessionUser.setCurrentSystemCode(systemCode);
            List<MenuVo> menuVoList=groupService.getGroupMenuList(sessionUser.getUserId(),sessionUser.getCurrentSystemCode());
            sessionUser.setMenuList(menuVoList);
            //根据平台系统信息填充用户扩展信息
            //userService.fillUserInfoVoByPlatformSystem(sessionUser, targetPlatformSystem);
            //重置会话用户信息
            session.setAttribute(CustomConst.LoginUser.SESSION_USER_INFO, sessionUser);
        }
        return sessionUser;
    }

}

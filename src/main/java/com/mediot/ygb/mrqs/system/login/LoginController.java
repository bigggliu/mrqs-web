package com.mediot.ygb.mrqs.system.login;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

import com.mediot.ygb.mrqs.common.config.YgbConfig;
import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.exception.MediotException;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.common.util.VerifyUtil;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.org.service.TOrgsService;

import com.mediot.ygb.mrqs.system.group.entity.Group;
import com.mediot.ygb.mrqs.system.group.service.GroupService;
import com.mediot.ygb.mrqs.system.group.service.GroupUserService;
import com.mediot.ygb.mrqs.system.groupUser.entity.GroupUser;
import com.mediot.ygb.mrqs.system.menu.entity.System;
import com.mediot.ygb.mrqs.system.menu.service.SystemMenuService;
import com.mediot.ygb.mrqs.system.menu.service.SystemService;
import com.mediot.ygb.mrqs.system.menu.vo.MenuOfSystemVo;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;
import com.mediot.ygb.mrqs.system.menu.vo.SystemVo;
import com.mediot.ygb.mrqs.system.user.service.UserService;

import com.mediot.ygb.mrqs.system.user.dto.UserInfoDto;
import com.mediot.ygb.mrqs.system.user.entity.UserInfo;
import com.mediot.ygb.mrqs.system.user.vo.UserInfoVo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dyuproject.protostuff.MapSchema.MessageFactories.HashMap;


/**
 * 登录
 *
 * @author
 * @version 1.0
 *
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RequestMapping("login")
class LoginController {

    @Value("${spring.profiles.active}")
    private String profile;

//    @Autowired
//    private HttpSession session;

    @Autowired
    private UserService userService;

//    @Autowired
//    private GroupMenuService groupMenuService;

    @Autowired
    private TOrgsService tOrgsService;

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupUserService groupUserService;



    /**
     * 登录接口
     *
     * @param userInfoDto
     * @return UserInfoVo
     */
    @RequestMapping(path = "ajaxLogin", produces = "application/json;charset=utf-8")
    public UserInfoVo ajaxLogin(UserInfoDto userInfoDto, HttpServletRequest request, HttpServletResponse response) {
        LocalAssert.notNull(userInfoDto, "登录对象不能为空");
        LocalAssert.notBlank(userInfoDto.getUserNo(), "用户名，不能为空！");
        LocalAssert.notBlank(userInfoDto.getPwd(), "密码，不能为空！");
        LocalAssert.notBlank(userInfoDto.getImageCode(), "验证码，不能为空！");
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            response.setHeader(YgbConfig.HTTP_SESSION_ID, session.getId());
            log.debug("➧➧➧ 复用会话：JSESSIONID={}", session.getId());
        }
        log.debug("当前用户会话：JSESSIONID={}", session.getId());

//        Object imageCode = session.getAttribute("imageCode");
//        if (ProfilesType.PROD_ENV.equals(profile)) {
//            LocalAssert.notNull(imageCode, "验证码不能为空");
//            if (!userInfoDto.getImageCode().equals(imageCode)) {
//                log.error("验证码不正确");
//                throw new MediotException("验证码不正确");
//            }
//        } else {
//            if (!userInfoDto.getImageCode().equals(CustomConst.DEFAULT_IMAGECODE) && !userInfoDto.getImageCode().equalsIgnoreCase(imageCode.toString())) {
//                log.error("验证码不正确");
//                throw new MediotException("验证码不正确");
//            }
//        }

        //检查用户名和密码是否合法（前置：验证码验证通过）
        UserInfo baseUserInfo = userService.findUserInfoByParameter(userInfoDto);
        if (null == baseUserInfo) {
            log.error("找不到用户信息:" + userInfoDto.getUserNo());
            throw new MediotException("用户名或密码错误");
        }
        if (null != baseUserInfo && baseUserInfo.getFstate() == CustomConst.Fstate.DISABLE) {
            log.error(baseUserInfo.getUserNo() + ":账号已经停用");
            throw new MediotException("账号已经停用");
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(baseUserInfo, userInfoVo);
        userInfoVo.setUserId(String.valueOf(baseUserInfo.getUserId()));
        userInfoVo.setSessionId(session.getId());
        if(StringUtils.isNotBlank(Long.toString(baseUserInfo.getOrgId()))){
            userInfoVo.setOrgId(String.valueOf(baseUserInfo.getOrgId()));
            //查找机构
            TOrgsEntity tOrgsEntity=tOrgsService.selectById(baseUserInfo.getOrgId());
            if(tOrgsEntity == null){
                throw new MediotException("机构不存在");
            }
            userInfoVo.setOrgName(tOrgsEntity.getOrgName());
            userInfoVo.setAppCode(tOrgsEntity.getAppCode());
            userInfoVo.setFqun(tOrgsEntity.getFqun());
        }
        //根据用户获取平台系统列表
        List<MenuVo> menuList= Lists.newArrayList();
        List<SystemVo> systemVoList=Lists.newArrayList();
        List<MenuOfSystemVo> menuOfSystemVoList=Lists.newArrayList();
        if(StringUtils.isNotBlank(baseUserInfo.getSystemCodes())){
            String systemCodeArr[] =baseUserInfo.getSystemCodes().split(",");
            //
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.in("SYSTEM_CODE", systemCodeArr);
            List<System> systemList=systemService.selectList(queryWrapper);
            systemVoList=systemList.stream().map(e->
                JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),SystemVo.class)
            ).collect(Collectors.toList());
            //
            menuOfSystemVoList=systemList.stream().map(e->
                    JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),MenuOfSystemVo.class)
            ).collect(Collectors.toList());
            menuOfSystemVoList.stream().forEach(e->{
                QueryWrapper<GroupUser> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("USER_ID",baseUserInfo.getUserId());
                List<GroupUser> groupUserList = groupUserService.selectList(queryWrapper1);
                if(groupUserList.size() == 0){
                    throw new MediotException("账号未设置角色");
                }
                List<MenuVo> baseMenuList=groupService.getGroupMenuList(userInfoVo.getUserId(),e.getSystemCode());
                List<MenuVo> finalMenuList=systemService.treeList(baseMenuList,"0");
                e.setMenuList(finalMenuList);
            });
            menuList = groupService.getGroupMenuList(userInfoVo.getUserId(),systemCodeArr[0]);
        }
        if(StringUtils.isNotBlank(baseUserInfo.getSystemCodes())){
            userInfoVo.setCurrentSystemCode(baseUserInfo.getSystemCodes().split(",")[0]);
        }
        userInfoVo.setMenuList(menuList);
        userInfoVo.setSystemList(systemVoList);
        userInfoVo.setUserMenuOfSystemList(menuOfSystemVoList);
        //缓存当前用户信息及用户扩展信息（所有子系统信息，子系统对应菜单信息）
        session.setAttribute(CustomConst.LoginUser.SESSION_USER_INFO, userInfoVo);
        session.removeAttribute("imageCode");
        return userInfoVo;
    }

    /**
     * 退出
     *
     * @author
     *      */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            //注销会话
            session.invalidate();
        }
    }

    /**
     * 验证码接口
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/createImage")
    public void createImage(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            response.setHeader(YgbConfig.HTTP_SESSION_ID, session.getId());
            log.debug("➧➧➧ 复用会话：JSESSIONID={}", session.getId());
        }
        session.setAttribute("imageCode", objs[0]);
        log.info("yzm session id:" + session.getId());
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
        os.flush();
        os.close();
    }

    /**
     * 获取平台logo和背景图
     *
     * @param url
     * @return
     */
//    @RequestMapping(path = "getBackResource", produces = "application/json;charset=utf-8")
//    @Opened
//    public Platform getBackResource(String url) {
//        LocalAssert.notBlank(url, "平台地址，不能为空！");
//        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("url", url);
//        return platformService.selectOne(queryWrapper);
//    }

}
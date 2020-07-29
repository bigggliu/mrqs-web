package com.mediot.ygb.mrqs.system.controller;

import com.mediot.ygb.mrqs.common.config.YgbConfig;
import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.util.VerifyUtil;
import com.mediot.ygb.mrqs.system.pojo.Org;
import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.pojo.User;
import com.mediot.ygb.mrqs.system.service.MenuService;
import com.mediot.ygb.mrqs.system.service.OrgService;
import com.mediot.ygb.mrqs.system.service.RoleService;
import com.mediot.ygb.mrqs.system.service.UserService;
import com.mediot.ygb.mrqs.system.vo.MenuTree;
import com.mediot.ygb.mrqs.system.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/ajaxLogin")
    public UserVo ajaxLogin(UserVo userVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LocalAssert.notNull(userVo, "登录对象不能为空");
        LocalAssert.notBlank(userVo.getUsername(), "用户名，不能为空！");
        LocalAssert.notBlank(userVo.getPassword(), "密码，不能为空！");
        //LocalAssert.notBlank(userVo.getImageCode(), "验证码，不能为空！");
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            response.setHeader(YgbConfig.HTTP_SESSION_ID, session.getId());
            log.debug("➧➧➧ 复用会话：JSESSIONID={}", session.getId());
        }
        log.debug("当前用户会话：JSESSIONID={}", session.getId());
        User user = userService.selectByUserNameAndPassWord(userVo);
        Org org = new Org();
        org.setOrgId(user.getOrgId());
        org = orgService.selectById(org);
        if(user == null){
            throw new Exception("用户名或密码错误");
        }
        if(org == null){
            throw new Exception("用户所属机构不存在");
        }
        if(user.getState() == 0 || org.getState() == 0){
            throw new Exception("账号已停用");
        }
        List<Role> roleList = roleService.selectRoleListByUserId(user);
        List<MenuTree> menuTrees = menuService.getMenuTreeByUser(user);
        UserVo loginUser = new UserVo();
        BeanUtils.copyProperties(loginUser, user);
        loginUser.setOrg(org);
        loginUser.setMenuTrees(menuTrees);
        loginUser.setRoleList(roleList);
        session.setAttribute(CustomConst.LoginUser.SESSION_USER_INFO, loginUser);
        //session.removeAttribute("imageCode");
        return loginUser;
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            //注销会话
            session.invalidate();
        }
    }

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
}

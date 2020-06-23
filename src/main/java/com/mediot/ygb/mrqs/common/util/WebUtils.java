package com.mediot.ygb.mrqs.common.util;

import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 控制器支持类, 包含控制器需要的公用性较强的一些方法
 * @author
 * @version 1.0
 * @since JDK 1.6
 */
public class WebUtils {

    public static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<>();

    /**
     * 获取当前request，没有就抛出异常
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        Assert.notNull(requestLocal.get(), "WebUtils没有绑定request对象！");
        return requestLocal.get();
    }
    /**
     * 获取当前会话数据
     * @return HttpSession
     */
    public static <T> T getSessionAttribute(String attributeName) {
        HttpSession session = getRequest().getSession(false);
        LocalAssert.notNull(session, "当前请求会话无法获取！");
        return (T) session.getAttribute(attributeName);
    }

    /**
     * 获取当前会话用户
     * @return HttpSession
     * @author
     *      */
    public static <T> T getSessionUser(String attributeName) {
        T sessionUser = (T) getSessionAttribute(attributeName);
        LocalAssert.notNull(sessionUser, "请确保已正常登录！");
        return sessionUser;
    }
}

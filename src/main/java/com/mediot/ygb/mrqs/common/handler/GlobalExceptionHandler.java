package com.mediot.ygb.mrqs.common.handler;

import com.mediot.ygb.mrqs.common.util.WebUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        //WebUtils.bindRequestAndResponse(request, response);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("uri",request.getRequestURI());
        resultMap.put("result",null);
        resultMap.put("status",500);
        resultMap.put("ept",exception.getClass().getName());
        resultMap.put("msg",exception.getMessage());
        resultMap.put("time",new Date());
        resultMap.put("during","");
        resultMap.put("failCount","");
        resultMap.put("version","");
        return resultMap;
    }

}

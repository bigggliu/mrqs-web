package com.mediot.ygb.mrqs.common.handler;


import com.mediot.ygb.mrqs.common.util.WebUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //ServletServerHttpResponse sshresp = (ServletServerHttpResponse) serverHttpResponse;
        //ServletServerHttpRequest sshreq = (ServletServerHttpRequest) serverHttpRequest;
        //HttpServletRequest request = sshreq.getServletRequest();
        //HttpServletResponse response = sshresp.getServletResponse();
        //WebUtils.bindRequestAndResponse(request, response);
        if(o instanceof HashMap){
            Map<String,Object> map = (HashMap<String,Object>) o;
            if(map.get("status") != null){
                if((int)map.get("status") == 500){
                    return o;
                }
            }
        }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("uri", serverHttpRequest.getURI());
            resultMap.put("result", o);
            resultMap.put("status", 200);
            resultMap.put("msg", "操作成功");
            resultMap.put("time", new Date());
            resultMap.put("during", "");
            resultMap.put("failCount", "");
            resultMap.put("version", "");
            return resultMap;
    }
}

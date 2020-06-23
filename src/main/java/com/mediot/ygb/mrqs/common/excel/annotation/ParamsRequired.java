package com.mediot.ygb.mrqs.common.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求参数校验
 *
 * @author
 * @create 2017-10-26 10:38
 **/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamsRequired {
    /**
     * 必须参数
     * @return
     */
    boolean requrie() default true;

}

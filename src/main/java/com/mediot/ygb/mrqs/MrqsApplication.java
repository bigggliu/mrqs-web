package com.mediot.ygb.mrqs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableRedisRepositories
@EnableRedisHttpSession
@ComponentScan(basePackages = "com.mediot")
@MapperScan(basePackages = "com.mediot.**.dao")
public class MrqsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrqsApplication.class, args);
    }

}

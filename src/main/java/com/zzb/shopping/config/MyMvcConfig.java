package com.zzb.shopping.config;

import com.zzb.shopping.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 列举不需要验证登录状态的接口
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("")
                .excludePathPatterns("/product/outxslx")
                .excludePathPatterns("/user/reg")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/product/test")
               ;
    }
}
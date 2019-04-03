package com.lei.mywechat.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public UserInfoInterceptor userInfoInterceptor(){
        UserInfoInterceptor userInfoInterceptor = new UserInfoInterceptor();
        return userInfoInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(userInfoInterceptor()).addPathPatterns("/**").excludePathPatterns("/wx/**");
    }

}

/*
package com.lei.oauth.config;

import com.lei.oauth.handler.MyAuthenticationFailHandler;
import com.lei.oauth.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

*/
/**
 * 资源服务器
 * Created by Fant.J.
 *//*


@Order(3)
@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //表单登录 方式
        http.formLogin()
                .loginPage("/authentication/require")
                //登录需要经过的url请求
                .loginProcessingUrl("/authentication/form")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailHandler);

        http
                .authorizeRequests()
                //请求路径“/”容许访问
                .antMatchers("/").permitAll()
                //其它请求都需要校验才能访问
                .anyRequest().authenticated()
                .and()
                // 定义登录的页面为“/login”，容许访问
                .formLogin().loginPage("/login").permitAll()
                .and()
                //默认的“/logout”,容许访问
                .logout().permitAll();
    }
}
*/

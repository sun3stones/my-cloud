package com.lei.oauth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.oauth.entity.OauthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IOauthUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper wrapper = new QueryWrapper<OauthUser>();
        wrapper.eq("username",username);
        OauthUser user = userService.getOne(wrapper);
        System.out.println("用户:"+user!=null?user.toString():"");
        return user;
    }
}
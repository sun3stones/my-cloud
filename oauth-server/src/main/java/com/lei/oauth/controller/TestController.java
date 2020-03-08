package com.lei.oauth.controller;

import com.lei.oauth.entity.OauthUser;
import com.lei.oauth.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping("/")
    public String test(){
        return "test";
    }

    @RequestMapping("/user")
    public String user(String name){
        OauthUser user = (OauthUser) userDetailsService.loadUserByUsername(name);
        return user.toString();
    }
}

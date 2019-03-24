package com.lei.mywechat.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${wechat.token}")
    private String token;

    @RequestMapping("test")
    public String test(){
        return token;
    }
}

package com.lei.mywechat.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
public class TestController {

    @Value("${wechat.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("test")
    public String test(){
        restTemplate.getForObject("http://route.showapi.com/341-2",String.class);
        return token;
    }
}

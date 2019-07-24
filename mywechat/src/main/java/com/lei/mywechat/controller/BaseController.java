package com.lei.mywechat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.mywechat.entity.WxUser;
import com.lei.mywechat.service.IWxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IWxUserService userService;

    public WxUser currentUser(HttpServletRequest request){
        String openid = request.getHeader("openid");
        Object object = redisTemplate.opsForValue().get("openid_"+openid);
        WxUser user = new WxUser();
        if(object == null){
            QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
            wrapper.eq("openid",openid);
            user = userService.getOne(wrapper);
            redisTemplate.opsForValue().set("openid_"+openid,user);
        }else{
            user = (WxUser)object;
        }
        return user;
    }
}

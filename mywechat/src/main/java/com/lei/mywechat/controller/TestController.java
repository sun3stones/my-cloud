package com.lei.mywechat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.mywechat.entity.GkUsers;
import com.lei.mywechat.entity.WxUser;
import com.lei.mywechat.mapper.GkUsersMapper;
import com.lei.mywechat.mapper.WxUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Api(tags = "测试",description = "测试相关接口")
@RestController
@RefreshScope
public class TestController {

    @Value("${wechat.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private GkUsersMapper gkUsersMapper;

    @RequestMapping("test")
    public String test(){
        restTemplate.getForObject("http://route.showapi.com/341-2",String.class);
        return token;
    }

    @ApiOperation("数据源1测试")
    @RequestMapping("sql1")
    public Object sql1(){
        QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", "o53WO1YoFZm3reAH8ZaTFn1kZZ-M");
        WxUser wxUser = wxUserMapper.selectOne(wrapper);
        return wxUser;
    }
    @ApiOperation("数据源2测试")
    @RequestMapping("sql2")
    public Object sql2(){
        QueryWrapper<GkUsers> wrapper = new QueryWrapper<>();
        wrapper.eq("user_login", "admin");
        GkUsers GkUsers = gkUsersMapper.selectOne(wrapper);
        return GkUsers;
    }
}

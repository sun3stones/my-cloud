package com.lei.mywechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.mywechat.entity.WxUser;
import com.lei.mywechat.service.IWxUserService;
import com.lei.mywechat.utils.Config;
import com.lei.mywechat.utils.HttpClientUtil;
import com.lei.mywechat.weixin.WXService;
import com.lei.mywechat.weixin.utils.WxUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sunlei
 * @since 2019-04-10
 */
@RestController
@RequestMapping("/user")
public class WxUserController {

    @Autowired
    private IWxUserService userService;
    @Autowired
    private WXService wxService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/getInfo")
    public Object getInfo(HttpServletRequest request, HttpServletResponse response){
        try {
            String openid = request.getHeader("openid");
            //openid = "o53WO1YoFZm3reAH8ZaTFn1kZZ-M";
            if(StringUtils.isEmpty(openid)) {
                openid = wxService.openId(request, response);
                System.out.println("获取的openid:" + openid);
                if (StringUtils.isBlank(openid)) {
                    return null;
                }
            }
            QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
            wrapper.eq("openid", openid);
            WxUser wxUser = userService.getOne(wrapper);
            if (wxUser == null) {
                wxUser = new WxUser();
                wxUser.setOpenid(openid);
                userService.save(wxUser);
            }
            return wxUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/authorizedUrl")
    public String authorizedUrl(){
        String uri= Config.getBaseUrl() + "/user/getUserInfo";//授权后重定向地址
        System.out.println("重定向地址："+uri);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Config.getAppId()+"&redirect_uri="+uri+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return url;
    }

    @RequestMapping("/getUserInfo")
    public String getUserInfo(HttpServletRequest request,HttpServletResponse response){
        try {
            String code = request.getParameter("code");
            if(StringUtils.isNotEmpty(code)){
                String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Config.getAppId()+"&secret="+Config.getAppSecret()+"&code="+code+"&grant_type=authorization_code";
                String result = HttpClientUtil.doGet(url);
                String openid = WxUtils.find(result, "\"openid\":\"(.*?)\"");
                if(StringUtils.isNotEmpty(openid)){
                    String access_token = WxUtils.find(result, "\"access_token\":\"(.*?)\"");
                    url= "https://api.weixin.qq.com/sns/userinfo?access_token="+ access_token +"&openid="+ openid +"&lang=zh_CN";
                    result = HttpClientUtil.doGet(url);
                    JSONObject object = JSONObject.parseObject(result);
                    QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
                    wrapper.eq("openid", openid);
                    WxUser wxUser = userService.getOne(wrapper);
                    if (wxUser == null) {
                        wxUser = new WxUser();
                        wxUser.setOpenid(openid);
                    }
                    wxUser.setSex(object.getString("sex"));
                    wxUser.setNickname(object.getString("nickname"));
                    wxUser.setHeadimgurl(object.getString("headimgurl"));
                    userService.saveOrUpdate(wxUser);
                    url="http://localhost:8010/#/mine?openid="+openid+"&nickname="+ URLEncoder.encode(wxUser.getNickname(),"UTF-8")+"&headimgurl="+wxUser.getHeadimgurl();
                    response.sendRedirect(url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.lei.mywechat.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class Config {

    private static String AppId;
    private static String AppSecret;
    private static String token;
    private static String openid;
    private static String BaseUrl;
    private static String JD_APPID;

    @Value("${wechat.AppId}")
    public void setAppid(String AppId){
        this.AppId = AppId;
    }
    @Value("${wechat.AppSecret}")
    public void setAppSecret(String AppSecret){
        this.AppSecret = AppSecret;
    }
    @Value("${wechat.token}")
    public void setToken(String token){
        this.token = token;
    }
    @Value("${wechat.openid}")
    public void setOpenid(String openid){
        this.openid = openid;
    }
    @Value("${base_url}")
    public void setBaseUrl(String BaseUrl){
        this.BaseUrl = BaseUrl;
    }
    @Value("${JD_APPID}")
    public void setJdAppid(String JD_APPID){
        this.JD_APPID = JD_APPID;
    }

    public static String getAppId() {
        return AppId;
    }

    public static String getAppSecret() {
        return AppSecret;
    }

    public static String getToken() {
        return token;
    }

    public static String getOpenid() {
        return openid;
    }

    public static String getBaseUrl() {
        return BaseUrl;
    }

    public static String getJdAppid() {
        return JD_APPID;
    }
}

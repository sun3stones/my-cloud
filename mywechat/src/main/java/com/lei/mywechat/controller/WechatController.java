package com.lei.mywechat.controller;

import com.lei.mywechat.utils.CheckUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("wx")
public class WechatController {

    @RequestMapping(value = "signature",method=RequestMethod.GET)
    public String login(HttpServletRequest request){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if(CheckUtil.checkSignature(signature, timestamp, nonce)){
            return echostr;
        }else{
            return "";
        }
    }
}

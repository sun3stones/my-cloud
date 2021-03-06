/**
 * 
 */
package com.lei.mywechat.weixin;

import com.lei.mywechat.utils.Config;
import com.lei.mywechat.utils.HttpClientUtil;
import com.lei.mywechat.weixin.utils.WxUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
* @ClassName: WXService
* @Description: TODO(微信业务处理工具)
* @author sun lei
* @date 2018年1月6日 下午1:17:56
*
*/
@Component
public class WXService {

	@Autowired
	private RedisTemplate redisTemplate;
	

	public String openId(HttpServletRequest request , HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		if(StringUtils.isBlank(code)){//code为空则微信授权，如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE
			String uri= Config.getBaseUrl() +request.getRequestURI();//授权后重定向地址
			System.out.println("重定向回："+uri);
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Config.getAppId()+"&redirect_uri="+uri+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
			response.sendRedirect(url);
		}else{//code不为空则返回code
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Config.getAppId()+"&secret="+Config.getAppSecret()+"&code="+code+"&grant_type=authorization_code";
			String result = HttpClientUtil.doGet(url);
			String openid = WxUtils.find(result, "\"openid\":\"(.*?)\"");
			if(openid != null){
				return openid;
			}
		}	
		return null;
	}
	
	public void wxJsConfig(HttpServletRequest request){
		request.setAttribute("appId", Config.getAppId());
		String timestamp = Long.toString(System.currentTimeMillis()/1000);
		String nonceStr = WxUtils.CreateNoncestr();
		request.setAttribute("timestamp",timestamp);
		request.setAttribute("nonceStr",nonceStr);
		request.setAttribute("access_token", getToken());
		String url=request.getRequestURL().toString();
		url=request.getQueryString()==null?url:url+"?"+request.getQueryString().toString();
		String  signature= WxUtils.getSignature(jsapi_ticket(), timestamp, nonceStr, url);
		request.setAttribute("signature", signature);
		System.out.println("signature:"+signature);
	}
	
	/**
	 * 获取token
	 */
	public String getToken(){
		String token = "";
		if(redisTemplate.opsForValue().get("token") == null){
			token = WxUtils.newToken();
			redisTemplate.opsForValue().set("token",token,2,TimeUnit.HOURS);
		}
		return token;
	}
	
	public String jsapi_ticket(){
		return WxUtils.jsapi_ticket(getToken());
	}
}

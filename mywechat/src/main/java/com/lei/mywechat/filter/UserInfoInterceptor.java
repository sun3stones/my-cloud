/**
 * 
 */
package com.lei.mywechat.filter;

import com.lei.mywechat.service.IWxUserService;
import com.lei.mywechat.weixin.WXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @ClassName: UserInfoInterceptor
* @Description: TODO(微信用户信息拦截器)
* @author sun lei
* @date 2018年1月6日 下午12:18:07
*
*/
public class UserInfoInterceptor implements HandlerInterceptor{

	@Autowired
	private WXService wxService;
	@Autowired
	private IWxUserService wxUserService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		/*//跨域请求
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, Accept,token,openid");
		//ajax请求会请求两次，第一次请求options不要拦截
		if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
			return true;
		}
		System.out.println("拦截地址："+request.getRequestURL());
  		String openid = request.getHeader("openid");
		if(StringUtils.isEmpty(openid)){
			openid = wxService.openId(request, response);
			System.out.println("获取的openid:"+openid);
			if(StringUtils.isBlank(openid)){
				return false;
			}
			QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
			wrapper.eq("openid",openid);
			WxUser wxUser = wxUserService.getOne(wrapper);
			if(wxUser == null){
				wxUser = new WxUser();
				wxUser.setOpenid(openid);
				wxUserService.save(wxUser);
			}
			HttpSession session = request.getSession();
			session.setAttribute("openid",openid);
		}*/
		return true;
	}

	private boolean isWxBowser(HttpServletRequest requset) {
		String ua = requset.getHeader("user-agent").toLowerCase();  
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器  
			return true;
		}
		else{
			return false;
		}
	}

}

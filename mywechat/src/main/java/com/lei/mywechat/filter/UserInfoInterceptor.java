/**
 * 
 */
package com.lei.mywechat.filter;

import com.lei.mywechat.service.IWxUserService;
import com.lei.mywechat.weixin.WXService;
import org.apache.commons.lang.StringUtils;
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
		if(isAjax(request)){//ajax请求不拦截
			return true;
		}else if(!request.getMethod().toLowerCase().equals("get")){//除get请求外都不拦截
			System.out.println(request.getMethod().toLowerCase());
			return true;
		}else if(!isWxBowser(request)){//不是微信浏览器拦截
			request.getRequestDispatcher("/common/browser").forward(request, response);
			return false;
		}else {
			System.out.println("拦截地址："+request.getRequestURL());
			WxUserDto wxUser = (WxUserDto) request.getSession().getAttribute(WxConfig.SESSION_USER);
			if(wxUser == null || wxUser.getStatus()==1){//session为空或已认证，则通过openid去数据库获取信息			
				if(wxUser == null){//session为空
					System.out.println("session信息为空，获取openid-->");
					String openid = wxService.openId(request, response);//session获取不到则调微信接口
					System.out.println("获取的openid:"+openid);
					if(StringUtils.isBlank(openid)){
						return false;
					}
					wxUser = wxUserService.getWxUser(openid);//根据openid获取用户信息
					if(wxUser == null ){
						wxUser = new WxUserDto();
						wxUser.setOpenid(openid);
						wxUserService.insertWxUser(wxUser);
					}else{
						if(wxUser.getStatus()==1){
							request.getSession().setAttribute(WxConfig.SESSION_USER, wxUser);
							return true;
						}
					}
				}else{//有session并且已认证不拦截
					return true; 
				}
			}
			request.setAttribute("openid", wxUser.getOpenid());
			Integer count = wxUserService.certifiateCount(wxUser.getOpenid());
			request.setAttribute("count", count);
			request.getRequestDispatcher("/wxuser/register").forward(request, response);
			return false;
		}
	}

	private boolean isAjax(HttpServletRequest request){
	    return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
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

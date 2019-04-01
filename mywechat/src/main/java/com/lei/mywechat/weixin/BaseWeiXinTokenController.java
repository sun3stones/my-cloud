/**
 * 
 */
package com.lei.mywechat.weixin;

import lei.front.utils.HttpClientUtil;
import lei.front.utils.ParamUtils;
import lei.front.utils.redis.RedisService;
import lei.front.utils.wx.WxConfig;
import lei.front.weixin.utils.StringUtils;
import lei.front.weixin.utils.WxUtils;
import lei.front.weixin.utils.XmlUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
* @ClassName: BaseWeiXinTokenController
* @Description: TODO(微信交互)
* @author sun lei
* @date 2017年12月23日 下午8:21:55
*
*/
@Controller
@RequestMapping("wx")
public class BaseWeiXinTokenController {

	@Autowired
	private RedisService redis;
	
	/** 客服消息服务地址 */
	private static final String CUST_SERVER_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	
	/**京东万象图灵接口**/
	private static final String JD_TURING_URL = "https://way.jd.com/turing/turing?appkey=";
	
	/** 缺省返回内容 - 空字符串 */
	private static final String DEFAULT_RV = "";
	
	@RequestMapping("wxmessage")
	@ResponseBody
	public String wxMessage(HttpServletRequest request,HttpServletResponse response,String signature,String timestamp 
            ,String nonce,String echostr) throws Exception{
		// 验证信息来源
		if (valid(request)) {
			// 微信验证的随机字符串，不为空需直接返回
			if (null != echostr) {
				return echostr;
			}
			String xml = receiveData(request);// 接收数据
			if (null == xml) {
				return DEFAULT_RV;// 直接返回
			}
			Msg msg = XmlUtils.reader(xml, Msg.class);// 解析xml
			if (null == msg) {
				return DEFAULT_RV;
			}
			//receiveLog(msg);// 记录消息
			if (!valid(msg)) {
				return DEFAULT_RV;
			}
			switch (msg.getType()) {
				case event:
					return dealEvent(request,response,msg);// 处理事件
				case text:
					// 处理文本消息
					return dealText(request,msg);
				default:
					break;
//					return MsgUtils.undefined(msg.getFromUser());// 返回未定义消息
			}
		}
		return DEFAULT_RV;
	}
	
	/** 处理事件消息 
	 * @throws Exception */
	private String dealEvent(HttpServletRequest request,HttpServletResponse response,Msg msg) throws Exception {
		EventType event = msg.getEvent();
		if (null != event) {
			String to = msg.getFromUser();// 信息接收人
			String key = msg.getEventKey();// 事件KEY值
			switch (event) {
			case CLICK:// 菜单点击事件
				if("onlinecustomservice".equals(msg.getEventKey())){
				//	onlineCustomService(request, response);
				}
				break;
			case subscribe:// 关注事件
				subscribePush(request, response, msg);
				break;
			case unsubscribe:// 取消关注
			//	cleanRegisterAccount(to);
				break;
			default:
				;// 其它事件不进行处理
				break;
			}
		}
		return DEFAULT_RV;
	}
	
	private String subscribePush(HttpServletRequest request,HttpServletResponse response,Msg msg)throws Exception{
		System.out.println("****************进入关注事件********subscribePush********");
		String content = "[{\"title\":\"欢迎关注孙磊测试号\","
						+ "\"description\":\"点击进入认证页面，填写手机号待管理员认证！\","
						+ "\"url\":\""+WxConfig.WX_MY()+"/wxuser/index\","
						+ "\"picurl\":\""+WxConfig.ATTENDTIONPIC()+"\"}]";
		return pushTextContent(request, response, content);
	}
	
	/**
	 * 验证消息来源
	 */
	private boolean valid(HttpServletRequest request) {
		String signature = ParamUtils.getParameter(request, "signature");
		String timestamp = ParamUtils.getParameter(request, "timestamp");
		String nonce = ParamUtils.getParameter(request, "nonce");
		boolean b = false;
		if (null != signature && null != timestamp && null != nonce) {
			String[] arr = { WxConfig.TOKEN(), timestamp, nonce };
			Arrays.sort(arr);
			String sign = DigestUtils.shaHex(arr[0] + arr[1] + arr[2]);
			b = signature.equals(sign);
		}
		
		return b;
	}
	/**
	 * 验证消息完整性
	 * 
	 * @param msg
	 *            消息
	 * @return 完整true
	 */
	private boolean valid(Msg msg) {
		return null != msg.getFromUser() && null != msg.getType();
	}
	
	/** 处理文本消息 */
	private String dealText(HttpServletRequest request,Msg msg) {
		Map<String,String> map = new HashMap<>();
		map.put("ToUserName", msg.getFromUser());//来自谁回复谁
		map.put("FromUserName", WxConfig.OPENID());
		map.put("CreateTime", String.valueOf(System.currentTimeMillis()));
		map.put("MsgType", "text");
		
		//调用京东图灵聊天机器人
		String rs = HttpClientUtil.doGet(JD_TURING_URL+WxConfig.JD_APPID()+"&info="+msg.getContent()+"&userid="+msg.getFromUser());
		String content = WxUtils.find(rs, "\"text\":\"(.*?)\"")	;
		
		map.put("Content", content);
		System.out.println(XmlUtils.toXml(map));
		return XmlUtils.toXml(map);
	}
	
	
	/** 接收数据 */
	private String receiveData(HttpServletRequest request) {
		try {
			return IOUtils.toString(request.getInputStream(), Charset.forName("UTF-8"));
		} catch (IOException e) {
		}
		return null;
	}
	
	private String pushTextContent(HttpServletRequest request,HttpServletResponse response,String content) throws Exception{
		String openId = request.getParameter("openid");
		if(StringUtils.isBlank(content))return DEFAULT_RV;
		String accessToken = getToken();
		String rs = HttpClientUtil.doPostJson(CUST_SERVER_URL+accessToken,loadJsonForImgContent(openId, content));// 发送消息
		System.out.println("**************发送消息"+CUST_SERVER_URL+accessToken+"*****************");
		System.out.println("**************关注推送消息结果*****************rs="+rs);
		return  WxUtils.find(rs, "\"errmsg\":\"(.*?)\"");
	}
	private static String loadJsonForImgContent(String openid,String content){
		return "{\"touser\":\""+openid+"\",\"msgtype\":\"news\",\"news\":{\"articles\":"+content+"}}";
	}
	
	/**
	 * 获取token
	 */
	public String getToken(){
		String token = redis.get("token");
		if(token == null){
			token = WxUtils.newToken();
			redis.setAndExpir("token", token,7200);
		}	
		return token;	
	}
		
	
}

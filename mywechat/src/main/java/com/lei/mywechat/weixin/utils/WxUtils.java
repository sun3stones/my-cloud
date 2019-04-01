package com.lei.mywechat.weixin.utils;

import com.lei.mywechat.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.DigestException;
import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 微信支持工具类
 * 
 * <pre>
 * 非web依赖，web相关工具类请参考{@link WxWeb}
 * </pre>
 * 
 * @author HeHongxin
 * @date 2014-10-30
 * 
 */
public class WxUtils {
	
	private WxUtils() {
	}

	public static final String LOG_PREFIX = "【服务号】";

	
	/**
	 * 申请token
	 * 
	 * @return token
	 */
	public static String newToken() {
		//https://api.weixin.qq.com/sns/userinfo
		String source = HttpClientUtil.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WxConfig.APPID() + "&secret=" + WxConfig.SECRET());
		System.out.println("*********newToken***************source="+source);
		return find(source, "\"access_token\":\"(.*?)\"");
	}
	
	/**
	 * 获取微信sdk所需ticket
	 */
	public static String jsapi_ticket(String access_token){
		String rs = HttpClientUtil.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi");
		return find(rs, "\"ticket\":\"(.*?)\"");
	}
	
	/**
	 * 获取分享签名
	 * @param accessticket jsapi_ticket
	 * @param timespanstr生成签名的时间戳
	 * @param nonceStr生成签名的随机串
	 * @param url 生成签名的url
	 */
	public static String getSignature(String accessticket,String timespanstr,String nonceStr, String url){
		String str = "jsapi_ticket=" + accessticket + "&noncestr=" + nonceStr +"&timestamp=" + timespanstr + "&url=" + url;        
		return SHA1(str);
	}
	
	/**
	 * 查找符合规则的目标字符串
	 * 
	 * @param source
	 *            源
	 * @param regex
	 *            规则
	 * @return 找到的字符串
	 */
	public static String find(String source, String regex) {
		Pattern pat = Pattern.compile(regex, Pattern.DOTALL);
		Matcher mat = pat.matcher(source);
		if (mat.find()) {
			return mat.group(1);
		}
		return null;
	}
	

	/**
	 * 编码url地址
	 * 
	 * @param url
	 *            url地址
	 */
	public static String urlEncode(String url) {
		return urlEncode(url,null);
	}
	
	public static String urlEncode(String url,String parameter) {
		if (WxConfig.TEST()) {
			return url;
		} else {
			try {
				String urlString = WxConfig.WX_MY() + url;
				urlString = WxUtils.filterUrl(urlString);
				String eu = URLEncoder.encode(urlString, "UTF-8");
				
				String mUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxConfig.APPID() + "&redirect_uri=" + eu + "&response_type=code&scope=snsapi_base&state="+parameter+"#wechat_redirect";
				return mUrl;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * zh访问的url地址
	 *"//"转换为"/"
	 * @param url
	 */
	public static String filterUrl(String url){
		
		if(url.startsWith("https")){//https请求
			String start = "https://";
			String str = "";
			if(url.startsWith(start)){
				str = url.substring(start.length());
				do{
					str = str.replaceAll("//", "/");
				}while(str.indexOf("//")!=-1);
			}
			url = start + str;
			
		}else{								//http请求
			String start = "http://";
			String str = "";
			if(url.startsWith(start)){
				str = url.substring(start.length());
				do{
					str = str.replaceAll("//", "/");
				}while(str.indexOf("//")!=-1);
			}
			url = start + str;
			
		}
		return url;
	}
	
	/**
	 * 编码url地址,有授权页面
	 * 
	 * @param url
	 *            url地址
	 */
	public static String urlEncodeByUserInfo(String url) {
		return urlEncodeByUserInfo(url,null);
	}
	
	public static String urlEncodeByUserInfo(String url,String parameter) {
		if (WxConfig.TEST()) {
			return url;
		} else {
			try {
				String eu = URLEncoder.encode(url, "UTF-8");
				String mUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxConfig.APPID() + "&redirect_uri=" + eu + "&response_type=code&scope=snsapi_userinfo&state="+parameter+"#wechat_redirect";
				return mUrl;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param charset
	 *            编码charset
	 * @return 所代表远程资源的响应结果
	 */
	public static String get(String url, String param, Charset charset) {
		url = fmtUrl(url);
		if (null == url) {
			return null;
		}
		BufferedReader in = null;
		try {
			param = StringUtils.trim(param);
			if (null != param) {
				if (url.indexOf("?") > 0) {
					url += "&" + param;
				} else {
					url += "?" + param;
				}
			}
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			conn.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			if (null == charset) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
	/**
	 * 格式化url
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 符合http规则的url
	 */
	public static String fmtUrl(String url) {
		url = StringUtils.trim(url);
		if (null != url) {
			return url.replaceAll(" ", "%20");
		}
		return null;
	}
	
	public static String CreateNoncestr() {
		String chars = "sun3stones";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	

		/** 
	     * SHA1 安全加密算法 
	     * @param maps 参数key-value map集合 
	     * @return 
	     * @throws DigestException  
	     */  
		//sha1 字符串加密    
		public static String SHA1(String str){
		    if(str==null || str.length()==0){
		        return null;
		    }
		    char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		    try {
		        MessageDigest mdTemp=MessageDigest.getInstance("SHA1");
		        mdTemp.update(str.getBytes("UTF-8"));
		        byte[] md=mdTemp.digest();
		        int j=md.length;
		        char buf[]=new char[j*2];
		        int k=0;
		        for(int i=0;i<j;i++){
		            byte byte0=md[i];
		            buf[k++]=hexDigits[byte0>>>4 & 0xf];
		            buf[k++]=hexDigits[byte0 & 0xf];
		            }
		        return new String(buf);
		    } catch (Exception e) {
		        return null;
		    }
		}

}

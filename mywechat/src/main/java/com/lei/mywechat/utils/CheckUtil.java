package com.lei.mywechat.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.*;

/**
 * 
 * 类名称: CheckUtil
 * 类描述: 微信请求校验
 */
@Component
@RefreshScope
public class CheckUtil {

	private static String token;

	@Value("${wechat.token}")
	public void setToken(String token){
		this.token = token;
	}

	public static boolean checkSignature(String signature,String timestamp,String nonce){
		if(StringUtils.isEmpty(signature) ||StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)){
			return false;
		}
		String[] str = new String[]{token,timestamp,nonce};
		//排序
		Arrays.sort(str);
		//拼接字符串
		StringBuffer buffer = new StringBuffer();
		for(int i =0 ;i<str.length;i++){
			buffer.append(str[i]);
		}
		//进行sha1加密
		String temp = SHA1.encode(buffer.toString());
		//与微信提供的signature进行匹对
		return signature.equals(temp);
	}

	public final static class SHA1 {
		private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

		/**
		 * Takes the raw bytes from the digest and formats them correct.
		 *
		 * @param bytes the raw bytes from the digest.
		 * @return the formatted bytes.
		 */
		private static String getFormattedText(byte[] bytes) {
			int len = bytes.length;
			StringBuilder buf = new StringBuilder(len * 2);
			// 把密文转换成十六进制的字符串形式
			for (int j = 0; j < len; j++) {
				buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
				buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
			}
			return buf.toString();
		}

		public static String encode(String str) {
			if (str == null) {
				return null;
			}
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
				messageDigest.update(str.getBytes());
				return getFormattedText(messageDigest.digest());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 根据ASCII码排序
	 *
	 * @param set
	 *            Set集合
	 * @return List集合
	 */
	public static List<String> sort(Set<String> set) {
		List<String> keys = new ArrayList<String>(set);
		Collections.sort(keys, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);// 根据ASCII码排序KEY
			};
		});
		return keys;
	}
}

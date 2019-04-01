package com.lei.mywechat.weixin.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Json构造
 * 
 * @author HeHongxin
 * @date 2015-3-3
 * 
 */
public class JsonMaker {

	private StringBuilder sb = null;

	public JsonMaker() {
		sb = new StringBuilder();
	}

	/**
	 * 填充元素
	 * 
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 * @return this
	 */
	public JsonMaker append(String name, String value) {
		sb.append(",\"");
		sb.append(name);
		sb.append("\":\"");
		if (null != value) {
			sb.append(value.replaceAll("\"", "\\\\\""));
		}
		sb.append("\"");
		return this;
	}

	/**
	 * 填充元素
	 * 
	 * @param name
	 *            名称
	 * @param json
	 *            值
	 * @return this
	 */
	public JsonMaker appendJson(String name, CharSequence json) {
		sb.append(",\"").append(name).append("\":");
		sb.append(json);
		return this;
	}

	/**
	 * 填充元素
	 * 
	 * @param name
	 *            名称
	 * @param jm
	 *            JsonMaker对象
	 * @return this
	 */
	public JsonMaker appendJson(String name, JsonMaker jm) {
		sb.append(",\"").append(name).append("\":");
		sb.append(jm.txt());
		return this;
	}

	/**
	 * 获取json明文
	 */
	public String txt() {
		if (sb.length() > 0) {
			return "{" + sb.substring(1) + "}";
		}
		return "";
	}

	/**
	 * 输出
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	public void out(HttpServletResponse response, String msg){
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(msg);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

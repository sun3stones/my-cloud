package com.lei.mywechat.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XML处理工具
 * 
 * @author HeHongxin
 * @date 2014-12-25
 * 
 */
public class XmlUtils {

	private XmlUtils() {
	}

	/**
	 * byte[]转为MAP
	 * 
	 * @param bytes
	 *            xml内容byte数组
	 * @param charset
	 *            字符编码
	 * @return Map<String,String>
	 */
	public static Map<String, String> toMap(byte[] bytes, String charset) {
		try {
			SAXReader reader = new SAXReader(false);
			InputSource source = new InputSource(new ByteArrayInputStream(bytes));
			source.setEncoding(charset);
			Document doc = reader.read(source);
			Map<String, String> map = XmlUtils.toMap(doc.getRootElement());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Element转为MAP
	 * 
	 * @param element
	 *            Element
	 * @return Map<String,String>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(Element element) {
		Map<String, String> rest = new HashMap<String, String>();
		List<Element> els = element.elements();
		for (Element el : els) {
			rest.put(el.getName().toLowerCase(), el.getTextTrim());
		}
		return rest;
	}

	/**
	 * Map转换为xml
	 * 
	 * @param map
	 *            map数据
	 * @return xml
	 */
	public static String toXml(Map<String, String> map) {
		StringBuilder buf = new StringBuilder();
		List<String> keys = CheckUtil.sort(map.keySet());
		buf.append("<xml>");
		for (String key : keys) {
			buf.append("<").append(key).append(">");
			buf.append("<![CDATA[").append(map.get(key)).append("]]>");
			buf.append("</").append(key).append(">");
		}
		buf.append("</xml>");
		return buf.toString();
	}

	/**
	 * 将对象转换为xml
	 * 
	 * @param type
	 *            对象
	 * @return xml字符
	 */
	public static <T> String xml(T type) {
		try {
			Marshaller m = JAXBContext.newInstance(type.getClass()).createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter writer = new StringWriter();
			m.marshal(type, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取XML并转换为指定对象
	 * 
	 * @param xml
	 *            xml源
	 * @param type
	 *            对象
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T reader(String xml, Class<T> type) {
		try {
			return (T) JAXBContext.newInstance(type).createUnmarshaller().unmarshal(new StringReader(xml));
		} catch (Exception e) {
		}
		return null;
	}

}

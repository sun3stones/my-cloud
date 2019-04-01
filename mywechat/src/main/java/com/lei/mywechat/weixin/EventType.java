package com.lei.mywechat.weixin;

/**
 * 事件类型
 * 
 * @author HeHongxin
 * @date 2014-12-26
 * 
 */
public enum EventType {

	/** 订阅 */
	subscribe,
	/** 取消订阅 */
	unsubscribe,
	/** 扫描二维码 */
	SCAN,
	/** 上报地理位置 */
	LOCATION,
	/** 点击自定义菜单事件 */
	CLICK,
	/** 点击自定义菜单链接 */
	VIEW;

	/**
	 * 转换事件类型
	 * 
	 * @param type
	 *            类型
	 * @return 事件类型
	 */
	public static EventType typeOf(String type) {
		if (null != type) {
			try {
				return EventType.valueOf(type);
			} catch (IllegalArgumentException e) {
			}
		}
		return null;
	}
}

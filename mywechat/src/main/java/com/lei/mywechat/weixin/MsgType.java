package com.lei.mywechat.weixin;


/**
 * 消息类型
 * 
 * @author HeHongxin
 * @date 2014-12-26
 * 
 */
public enum MsgType {

	/** 文本消息 */
	text,
	/** 图片消息 */
	image,
	/** 语音消息 */
	voice,
	/** 视频消息 */
	video,
	/** 地理位置 */
	location,
	/** 链接消息 */
	link,
	/** 事件推送 */
	event;

	/**
	 * 转换消息类型
	 * 
	 * @param type
	 *            类型
	 * @return 消息类型
	 */
	public static MsgType typeOf(String type) {
		if (null != type) {
			try {
				return MsgType.valueOf(type);
			} catch (IllegalArgumentException e) {
			}
		}
		return null;
	}

}

package com.lei.mywechat.weixin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 微信消息
 * 
 * @author HeHongxin
 * @date 2014-12-26
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class Msg {

	/* ----- 非xml内容 ------------------------------------------------- */
	/** 记录ID */
	private String id;
	/** 记录时间 */
	private Date dt;

	/* ----- 基础内容 ------------------------------------------------- */
	/** 开发者微信ID */
	@XmlElement(name = "ToUserName")
	private String toUser;
	/** 发送方微信ID */
	@XmlElement(name = "FromUserName")
	private String fromUser;
	/** 消息时间(秒) */
	@XmlElement(name = "CreateTime")
	private long time;
	/** 消息类型 */
	@XmlElement(name = "MsgType")
	private MsgType type;

	/* ----- 消息ID ------------------------------------------------- */

	/** 消息ID */
	@XmlElement(name = "MsgId")
	private String msgId;
	/** 媒体ID */
	@XmlElement(name = "MediaId")
	private String mediaId;

	/* ----- 文本消息 ------------------------------------------------- */
	/** 文本内容 */
	@XmlElement(name = "Content")
	private String content;

	/* ----- 图片消息 ------------------------------------------------- */
	/** 图片地址 */
	@XmlElement(name = "PicUrl")
	private String picUrl;

	/* ----- 语音消息 ------------------------------------------------- */
	/** 语音格式 */
	@XmlElement(name = "Format")
	private String format;

	/* ----- 视频消息 ------------------------------------------------- */
	/** 缩略视频ID */
	@XmlElement(name = "ThumbMediaId")
	private String thumbId;

	/* ----- 地理位置 ------------------------------------------------- */
	/** 地理位置纬度 */
	@XmlElement(name = "Location_X")
	private String ltX;
	/** 地理位置经度 */
	@XmlElement(name = "Location_Y")
	private String ltY;
	/** 地图缩放大小 */
	@XmlElement(name = "Scale")
	private String ltScale;
	/** 地理位置信息 */
	@XmlElement(name = "Label")
	private String ltLabel;

	/* ----- 链接消息 ------------------------------------------------- */
	/** 消息标题 */
	@XmlElement(name = "Title")
	private String lnkTitle;
	/** 消息描述 */
	@XmlElement(name = "Description")
	private String lnkDesc;
	/** 消息链接 */
	@XmlElement(name = "Url")
	private String lnkUrl;

	/* ----- 事件推送 ------------------------------------------------- */
	/** 事件类型 */
	@XmlElement(name = "Event")
	private EventType event;
	/** 事件KEY值 */
	@XmlElement(name = "EventKey")
	private String eventKey;
	/** 二维码的ticket */
	@XmlElement(name = "Ticket")
	private String eventTicket;
	/** 地理位置纬度 */
	@XmlElement(name = "Latitude")
	private String eventLt;
	/** 地理位置经度 */
	@XmlElement(name = "Longitude")
	private String eventLg;
	/** 地理位置精度 */
	@XmlElement(name = "Precision")
	private String eventPs;

	/* ------------------------------------------------------ */

	/** 输出所有值 */
	@Override
	public String toString() {
		return "Msg [toUser=" + toUser + ", fromUser=" + fromUser + ", time=" + time + ", type=" + type + ", msgId=" + msgId + ", mediaId=" + mediaId + ", content=" + content + ", picUrl=" + picUrl
				+ ", format=" + format + ", thumbId=" + thumbId + ", ltX=" + ltX + ", ltY=" + ltY + ", ltScale=" + ltScale + ", ltLabel=" + ltLabel + ", lnkTitle=" + lnkTitle + ", lnkDesc=" + lnkDesc
				+ ", lnkUrl=" + lnkUrl + ", event=" + event + ", eventKey=" + eventKey + ", eventTicket=" + eventTicket + ", eventLt=" + eventLt + ", eventLg=" + eventLg + ", eventPs=" + eventPs
				+ "]";
	}

	/* ------------------------------------------------------ */

	/**
	 * 设置消息类型
	 */
	public void setType(String type) {
		this.type = MsgType.typeOf(type);
	}

	/**
	 * 设置事件
	 */
	public void setEvent(String event) {
		this.event = EventType.typeOf(event);
	}

	/**
	 * 获取开发者微信ID
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * 设置开发者微信ID
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	/**
	 * 获取发送方微信ID
	 */
	public String getFromUser() {
		return fromUser;
	}

	/**
	 * 设置发送方微信ID
	 */
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * 获取消息时间(秒)
	 */
	public long getTime() {
		return time;
	}

	/**
	 * 设置消息时间(秒)
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * 获取消息ID
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * 设置消息ID
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * 获取媒体ID
	 */
	public String getMediaId() {
		return mediaId;
	}

	/**
	 * 设置媒体ID
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * 获取文本内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置文本内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取图片地址
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * 设置图片地址
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * 获取语音格式
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * 设置语音格式
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 获取缩略视频ID
	 */
	public String getThumbId() {
		return thumbId;
	}

	/**
	 * 设置缩略视频ID
	 */
	public void setThumbId(String thumbId) {
		this.thumbId = thumbId;
	}

	/**
	 * 获取地理位置纬度
	 */
	public String getLtX() {
		return ltX;
	}

	/**
	 * 设置地理位置纬度
	 */
	public void setLtX(String ltX) {
		this.ltX = ltX;
	}

	/**
	 * 获取地理位置经度
	 */
	public String getLtY() {
		return ltY;
	}

	/**
	 * 设置地理位置经度
	 */
	public void setLtY(String ltY) {
		this.ltY = ltY;
	}

	/**
	 * 获取地图缩放大小
	 */
	public String getLtScale() {
		return ltScale;
	}

	/**
	 * 设置地图缩放大小
	 */
	public void setLtScale(String ltScale) {
		this.ltScale = ltScale;
	}

	/**
	 * 获取地理位置信息
	 */
	public String getLtLabel() {
		return ltLabel;
	}

	/**
	 * 设置地理位置信息
	 */
	public void setLtLabel(String ltLabel) {
		this.ltLabel = ltLabel;
	}

	/**
	 * 获取消息标题
	 */
	public String getLnkTitle() {
		return lnkTitle;
	}

	/**
	 * 设置消息标题
	 */
	public void setLnkTitle(String lnkTitle) {
		this.lnkTitle = lnkTitle;
	}

	/**
	 * 获取消息描述
	 */
	public String getLnkDesc() {
		return lnkDesc;
	}

	/**
	 * 设置消息描述
	 */
	public void setLnkDesc(String lnkDesc) {
		this.lnkDesc = lnkDesc;
	}

	/**
	 * 获取消息链接
	 */
	public String getLnkUrl() {
		return lnkUrl;
	}

	/**
	 * 设置消息链接
	 */
	public void setLnkUrl(String lnkUrl) {
		this.lnkUrl = lnkUrl;
	}

	/**
	 * 获取事件KEY值
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * 设置事件KEY值
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	/**
	 * 获取二维码的ticket
	 */
	public String getEventTicket() {
		return eventTicket;
	}

	/**
	 * 设置二维码的ticket
	 */
	public void setEventTicket(String eventTicket) {
		this.eventTicket = eventTicket;
	}

	/**
	 * 获取地理位置纬度
	 */
	public String getEventLt() {
		return eventLt;
	}

	/**
	 * 设置地理位置纬度
	 */
	public void setEventLt(String eventLt) {
		this.eventLt = eventLt;
	}

	/**
	 * 获取地理位置经度
	 */
	public String getEventLg() {
		return eventLg;
	}

	/**
	 * 设置地理位置经度
	 */
	public void setEventLg(String eventLg) {
		this.eventLg = eventLg;
	}

	/**
	 * 获取地理位置精度
	 */
	public String getEventPs() {
		return eventPs;
	}

	/**
	 * 设置地理位置精度
	 */
	public void setEventPs(String eventPs) {
		this.eventPs = eventPs;
	}

	/**
	 * 获取消息类型
	 */
	public MsgType getType() {
		return type;
	}

	/**
	 * 获取事件类型
	 */
	public EventType getEvent() {
		return event;
	}

	/**
	 * 获取记录ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置记录ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取记录时间
	 */
	public Date getDt() {
		return dt;
	}

	/**
	 * 设置记录时间
	 */
	public void setDt(Date dt) {
		this.dt = dt;
	}

}

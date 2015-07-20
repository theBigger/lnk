package org.mos.lnk.message;

import org.mos.lnk.packet.OutMessage;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月3日 上午11:50:14
 */
public class Message {

	private long id;
	
	/** 发起报文的用户的唯一ID */
	private long mid;
	
	/** 第三方系统账号ID */
	private String party_id;

	/** 用户昵称 */
	private String nick;

	/** 用户头像 */
	private String avatar;
	
	/** 消息到达方的用户的唯一ID */
	private long tid;

	/** 消息内容体 */
	private String body;

	/** 消息发送时间 */
	private long gmt_created;
	
	public OutMessage toOutMessage() {
		OutMessage o = new OutMessage().ok();
		o.setAvatar(avatar);
		o.setBody(body);
		o.setGmt_created(gmt_created);
		o.setMid(mid);
		o.setNick(nick);
		o.setParty_id(party_id);
		o.setTid(tid);
		return o;
	}
	
	public static Message newInstance(OutMessage o) {
		Message message = new Message();
		message.setAvatar(o.getAvatar());
		message.setBody(o.getBody());
		message.setGmt_created(o.getGmt_created());
		message.setMid(o.getMid());
		message.setNick(o.getNick());
		message.setParty_id(o.getParty_id());
		message.setTid(o.getTid());
		return message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getParty_id() {
		return party_id;
	}

	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getGmt_created() {
		return gmt_created;
	}

	public void setGmt_created(long gmt_created) {
		this.gmt_created = gmt_created;
	}
}
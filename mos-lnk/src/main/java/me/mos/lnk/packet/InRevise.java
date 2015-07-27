package me.mos.lnk.packet;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户信息修订报文定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 下午11:21:27
 */
@XStreamAlias(Alias.REVISE_NAME)
public class InRevise extends AbstractInPacket {
	
	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;

	/** 第三方系统账号ID */
	@XStreamAlias("party-id")
	@XStreamAsAttribute
	private String party_id;

	/** 用户昵称 */
	@XStreamAlias("nick")
	private String nick;

	/** 用户密码 */
	@XStreamAlias("passwd")
	private String passwd;

	/** 用户头像 */
	@XStreamAlias("avatar")
	private String avatar;

	/** 微信号 */
	@XStreamAlias("weixin")
	private String weixin;

	/** QQ号 */
	@XStreamAlias("qq")
	private String qq;

	/** EMail */
	@XStreamAlias("email")
	private String email;

	/** 电话号码 */
	@XStreamAlias("telephone")
	private String telephone;

	/** 固话 */
	@XStreamAlias("phone")
	private String phone;

	public InRevise() {
		super(Type.Revise.type);
	}
	
	@Override
	public OutRevise toOutPacket() {
		OutRevise outRevise = new OutRevise();
		outRevise.setAvatar(avatar);
		outRevise.setEmail(email);
		outRevise.setNick(nick);
		outRevise.setParty_id(party_id);
		outRevise.setPhone(phone);
		outRevise.setQq(qq);
		outRevise.setTelephone(telephone);
		outRevise.setWeixin(weixin);
		outRevise.setMid(mid);
		outRevise.setGmt_created(new Date().getTime());
		return outRevise.ok();
	}

	@Override
	public Type getPacketType() {
		return Type.Revise;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	@Override
	public long getMid() {
		return mid;
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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
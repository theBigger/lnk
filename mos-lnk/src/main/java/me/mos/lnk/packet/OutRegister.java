package me.mos.lnk.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户注册报文定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 下午11:21:27
 */
@XStreamAlias(Alias.REGISTER_NAME)
public class OutRegister extends AbstractOutPacket {

	private static final byte ERR = 2;

	private static final byte OK = 1;

	/** 第三方系统账号ID */
	@XStreamAlias("party-id")
	@XStreamAsAttribute
	private String party_id;

	/** 用户昵称 */
	@XStreamAlias("nick")
	private String nick;

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
	
	/** 用户注册时间 */
	@XStreamAlias("gmt-created")
	private long gmt_created;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;
	
	public OutRegister() {
		super(Type.Register.type);
	}

	public OutRegister ok() {
		status = OK;
		return this;
	}
	
	public OutRegister err() {
		status = ERR;
		return this;
	}

	@Override
	public Type type() {
		return Type.Register;
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

	public long getGmt_created() {
		return gmt_created;
	}

	public void setGmt_created(long gmt_created) {
		this.gmt_created = gmt_created;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}
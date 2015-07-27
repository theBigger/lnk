package me.mos.lnk.user;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import me.mos.lnk.packet.InRegister;
import me.mos.lnk.packet.InRevise;

/**
 * 通讯用户定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午9:43:28
 */
public class User {
	
	public static final String ONLINE = "online";

	public static final String OFFLINE = "offline";

	/** 用户唯一ID */
	private long mid;

	/** 第三方系统账号ID */
	private String party_id;

	/** 用户昵称 */
	private String nick;

	/** 用户密码 */
	private String passwd;

	/** 用户头像 */
	private String avatar;

	/** 微信号 */
	private String weixin;

	/** QQ号 */
	private String qq;

	/** EMail */
	private String email;

	/** 电话号码 */
	private String telephone;

	/** 固话 */
	private String phone;

	/** 地址 */
	private String address;

	/** IP地址 */
	private String ip;

	/** 经度 */
	private double lng;

	/** 纬度 */
	private double lat;

	/** 用户当前状态 */
	private String status;

	/** 用户扩展信息 */
	private String extend;

	/** 用户注册时间 */
	private Date gmt_created;

	/** 用户最后修改信息时间 */
	private Date gmt_modified;
	
	public static User newInstance(InRegister inRegister) {
		Date date = new Date();
		User user = new User();
		user.setAvatar(inRegister.getAvatar());
		user.setEmail(inRegister.getEmail());
		user.setGmt_created(date);
		user.setGmt_modified(date);
		user.setNick(inRegister.getNick());
		user.setParty_id(inRegister.getParty_id());
		user.setPasswd(inRegister.getPasswd());
		user.setPhone(inRegister.getPhone());
		user.setQq(inRegister.getQq());
		user.setTelephone(inRegister.getTelephone());
		user.setWeixin(inRegister.getWeixin());
		user.offline();
		return user;
	}
	
	public User merge(InRevise inRevise) {
		setGmt_modified(new Date());
		if(StringUtils.isNotBlank(inRevise.getAvatar())) setAvatar(inRevise.getAvatar());
		if(StringUtils.isNotBlank(inRevise.getEmail())) setEmail(inRevise.getEmail());
		if(StringUtils.isNotBlank(inRevise.getNick())) setNick(inRevise.getNick());
		if(StringUtils.isNotBlank(inRevise.getParty_id())) setParty_id(inRevise.getParty_id());
		if(StringUtils.isNotBlank(inRevise.getPasswd())) setPasswd(inRevise.getPasswd());
		if(StringUtils.isNotBlank(inRevise.getPhone())) setPhone(inRevise.getPhone());
		if(StringUtils.isNotBlank(inRevise.getQq())) setQq(inRevise.getQq());
		if(StringUtils.isNotBlank(inRevise.getTelephone())) setTelephone(inRevise.getTelephone());
		if(StringUtils.isNotBlank(inRevise.getWeixin())) setWeixin(inRevise.getWeixin());
		return this;
	}
	
	public boolean isOnline() {
		return StringUtils.equals(ONLINE, status);
	}
	
	public boolean isOffline() {
		return StringUtils.equals(OFFLINE, status);
	}
	
	public User online() {
		status = ONLINE;
		return this;
	}
	
	public User offline() {
		status = OFFLINE;
		return this;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Date getGmt_created() {
		return gmt_created;
	}

	public void setGmt_created(Date gmt_created) {
		this.gmt_created = gmt_created;
	}

	public Date getGmt_modified() {
		return gmt_modified;
	}

	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((extend == null) ? 0 : extend.hashCode());
		result = prime * result + ((gmt_created == null) ? 0 : gmt_created.hashCode());
		result = prime * result + ((gmt_modified == null) ? 0 : gmt_modified.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lng);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (mid ^ (mid >>> 32));
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((party_id == null) ? 0 : party_id.hashCode());
		result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((qq == null) ? 0 : qq.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((weixin == null) ? 0 : weixin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (extend == null) {
			if (other.extend != null)
				return false;
		} else if (!extend.equals(other.extend))
			return false;
		if (gmt_created == null) {
			if (other.gmt_created != null)
				return false;
		} else if (!gmt_created.equals(other.gmt_created))
			return false;
		if (gmt_modified == null) {
			if (other.gmt_modified != null)
				return false;
		} else if (!gmt_modified.equals(other.gmt_modified))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (Double.doubleToLongBits(lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(lng) != Double.doubleToLongBits(other.lng))
			return false;
		if (mid != other.mid)
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (party_id == null) {
			if (other.party_id != null)
				return false;
		} else if (!party_id.equals(other.party_id))
			return false;
		if (passwd == null) {
			if (other.passwd != null)
				return false;
		} else if (!passwd.equals(other.passwd))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (qq == null) {
			if (other.qq != null)
				return false;
		} else if (!qq.equals(other.qq))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (weixin == null) {
			if (other.weixin != null)
				return false;
		} else if (!weixin.equals(other.weixin))
			return false;
		return true;
	}
}
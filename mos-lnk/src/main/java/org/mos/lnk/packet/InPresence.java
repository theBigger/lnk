package org.mos.lnk.packet;

import org.mos.lnk.serializer.SerializerUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户上线消息报文定义.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午1:11:50
 */
@XStreamAlias(Alias.PRESENCE_NAME)
public class InPresence extends AbstractInPacket {
	
	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;
	
	/** 用户密码 */
	@XStreamAlias("passwd")
	@XStreamAsAttribute
	private String passwd;

	public InPresence() {
		super(Type.Presence.type);
	}

	public static InPresence fromPacket(String packet) {
		return SerializerUtils.currentSerializer().deserialize(InPresence.class, packet);
	}

	@Override
	public OutPresence toOutPacket() {
		OutPresence outPresence = new OutPresence();
		outPresence.setMid(mid);
		return outPresence.ok();
	}

	@Override
	public Type getPacketType() {
		return Type.Presence;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
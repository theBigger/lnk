package me.mos.lnk.packet;

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
public class OutPresence extends AbstractOutPacket {
	
	private static final byte ERR = 2;
	private static final byte OK = 1;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;

	public OutPresence() {
		super(Type.Presence.type);
	}

	@Override
	public Type type() {
		return Type.Presence;
	}
	
	public OutPresence ok() {
		status = OK;
		return this;
	}
	
	public OutPresence err() {
		status = ERR;
		return this;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}
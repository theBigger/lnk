package me.mos.lnk.packet;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 用户在线状态心跳检测消息报文.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午1:06:02
 */
@XStreamAlias(Alias.IQ_NAME)
public class OutIQ extends AbstractOutPacket {
	
	private static final byte OFFLINE = 2;
	private static final byte ONLINE = 1;
	
	@XStreamAlias("status")
	@XStreamAsAttribute
	private byte status;

	public OutIQ() {
		super(Type.IQ.type);
	}

	@Override
	public Type getPacketType() {
		return Type.IQ;
	}
	
	public OutIQ online() {
		status = ONLINE;
		return this;
	}
	
	public OutIQ offline() {
		status = OFFLINE;
		return this;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}
package org.mos.lnk.packet;

import org.mos.lnk.serializer.Serializer;
import org.mos.lnk.serializer.SerializerProvider;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 下行通讯消息报文基类.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午12:52:46
 */
public abstract class AbstractOutPacket implements OutPacket {

	/** 发起报文的用户的唯一ID */
	@XStreamAlias("mid")
	@XStreamAsAttribute
	private long mid;
	
	/** 消息类型 */
	@XStreamAlias("type")
	@XStreamAsAttribute
	private byte type;

	public AbstractOutPacket(byte type) {
		super();
		this.type = type;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}
	
	@Override
	public String toPacket() {
		return serializer().serialize(this);
	}

	@Override
	public Serializer serializer() {
		return SerializerProvider.currentSerializer();
	}

	/**
	 * 将消息格式化为可发送的格式
	 */
	@Override
	public String toString() {
		return toPacket();
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
}
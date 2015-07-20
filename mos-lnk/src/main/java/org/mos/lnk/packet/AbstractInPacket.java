package org.mos.lnk.packet;

import org.mos.lnk.serializer.Serializer;
import org.mos.lnk.serializer.SerializerUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 上行通讯消息报文基类.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 上午12:52:46
 */
public abstract class AbstractInPacket implements InPacket {
	
	/** 消息类型 */
	@XStreamAlias("type")
	@XStreamAsAttribute
	private byte type;

	public AbstractInPacket(byte type) {
		super();
		this.type = type;
	}

	/**
	 * 将消息格式化为可发送的格式
	 */
	@Override
	public String toPacket() {
		return serializer().serialize(this);
	}

	@Override
	public Serializer serializer() {
		return SerializerUtils.currentSerializer();
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
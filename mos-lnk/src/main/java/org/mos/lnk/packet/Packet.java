package org.mos.lnk.packet;

import org.mos.lnk.serializer.Serializer;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:54:04
 */
public interface Packet {
	
	/**
	 * 将消息格式化为可发送的格式
	 */
	String toPacket();
	
	/**
	 * 消息发出者地址
	 */
	long getMid();
	
	/**
	 * 消息类型
	 */
	byte getType();
	
	/**
	 * 消息类型
	 */
	Type getPacketType();
	
	/**
	 * 序列化器
	 */
	Serializer serializer();
}
package me.mos.lnk.packet;

/**
 * 下行通讯消息报文.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月31日 下午11:28:53
 */
public interface OutPacket extends Packet {
	
	/**
	 * 获取操作状态
	 */
	byte getStatus();
}
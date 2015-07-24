package org.mos.lnk.handler;

import org.mos.lnk.channel.Channel;
import org.mos.lnk.packet.InPacket;
import org.mos.lnk.packet.OutPacket;

/**
 * 通讯数据报文处理.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:15:56
 */
public interface PacketHandler<I extends InPacket> {
	OutPacket process(Channel channel, I packet) throws Throwable;
}
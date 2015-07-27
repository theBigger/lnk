package me.mos.lnk.handler;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.channel.Channels;
import me.mos.lnk.packet.InIQ;
import me.mos.lnk.packet.OutIQ;
import me.mos.lnk.packet.OutPacket;

/**
 * IQ消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:18:45
 */
public class IQHandler extends AbstractPacketHandler<InIQ> {

	@Override
	public OutPacket process(Channel<?> channel, InIQ packet) throws Throwable {
		OutIQ outIQ = packet.toOutPacket();
		return Channels.isOnline(packet.getMid()) ? outIQ.online() : outIQ.offline();
	}
}
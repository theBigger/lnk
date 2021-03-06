package me.mos.lnk.handler;

import org.apache.commons.lang3.StringUtils;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.channel.Channels;
import me.mos.lnk.packet.InRegister;
import me.mos.lnk.packet.OutPacket;
import me.mos.lnk.packet.OutRegister;
import me.mos.lnk.user.User;

/**
 * Register消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:22:47
 */
public class RegisterHandler extends AbstractPacketHandler<InRegister> {

	@Override
	public OutPacket process(Channel<?> channel, InRegister packet) throws Throwable {
		OutRegister outRegister = packet.toOutPacket();
		User user = User.newInstance(packet);
		user.setAddress(StringUtils.EMPTY);
		user.setExtend(StringUtils.EMPTY);
		user.setIp(channel.toString());
		long mid = 0;
		try {
			mid = userProvider.save(user);
		} catch (Throwable e) {
		    log.error("Handler Register Error.\n" + " InRegister : " + packet, e);
			return outRegister.err();
		}
		user.online().setMid(mid);
		channel.setChannelId(mid);
		Channels.online(channel);
		outRegister.setMid(mid);
		return outRegister;
	}
}
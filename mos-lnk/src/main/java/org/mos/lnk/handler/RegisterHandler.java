package org.mos.lnk.handler;

import org.apache.commons.lang3.StringUtils;
import org.mos.lnk.channel.Channel;
import org.mos.lnk.channel.Channels;
import org.mos.lnk.packet.InRegister;
import org.mos.lnk.packet.OutPacket;
import org.mos.lnk.packet.OutRegister;
import org.mos.lnk.user.User;

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
	public OutPacket process(Channel channel, InRegister packet) throws Throwable {
		OutRegister outRegister = packet.toOutPacket();
		User user = User.newInstance(packet);
		user.setAddress(StringUtils.EMPTY);
		user.setExtend(StringUtils.EMPTY);
		user.setIp(channel.toString());
		long mid = 0;
		try {
			mid = userProvider.save(user);
		} catch (Throwable e) {
			return outRegister.err();
		}
		user.online().setMid(mid);
		channel.setChannelId(mid);
		Channels.online(channel);
		outRegister.setMid(mid);
		return outRegister;
	}
}
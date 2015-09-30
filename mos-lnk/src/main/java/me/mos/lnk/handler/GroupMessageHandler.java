package me.mos.lnk.handler;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.channel.Channels;
import me.mos.lnk.message.Message;
import me.mos.lnk.packet.Acknowledge;
import me.mos.lnk.packet.InGroupMessage;
import me.mos.lnk.packet.OutGroupMessage;
import me.mos.lnk.packet.OutPacket;
import me.mos.lnk.user.User;

/**
 * Message消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:20:27
 */
public class GroupMessageHandler extends AbstractPacketHandler<InGroupMessage> {

	@Override
	public OutPacket process(Channel<?> channel, InGroupMessage packet) throws Throwable {
		User user = userProvider.query(packet.getMid());
		if (user == null) {
			return new Acknowledge(packet.getMid()).meNotExist();
		}
		OutGroupMessage outMessage = packet.toOutPacket();
		
		outMessage.setAvatar(user.getAvatar());
		outMessage.setNick(user.getNick());
		outMessage.setParty_id(user.getParty_id());
		
		/*Channel<?> peerChannel = Channels.channel(String.valueOf(packet.getTid()));
		if (peerChannel == null || !peerChannel.isConnect()) {
			messageProvider.save(Message.newInstance(outMessage));
			return new Acknowledge(packet.getMid()).peerOffline();
		}
		peerChannel.deliver(outMessage);*/
		return new Acknowledge(packet.getMid()).ok();
	}
}
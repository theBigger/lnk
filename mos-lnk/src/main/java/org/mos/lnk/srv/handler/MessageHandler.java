package org.mos.lnk.srv.handler;

import org.mos.lnk.channel.Channel;
import org.mos.lnk.channel.Channels;
import org.mos.lnk.message.Message;
import org.mos.lnk.packet.Acknowledge;
import org.mos.lnk.packet.InMessage;
import org.mos.lnk.packet.OutMessage;
import org.mos.lnk.packet.OutPacket;
import org.mos.lnk.user.User;

/**
 * Message消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:20:27
 */
public class MessageHandler extends AbstractPacketHandler<InMessage> {

	@Override
	public OutPacket process(Channel<?> channel, InMessage packet) throws Throwable {
		User user = userProvider.query(packet.getMid());
		if (user == null) {
			// 非法用户发来的 丢弃消息
			return new Acknowledge(packet.getMid()).meNotExist();
		}
		OutMessage outMessage = packet.toOutPacket();
		outMessage.setAvatar(user.getAvatar());
		outMessage.setNick(user.getNick());
		outMessage.setParty_id(user.getParty_id());
		Channel<?> peerChannel = Channels.channel(String.valueOf(packet.getTid()));
		if (peerChannel == null || !peerChannel.isConnect()) {
			// 对方不存在离线消息保存并回执
			messageProvider.save(Message.newInstance(outMessage));
			return new Acknowledge(packet.getMid()).peerOffline();
		}
		peerChannel.deliver(outMessage);// 发送给对方
		return new Acknowledge(packet.getMid()).ok();
	}
}
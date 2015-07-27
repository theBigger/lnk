package me.mos.lnk.processor;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.handler.IQHandler;
import me.mos.lnk.handler.MessageHandler;
import me.mos.lnk.handler.PresenceHandler;
import me.mos.lnk.handler.RegisterHandler;
import me.mos.lnk.handler.ReviseHandler;
import me.mos.lnk.packet.InIQ;
import me.mos.lnk.packet.InMessage;
import me.mos.lnk.packet.InPacket;
import me.mos.lnk.packet.InPresence;
import me.mos.lnk.packet.InRegister;
import me.mos.lnk.packet.InRevise;
import me.mos.lnk.packet.OutPacket;

/**
 * Lnk服务通道消息业务处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:44:18
 */
public class DefaultServerProcessor implements ServerProcessor {

	private IQHandler iqHandler;

	private MessageHandler messageHandler;

	private PresenceHandler presenceHandler;

	private RegisterHandler registerHandler;

	private ReviseHandler reviseHandler;

	public DefaultServerProcessor() {
		super();
		iqHandler = new IQHandler();
		messageHandler = new MessageHandler();
		presenceHandler = new PresenceHandler();
		registerHandler = new RegisterHandler();
		reviseHandler = new ReviseHandler();
	}

	@Override
	public <I extends InPacket> OutPacket process(Channel<?> channel, I packet) throws Throwable {
		OutPacket outPacket = null;
		switch (packet.getPacketType()) {
		case IQ:
			outPacket = iqHandler.process(channel, (InIQ) packet);
			break;
		case Message:
			outPacket = messageHandler.process(channel, (InMessage) packet);
			break;
		case Presence:
			outPacket = presenceHandler.process(channel, (InPresence) packet);
			break;
		case Register:
			outPacket = registerHandler.process(channel, (InRegister) packet);
			break;
		case Revise:
			outPacket = reviseHandler.process(channel, (InRevise) packet);
			break;
		default:
			break;
		}
		return outPacket;
	}
}
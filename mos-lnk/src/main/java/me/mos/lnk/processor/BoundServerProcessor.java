package me.mos.lnk.processor;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.handler.GroupHandler;
import me.mos.lnk.handler.GroupMessageHandler;
import me.mos.lnk.handler.IQHandler;
import me.mos.lnk.handler.JoinGroupHandler;
import me.mos.lnk.handler.MessageHandler;
import me.mos.lnk.handler.PresenceHandler;
import me.mos.lnk.handler.PushIdHandler;
import me.mos.lnk.handler.RegisterHandler;
import me.mos.lnk.handler.ReviseHandler;
import me.mos.lnk.packet.InGroup;
import me.mos.lnk.packet.InGroupMessage;
import me.mos.lnk.packet.InIQ;
import me.mos.lnk.packet.InJoinGroup;
import me.mos.lnk.packet.InMessage;
import me.mos.lnk.packet.InPacket;
import me.mos.lnk.packet.InPresence;
import me.mos.lnk.packet.InPushId;
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
public class BoundServerProcessor implements ServerProcessor {

	private IQHandler iqHandler;

	private MessageHandler messageHandler;

	private PresenceHandler presenceHandler;

	private RegisterHandler registerHandler;

	private ReviseHandler reviseHandler;
	
	private PushIdHandler pushIdHandler;
    
    private GroupHandler groupHandler;
    
    private GroupMessageHandler groupMessageHandler;
    
    private JoinGroupHandler joinGroupHandler;

	public BoundServerProcessor() {
		super();
		iqHandler = new IQHandler();
		messageHandler = new MessageHandler();
		presenceHandler = new PresenceHandler();
		registerHandler = new RegisterHandler();
		reviseHandler = new ReviseHandler();
		pushIdHandler = new PushIdHandler();
		groupHandler = new GroupHandler();
		groupMessageHandler = new GroupMessageHandler();
		joinGroupHandler = new JoinGroupHandler();
	}

	@Override
	public <I extends InPacket> OutPacket process(Channel<?> channel, I packet) throws Throwable {
		OutPacket outPacket = null;
		switch (packet.type()) {
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
        case PushId:
            outPacket = pushIdHandler.process(channel, (InPushId) packet);
            break;
        case Group:
            outPacket = groupHandler.process(channel, (InGroup) packet);
            break;
        case GroupMessage:
            outPacket = groupMessageHandler.process(channel, (InGroupMessage) packet);
            break;
        case JoinGroup:
            outPacket = joinGroupHandler.process(channel, (InJoinGroup) packet);
            break;
		default:
			break;
		}
		return outPacket;
	}
}
package me.mos.lnk.handler;

import java.util.List;

import org.springframework.util.CollectionUtils;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.channel.Channels;
import me.mos.lnk.groups.message.GroupMessage;
import me.mos.lnk.message.Message;
import me.mos.lnk.packet.InPresence;
import me.mos.lnk.packet.OutGroupMessage;
import me.mos.lnk.packet.OutMessage;
import me.mos.lnk.packet.OutPacket;
import me.mos.lnk.packet.OutPresence;

/**
 * Presence出席消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:22:00
 */
public class PresenceHandler extends AbstractPacketHandler<InPresence> {

	@Override
	public OutPacket process(Channel<?> channel, InPresence packet) throws Throwable {
		OutPresence outPresence = packet.toOutPacket();
		try {
			Channels.online(channel);
			// 我出席了之后 拉取我的离线消息
			List<Message> offlineMessageList = messageProvider.queryMessageList(packet.getMid());
			if (!CollectionUtils.isEmpty(offlineMessageList)) {
			    for (Message message : offlineMessageList) {
	                if (message == null) {
	                    continue;
	                }
	                OutMessage outMessage = message.toOutMessage();
	                channel.deliver(outMessage.ok());
	                messageProvider.delete(message.getId());
	            }
			}
			List<GroupMessage> offlineGroupMessageList = groupMessageProvider.queryUserGroupMessageList(packet.getMid());
			if (!CollectionUtils.isEmpty(offlineGroupMessageList)) {
                for (GroupMessage message : offlineGroupMessageList) {
                    if (message == null) {
                        continue;
                    }
                    OutGroupMessage outMessage = new OutGroupMessage();
                    outMessage.setBody(message.getBody());
                    outMessage.setGmt_created(message.getGmt_created());
                    outMessage.setMid(message.getMid());
                    outMessage.setGroupId(message.getGroup_id());
                    outMessage.setAvatar(message.getAvatar());
                    outMessage.setNick(message.getNick());
                    outMessage.setParty_id(message.getParty_id());
                    channel.deliver(outMessage.ok());
                    groupMessageProvider.delete(message.getId());
                }
            }
		} catch (Exception e) {
			log.error("Presence Processing Error.\n" + " InPresence : " + packet, e);
			return outPresence.err();
		}
		return outPresence;
	}
}
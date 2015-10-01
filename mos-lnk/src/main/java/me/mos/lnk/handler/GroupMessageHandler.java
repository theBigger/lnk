package me.mos.lnk.handler;

import java.util.List;

import org.springframework.util.CollectionUtils;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.channel.Channels;
import me.mos.lnk.groups.message.GroupMessage;
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
        List<Long> mids = joinGroupProvider.getUserInGroup(packet.getGroupId());
        if (!CollectionUtils.isEmpty(mids)) {
            for (Long mid : mids) {
                Channel<?> peerChannel = Channels.channel(String.valueOf(mid));
                if (peerChannel == null || !peerChannel.isConnect()) {
                    GroupMessage message = new GroupMessage();
                    message.setAvatar(outMessage.getAvatar());
                    message.setBody(outMessage.getBody());
                    message.setGmt_created(outMessage.getGmt_created());
                    message.setMid(outMessage.getMid());
                    message.setNick(outMessage.getNick());
                    message.setParty_id(outMessage.getParty_id());
                    message.setTid(mid);
                    message.setGroup_id(packet.getGroupId());
                    groupMessageProvider.save(message);
                    continue;
                }
                peerChannel.deliver(outMessage);
            }
        }
        return new Acknowledge(packet.getMid()).ok();
    }
}

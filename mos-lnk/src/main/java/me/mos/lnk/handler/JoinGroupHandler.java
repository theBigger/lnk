package me.mos.lnk.handler;

import java.util.Date;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.groups.join.JoinGroup;
import me.mos.lnk.packet.InJoinGroup;
import me.mos.lnk.packet.OutJoinGroup;
import me.mos.lnk.packet.OutPacket;

/**
 * 加入群聊消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:20:27
 */
public class JoinGroupHandler extends AbstractPacketHandler<InJoinGroup> {

	@Override
	public OutPacket process(Channel<?> channel, InJoinGroup packet) throws Throwable {
		OutJoinGroup outMessage = packet.toOutPacket();
		try {
		    switch (packet.getAction()) {
                case 0:
                    joinGroupProvider.remove(packet.getGroup_id(), packet.getMid());
                    return outMessage.ok();
                case 1:
                    JoinGroup joinGroup = new JoinGroup();
                    joinGroup.setGmt_created(new Date());
                    joinGroup.setGroup_id(packet.getGroup_id());
                    joinGroup.setMid(packet.getMid());
                    joinGroupProvider.save(joinGroup);
                    return outMessage.ok();
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("Join Group[" + packet.getGroup_id() + "] Error.\n" + " InJoinGroup : " + packet, e);
        }
		return outMessage.err();
	}
}
package me.mos.lnk.handler;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.groups.Group;
import me.mos.lnk.packet.InGroup;
import me.mos.lnk.packet.OutGroup;
import me.mos.lnk.packet.OutPacket;

/**
 * 聊天组处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:18:45
 */
public class GroupHandler extends AbstractPacketHandler<InGroup> {

	@Override
	public OutPacket process(Channel<?> channel, InGroup packet) throws Throwable {
	    OutGroup outGroup = packet.toOutPacket();
	    long groupId = 0L;
        try {
            Group group = new Group();
            group.setName(packet.getName());
            group.setOwner_mid(packet.getMid());
            group.setTags(packet.getTags());
            groupId = groupProvider.save(group);
            outGroup.setGroupId(groupId);
        } catch (Exception e) {
            log.error("Start Group Error.\n" + " InGroup : " + packet, e);
            return outGroup.err();
        }
		return groupId > 0L ? outGroup.ok() : outGroup.err();
	}
}
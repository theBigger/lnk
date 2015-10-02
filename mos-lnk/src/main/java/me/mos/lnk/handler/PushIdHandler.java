package me.mos.lnk.handler;

import me.mos.lnk.channel.Channel;
import me.mos.lnk.packet.InPushId;
import me.mos.lnk.packet.OutPacket;
import me.mos.lnk.packet.OutPushId;

/**
 * 推送ID消息处理器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:18:45
 */
public class PushIdHandler extends AbstractPacketHandler<InPushId> {

	@Override
	public OutPacket process(Channel<?> channel, InPushId packet) throws Throwable {
	    OutPushId outPushId = packet.toOutPacket();
	    int ret = 0;
        try {
            ret = userProvider.uploadPushId(packet.getMid(), packet.getPush_id());
        } catch (Exception e) {
            log.error("Upload Push ID Error.\n" + " InPushId : " + packet, e);
            return outPushId.err();
        }
		return ret > 0 ? outPushId.ok() : outPushId.err();
	}
}
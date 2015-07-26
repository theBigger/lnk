package org.mos.lnk.server.nio;

import org.apache.commons.lang3.StringUtils;
import org.mos.lnk.channel.Channels;
import org.mos.lnk.packet.InPacket;
import org.mos.lnk.packet.OutPacket;
import org.mos.lnk.parser.PacketParser;
import org.mos.lnk.processor.ServerProcessor;
import org.mos.lnk.server.Handler;
import org.mos.lnk.server.PacketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lnk服务通道事件处理句柄.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 上午12:08:50
 */
final class ServerIoHandler implements Runnable, Handler, PacketProtocol {

	private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);

	private final BoundChannel channel;

	private final ServerProcessor processor;

	private final PacketParser parser;

	public ServerIoHandler(BoundChannel channel, ServerProcessor processor, PacketParser parser) {
		super();
		this.channel = channel;
		this.processor = processor;
		this.parser = parser;
	}

	@Override
	public void run() {
		String packet = StringUtils.EMPTY;
		try {
			if (!channel.isConnect()) {
				channel.close();
				Channels.offline(channel);
				return;
			}
			packet = channel.received();
			if (StringUtils.isBlank(packet)) {
				return;
			}
			log.error("Original Incoming Packet : {}", packet);
			InPacket inPacket = parser.parse(packet);
			if (inPacket == null) {
				return;
			}
			channel.setChannelId(inPacket.getMid());
			OutPacket outPacket = processor.process(channel, inPacket);
			if (outPacket == null) {
				return;
			}
			channel.deliver(outPacket);
		} catch (Throwable e) {
			log.error("Nio ServerHandler Error.");
		}
	}
}
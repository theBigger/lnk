package org.mos.lnk.srv.ws;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.mos.lnk.channel.Channels;
import org.mos.lnk.channel.WebSocketChannel;
import org.mos.lnk.packet.InPacket;
import org.mos.lnk.packet.OutPacket;
import org.mos.lnk.parser.JsonPacketParser;
import org.mos.lnk.parser.PacketParser;
import org.mos.lnk.srv.process.DefaultServerProcessor;
import org.mos.lnk.srv.process.ServerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午8:49:26
 */
@ServerEndpoint(value = "/lnk", configurator = ServerEndpointConfigurator.class)
public final class ServerIoHandler {

	private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);

	private static final String IO_CHANNEL = "IO-CHANNEL";

	private final ServerProcessor processor;

	private final PacketParser parser;

	public ServerIoHandler() {
		super();
		processor = new DefaultServerProcessor();
		parser = new JsonPacketParser();
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		WebSocketChannel channel = Channels.newChannel(session);
		session.getUserProperties().put(IO_CHANNEL, channel);
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		WebSocketChannel channel = (WebSocketChannel) session.getUserProperties().get(IO_CHANNEL);
		try {
			InPacket inPacket = parser.parse(message);
			channel.setChannelId(inPacket.getMid());
			OutPacket outPacket = processor.process(channel, inPacket);
			if (outPacket == null) {
				return StringUtils.EMPTY;
			}
			channel.deliver(outPacket);
		} catch (Throwable e) {
			log.error("ServerIoHandler MessageReceived Error.", e);
		}
		return StringUtils.EMPTY;
	}
	
	@OnError
	public void onError(Session session, Throwable t) {
		WebSocketChannel channel = (WebSocketChannel) session.getUserProperties().get(IO_CHANNEL);
		log.error("ServerIoHandler: Channel Error.\n" + channel, t);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		WebSocketChannel channel = (WebSocketChannel) session.getUserProperties().get(IO_CHANNEL);
		log.error("ServerIoHandler: Closing channel due to session Closed: " + channel);
		channel.close();
	}
}
package me.mos.lnk.server.websocket.jetty;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.mos.lnk.parser.JsonPacketParser;
import me.mos.lnk.parser.PacketParser;
import me.mos.lnk.processor.DefaultServerProcessor;
import me.mos.lnk.processor.ServerProcessor;
import me.mos.lnk.server.Handler;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月11日 下午5:54:59
 */
public class ServerIoHandler implements WebSocketListener, Handler {

	private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);
	
	private static final String IO_CHANNEL = "IO-CHANNEL";

	private final ServerProcessor processor;

	private final PacketParser parser;
	
	ServerIoHandler() {
		super();
		processor = new DefaultServerProcessor();
		parser = new JsonPacketParser();
	}
	
	@Override
	public void onWebSocketConnect(Session session) {
		BoundChannel channel = new BoundChannel(session);
	}

	@Override
	public void onWebSocketText(String message) {
		
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		
	}

	@Override
	public void onWebSocketError(Throwable cause) {
		
	}

	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		
	}
}
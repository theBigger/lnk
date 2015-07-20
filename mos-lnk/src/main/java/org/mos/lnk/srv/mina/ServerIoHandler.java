package org.mos.lnk.srv.mina;

import java.io.IOException;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.mos.lnk.channel.Channels;
import org.mos.lnk.channel.IoSessionChannel;
import org.mos.lnk.packet.InPacket;
import org.mos.lnk.packet.OutPacket;
import org.mos.lnk.srv.process.ServerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lnk服务通道事件处理句柄.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月10日 下午4:12:34
 */
final class ServerIoHandler implements IoHandler {

	private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);

	private static final String NIO_CHANNEL = "NIO-CHANNEL";

	private final ServerProcessor processor;

	ServerIoHandler(ServerProcessor processor) {
		super();
		this.processor = processor;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		IoSessionChannel channel = Channels.newChannel(session);
		session.setAttribute(NIO_CHANNEL, channel);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		IoSessionChannel channel = (IoSessionChannel) session.getAttribute(NIO_CHANNEL);
		try {
			InPacket inPacket = (InPacket) message;
			channel.setChannelId(inPacket.getMid());
			OutPacket outPacket = processor.process(channel, inPacket);
			if (outPacket == null) {
				return;
			}
			channel.deliver(outPacket);
		} catch (Throwable e) {
			log.error("ServerIoHandler MessageReceived Error.", e);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		IoSessionChannel channel = (IoSessionChannel) session.getAttribute(NIO_CHANNEL);
		log.error("ServerIoHandler: Closing channel due to session Closed: " + channel);
		channel.close();
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		if (session.getIdleCount(status) > 1) {
			final IoSessionChannel channel = (IoSessionChannel) session.getAttribute(NIO_CHANNEL);
			log.error("ServerIoHandler: Closing channel that has been idle: " + channel);
			channel.close();
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			log.error("ServerIoHandler reports IOException for session: " + session, cause);
		} else if (cause instanceof ProtocolDecoderException) {
			log.error("Closing channel due to exception: " + session, cause);
			final IoSessionChannel channel = (IoSessionChannel) session.getAttribute(NIO_CHANNEL);
			channel.close();
		} else {
			log.error("ServerIoHandler reports unexpected exception for session: " + session, cause);
		}
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		final IoSessionChannel channel = (IoSessionChannel) session.getAttribute(NIO_CHANNEL);
		log.error("ServerIoHandler: Closing channel due to input Closed: " + channel);
		channel.close();
	}
}
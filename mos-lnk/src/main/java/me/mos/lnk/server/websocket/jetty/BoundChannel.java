package me.mos.lnk.server.websocket.jetty;

import java.net.InetSocketAddress;

import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.jsr356.JsrSession;

import me.mos.lnk.channel.AbstractChannel;
import me.mos.lnk.packet.Packet;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月11日 下午5:32:07
 */
final class BoundChannel extends AbstractChannel<JsrSession> {
	
	private final JsrSession session;

	BoundChannel(JsrSession session) {
		super();
		this.session = session;
	}

	@Override
	public JsrSession getChannel() {
		return session;
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return session.getRemoteAddress();
	}

	@Override
	public String received() {
		throw new UnsupportedOperationException("'org.eclipse.jetty.websocket.jsr356.JsrSession' Unsupported Received Message!!!");
	}

	@Override
	public void deliver(Packet packet) {
		session.getRemote().sendStringByFuture(packet.toPacket());
	}

	@Override
	public boolean isConnect() {
		return session.isOpen();
	}

	@Override
	protected void channelInactive() {
		try {
			session.disconnect();
			session.close(new CloseStatus(StatusCode.NORMAL, "离线"));
		} catch (Throwable e) {
			log.error(toString() + " Offline Error.", e);
		}
	}
}
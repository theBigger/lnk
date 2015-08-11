package me.mos.lnk.server.websocket.jetty;

import java.net.InetSocketAddress;

import org.eclipse.jetty.websocket.api.Session;

import me.mos.lnk.channel.AbstractChannel;
import me.mos.lnk.packet.Packet;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月11日 下午5:32:07
 */
public class BoundChannel extends AbstractChannel<Session> {
	
	private final Session session;

	BoundChannel(Session session) {
		super();
		this.session = session;
	}

	@Override
	public Session getChannel() {
		return session;
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return session.getRemoteAddress();
	}

	@Override
	public String received() {
		throw new UnsupportedOperationException("'Session' Unsupported Received Message!!!");
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
	protected void _close() {
		try {
			session.disconnect();
			session.close();
		} catch (Throwable e) {
			log.error(toString() + " Offline Error.", e);
		}
	}
}
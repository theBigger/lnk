package me.mos.lnk.server.websocket.glassfish;

import java.net.InetSocketAddress;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.Session;

import me.mos.lnk.channel.AbstractChannel;
import me.mos.lnk.packet.Packet;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午8:32:41
 */
final class BoundChannel extends AbstractChannel<Session> {

	private final Session session;

	BoundChannel(Session session) {
		super();
		this.session = session;
	}

	@Override
	public String received() {
		throw new UnsupportedOperationException("'javax.websocket.Session' Unsupported Received Message!!!");
	}

	@Override
	public Session getChannel() {
		return session;
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return null;
	}

	@Override
	public void deliver(Packet packet) {
		try {
			session.getAsyncRemote().sendText(packet.toPacket());
		} catch (Throwable e) {
			log.error(toString() + " Deliver Packet Error.", e);
		}
	}

	@Override
	public boolean isConnect() {
		return session.isOpen();
	}

	@Override
	protected void _close() {
		try {
			session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "离线"));
		} catch (Throwable e) {
			log.error(toString() + " Offline Error.", e);
		}
	}
}
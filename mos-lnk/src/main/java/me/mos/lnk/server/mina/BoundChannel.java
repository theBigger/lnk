package me.mos.lnk.server.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.session.IoSession;

import me.mos.lnk.channel.AbstractChannel;
import me.mos.lnk.packet.Packet;

/**
 * 基于mina的连接通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月14日 下午12:39:39
 */
final class BoundChannel extends AbstractChannel<IoSession> {

	private IoSession session;

	private boolean closed;

	BoundChannel(IoSession session) {
		super();
		this.session = session;
		this.closed = false;
	}

	@Override
	public String received() {
		throw new UnsupportedOperationException("'org.apache.mina.core.session.IoSession' Unsupported Received Message!!!");
	}

	@Override
	public void deliver(Packet packet) {
		session.write(packet);
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return (InetSocketAddress) session.getRemoteAddress();
	}

	@Override
	public IoSession getChannel() {
		return session;
	}

	@Override
	protected void _close() {
		session.close(false);
		closed = true;
	}

	@Override
	public boolean isConnect() {
		return session.isConnected() && !closed;
	}
}
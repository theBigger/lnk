package me.mos.lnk.server.websocket.netty;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import me.mos.lnk.channel.AbstractChannel;
import me.mos.lnk.packet.Packet;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月12日 下午4:06:10
 */
final class BoundChannel extends AbstractChannel<Channel> {
	
	private final Channel channel;

	BoundChannel(Channel channel) {
		super();
		this.channel = channel;
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return (InetSocketAddress) channel.remoteAddress();
	}

	@Override
	public String received() {
		throw new UnsupportedOperationException("'io.netty.channel.Channel' Unsupported Received Message!!!");
	}

	@Override
	public void deliver(Packet packet) {
		channel.writeAndFlush(packet.toPacket());
	}

	@Override
	public boolean isConnect() {
		return channel.isOpen() && channel.isActive();
	}

	@Override
	protected void _close() {
		channel.close();
	}
}
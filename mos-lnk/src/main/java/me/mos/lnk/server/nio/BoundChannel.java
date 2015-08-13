package me.mos.lnk.server.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import me.mos.lnk.channel.AbstractChannel;
import me.mos.lnk.channel.Channels;
import me.mos.lnk.packet.Packet;
import me.mos.lnk.server.Version;
import me.mos.lnk.utils.ByteUtil;

/**
 * 客户端通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月15日 下午10:02:23
 */
final class BoundChannel extends AbstractChannel<SelectionKey> {

	private final Charset charset;

	private SelectionKey key;

	private SocketChannel channel;

	BoundChannel(SelectionKey key, Charset charset) {
		super();
		this.key = key;
		this.channel = (SocketChannel) key.channel();
		this.charset = charset;
	}

	@Override
	public SelectionKey getChannel() {
		return key;
	}

	@Override
	public String received() {
		ByteBuffer buf = ByteBuffer.allocate(READ_BYTE_BUF);
		try {
			while (channel.read(buf) > 0) {
				buf.flip();
				// buf.get(dst, offset, length);
			}
		} catch (Throwable e) {
			log.error("ServerHandler read Message Error.", e);
		} finally {
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
		return null;
	}

	@Override
	public void deliver(Packet packet) {
		try {
			String packet0 = packet.toPacket();
			byte[] packetBytes = packet0.getBytes(charset);
			int packetLength = packetBytes.length;
			byte[] packetLengthBytes = ByteUtil.parseBytes(packetLength, PACKET_BYTE_LENGTH);
			int parseLen = ByteUtil.toInt(packetLengthBytes);
			if (parseLen != packetLength) {
				log.error("由于数据包太长头信息已经截断【{},{}】, 不支持发送过长的消息.", packetLength, parseLen);
				return;
			}
			byte[] headBytes = new byte[HEAD_BYTE_LENGTH];
			System.arraycopy(packetLengthBytes, 0, headBytes, 0, packetLengthBytes.length);
			headBytes[VERSION_POSITION - 1] = Version.VERSION_1_0_0.getId();
			ByteBuffer buf = ByteBuffer.allocate(HEAD_BYTE_LENGTH + packetLength);
			buf.clear();
			buf.put(headBytes);
			buf.put(packetBytes);
			buf.flip();
			while (buf.hasRemaining()) {
				channel.write(buf);
			}
		} catch (Throwable ex) {
			log.error("Channel Write Packet Error -> " + packet.toPacket(), ex);
			try {
				if (!isConnect()) {
					Channels.offline(this);
				}
			} catch (Throwable e) {
			}
		} finally {
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		return (InetSocketAddress) channel.socket().getRemoteSocketAddress();
	}

	@Override
	public boolean isConnect() {
		if (channel == null) {
			return false;
		}
		try {
			channel.socket().sendUrgentData(0xFF);
			return true;
		} catch (Throwable e) {
		}
		return false;
	}

	@Override
	protected void channelInactive() {
		if (channel != null) {
			try {
				channel.close();
			} catch (Throwable e) {
				log.error("Channel Named " + toString() + " close Error.", e);
			}
			channel = null;
		}
	}
}
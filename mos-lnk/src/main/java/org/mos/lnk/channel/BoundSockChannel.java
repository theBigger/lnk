package org.mos.lnk.channel;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

import org.mos.lnk.packet.Packet;
import org.mos.lnk.server.Version;
import org.mos.lnk.utils.ByteUtil;

/**
 * 客户端通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午5:51:34
 */
final class BoundSockChannel extends AbstractChannel implements SockChannel {

	private final Charset charset;

	private Socket channel;

	private InputStream in;

	private OutputStream out;

	public BoundSockChannel(Socket channel, Charset charset) {
		super();
		this.channel = channel;
		this.charset = charset;
		try {
			in = channel.getInputStream();
			out = channel.getOutputStream();
		} catch (Throwable e) {
			log.error(toString() + " Bound Channel Error.", e);
		}
	}

	@Override
	public Socket getChannel() {
		return channel;
	}

	@Override
	public InetSocketAddress getPeerAddress() {
		if (!isConnect()) {
			return null;
		}
		return (InetSocketAddress) channel.getRemoteSocketAddress();
	}

	@Override
	public String read() {
		try {
			byte[] headBytes = new byte[HEAD_BYTE_LENGTH];
			in.mark(HEAD_BYTE_LENGTH);
			if (in.read(headBytes) != HEAD_BYTE_LENGTH) {
				in.reset();
				return null;
			}
			int length = ByteUtil.toInt(ByteUtil.getBytes(headBytes, 0, PACKET_BYTE_LENGTH));
			byte[] packetBytes = new byte[length];
			in.read(packetBytes);
			byte version = headBytes[VERSION_POSITION - 1];
			log.error("消息版本号[{}].", Version.parse(version));
			return new String(packetBytes, charset);
		} catch (Throwable ingore) {
			try {
				if (!isConnect()) {
					Channels.offline(this);
				}
			} catch (Throwable e) {
			}
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
			out.write(headBytes);
			out.write(packetBytes);
			out.flush();
		} catch (Throwable ex) {
			log.error("Channel Write Packet Error -> " + packet.toPacket(), ex);
			try {
				if (!isConnect()) {
					Channels.offline(this);
				}
			} catch (Throwable e) {
			}
		}
	}

	@Override
	public boolean isConnect() {
		if (channel == null) {
			return false;
		}
		try {
			channel.sendUrgentData(0xFF);
			return true;
		} catch (Throwable e) {
		}
		return false;
	}

	@Override
	protected void _close() {
		if (in != null) {
			try {
				in.close();
			} catch (Throwable e) {
				log.error("Channel Reader " + toString() + " close Error.", e);
			}
			in = null;
		}
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (Throwable e) {
				log.error("Channel Writer " + toString() + " close Error.", e);
			}
			out = null;
		}
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
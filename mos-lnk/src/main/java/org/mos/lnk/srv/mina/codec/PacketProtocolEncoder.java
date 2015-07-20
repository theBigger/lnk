package org.mos.lnk.srv.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.mos.lnk.packet.OutPacket;
import org.mos.lnk.srv.PacketProtocol;
import org.mos.lnk.srv.Version;
import org.mos.lnk.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 报文消息编码器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月11日 下午5:45:30
 */
final class PacketProtocolEncoder implements ProtocolEncoder, PacketProtocol {

	private static final Logger log = LoggerFactory.getLogger(PacketProtocolEncoder.class);

	final Charset charset;

	PacketProtocolEncoder(Charset charset) {
		super();
		this.charset = charset;
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		OutPacket outPacket = (OutPacket) message;
		String packet = outPacket.toPacket();
		log.error("Outcoming Packet : {}", packet);
		byte[] packetBytes = packet.getBytes(charset);
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
		IoBuffer buf = IoBuffer.allocate(packetLength + headBytes.length).setAutoExpand(true);
		buf.put(headBytes);
		buf.put(packetBytes);
		buf.flip();
		out.write(buf);
	}

	@Override
	public void dispose(IoSession session) throws Exception {

	}
}
package me.mos.lnk.server.netty.codec;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.mos.lnk.packet.OutPacket;
import me.mos.lnk.server.PacketProtocol;
import me.mos.lnk.server.Version;
import me.mos.lnk.utils.ByteUtil;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月13日 下午4:24:55
 */
public class PacketProtocolEncoder extends MessageToByteEncoder<OutPacket> implements PacketProtocol {

	private static final Logger log = LoggerFactory.getLogger(PacketProtocolEncoder.class);

	final Charset charset;
	
	public PacketProtocolEncoder(Charset charset) {
		super();
		this.charset = charset;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, OutPacket outPacket, ByteBuf out) throws Exception {
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
		out.writeBytes(headBytes);
		out.writeBytes(packetBytes);
	}
}
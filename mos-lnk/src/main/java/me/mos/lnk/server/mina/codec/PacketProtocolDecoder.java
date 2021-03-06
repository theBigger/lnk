package me.mos.lnk.server.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.mos.lnk.packet.InPacket;
import me.mos.lnk.parser.PacketParser;
import me.mos.lnk.server.PacketProtocol;
import me.mos.lnk.server.Version;
import me.mos.lnk.utils.ByteUtil;

/**
 * 累计报文编码器, 读取完整的报文信息.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月11日 下午5:42:42
 */
final class PacketProtocolDecoder extends CumulativeProtocolDecoder implements PacketProtocol {

	private static final Logger log = LoggerFactory.getLogger(PacketProtocolDecoder.class);

	private final Charset charset;

	private final PacketParser parser;

	PacketProtocolDecoder(Charset charset, PacketParser parser) {
		super();
		this.charset = charset;
		this.parser = parser;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() < HEAD_BYTE_LENGTH) {
			return false;
		}
		if (in.remaining() > 0) {
			byte[] head = new byte[HEAD_BYTE_LENGTH];
			in.mark();
			in.get(head);
			int length = ByteUtil.toInt(ByteUtil.getBytes(head, 0, PACKET_BYTE_LENGTH));
			if (length != in.remaining()) {
				in.reset();
				return false;
			}
			byte[] packetBytes = new byte[length];
			in.get(packetBytes);
			byte version = head[VERSION_POSITION - 1];
			log.error("消息版本号[{}].", Version.parse(version));
			String packetString = new String(packetBytes, charset);
			log.error("Incoming Packet : {}", packetString);
			try {
				InPacket inPacket = parser.parse(packetString);
				out.write(inPacket);
			} catch (Throwable e) {
				log.error("Incoming Packet Parse Error【" + packetString + "】.", e);
				return false;
			}
			if (in.remaining() > 0) {
				return true;
			}
		}
		return false;
	}
}
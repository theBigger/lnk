package me.mos.lnk.server.netty.codec;

import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.mos.lnk.packet.InPacket;
import me.mos.lnk.parser.PacketParser;
import me.mos.lnk.server.PacketProtocol;
import me.mos.lnk.server.Version;
import me.mos.lnk.utils.ByteUtil;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月13日 下午4:22:17
 */
public class PacketProtocolDecoder extends ByteToMessageDecoder implements PacketProtocol {
	
	private static final Logger log = LoggerFactory.getLogger(PacketProtocolDecoder.class);

	private final Charset charset;

	private final PacketParser parser;

	public PacketProtocolDecoder(Charset charset, PacketParser parser) {
		super();
		this.charset = charset;
		this.parser = parser;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < HEAD_BYTE_LENGTH) {
			return;
		}
		if (in.readableBytes() > 0) {
			byte[] head = new byte[HEAD_BYTE_LENGTH];
			in.markReaderIndex();
			in.readBytes(head);
			int length = ByteUtil.toInt(ByteUtil.getBytes(head, 0, PACKET_BYTE_LENGTH));
			if (length != in.readableBytes()) {
				in.resetReaderIndex();
				return;
			}
			byte[] packetBytes = new byte[length];
			in.readBytes(packetBytes);
			byte version = head[VERSION_POSITION - 1];
			log.error("消息版本号[{}].", Version.parse(version));
			String packetString = new String(packetBytes, charset);
			log.error("Incoming Packet : {}", packetString);
			try {
				InPacket inPacket = parser.parse(packetString);
				out.add(inPacket);
			} catch (Throwable e) {
				log.error("Incoming Packet Parse Error【" + packetString + "】.", e);
			}
		}
	}
}
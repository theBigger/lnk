package org.mos.lnk.server.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.mos.lnk.parser.PacketParser;

/**
 * 报文消息编码解码器工厂.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月11日 下午5:48:56
 */
final class PacketProtocolCodecFactory implements ProtocolCodecFactory {
	
	private final ProtocolEncoder encoder;
	private final ProtocolDecoder decoder;

	PacketProtocolCodecFactory(Charset charset, PacketParser parser) {
		super();
		encoder = new PacketProtocolEncoder(charset);
		decoder = new PacketProtocolDecoder(charset, parser);
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
}
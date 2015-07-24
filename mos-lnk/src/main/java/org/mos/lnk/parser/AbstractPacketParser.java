package org.mos.lnk.parser;

import org.mos.lnk.packet.InIQ;
import org.mos.lnk.packet.InMessage;
import org.mos.lnk.packet.InPacket;
import org.mos.lnk.packet.InPresence;
import org.mos.lnk.packet.InRegister;
import org.mos.lnk.packet.InRevise;
import org.mos.lnk.packet.Type;
import org.mos.lnk.serializer.SerializerProvider;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年7月24日 下午1:48:15
 */
public abstract class AbstractPacketParser implements PacketParser {
	
	protected static final String TYPE = "type";

	@Override
	public final InPacket parse(String packet) throws Throwable {
		Type type = parseType(packet);
		if (type == null) {
			throw new IllegalStateException("Error Type of Packet " + packet);
		}
		switch (type) {
		case IQ:
			return SerializerProvider.deserialize(InIQ.class, packet);
		case Message:
			return SerializerProvider.deserialize(InMessage.class, packet);
		case Presence:
			return SerializerProvider.deserialize(InPresence.class, packet);
		case Register:
			return SerializerProvider.deserialize(InRegister.class, packet);
		case Revise:
			return SerializerProvider.deserialize(InRevise.class, packet);
		default:
			throw new IllegalStateException("Error Type of Packet " + packet);
		}
	}

	protected abstract Type parseType(String packet) throws Throwable;
}
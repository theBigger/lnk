package org.mos.lnk.parser;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
 * @since 2015年7月22日 下午6:11:13
 */
public class XMLPacketParser implements PacketParser {
	
	@Override
	public InPacket parse(String packet) throws Throwable {
		Document document = DocumentHelper.parseText(packet);
		Element root = document.getRootElement();
		String typeString = root.attributeValue("type");
		Type type = Type.parse(NumberUtils.toByte(typeString));
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
}
package me.mos.lnk.parser;

import java.util.Map;

import me.mos.lnk.packet.Type;
import me.mos.lnk.serializer.SerializerProvider;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月13日 上午12:14:04
 */
public class JsonPacketParser extends AbstractPacketParser {

	@Override
	protected Type parseType(String packet) throws Throwable {
		@SuppressWarnings("unchecked")
		Map<String, Object> json = SerializerProvider.json().deserialize(Map.class, packet);
		return Type.parse(Byte.parseByte(String.valueOf(json.get(TYPE))));
	}
}
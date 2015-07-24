package org.mos.lnk.parser;

import org.mos.lnk.packet.Type;

import net.sf.json.JSONObject;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月13日 上午12:14:04
 */
public class JsonPacketParser extends AbstractPacketParser {

	@Override
	protected Type parseType(String packet) throws Throwable {
		JSONObject json = JSONObject.fromObject(packet);
		return Type.parse((byte) json.getInt(TYPE));
	}
}
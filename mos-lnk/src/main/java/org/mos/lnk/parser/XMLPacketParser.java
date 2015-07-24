package org.mos.lnk.parser;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.mos.lnk.packet.Type;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年7月22日 下午6:11:13
 */
public class XMLPacketParser extends AbstractPacketParser {

	@Override
	protected Type parseType(String packet) throws Throwable {
		Document document = DocumentHelper.parseText(packet);
		Element root = document.getRootElement();
		String typeString = root.attributeValue(TYPE);
		return Type.parse(NumberUtils.toByte(typeString));
	}
}
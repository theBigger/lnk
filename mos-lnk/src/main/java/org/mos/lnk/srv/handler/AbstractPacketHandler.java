package org.mos.lnk.srv.handler;

import org.mos.lnk.message.DefaultMessageProvider;
import org.mos.lnk.message.MessageProvider;
import org.mos.lnk.packet.InPacket;
import org.mos.lnk.user.DefaultUserProvider;
import org.mos.lnk.user.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月3日 下午4:31:36
 */
public abstract class AbstractPacketHandler<I extends InPacket> implements PacketHandler<I> {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected MessageProvider messageProvider;
	
	protected UserProvider userProvider;

	public AbstractPacketHandler() {
		super();
		messageProvider = DefaultMessageProvider.getInstance();
		userProvider = DefaultUserProvider.getInstance();
	}
}
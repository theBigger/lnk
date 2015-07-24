package org.mos.lnk.channel;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.mos.lnk.packet.Packet;
import org.mos.lnk.server.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月14日 下午5:53:26
 */
public abstract class AbstractChannel implements Channel {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private String channelId;

	@Override
	public String getChannelId() {
		return channelId;
	}

	@Override
	public AbstractChannel setChannelId(long channelId) {
		if (channelId <= 0L) {
			return this;
		}
		this.channelId = String.valueOf(channelId);
		return this;
	}

	@Override
	public void deliver(Packet packet, boolean closeAfterDeliver) {
		deliver(packet);
		if (closeAfterDeliver) close();
	}

	@Override
	public final void close() {
		synchronized (this) {
			try {
				Channels.offline(this);
			} catch (Throwable e) {
				log.error("Channel : " + this + " Close Error.", e);
			} finally {
				_close();
			}
		}
	}

	protected abstract void _close();

	@Override
	public String toString() {
		InetSocketAddress address = getPeerAddress();
		if (address != null) {
			return address + DOT + getChannelId();
		}
		if (getChannel() != null) {
			return getChannel() + DOT + getChannelId();
		}
		if (StringUtils.isNotBlank(getChannelId())) {
			return getChannelId();
		}
		return super.toString();
	}
}
package org.mos.lnk.channel;

import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.mos.lnk.user.DefaultUserProvider;
import org.mos.lnk.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月5日 下午9:38:13
 */
public final class ChannelActiveMonitor implements Runnable {

	private final static Logger log = LoggerFactory.getLogger(ChannelActiveMonitor.class);

	private static final Timer TIMER = new Timer("Channel Active Monitor", false);

	@Override
	public void run() {
		try {
			TIMER.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						Enumeration<String> channels = Channels.channels();
						while (channels.hasMoreElements()) {
							String mid = channels.nextElement();
							Channel<?> channel = Channels.channel(mid);
							if (channel == null) {
								Channels.offline(mid);
							}
							if (!channel.isConnect()) {
								Channels.offline(channel);
							}
						}
					} catch (Exception e) {
						log.error("Channel Active Monitor Running Error.", e);
					}
					try {
						List<User> userList = DefaultUserProvider.getInstance().queryOnline();
						if (!CollectionUtils.isEmpty(userList)) {
							for (User user : userList) {
								long mid = user.getMid();
								if (Channels.isOnline(mid)) {
									continue;
								}
								Channels.offline(mid);
							}
						}
					} catch (Exception e) {
						log.error("Channel Active Monitor Running Error.", e);
					}
				}
			}, 60000L, 300000L);
		} catch (Exception e) {
			log.error("Channel Active Monitor Starting Error.", e);
		}
	}
}
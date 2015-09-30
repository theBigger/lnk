package me.mos.lnk.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端通道寄存器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午3:43:32
 */
public class ChannelGroups {

	private final static Logger log = LoggerFactory.getLogger(ChannelGroups.class);

	private static final ConcurrentHashMap<String, List<Channel<?>>> channelGroups = new ConcurrentHashMap<String, List<Channel<?>>>(2000);
	
	public static void joinGroup(long groupId, Channel<?> channel) {
	    String _groupId = String.valueOf(groupId);
        List<Channel<?>> groupChannels = channelGroups.get(_groupId);
	    if (groupChannels == null) {
	        groupChannels = new ArrayList<Channel<?>>();
	    }
	    groupChannels.add(channel);
	    channelGroups.put(_groupId, groupChannels);
	}
}
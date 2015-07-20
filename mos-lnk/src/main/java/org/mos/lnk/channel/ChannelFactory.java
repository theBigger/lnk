package org.mos.lnk.channel;

/**
 * 连接通道工厂.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月16日 下午7:53:28
 */
public interface ChannelFactory<I> {
	Channel<I> newChannel(I channel, ChannelHandler<I> handler);
	public interface ChannelHandler<I> {
		Channel<I> handler(Channel<I> channel);
	}
}
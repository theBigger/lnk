package org.mos.lnk.channel;

import java.net.Socket;

/**
 * 表示一个客户端连接通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午5:33:03
 */
public interface SockChannel extends Channel<Socket> {
	
	/**
	 * 读消息
	 */
	String read();
	
	ChannelFactory<Socket> CHANNEL_FACTORY = new ChannelFactory<Socket>() {
		@Override
		public SockChannel newChannel(Socket channel, ChannelHandler<Socket> handler) {
			return new BoundSockChannel(channel, null);
		}
	};
}
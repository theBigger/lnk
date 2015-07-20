package org.mos.lnk.channel;

import java.nio.channels.SocketChannel;

/**
 * 表示一个客户端连接通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月1日 下午5:33:03
 */
public interface NioSockChannel extends Channel<SocketChannel> {
	NioSockChannel interestOps(int ops);
}
package org.mos.lnk.channel;

import java.net.InetSocketAddress;

import org.mos.lnk.packet.Packet;
import org.mos.lnk.server.PacketProtocol;

/**
 * 表示一个客户端连接通道.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月14日 下午12:59:35
 */
public interface Channel<I> extends PacketProtocol {
	/**
	 * 获取用户通道ID
	 */
	String getChannelId();

	/**
	 * 注入用户通道ID
	 */
	Channel<I> setChannelId(long mid);
	
	/**
	 * 获取通道内部包装对象
	 */
	I getChannel();
	
	/**
	 * 对通道对象执行操作
	 */
	void doAction(I channel);
	
	/**
	 * 获取客户端的地址
	 */
	InetSocketAddress getPeerAddress();
	
	/**
	 * 接收消息
	 */
	String received();
	
	/**
	 * 将消息投递到通道
	 */
	void deliver(Packet packet);

	/**
	 * 通道是否处于连接状态
	 */
	boolean isConnect();

	/**
	 * 关闭通道
	 */
	void close();
}
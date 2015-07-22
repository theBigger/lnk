package org.mos.lnk.server;

import org.mos.lnk.utils.Charsets;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月13日 下午11:28:20
 */
public interface PacketProtocol {
	
	String DOT = "/";
	
	/**
	 * 数据读取缓冲区大小
	 */
	int READ_BYTE_BUF = 1024;

	/**
	 * 定义头信息的字节数
	 */
	int HEAD_BYTE_LENGTH = 20;

	/**
	 * 在头信息中, 前4位表示该报文的长度, 剩余的16位头信息备用
	 */
	int PACKET_BYTE_LENGTH = 4;

	/**
	 * 版本号所在的头位置
	 */
	int VERSION_POSITION = 5;

	/**
	 * 默认端口
	 */
	int DEFAULT_PORT = 9099;

	/**
	 * 核心线程池大小
	 */
	int DEFAULT_CORE_POOL_SIZE = 50;

	/**
	 * 最大线程池大小
	 */
	int DEFAULT_MAXIMUM_POOL_SIZE = 1000;

	/**
	 * 任务缓冲队列大小
	 */
	int DEFAULT_QUEUE_SIZE = 10000;

	/**
	 * 消息读取超时时间
	 */
	int DEFAULT_READ_TIMEOUT = 30;
	
	/**
	 * 输入连接指示（对连接的请求）的最大队列长度被设置为 backlog参数。如果队列满时收到连接指示，则拒绝该连接。
	 */
	int DEFAULT_BACKLOG = 5000;
	
	/**
	 * 空闲时间（单位 : 秒）
	 */
	int DEFAULT_IDLETIME = 1800;
	
	/**
	 * <pre>
	 * -1 | 0 | nSec 
	 * -1表示使用OS缺省参数，0表示立即释放，nSec表示等待n秒后释放
	 * </pre>
	 */
	int DEFAULT_OS_SOLINGER = -1;
	
	/**
	 * 默认编码格式
	 */
	String DEFAULT_CHARSET = Charsets.UTF_8_NAME;
}
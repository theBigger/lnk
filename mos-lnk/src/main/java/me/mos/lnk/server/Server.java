package me.mos.lnk.server;

/**
 * Mos-Lnk服务器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午1:46:09
 */
public interface Server extends PacketProtocol {

	/**
	 * 启动服务
	 */
	void start();
	
	/**
	 * 停止服务
	 */
	void stop();
	
	/**
	 * 注册jvm关闭钩子, 然后启动服务.
	 */
	void registerShutdownHookStart();
}
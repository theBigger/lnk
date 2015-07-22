package org.mos.lnk.server.ws.glassfish;

/**
 * 服务器启动入口.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月4日 下午4:06:17
 */
public class LnkServerStarter {

	public static void main(String[] args) {
		final LnkServer lnkServer = new LnkServer();
		lnkServer.start();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				lnkServer.stop();
			}
		}));
	}
}
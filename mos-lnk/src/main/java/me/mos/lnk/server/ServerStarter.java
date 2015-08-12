package me.mos.lnk.server;

/**
 * 服务器启动入口.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月4日 下午4:06:17
 */
public class ServerStarter {
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				me.mos.lnk.server.websocket.glassfish.LnkServerStarter.main(args);
				System.err.println("WS Lnk Server Started Success!!!");
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				me.mos.lnk.server.websocket.jetty.LnkServerStarter.main(args);
				System.err.println("WS Lnk Server Started Success!!!");
			}
		}).start();
	}
}
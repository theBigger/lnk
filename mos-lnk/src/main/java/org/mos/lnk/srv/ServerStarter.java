package org.mos.lnk.srv;

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
		org.mos.lnk.srv.ws.LnkServerStarter.main(args);
		System.err.println("WS Lnk Server Started Success!!!");
	}
}
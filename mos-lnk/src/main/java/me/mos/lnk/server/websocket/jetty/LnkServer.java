package me.mos.lnk.server.websocket.jetty;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import me.mos.lnk.etc.Profile;
import me.mos.lnk.server.AbstractServer;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午8:48:00
 */
public class LnkServer extends AbstractServer {

	private int port = DEFAULT_PORT;

	private org.eclipse.jetty.server.Server server;

	private Profile profile;

	public LnkServer() {
		super();
		try {
			profile = Profile.newInstance();
			setPort(profile.getPort());
			log.error("Config LnkServer Success.");
		} catch (Exception e) {
			log.error("Create Server Profile from XML Error.", e);
		}
	}

	@Override
	protected void doStart() {
		try {
			server = new org.eclipse.jetty.server.Server();
			ServerConnector connector = new ServerConnector(server);
			connector.setPort(port);
			server.addConnector(connector);
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath(ROOT);
			server.setHandler(context);
			ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
			wscontainer.addEndpoint(ServerIoHandler.class);
			wscontainer.setAsyncSendTimeout(30000);// 30s
			wscontainer.setDefaultMaxBinaryMessageBufferSize(1024 * 1024 * 100);// 100M
			wscontainer.setDefaultMaxSessionIdleTimeout(1000 * 60 * 30);// 30min
			wscontainer.setDefaultMaxTextMessageBufferSize(1024 * 1024 * 100);// 100M
			server.setDumpAfterStart(true);
			server.setStopAtShutdown(true);
			server.start();
			log.error("LnkServer[WS] started success on port {}.", port);
			server.join();
		} catch (Throwable e) {
			log.error("LnkServer Starting Error.", e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	protected void doStop() {
		try {
			server.stop();
			server = null;
		} catch (Exception e) {
			log.error("LnkServer Started Error.", e);
		}
	}

	public void setPort(int port) {
		this.port = port;
	}
}
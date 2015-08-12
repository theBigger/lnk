package me.mos.lnk.server.websocket.jetty;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.mos.lnk.etc.Profile;
import me.mos.lnk.server.Server;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午8:48:00
 */
public class LnkServer implements Server {

	private static final Logger log = LoggerFactory.getLogger(LnkServer.class);

	private int port = DEFAULT_PORT;

	private org.eclipse.jetty.server.Server server;

	private Profile profile;

	LnkServer() {
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
	public void start() {
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
			server.start();
			server.dumpStdErr();
			log.error("LnkServer[WS] started success on port {}.", port);
			server.join();
		} catch (Throwable e) {
			log.error("LnkServer Starting Error.", e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void stop() {
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
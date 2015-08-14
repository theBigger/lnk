package me.mos.lnk.server.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;

import me.mos.lnk.channel.ChannelActiveMonitor;
import me.mos.lnk.etc.Profile;
import me.mos.lnk.executor.LnkExecutor;
import me.mos.lnk.parser.JsonPacketParser;
import me.mos.lnk.parser.PacketParser;
import me.mos.lnk.processor.DefaultServerProcessor;
import me.mos.lnk.processor.ServerProcessor;
import me.mos.lnk.server.AbstractServer;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月15日 下午5:18:59
 */
public class LnkServer extends AbstractServer {

	private int port = DEFAULT_PORT;

	/**
	 * 输入连接指示（对连接的请求）的最大队列长度被设置为 backlog 参数。如果队列满时收到连接指示，则拒绝该连接。
	 */
	private int backlog = DEFAULT_BACKLOG;

	private String charset = DEFAULT_CHARSET;

	private ThreadPoolExecutor threadPoolExecutor;

	private Profile profile;

	private ServerProcessor processor;

	private PacketParser parser;

	private ServerSocketChannel server;

	private Selector selector;

	public LnkServer() {
		super();
		try {
			profile = Profile.newInstance();
			setPort(profile.getPort());
			setCharset(profile.getCharset());
			setBacklog(profile.getBacklog());
			setProcessor(new DefaultServerProcessor());
			setParser(new JsonPacketParser());
			log.error("Config LnkServer Success.");
		} catch (Exception e) {
			log.error("Create Server Profile from XML Error.", e);
		}
	}

	@Override
	protected void doStart() {
		try {
			log.error("LnkServer starting on port {}", port);
			threadPoolExecutor = new LnkExecutor(profile);
			selector = SelectorProvider.provider().openSelector();
			server = ServerSocketChannel.open();
			server.configureBlocking(false);
			ServerSocket socket = server.socket();
			socket.bind(new InetSocketAddress(port), backlog);
			socket.setReuseAddress(true);
			socket.setReceiveBufferSize(1024 * 1024);
			server.register(selector, SelectionKey.OP_ACCEPT);
			Thread masterWorker = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							selector.select();
							Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
							while (selectedKeys.hasNext()) {
								SelectionKey key = selectedKeys.next();
								selectedKeys.remove();
								if (!key.isValid()) {
									continue;
								}
								if (key.isAcceptable()) {
									ServerSocketChannel server = (ServerSocketChannel) key.channel();
									SocketChannel channel = server.accept();
									channel.configureBlocking(false);
									channel.register(selector, SelectionKey.OP_READ);
								} else if (key.isReadable()) {
									BoundChannel channel = new BoundChannel(key, Charset.forName(charset));
									threadPoolExecutor.execute(new ServerIoHandler(channel, processor, parser));
									log.error(channel + " Connection to LnkServer.");
								}
							}
						} catch (Throwable t) {
							log.error("Process Channel Error.", t);
							if (t instanceof InterruptedException) {
								throw new IllegalStateException(t);
							}
						}
					}
				}
			};
			masterWorker.setDaemon(true);
			masterWorker.start();
			threadPoolExecutor.execute(new ChannelActiveMonitor());
			log.error("LnkServer[Sel] started success on port {}.", port);
		} catch (Throwable e) {
			log.error("Start LnkServer Failed.", e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	protected void doStop() {
		log.info("Stoping LnkServer, port={}", port);
		if (threadPoolExecutor != null) {
			try {
				threadPoolExecutor.shutdownNow();
			} catch (Exception e) {
			}
			threadPoolExecutor = null;
		}
		if (server != null) {
			try {
				server.close();
			} catch (Exception e) {
			}
			server = null;
		}
		log.info("Stoped LnkServer, port={}", port);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public void setProcessor(ServerProcessor processor) {
		this.processor = processor;
	}

	public void setParser(PacketParser parser) {
		this.parser = parser;
	}
}
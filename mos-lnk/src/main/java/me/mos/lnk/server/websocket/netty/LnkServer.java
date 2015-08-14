package me.mos.lnk.server.websocket.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import me.mos.lnk.etc.Profile;
import me.mos.lnk.parser.JsonPacketParser;
import me.mos.lnk.parser.PacketParser;
import me.mos.lnk.processor.DefaultServerProcessor;
import me.mos.lnk.processor.ServerProcessor;
import me.mos.lnk.server.Server;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午8:48:00
 */
public class LnkServer implements Server {

	private static final Logger log = LoggerFactory.getLogger(LnkServer.class);

	private int port = DEFAULT_PORT;

	private Channel channel;
	
	private Profile profile;

	/**
	 * 输入连接指示（对连接的请求）的最大队列长度被设置为 backlog参数。如果队列满时收到连接指示，则拒绝该连接。
	 */
	private int backlog = DEFAULT_BACKLOG;
	
	private ServerProcessor processor;
	
	private PacketParser parser;

	LnkServer() {
		super();
		try {
			profile = Profile.newInstance();
			setPort(profile.getPort());
			setBacklog(profile.getBacklog());
			setProcessor(new DefaultServerProcessor());
			setParser(new JsonPacketParser());
			log.error("Config LnkServer Success.");
		} catch (Exception e) {
			log.error("Create Server Profile from XML Error.", e);
		}
	}

	@Override
	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap()
			.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, backlog)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				protected void initChannel(SocketChannel channel) throws Exception {
					ChannelPipeline pipeline = channel.pipeline();
					pipeline.addLast("codec-http", new HttpServerCodec());
					pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 100));// 100M
					pipeline.addLast("handler", new ServerIoHandler(processor, parser));
				}
			});
			log.error("LnkServer[WS] started success on port {}.", port);
			channel = server.bind(port).sync().channel();
			channel.closeFuture().sync();
		} catch (Throwable e) {
			log.error("LnkServer Starting Error.", e);
			throw new IllegalStateException(e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	@Override
	public void stop() {
		try {
		    channel.disconnect();
			channel.close();
			channel = null;
		} catch (Exception e) {
			log.error("LnkServer Started Error.", e);
		}
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void setProcessor(ServerProcessor processor) {
		this.processor = processor;
	}
	
	public void setParser(PacketParser parser) {
		this.parser = parser;
	}
	
	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}
}
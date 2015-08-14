package me.mos.lnk.server.netty;

import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import me.mos.lnk.channel.ChannelActiveMonitor;
import me.mos.lnk.etc.Profile;
import me.mos.lnk.parser.JsonPacketParser;
import me.mos.lnk.parser.PacketParser;
import me.mos.lnk.processor.DefaultServerProcessor;
import me.mos.lnk.processor.ServerProcessor;
import me.mos.lnk.server.AbstractServer;
import me.mos.lnk.server.netty.codec.PacketProtocolDecoder;
import me.mos.lnk.server.netty.codec.PacketProtocolEncoder;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午8:48:00
 */
public class LnkServer extends AbstractServer {

	private int port = DEFAULT_PORT;

	private Channel channel;

	private Profile profile;
	
	private String charset = DEFAULT_CHARSET;

	/**
	 * 输入连接指示（对连接的请求）的最大队列长度被设置为 backlog参数。如果队列满时收到连接指示，则拒绝该连接。
	 */
	private int backlog = DEFAULT_BACKLOG;

	private ServerProcessor processor;

	private PacketParser parser;

	public LnkServer() {
		super();
		try {
			profile = Profile.newInstance();
			setPort(profile.getPort() + 1);
			setBacklog(profile.getBacklog());
			setCharset(profile.getCharset());
			setProcessor(new DefaultServerProcessor());
			setParser(new JsonPacketParser());
			log.error("Config LnkServer Success.");
		} catch (Exception e) {
			log.error("Create Server Profile from XML Error.", e);
		}
	}
	
	@Override
	protected void doStart() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap()
			.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, backlog)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("decoder", new PacketProtocolDecoder(Charset.forName(charset), parser));
					pipeline.addLast("encoder", new PacketProtocolEncoder(Charset.forName(charset)));
					pipeline.addLast("handler", new ServerIoHandler(processor));
				}
			});
			new Thread(new ChannelActiveMonitor()).start();
			log.error("LnkServer Started success on port {}.", port);
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
	protected void doStop() {
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
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}
}
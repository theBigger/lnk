package me.mos.lnk.server.websocket.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
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

	private Channel channel;
	
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
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("codec-http", new HttpServerCodec());
					pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
					pipeline.addLast("handler", new ServerIoHandler());
				}
			});
			channel = server.bind(port).sync().channel();
			channel.closeFuture().sync();
			log.error("LnkServer[WS] started success on port {}.", port);
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
}
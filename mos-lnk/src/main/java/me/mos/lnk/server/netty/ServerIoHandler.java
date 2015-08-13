package me.mos.lnk.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import me.mos.lnk.packet.InPacket;
import me.mos.lnk.packet.OutPacket;
import me.mos.lnk.processor.ServerProcessor;
import me.mos.lnk.server.Handler;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月12日 下午4:15:44
 */
final class ServerIoHandler extends SimpleChannelInboundHandler<InPacket> implements Handler {

    private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);

	private static final AttributeKey<BoundChannel> IO_CHANNEL = AttributeKey.<BoundChannel>valueOf("IO-CHANNEL");

    private final ServerProcessor processor;

	ServerIoHandler(ServerProcessor processor) {
		super();
		this.processor = processor;
	}

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        BoundChannel channel = new BoundChannel(ctx.channel());
        ctx.channel().attr(IO_CHANNEL).set(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        BoundChannel channel = ctx.channel().attr(IO_CHANNEL).get();
		log.error("ServerIoHandler: Closing channel due to session Closed: " + channel);
        channel.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InPacket inPacket) throws Exception {
        BoundChannel channel = ctx.channel().attr(IO_CHANNEL).get();
        try {
			channel.setChannelId(inPacket.getMid());
			OutPacket outPacket = processor.process(channel, inPacket);
			if (outPacket == null) {
				return;
			}
			channel.deliver(outPacket);
		} catch (Throwable e) {
			log.error("ServerIoHandler MessageReceived Error.", e);
		}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        BoundChannel channel = ctx.channel().attr(IO_CHANNEL).get();
		log.error("ServerIoHandler: Channel Error.\n" + channel, cause);
        ctx.close();
    }
}

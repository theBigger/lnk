package me.mos.lnk.server.websocket.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import me.mos.lnk.packet.InPacket;
import me.mos.lnk.packet.OutPacket;
import me.mos.lnk.parser.PacketParser;
import me.mos.lnk.processor.ServerProcessor;
import me.mos.lnk.server.Handler;
import me.mos.lnk.server.Server;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月12日 下午4:15:44
 */
final class ServerIoHandler extends SimpleChannelInboundHandler<Object> implements Handler {

    private static final Logger log = LoggerFactory.getLogger(ServerIoHandler.class);

	private static final AttributeKey<BoundChannel> IO_CHANNEL = AttributeKey.<BoundChannel>valueOf("IO-CHANNEL");

    private WebSocketServerHandshaker handshaker;
    
    private final ServerProcessor processor;

	private final PacketParser parser;

	ServerIoHandler(ServerProcessor processor, PacketParser parser) {
		super();
		this.processor = processor;
		this.parser = parser;
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
    protected void channelRead0(ChannelHandlerContext ctx, Object message) throws Exception {
        if (message instanceof FullHttpRequest) {
            handleHttpInboundMessage(ctx, FullHttpRequest.class.cast(message));
        } else if (message instanceof WebSocketFrame) {
            handleWebSocketInboundMessage(ctx, WebSocketFrame.class.cast(message));
        }
    }

    private void handleHttpInboundMessage(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (!req.getDecoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        if (req.getMethod() != HttpMethod.GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketInboundMessage(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        String message = TextWebSocketFrame.class.cast(frame).text();
        BoundChannel channel = ctx.channel().attr(IO_CHANNEL).get();
        try {
			InPacket inPacket = parser.parse(message);
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

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(res, res.content().readableBytes());
        }
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private String getWebSocketLocation(FullHttpRequest req) {
        return "ws://" + req.headers().get(HttpHeaders.Names.HOST) + Server.ROOT;
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

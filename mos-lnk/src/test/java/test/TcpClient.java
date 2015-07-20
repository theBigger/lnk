package test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月10日 下午6:33:50
 */
public class TcpClient {
	private SocketConnector connector;
	private ConnectFuture future;
	private IoSession session;

	public boolean connect() {
		try {
			connector = new NioSocketConnector();
			connector.setConnectTimeoutMillis(3000);
			DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
			TextLineCodecFactory lineCodec = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue());
			lineCodec.setDecoderMaxLineLength(2 * 1024 * 1024);
			lineCodec.setEncoderMaxLineLength(2 * 1024 * 1024);
			filterChain.addLast("codec", new ProtocolCodecFilter(lineCodec));
			filterChain.addLast("exceutor", new ExecutorFilter(Executors.newCachedThreadPool()));

			// connector.setHandler(new ServerHandler());

			future = connector.connect(new InetSocketAddress("127.0.0.1", 8292));
			future.awaitUninterruptibly();
			session = future.getSession();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void write(Object request) throws Exception {
		session.write(request.toString());
	}

	public boolean close() {
		try {
			CloseFuture future = session.getCloseFuture();
			future.awaitUninterruptibly(1000);
			connector.dispose();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
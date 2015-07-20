package nio0;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月5日 下午3:41:01
 */
public class NioServer extends Thread {

	private static final Logger log = Logger.getLogger(NioServer.class.getName());

	private InetSocketAddress inetSocketAddress;
	private Handler handler = new ServerHandler();

	public NioServer(String hostname, int port) {
		inetSocketAddress = new InetSocketAddress(hostname, port);
	}

	@Override
	public void run() {
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(inetSocketAddress);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			log.info("NioServer Started.");
			while (true) {
				int nKeys = selector.select();
				if (nKeys > 0) {
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					Iterator<SelectionKey> it = selectedKeys.iterator();
					while (it.hasNext()) {
						SelectionKey key = it.next();
						if (key.isAcceptable()) {
							log.info("NioServer SelectionKey is acceptable.");
							handler.handleAccept(key);
						} else if (key.isReadable()) {
							log.info("NioServer SelectionKey is readable.");
							handler.handleRead(key);
						} else if (key.isWritable()) {
							log.info("NioServer SelectionKey is writable.");
							handler.handleWrite(key);
						}
						it.remove();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	interface Handler {
		void handleAccept(SelectionKey key) throws IOException;
		void handleRead(SelectionKey key) throws IOException;
		void handleWrite(SelectionKey key) throws IOException;
	}

	class ServerHandler implements Handler {

		@Override
		public void handleAccept(SelectionKey key) throws IOException {
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
			SocketChannel socketChannel = serverSocketChannel.accept();
			log.info("NioServer accept client " + socketChannel);
			socketChannel.configureBlocking(false);
			socketChannel.register(key.selector(), SelectionKey.OP_READ);
		}

		@Override
		public void handleRead(SelectionKey key) throws IOException {
			ByteBuffer byteBuffer = ByteBuffer.allocate(512);
			SocketChannel socketChannel = (SocketChannel) key.channel();
			while (true) {
				int readBytes = socketChannel.read(byteBuffer);
				if (readBytes > 0) {
					log.info("NioServer readBytes = " + readBytes);
					log.info("NioServer data = " + new String(byteBuffer.array(), 0, readBytes));
					byteBuffer.flip();
					socketChannel.write(byteBuffer);
					break;
				}
			}
			socketChannel.close();
		}

		@Override
		public void handleWrite(SelectionKey key) throws IOException {
			ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
			byteBuffer.flip();
			SocketChannel socketChannel = (SocketChannel) key.channel();
			socketChannel.write(byteBuffer);
			if (byteBuffer.hasRemaining()) {
				key.interestOps(SelectionKey.OP_READ);
			}
			byteBuffer.compact();
		}
	}

	public static void main(String[] args) {
		NioServer server = new NioServer("localhost", 12210);
		server.start();
	}
}
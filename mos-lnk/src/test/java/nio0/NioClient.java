package nio0;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月5日 下午3:41:17
 */
public class NioClient {

	private static final Logger log = Logger.getLogger(NioClient.class.getName());
	private InetSocketAddress inetSocketAddress;

	public NioClient(String hostname, int port) {
		inetSocketAddress = new InetSocketAddress(hostname, port);
	}

	public void send(String requestData) {
		try {
			SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
			socketChannel.configureBlocking(false);
			ByteBuffer byteBuffer = ByteBuffer.allocate(512);
			socketChannel.write(ByteBuffer.wrap(requestData.getBytes()));
			while (true) {
				byteBuffer.clear();
				int readBytes = socketChannel.read(byteBuffer);
				if (readBytes > 0) {
					byteBuffer.flip();
					log.info("NioClient readBytes = " + readBytes);
					log.info("NioClient data = " + new String(byteBuffer.array(), 0, readBytes));
					socketChannel.close();
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String requestData = "你好啊，服务器！！\nO(∩_∩)O哈哈~";
		new NioClient("localhost", 12210).send(requestData);
	}
}
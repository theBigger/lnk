package ws.jetty.java.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;

public class RemoteEndpointExamples {
	private Session session;

	public void exampleAsyncSendBytesFireAndForget() {
		RemoteEndpoint remote = session.getRemote();
		ByteBuffer buf = ByteBuffer.wrap(new byte[] { 0x11, 0x22, 0x33, 0x44 });
		remote.sendBytesByFuture(buf);
	}

	public void exampleAsyncSendBytesTimeout() {
		RemoteEndpoint remote = session.getRemote();
		ByteBuffer buf = ByteBuffer.wrap(new byte[] { 0x11, 0x22, 0x33, 0x44 });
		Future<Void> fut = null;
		try {
			fut = remote.sendBytesByFuture(buf);
			fut.get(2, TimeUnit.SECONDS);
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
			if (fut != null) {
				fut.cancel(true);
			}
		}
	}

	public void exampleAsyncSendBytesWaitForCompletion() {
		RemoteEndpoint remote = session.getRemote();
		ByteBuffer buf = ByteBuffer.wrap(new byte[] { 0x11, 0x22, 0x33, 0x44 });
		try {
			Future<Void> fut = remote.sendBytesByFuture(buf);
			fut.get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void exampleAsyncSendStringFireAndForget() {
		RemoteEndpoint remote = session.getRemote();
		remote.sendStringByFuture("Hello World");
	}

	public void exampleAsyncSendStringTimeout() {
		RemoteEndpoint remote = session.getRemote();
		Future<Void> fut = null;
		try {
			fut = remote.sendStringByFuture("Hello World");
			fut.get(2, TimeUnit.SECONDS);
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
			if (fut != null) {
				fut.cancel(true);
			}
		}
	}

	public void exampleAsyncSendStringWaitForCompletion() {
		RemoteEndpoint remote = session.getRemote();
		try {
			Future<Void> fut = remote.sendStringByFuture("Hello World");
			fut.get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void exampleBlockingSendBytes() {
		RemoteEndpoint remote = session.getRemote();
		byte array[] = new byte[] { 0x11, 0x22, 0x33, 0x44 };
		ByteBuffer buf = ByteBuffer.wrap(array);
		try {
			remote.sendBytes(buf);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public void exampleBlockingSendPartialBytes() {
		RemoteEndpoint remote = session.getRemote();
		ByteBuffer buf1 = ByteBuffer.wrap(new byte[] { 0x11, 0x22 });
		ByteBuffer buf2 = ByteBuffer.wrap(new byte[] { 0x33, 0x44 });
		try {
			remote.sendPartialBytes(buf1, false);
			remote.sendPartialBytes(buf2, true);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public void exampleBlockingSendPartialString() {
		RemoteEndpoint remote = session.getRemote();
		String part1 = "Hello";
		String part2 = " World";
		try {
			remote.sendPartialString(part1, false);
			remote.sendPartialString(part2, true);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public void exampleBlockingSendPing() {
		RemoteEndpoint remote = session.getRemote();
		String data = "You There?";
		ByteBuffer payload = ByteBuffer.wrap(data.getBytes());
		try {
			remote.sendPing(payload);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public void exampleBlockingSendPong() {
		RemoteEndpoint remote = session.getRemote();
		String data = "Yup, I'm here";
		ByteBuffer payload = ByteBuffer.wrap(data.getBytes());
		try {
			remote.sendPong(payload);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public void exampleBlockingSendString() {
		RemoteEndpoint remote = session.getRemote();
		try {
			remote.sendString("Hello World");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
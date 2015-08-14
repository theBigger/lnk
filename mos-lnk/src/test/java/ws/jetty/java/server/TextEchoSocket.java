package ws.jetty.java.server;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class TextEchoSocket extends WebSocketAdapter {
	private static AtomicInteger socketNum = new AtomicInteger(0);
	private int socketId;
	private RemoteEndpoint remote;

	@Override
	public void onWebSocketConnect(Session sess) {
		super.onWebSocketConnect(sess);

		this.socketId = socketNum.incrementAndGet();
		this.remote = getRemote();
		respond("Thanks for connecting");
	}

	@Override
	public void onWebSocketText(String message) {
		respond(message);
	}

	private void respond(String string) {
		if (!isConnected()) {
			System.err.printf("Not connected!%n");
			return;
		}
		remote.sendStringByFuture(String.format("[%d] %s", socketId, string));
	}
}

package ws.jetty.java.client;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class TextEchoSocket extends WebSocketAdapter {
	@Override
	public void onWebSocketText(String message) {
		if (isConnected()) {
			getRemote().sendStringByFuture(message);
		}
	}
}

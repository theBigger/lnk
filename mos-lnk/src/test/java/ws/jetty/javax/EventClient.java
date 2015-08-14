package ws.jetty.javax;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.eclipse.jetty.util.component.LifeCycle;

public class EventClient {
	public static void main(String[] args) {
		URI uri = URI.create("ws://localhost:8080/events/");
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			try {
				Session session = container.connectToServer(EventSocket.class, uri);
				session.getBasicRemote().sendText("Hello");
				session.close();
			} finally {
				if (container instanceof LifeCycle) {
					((LifeCycle) container).stop();
				}
			}
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
}
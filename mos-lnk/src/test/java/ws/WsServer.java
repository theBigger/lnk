package ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.server.Server;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午12:16:01
 */
@ServerEndpoint("/lnk")
//@ServerEndpoint(value = "/game", decoders = WsDecoder.class, encoders = WsEncoder.class)
public class WsServer {

    @OnOpen
    public void onOpen(Session session) {
        System.err.println("Connected ... " + session.getId());
    }
  
    @OnMessage
    public String onMessage(String message, Session session) {
        switch (message) {
        case "quit":
            try {
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game ended"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            break;
        }
        return message;
    }
  
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.err.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

	public static void main(String[] args) {
		Server server = new Server("localhost", 8025, "/ws", WsServer.class);
		try {
			server.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.err.print("Please press a key to stop the server.");
			reader.readLine();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			server.stop();
		}
	}
}
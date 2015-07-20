package ws;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午1:00:04
 */
public class WsEncoder implements Encoder.Text<String> {

    @Override
    public String encode(String message) {
        return message;
    }

    @Override
    public void init(EndpointConfig config) {
        // do nothing.
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
package ws;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午12:58:59
 */
public class WsDecoder implements Decoder.Text<String> {

    @Override
    public String decode(String s) {
        return s;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
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
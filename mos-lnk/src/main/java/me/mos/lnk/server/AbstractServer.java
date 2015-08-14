package me.mos.lnk.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月15日 上午12:24:47
 */
public abstract class AbstractServer implements Server {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final void start() {
        doStart();
    }

    @Override
    public final void stop() {
        doStop();
    }

    protected abstract void doStart();

    protected abstract void doStop();

    @Override
    public void registerShutdownHookStart() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }));
        start();
    }
}

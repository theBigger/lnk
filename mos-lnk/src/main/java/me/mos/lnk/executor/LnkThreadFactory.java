package me.mos.lnk.executor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月10日 下午11:10:44
 */
final class LnkThreadFactory implements ThreadFactory {

	private final Logger log = LoggerFactory.getLogger(LnkExecutor.class);

	private static final String LNK_SERVER_WORKER = "LnkServer-Worker-";

	LnkThreadFactory() {
		super();
	}

	@Override
	public Thread newThread(Runnable r) {
		final UncaughtExceptionHandler eh = Thread.getDefaultUncaughtExceptionHandler();
		Thread t = new Thread(Thread.currentThread().getThreadGroup(), r);
		t.setName(LNK_SERVER_WORKER + System.currentTimeMillis());
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				log.error("LnkServer Worker UncaughtExceptionHandler " + t.getName() + " Error.", e);
				eh.uncaughtException(t, e);
			}
		});
		return t;
	}
}
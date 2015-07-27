package me.mos.lnk.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.mos.lnk.etc.Profile;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月10日 下午10:49:09
 */
public class LnkExecutor extends ThreadPoolExecutor {

	private static final long DEFAULT_KEEPALIVETIME = 60L;

	public LnkExecutor(Profile profile) {
		super(
				profile.getCorePoolSize(), 
				profile.getMaximumPoolSize(), 
				DEFAULT_KEEPALIVETIME, 
				TimeUnit.SECONDS, 
				/**
				 * @see ArrayBlockingQueue
				 */
				new LinkedBlockingQueue<Runnable>(profile.getQueueSize()), 
				new LnkThreadFactory(),
				new LnkRejectedExecutionHandler()
			);
	}
}
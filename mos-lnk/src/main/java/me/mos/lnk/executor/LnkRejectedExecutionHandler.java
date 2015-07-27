package me.mos.lnk.executor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月10日 下午11:12:08
 */
public class LnkRejectedExecutionHandler extends CallerRunsPolicy {

	private final Logger log = LoggerFactory.getLogger(LnkExecutor.class);
	
	LnkRejectedExecutionHandler() {
		super();
	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		super.rejectedExecution(r, e);
		log.error("Thread Resource and Cache Queue Resource work out...");
	}
}
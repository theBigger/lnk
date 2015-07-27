package me.mos.lnk.utils;

import java.io.File;
import java.io.IOException;

/**
 * 自动创建文件目录的Appender.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午2:55:52
 */
public class DailyRollingFileAppender extends org.apache.log4j.DailyRollingFileAppender {
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
		File logfile = new File(fileName);
		logfile.getParentFile().mkdirs();
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}
}
package org.mos.lnk.etc;

import org.mos.lnk.config.ConfigUtils;
import org.mos.lnk.config.Resource;
import org.mos.lnk.srv.Server;
import org.mos.lnk.utils.Charsets;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午7:14:01
 */
@XStreamAlias("server")
@Resource(location = "../etc/profile.xml")
public class Profile {

	@XStreamAlias("port")
	@XStreamAsAttribute
	private int port = Server.DEFAULT_PORT;

	@XStreamAlias("core-pool-size")
	private int corePoolSize = Server.DEFAULT_CORE_POOL_SIZE;

	/**
	 * 最大线程数目
	 */
	@XStreamAlias("maximum-pool-size")
	private int maximumPoolSize = Server.DEFAULT_MAXIMUM_POOL_SIZE;

	/**
	 * 缓冲队列大小
	 */
	@XStreamAlias("queue-size")
	private int queueSize = Server.DEFAULT_QUEUE_SIZE;

	/**
	 * 读取超时(单位:秒)，默认30s
	 */
	@XStreamAlias("read-timeout")
	private int readTimeout = Server.DEFAULT_READ_TIMEOUT;

	@XStreamAlias("charset")
	private String charset = Charsets.UTF_8_NAME;

	@XStreamAlias("backlog")
	private int backlog = Server.DEFAULT_BACKLOG;

	@XStreamAlias("idle-time")
	private int idleTime = Server.DEFAULT_IDLETIME;
	
	/**
	 * <pre>
	 * -1 | 0 | nSec 
	 * -1表示使用OS缺省参数，0表示立即释放，nSec表示等待n秒后释放
	 * </pre>
	 */
	@XStreamAlias("so-linger")
	private int soLinger = Server.DEFAULT_OS_SOLINGER;
	
	public static Profile newInstance() {
		return ConfigUtils.conf(Profile.class, Charsets.UTF_8);
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}

	public int getSoLinger() {
		return soLinger;
	}

	public void setSoLinger(int soLinger) {
		this.soLinger = soLinger;
	}
}
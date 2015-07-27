package me.mos.lnk.etc;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import me.mos.lnk.config.ConfigProvider;
import me.mos.lnk.config.Resource;
import me.mos.lnk.utils.Charsets;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午7:14:01
 */
@XStreamAlias("database")
@Resource(location = "../etc/database.xml")
public class Database {

	@XStreamAlias("driver")
	private String driverClassName;

	@XStreamAlias("test-while-idle")
	private boolean testWhileIdle;

	@XStreamAlias("validation-query")
	private String validationQuery;

	@XStreamAlias("validation-interval")
	private long validationInterval;

	@XStreamAlias("max-wait")
	private int maxWait;

	@XStreamAlias("jdbc-interceptors")
	private String jdbcInterceptors;

	@XStreamAlias("initial-size")
	private int initialSize;

	@XStreamAlias("url")
	private String url;

	@XStreamAlias("username")
	private String username;

	@XStreamAlias("password")
	private String password;

	public static Database newInstance() {
		return ConfigProvider.conf(Database.class, Charsets.UTF_8);
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public long getValidationInterval() {
		return validationInterval;
	}

	public void setValidationInterval(long validationInterval) {
		this.validationInterval = validationInterval;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public String getJdbcInterceptors() {
		return jdbcInterceptors;
	}

	public void setJdbcInterceptors(String jdbcInterceptors) {
		this.jdbcInterceptors = jdbcInterceptors;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
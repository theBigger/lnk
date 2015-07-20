package org.mos.lnk.database;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.mos.lnk.etc.Database;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午5:04:49
 */
public class DefaultDataSourceProvider implements DataSourceProvider {

	private DataSource dataSource;

	@Override
	public DataSource getDataSource() throws Throwable {
		if(dataSource != null) {
			return dataSource;
		}
		Database database = Database.newInstance();
		dataSource = new DataSource();
		dataSource.setDriverClassName(database.getDriverClassName());
		dataSource.setTestWhileIdle(database.isTestWhileIdle());
		dataSource.setValidationQuery(database.getValidationQuery());
		dataSource.setValidationInterval(database.getValidationInterval());
		dataSource.setMaxWait(database.getMaxWait());
		dataSource.setJdbcInterceptors(database.getJdbcInterceptors());
		dataSource.setInitialSize(database.getInitialSize());
		dataSource.setUrl(database.getUrl());
		dataSource.setUsername(database.getUsername());
		dataSource.setPassword(database.getPassword());
		return dataSource;
	}
}

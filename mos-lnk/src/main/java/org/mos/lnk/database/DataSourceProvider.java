package org.mos.lnk.database;

import javax.sql.DataSource;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午5:03:34
 */
public interface DataSourceProvider {
	DataSource getDataSource() throws Throwable;
}
package me.mos.lnk.database;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午7:57:31
 */
public class JdbcTemplateProvider {

	private static final class JdbcTemplateHolder {
		private static final NamedParameterJdbcTemplate JDBC_TEMPLATE = createJdbcTemplate();

		private static NamedParameterJdbcTemplate createJdbcTemplate() {
			DataSourceProvider dataSourceProvider = new DefaultDataSourceProvider();
			try {
				return new NamedParameterJdbcTemplate(dataSourceProvider.getDataSource());
			} catch (Throwable e) {
				throw new IllegalStateException(e);
			}
		}
	}

	private JdbcTemplateProvider() {
		super();
	}

	public static NamedParameterJdbcTemplate getJdbcTemplate() {
		return JdbcTemplateHolder.JDBC_TEMPLATE;
	}
}
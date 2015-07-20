package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年7月16日 下午2:00:16
 */
public class DBMeta {

	public static void main(String[] args) throws Throwable {
		String url = "jdbc:mysql://10.146.30.209:3307";
		String username = "dap";
		String password = "meandpay123";
		DriverManagerDataSource dataSource = createDataSource(url, username, password);
		Connection connection = dataSource.getConnection();
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet catalogs = dbmd.getCatalogs();
		while (catalogs.next()) {
			String catalog = catalogs.getString(1);
			System.err.println("\ncatalog " + catalog);
			ResultSet tables = dbmd.getTables(catalog, null, null, new String[] { "TABLE" });
			StringBuilder sb = new StringBuilder();
			while (tables.next()) {
				sb
				.append(tables.getString(4))
				.append(" ")
				.append(tables.getString(3))
				.append("\n\t");
			}
			System.out.println("\t" + sb.toString());
		}
	}

	private static DriverManagerDataSource createDataSource(String url, String username, String password) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
}
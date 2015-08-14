package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.CollectionUtils;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年7月14日 下午8:39:12
 */
public class LocalJob {

	private static NamedParameterJdbcTemplate jdbcTemplate;

	static {
		try {
			String url = "jdbc:mysql://10.146.30.209:3307/ti_dap_db?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useServerPrepStmts=true&useCursorFetch=true";
			jdbcTemplate = new NamedParameterJdbcTemplate(createDataSource(url, "dap", "meandpay123"));
//			String url = "jdbc:mysql://10.146.30.205:3306/ac_rcs_db?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useServerPrepStmts=true&useCursorFetch=true";
//			jdbcTemplate = new NamedParameterJdbcTemplate(createDataSource(url, "root", "meandpay123"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	static String sql0 = "SELECT agentPartyGrpId, gmt_date FROM ti_dap_db.AgentT0StlAgreement group by agentPartyGrpId, gmt_date order by gmt_date desc";

	static String sql1 = "SELECT id FROM ti_dap_db.AgentT0StlAgreement where agentPartyGrpId = :agentPartyGrpId and gmt_date = :gmt_date;";

	static String sql2 = "DELETE FROM ti_dap_db.AgentT0StlAgreement where id = :id;";

	static String sql3 = "SELECT agentPartyGrpId, gmt_date FROM ti_dap_db.SecAgentT0StlAgreement group by agentPartyGrpId, gmt_date order by gmt_date desc";

	static String sql4 = "SELECT id FROM ti_dap_db.SecAgentT0StlAgreement where agentPartyGrpId = :agentPartyGrpId and gmt_date = :gmt_date;";

	static String sql5 = "DELETE FROM ti_dap_db.SecAgentT0StlAgreement where id = :id;";

	static String sql6 = "SELECT agentPartyGrpId, gmt_date FROM ti_dap_db.ThirdAgentT0StlAgreement group by agentPartyGrpId, gmt_date order by gmt_date desc";

	static String sql7 = "SELECT id FROM ti_dap_db.ThirdAgentT0StlAgreement where agentPartyGrpId = :agentPartyGrpId and gmt_date = :gmt_date;";

	static String sql8 = "DELETE FROM ti_dap_db.ThirdAgentT0StlAgreement where id = :id;";

	static String ruleSQL = "SELECT rl.idRules idRules FROM ac_rcs_db.RuleLog rl left join ac_txn_db.TxnCtrl tc "
			+ "on rl.txnId = tc.txnId where tc.txnTime "
			+ "between '2015-02-01 00:00:00' AND '2015-02-31 23:59:59';";
	
	public static void main1233(String[] args) throws Throwable {
		List<Map<String, Object>> rules = jdbcTemplate.queryForList(ruleSQL, new HashMap<String, Object>());
		Set<String> ids = new HashSet<String>();
		for (Map<String, Object> map : rules) {
			Object obj = map.get("idRules");
			String[] s = StringUtils.split(String.valueOf(obj), ",");
			ids.addAll(Arrays.asList(s));
		}
		System.out.println("总数 : " + ids.size());
	}
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.execute(new Runnable() {
			public void run() {
				LocalJob.run("一代T0", sql0, sql1, sql2);
			}
		});
		service.execute(new Runnable() {
			public void run() {
				LocalJob.run("二代T0", sql3, sql4, sql5);
			}
		});
		service.execute(new Runnable() {
			public void run() {
				LocalJob.run("三代T0", sql6, sql7, sql8);
			}
		});
	}

	private static void run(String name, String sql0, String sql1, String sql2) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql0, new HashMap<String, Object>());
		System.err.println(name + " : " + list.size());
		String ids = "";
		int i = 0;
		for (Map<String, Object> map : list) {
			try {
				List<Map<String, Object>> el = jdbcTemplate.queryForList(sql1, map);
				if (CollectionUtils.isEmpty(el)) {
					continue;
				}
				if (el.size() >= 2) {
					i++;
					ids += (el.get(0).get("id") + " ");
					jdbcTemplate.update(sql2, el.get(0));
					if (i % 100 == 0) {
						System.err.println(name + " DEL " + ids);
						ids = "";
					}
				}
			} catch (Throwable e) {
				e.printStackTrace(System.err);
			}
		}
		System.err.println(name + " DEL " + ids);
		System.err.println(name + " Execute Success.");
	}

	static DriverManagerDataSource createDataSource(String url, String username, String password) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
}

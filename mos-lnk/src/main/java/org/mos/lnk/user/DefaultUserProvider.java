package org.mos.lnk.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mos.lnk.database.JdbcTemplateProvider;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午10:09:05
 */
public class DefaultUserProvider implements UserProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static class UserProviderHolder {
		private static final UserProvider USER_PROVIDER = new DefaultUserProvider();
	}
	
	private static final String SELECT_SQL = "SELECT "
			+ "`lnk-user`.`mid`, "
			+ "`lnk-user`.`party_id`, "
			+ "`lnk-user`.`nick`, "
			+ "`lnk-user`.`passwd`, "
			+ "`lnk-user`.`avatar`, "
			+ "`lnk-user`.`weixin`, "
			+ "`lnk-user`.`qq`, "
			+ "`lnk-user`.`email`, "
			+ "`lnk-user`.`telephone`, "
			+ "`lnk-user`.`phone`, "
			+ "`lnk-user`.`address`, "
			+ "`lnk-user`.`ip`, "
			+ "`lnk-user`.`lng`, "
			+ "`lnk-user`.`lat`, "
			+ "`lnk-user`.`status`, "
			+ "`lnk-user`.`extend`, "
			+ "`lnk-user`.`gmt_created`, "
			+ "`lnk-user`.`gmt_modified` FROM `mos-lnk`.`lnk-user` WHERE `lnk-user`.`mid` = :mid;";
	
	private static final String SELECT_ONLINE_SQL = "SELECT "
			+ "`lnk-user`.`mid`, "
			+ "`lnk-user`.`party_id`, "
			+ "`lnk-user`.`nick`, "
			+ "`lnk-user`.`passwd`, "
			+ "`lnk-user`.`avatar`, "
			+ "`lnk-user`.`weixin`, "
			+ "`lnk-user`.`qq`, "
			+ "`lnk-user`.`email`, "
			+ "`lnk-user`.`telephone`, "
			+ "`lnk-user`.`phone`, "
			+ "`lnk-user`.`address`, "
			+ "`lnk-user`.`ip`, "
			+ "`lnk-user`.`lng`, "
			+ "`lnk-user`.`lat`, "
			+ "`lnk-user`.`status`, "
			+ "`lnk-user`.`extend`, "
			+ "`lnk-user`.`gmt_created`, "
			+ "`lnk-user`.`gmt_modified` FROM `mos-lnk`.`lnk-user` WHERE `lnk-user`.`status` = '" + User.ONLINE + "';";
	
	
	private static final String CREATE_USR_SQL = "INSERT INTO `mos-lnk`.`lnk-user` "
			+ "(`party_id`, `nick`, `passwd`, `avatar`, `weixin`, `qq`, `email`, `telephone`, `phone`, `address`, `ip`, `lng`, `lat`, `status`, `extend`, `gmt_created`, `gmt_modified`) "
			+ "VALUES "
			+ "(:party_id, :nick, :passwd, :avatar, :weixin, :qq, :email, :telephone, :phone, :address, :ip, :lng, :lat, :status, :extend, :gmt_created, :gmt_modified);";

	private static final String UPDATE_USER_STATUS_SQL = "UPDATE `mos-lnk`.`lnk-user` SET `lnk-user`.`status` = :status, `lnk-user`.`gmt_modified` = NOW() WHERE `lnk-user`.`mid` = :mid;";
	
	private static final String UPDATE_USER_SQL = "UPDATE `mos-lnk`.`lnk-user` SET "
			+ "`party_id` = :party_id, "
			+ "`nick` = :nick, "
			+ "`passwd` = :passwd, "
			+ "`avatar` = :avatar, "
			+ "`weixin` = :weixin, "
			+ "`qq` = :qq, "
			+ "`email` = :email, "
			+ "`telephone` = :telephone, "
			+ "`phone` = :phone, "
			+ "`gmt_modified` = NOW() WHERE `mid` = :mid";
	
	private DefaultUserProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	public static UserProvider getInstance() {
		return UserProviderHolder.USER_PROVIDER;
	}

	@Override
	public int update(User user) {
		return jdbcTemplate.update(UPDATE_USER_SQL, new BeanPropertySqlParameterSource(user));
	}

	@Override
	public List<User> queryOnline() {
		return jdbcTemplate.query(SELECT_ONLINE_SQL, new UserMapper());
	}

	@Override
	public User query(long mid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		return jdbcTemplate.queryForObject(SELECT_SQL, paramMap, new UserMapper());
	}

	@Override
	public long save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(CREATE_USR_SQL, new BeanPropertySqlParameterSource(user), keyHolder, new String[] { "mid" });
		return keyHolder.getKey().longValue();
	}

	@Override
	public int online(long mid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		paramMap.put("status", User.ONLINE);
		return jdbcTemplate.update(UPDATE_USER_STATUS_SQL, paramMap);
	}

	@Override
	public int offline(long mid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		paramMap.put("status", User.OFFLINE);
		return jdbcTemplate.update(UPDATE_USER_STATUS_SQL, paramMap);
	}

	private class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			long mid = rs.getLong("mid");
			if (mid <= 0L) {
				return null;
			}
			User user = new User();
			user.setMid(mid);
			user.setAddress(rs.getString("address"));
			user.setAvatar(rs.getString("avatar"));
			user.setEmail(rs.getString("email"));
			user.setExtend(rs.getString("extend"));
			user.setGmt_created(rs.getDate("gmt_created"));
			user.setGmt_modified(rs.getDate("gmt_modified"));
			user.setIp(rs.getString("ip"));
			user.setLat(rs.getDouble("lat"));
			user.setLng(rs.getDouble("lng"));
			user.setNick(rs.getString("nick"));
			user.setParty_id(rs.getString("party_id"));
			user.setPasswd(rs.getString("passwd"));
			user.setPhone(rs.getString("phone"));
			user.setQq(rs.getString("qq"));
			user.setStatus(rs.getString("status"));
			user.setTelephone(rs.getString("telephone"));
			user.setWeixin(rs.getString("weixin"));
			return user;
		}
	}
}
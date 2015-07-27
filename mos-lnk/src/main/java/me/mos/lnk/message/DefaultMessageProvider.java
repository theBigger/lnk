package me.mos.lnk.message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import me.mos.lnk.database.JdbcTemplateProvider;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月3日 上午11:54:08
 */
public class DefaultMessageProvider implements MessageProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static class MessageProviderHolder {
		private static final MessageProvider MESSAGE_PROVIDER = new DefaultMessageProvider();
	}

	private static final String SAVE_MESSAGE_SQL = "INSERT INTO `mos-lnk`.`lnk-message` " + "(`mid`, `party_id`, `nick`, `avatar`, `tid`, `body`, `gmt_created`) " + "VALUES "
			+ "(:mid, :party_id, :nick, :avatar, :tid, :body, :gmt_created)";

	private static final String DEL_MESSAGE_SQL = "DELETE FROM `mos-lnk`.`lnk-message` WHERE id = :id;";

	private static final String QUERY_MESSAGE_SQL = "SELECT " + "`lnk-message`.`id`, `lnk-message`.`mid`, `lnk-message`.`party_id`, "
			+ "`lnk-message`.`nick`, `lnk-message`.`avatar`, `lnk-message`.`tid`, "
			+ "`lnk-message`.`body`, `lnk-message`.`gmt_created` FROM `mos-lnk`.`lnk-message` where `lnk-message`.`tid` = :tid;";

	private DefaultMessageProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	public static MessageProvider getInstance() {
		return MessageProviderHolder.MESSAGE_PROVIDER;
	}

	@Override
	public long save(Message message) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SAVE_MESSAGE_SQL, new BeanPropertySqlParameterSource(message), keyHolder, new String[] { "id" });
		return keyHolder.getKey().longValue();
	}

	@Override
	public List<Message> queryMessageList(long tid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tid", tid);
		return jdbcTemplate.query(QUERY_MESSAGE_SQL, paramMap, new MessageMapper());
	}

	@Override
	public int delete(long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return jdbcTemplate.update(DEL_MESSAGE_SQL, paramMap);
	}

	private class MessageMapper implements RowMapper<Message> {
		@Override
		public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			if (id <= 0L) {
				return null;
			}
			Message message = new Message();
			message.setAvatar(rs.getString("avatar"));
			message.setBody(rs.getString("body"));
			message.setGmt_created(rs.getLong("gmt_created"));
			message.setId(id);
			message.setMid(rs.getLong("mid"));
			message.setNick(rs.getString("nick"));
			message.setParty_id(rs.getString("party_id"));
			message.setTid(rs.getLong("tid"));
			return message;
		}
	}
}
package me.mos.lnk.groups.message;

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
public class DefaultGroupMessageProvider implements GroupMessageProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static class MessageProviderHolder {
		private static final GroupMessageProvider GROUP_MESSAGE_PROVIDER = new DefaultGroupMessageProvider();
	}

	private static final String SAVE_GROUP_MESSAGE_SQL = "INSERT INTO `mos_lnk`.`lnk_group_message` " + "(`mid`, `party_id`, `nick`, `avatar`, `group_id`, `body`, `gmt_created`) " + "VALUES "
			+ "(:mid, :party_id, :nick, :avatar, :group_id, :body, :gmt_created)";

	private static final String DEL_GROUP_MESSAGE_SQL = "DELETE FROM `mos_lnk`.`lnk_group_message` WHERE id = :id;";

	private static final String QUERY_GROUP_MESSAGE_SQL = "SELECT " + "`lnk_group_message`.`id`, `lnk_group_message`.`mid`, `lnk_group_message`.`party_id`, "
			+ "`lnk_group_message`.`nick`, `lnk_group_message`.`avatar`, `lnk_group_message`.`group_id`, "
			+ "`lnk_group_message`.`body`, `lnk_group_message`.`gmt_created` FROM `mos_lnk`.`lnk_group_message` where `lnk_group_message`.`group_id` = :group_id;";
	
	private DefaultGroupMessageProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	public static GroupMessageProvider getInstance() {
		return MessageProviderHolder.GROUP_MESSAGE_PROVIDER;
	}

	@Override
	public long save(GroupMessage message) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(SAVE_GROUP_MESSAGE_SQL, new BeanPropertySqlParameterSource(message), keyHolder, new String[] { "id" });
		return keyHolder.getKey().longValue();
	}

	@Override
	public List<GroupMessage> queryGroupMessageList(long groupId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("group_id", groupId);
		return jdbcTemplate.query(QUERY_GROUP_MESSAGE_SQL, paramMap, new GroupMessageMapper());
	}

	@Override
	public int delete(long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return jdbcTemplate.update(DEL_GROUP_MESSAGE_SQL, paramMap);
	}

	private class GroupMessageMapper implements RowMapper<GroupMessage> {
		@Override
		public GroupMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			if (id <= 0L) {
				return null;
			}
			GroupMessage message = new GroupMessage();
			message.setAvatar(rs.getString("avatar"));
			message.setBody(rs.getString("body"));
			message.setGmt_created(rs.getLong("gmt_created"));
			message.setId(id);
			message.setMid(rs.getLong("mid"));
			message.setNick(rs.getString("nick"));
			message.setParty_id(rs.getString("party_id"));
			message.setGroup_id(rs.getLong("group_id"));
			return message;
		}
	}
}
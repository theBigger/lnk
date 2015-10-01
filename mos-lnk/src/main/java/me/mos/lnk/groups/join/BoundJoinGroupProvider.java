package me.mos.lnk.groups.join;

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
 * @since 2015年6月2日 下午10:09:05
 */
public class BoundJoinGroupProvider implements JoinGroupProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static class GroupUserProviderHolder {
		private static final JoinGroupProvider GROUP_USER_PROVIDER = new BoundJoinGroupProvider();
	}
	
	private static final String SELECT_SQL = "SELECT "
	        + "`lnk_join_group`.`id` "
            + "`lnk_join_group`.`group_id` "
            + "`lnk_join_group`.`mid` "
            + "`lnk_join_group`.`gmt_created` "
	        + "FROM `mos_lnk`.`lnk_join_group` "
	        + "WHERE "
	        + "`lnk_join_group`.`group_id` = :group_id AND "
	        + "`lnk_join_group`.`mid` = :mid;";
	
	private static final String SELECT_MID_SQL = "SELECT `lnk_join_group`.`mid` FROM `mos_lnk`.`lnk_join_group` WHERE `lnk_join_group`.`group_id` = :group_id;";
	
	private static final String CREATE_JOIN_GROUP_SQL = "INSERT INTO `mos_lnk`.`lnk_join_group` "
			+ "(`group_id`, `mid`, `gmt_created`) "
			+ "VALUES "
			+ "(:group_id, :mid, NOW());";

	private static final String UPDATE_JOIN_GROUP_SQL = "UPDATE `mos_lnk`.`lnk_join_group` SET "
			+ "`group_id` = :group_id, "
			+ "`mid` = :mid WHERE `id` = :id";

    private static final String DELETE_JOIN_GROUP_SQL = "DELETE FROM `mos_lnk`.`lnk_join_group` WHERE "
            + "`lnk_join_group`.`group_id` = :group_id AND "
            + "`lnk_join_group`.`mid` = :mid;";
	
	private BoundJoinGroupProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	public static JoinGroupProvider getInstance() {
		return GroupUserProviderHolder.GROUP_USER_PROVIDER;
	}

    @Override
    public int remove(long groupId, long mid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("group_id", groupId);
        paramMap.put("mid", mid);
        return jdbcTemplate.update(DELETE_JOIN_GROUP_SQL, paramMap);
    }

    @Override
    public long save(JoinGroup joinGroup) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(CREATE_JOIN_GROUP_SQL, new BeanPropertySqlParameterSource(joinGroup), keyHolder, new String[] { "id" });
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(JoinGroup joinGroup) {
        return jdbcTemplate.update(UPDATE_JOIN_GROUP_SQL, new BeanPropertySqlParameterSource(joinGroup));
    }

    @Override
    public List<Long> getUserInGroup(long groupId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("group_id", groupId);
        return jdbcTemplate.queryForList(SELECT_MID_SQL, paramMap, Long.class);
    }

    @Override
    public JoinGroup get(long groupId, long mid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("group_id", groupId);
        paramMap.put("mid", mid);
        return jdbcTemplate.queryForObject(SELECT_SQL, paramMap, new GroupUserMapper());
    }

    private class GroupUserMapper implements RowMapper<JoinGroup> {
		@Override
		public JoinGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			if (id <= 0L) {
				return null;
			}
			JoinGroup groupUser = new JoinGroup();
			groupUser.setGmt_created(rs.getDate("gmt_created"));
			groupUser.setId(id);
			groupUser.setGroup_id(rs.getLong("group_id"));
			groupUser.setMid(rs.getLong("mid"));
			return groupUser;
		}
	}
}
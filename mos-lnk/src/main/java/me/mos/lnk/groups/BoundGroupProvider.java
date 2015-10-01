package me.mos.lnk.groups;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
public class BoundGroupProvider implements GroupProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static class GroupProviderHolder {
		private static final GroupProvider GROUP_PROVIDER = new BoundGroupProvider();
	}
	
	private static final String SELECT_SQL = "SELECT "
			+ "`lnk_group`.`id`, "
			+ "`lnk_group`.`name`, "
            + "`lnk_group`.`owner_mid`, "
			+ "`lnk_group`.`tags`, "
			+ "`lnk_group`.`gmt_created`, "
			+ "`lnk_group`.`gmt_modified` FROM `mos_lnk`.`lnk_group` WHERE `lnk_group`.`id` = :id;";
	
	
	private static final String CREATE_GROUP_SQL = "INSERT INTO `mos_lnk`.`lnk_group` "
			+ "(`name`, `owner_mid`, `tags`, `gmt_created`, `gmt_modified`) "
			+ "VALUES "
			+ "(:name, :owner_mid, :tags, NOW(), NOW());";

	private static final String UPDATE_GROUP_SQL = "UPDATE `mos_lnk`.`lnk_group` SET "
			+ "`name` = :name, "
			+ "`owner_mid` = :owner_mid, "
			+ "`tags` = :tags, "
			+ "`gmt_modified` = NOW() WHERE `id` = :id";
	
	private BoundGroupProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	public static GroupProvider getInstance() {
		return GroupProviderHolder.GROUP_PROVIDER;
	}

	@Override
    public long save(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(CREATE_GROUP_SQL, new BeanPropertySqlParameterSource(group), keyHolder, new String[] { "id" });
        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Group group) {
        return jdbcTemplate.update(UPDATE_GROUP_SQL, new BeanPropertySqlParameterSource(group));
    }

    @Override
    public Group get(long groupId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", groupId);
        return jdbcTemplate.queryForObject(SELECT_SQL, paramMap, new GroupMapper());
    }

    private class GroupMapper implements RowMapper<Group> {
		@Override
		public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			if (id <= 0L) {
				return null;
			}
			Group group = new Group();
			group.setGmt_created(rs.getDate("gmt_created"));
			group.setGmt_modified(rs.getDate("gmt_modified"));
			group.setId(id);
			group.setName(rs.getString("name"));
			group.setOwner_mid(rs.getLong("owner_mid"));
			group.setTags(rs.getString("tags"));
			return group;
		}
	}
}
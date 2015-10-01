package me.mos.lnk.groups.join;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午10:05:10
 */
public interface JoinGroupProvider {
	long save(JoinGroup joinGroup);
	
	int update(JoinGroup joinGroup);
	
	int remove(long groupId, long mid);
	
	JoinGroup get(long groupId, long mid);
	
	List<Long> getUserInGroup(long groupId);
}
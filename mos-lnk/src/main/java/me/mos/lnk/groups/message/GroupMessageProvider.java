package me.mos.lnk.groups.message;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月3日 上午11:44:31
 */
public interface GroupMessageProvider {

	long save(GroupMessage message);
	
	List<GroupMessage> queryGroupMessageList(long groupId);
	
	int delete(long id);
}
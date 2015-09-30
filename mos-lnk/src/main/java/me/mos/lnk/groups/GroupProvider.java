package me.mos.lnk.groups;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午10:05:10
 */
public interface GroupProvider {
	long save(Group group);
	
	int update(Group group);
	
	Group get(long groupId);
}
package me.mos.lnk.user;

import java.util.List;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午10:05:10
 */
public interface UserProvider {
	User query(long mid);
	long save(User user);
	
	int update(User user);
	
	int online(long mid);
	int offline(long mid);
	
	int uploadPushId(long mid, String pushId);
	
	List<User> queryOnline();
}
package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author 刘飞
 * 
 * @version 1.0.0
 * @since 2015年6月1日 上午10:36:25
 */
public class Cal {
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		System.out.println(week(-1));
		System.out.println(week(-2));
		System.out.println(week(-3));
		System.out.println(week(-4));
	}

	public static String getWeekFirstDay(int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
		// 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
		cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		// 分别取得当前日期的年、月、日
		return df.format(cal.getTime());
	}

	public static String week(int offset) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
		calendar.add(java.util.Calendar.WEEK_OF_YEAR, offset);
		return df.format(calendar.getTime());
	}
}
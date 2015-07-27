package me.mos.lnk.serializer;

import java.io.Writer;

/**
 * 表示一个序列化器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月11日 下午11:01:16
 */
public interface Serializer {

	void serialize(Object bean, Writer writer);

	String serialize(Object bean);

	<T> T deserialize(Class<T> clazz, String data);
}
package org.mos.lnk.serializer;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 使用XStream实现的序列化器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月11日 下午11:01:53
 */
public class XStreamSerializer implements Serializer {

	private final XStream xstream;

	public XStreamSerializer() {
		this(true);
	}

	public XStreamSerializer(boolean autodetectAnnotations) {
		super();
		xstream = new XStream();
		xstream.autodetectAnnotations(autodetectAnnotations);
	}

	@Override
	public void serialize(Object bean, Writer writer) {
		Class<?> clazz = bean.getClass();
		XStreamAlias alias = clazz.getAnnotation(XStreamAlias.class);
		if (alias != null) {
			xstream.alias(alias.value(), clazz);
		}
		xstream.toXML(bean, writer);
	}

	@Override
	public String serialize(Object bean) {
		Class<?> clazz = bean.getClass();
		XStreamAlias alias = clazz.getAnnotation(XStreamAlias.class);
		if (alias != null) {
			xstream.alias(alias.value(), clazz);
		}
		return xstream.toXML(bean);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, String data) {
		XStreamAlias alias = clazz.getAnnotation(XStreamAlias.class);
		if (alias != null) {
			xstream.alias(alias.value(), clazz);
		}
		return clazz.cast(xstream.fromXML(data));
	}
}
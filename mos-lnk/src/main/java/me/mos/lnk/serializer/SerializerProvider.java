package me.mos.lnk.serializer;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月12日 上午10:39:18
 */
public class SerializerProvider {

	private static class SerializerHolder {
		private static final Serializer JSON = new JacksonSerializer();
		private static final Serializer XSTREAM = new XStreamSerializer();
	}
	
	public static <T> T deserialize(Class<T> type, String data) {
		return SerializerProvider.currentSerializer().deserialize(type, data);
	}
	
	public static String serialize(Object bean) {
		return SerializerProvider.currentSerializer().serialize(bean);
	}

	public static Serializer currentSerializer() {
		return SerializerProvider.json();
	}
	
	public static Serializer json() {
		return SerializerHolder.JSON;
	}
	
	public static Serializer xstream() {
		return SerializerHolder.XSTREAM;
	}
}
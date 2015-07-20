package org.mos.lnk.serializer;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月12日 上午10:39:18
 */
public class SerializerUtils {

	private static class SerializerHolder {
		private static final Serializer GSON = new GsonSerializer(false);
		private static final Serializer XSTREAM = new XStreamSerializer();
	}

	public static Serializer currentSerializer() {
		return SerializerUtils.gson();
	}
	
	public static Serializer gson() {
		return SerializerHolder.GSON;
	}
	
	public static Serializer xstream() {
		return SerializerHolder.XSTREAM;
	}
}
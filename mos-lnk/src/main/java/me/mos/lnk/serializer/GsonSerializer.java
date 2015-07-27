package me.mos.lnk.serializer;

import java.io.Writer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson实现的序列化器.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月11日 下午11:01:31
 */
public class GsonSerializer implements Serializer {

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private final Gson gson;
	
	public GsonSerializer() {
		this(false, true, true);
	}
	
	public GsonSerializer(boolean serializeNulls) {
		this(false, serializeNulls, true);
	}
	
	public GsonSerializer(boolean pretty, boolean serializeNulls, boolean disableHtmlEscaping) {
		this(pretty, serializeNulls, disableHtmlEscaping, YYYY_MM_DD_HH_MM_SS);
	}

	public GsonSerializer(boolean pretty, boolean serializeNulls, boolean disableHtmlEscaping, String dateFormat) {
		this(pretty, serializeNulls, true, disableHtmlEscaping, dateFormat);
	}

	public GsonSerializer(boolean pretty, boolean serializeNulls, boolean complexMapKey, boolean disableHtmlEscaping, String dateFormat) {
		super();
		GsonBuilder builder = 
				new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
				.setDateFormat(dateFormat)
				;
		
		if (pretty) {
			builder.setPrettyPrinting();// 格式化
		}
		if (serializeNulls) {
			builder.serializeNulls();
		}
		if (complexMapKey) {
			builder.enableComplexMapKeySerialization();
		}
		if (disableHtmlEscaping) {
			builder.disableHtmlEscaping();
		}
		gson = builder.create();
	}

	@Override
	public void serialize(Object bean, Writer writer) {
		gson.toJson(bean, writer);
	}

	@Override
	public String serialize(Object bean) {
		return gson.toJson(bean);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, String data) {
		return gson.fromJson(data, clazz);
	}
}
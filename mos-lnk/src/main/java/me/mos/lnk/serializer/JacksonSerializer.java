package me.mos.lnk.serializer;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月12日 下午11:20:20
 */
public class JacksonSerializer implements Serializer {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private final ObjectMapper objectMapper;

    public JacksonSerializer() {
        super();
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.setDateFormat(new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS));
    }

    @Override
    public void serialize(Object bean, Writer writer) {
        try {
            objectMapper.writeValue(writer, bean);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String serialize(Object bean) {
        try {
            return objectMapper.writeValueAsString(bean);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, String data) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

/**
 * 
 */
package com.vyd.base.utils.json;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author Dirk
 * @date 2017年6月1日 上午10:50:44
 */
public class JsonUtil {
	private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final ObjectMapper mapper;

	public ObjectMapper getMapper() {

		return mapper;
	}

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);

		mapper = new ObjectMapper();
		mapper.setDateFormat(dateFormat);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// json字符串转对象时,忽略不存在的key
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// null 对象忽略
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {

			private static final long serialVersionUID = -5684967182250938195L;

			@Override
			public Object findSerializer(Annotated a) {

				if (a instanceof AnnotatedMethod) {
					AnnotatedElement m = a.getAnnotated();
					DateTimeFormat an = m.getAnnotation(DateTimeFormat.class);
					if (an != null) {
						if (!TIME_FORMAT.equals(an.pattern())) {
							return new JsonDateSerializer(an.pattern());
						}
					}
				}
				return super.findSerializer(a);
			}
		});
	}

	public static String toJson(Object obj) {

		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("转换json字符失败!", e);
		}
	}

	public static <T> T toObject(String json, Class<T> clazz) {

		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException("将json字符转换为对象时失败!\n数据：" + json, e);
		}
	}

	public static <T> List<T> toObjectList(String json, JavaType javaType) {

		try {
			return mapper.readValue(json, javaType);
		} catch (IOException e) {
			throw new RuntimeException("将json字符转换为对象时失败!\n数据：" + json, e);
		}
	}

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {

		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static class JsonDateSerializer extends JsonSerializer<Date> {

		private SimpleDateFormat dateFormat;

		public JsonDateSerializer(String format) {

			dateFormat = new SimpleDateFormat(format);
		}

		@Override
		public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
				throws IOException, JsonProcessingException {

			String value = dateFormat.format(date);
			gen.writeString(value);
		}
	}
}
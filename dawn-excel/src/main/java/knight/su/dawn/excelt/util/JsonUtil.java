package knight.su.dawn.excelt.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	private JsonUtil(){
		super();
	}
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper objectMapper;
	/** 格式化时间的string */
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	static {
		objectMapper = new ObjectMapper();
		// 去掉默认的时间戳格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 设置为中国北京时区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		// 空值不序列化
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		// 反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		// 序列化时，期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// 空值转换-异常情况处理
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		//转换数组
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	}

	/**
	 * json 转换成 Object
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Object(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			logger.error("JsonUtil json2Object execute error", e);
		}
		return null;
	}

	public static <T> T json2Object(String json, TypeRef<T> tr) {
		try {
			return objectMapper.readValue(json, tr.getType());
		} catch (Exception e) {
			logger.error("JsonUtil json2Object execute error", e);
		}
		return null;
	}
	
	public static <T> List<T> json2List(String json) {
		return json2Object(json, new TypeRef<List<T>>() {
		});
	}

	public static <T> T jsonToObject(String json, TypeReference<T> tr) {
		try {
			return objectMapper.readValue(json, tr);
		} catch (IOException e) {
			logger.error("JsonUtil json2Object execute error", e);
		}
		return null;
	}

	/**
	 * obj 转换成json
	 * 
	 * @param entity
	 * @return
	 */
	public static <T> String object2Json(T entity) {
		try {
			return objectMapper.writeValueAsString(entity);
		} catch (IOException e) {
			logger.error("JsonUtil object2Json execute error", e);
		}
		return null;
	}

	/**
	 * obj对象 转换成树型JSON
	 * 
	 * @param obj
	 * @return
	 */
	public static JsonNode object2TreeJson(Object obj) {
		try {
			return objectMapper.valueToTree(obj);
		} catch (Exception e) {
			logger.error("JsonUtil object2TreeJson execute error", e);
		}
		return null;
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	public static <T> Object fromListJson(String jsonString, Class<T> pojoClass) {
		try {
			JavaType javaType = getCollectionType(ArrayList.class, pojoClass);
			return objectMapper.readValue(jsonString, javaType);
		} catch (Exception e) {
			logger.error("fromListJson-error", e);
		}
		return null;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String string2Json(String input) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			switch (c) {
//			case '\"':
//				sb.append("\\\"");
//				break;
//			case '\\':
//				sb.append("\\\\");
//				break;
//			case '/':
//				sb.append("\\/");
//				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

}

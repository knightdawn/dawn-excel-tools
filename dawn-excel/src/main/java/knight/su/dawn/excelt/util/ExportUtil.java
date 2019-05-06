package knight.su.dawn.excelt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import knight.su.dawn.excelt.exp.validate.ValidateAssert;
import org.apache.commons.collections.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ExportUtil {

	private static final Logger logger = LoggerFactory.getLogger("CCSExportUtil");

	/**
	 * GBK 一个全角字符占2个字节
	 */
	public static final int BYTE_NUMBER_OF_GBK_CHAR = 2;

	/**
	 * utf-8 一个全角字符占3个字节
	 */
	public static final int BYTE_NUMBER_OF_UTF8_CHAR = 3;

	private static Map<String, List<Field>> fieldsCache = new HashMap<>();
	
	private static ObjectMapper m = new ObjectMapper();
//	private static Map<String, List<FieldNode>> fieldsCacheAsm = new HashMap<>();
	

	private ExportUtil() {
		super();
	}

	/**
	 * 判断全角 半角
	 * 
	 * @param c
	 * @return 半角 true, 全角 false
	 */
	public static boolean isDbcCase(char c) {
		if (c >= 32 && c <= 127) {// 基本拉丁字母，键盘上可见的空格，数字，字母，符号
			return true;
		} else if (c >= 65377 && c <= 65439) {// 文半角片假名和符号
			return true;
		}
		return false;
	}

	/**
	 * 字符串长度（区分半角、全角）
	 * 
	 * @param input
	 * @param code
	 * @return
	 */
	public static int length(String input, int code) {
		int len = 0;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			len = isDbcCase(c) ? len + 1 : len + code;
		}
		return len;
	}

	/**
	 * object to string
	 * 
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		if (null == o) {
			return "";
		} else {
			// 此处是为了解决Java浮点数超过11位调用toString转换字符串就会采用科学计数法的问题
			if (o instanceof Float || o instanceof Double) {
				BigDecimal bigDecimal = new BigDecimal(o.toString());
				return bigDecimal.toString();
			} else {
				return o.toString().trim();
			}
		}
	}

//	/**
//	 * Map 转 Bean 835883 2017/6/6
//	 *
//	 * @Description
//	 * @param map
//	 * @param beanClass
//	 * @return
//	 * @throws IllegalAccessException
//	 * @throws InstantiationException
//	 * @throws InvocationTargetException
//	 */
//	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
//			throws InstantiationException, IllegalAccessException, InvocationTargetException {
//		if (map == null) {
//			return null;
//		}
//
//		Object obj = beanClass.newInstance();
//		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
//
//		return obj;
//	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map objectToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		return new BeanMap(obj);
	}

//	/**
//	 *
//	 * @param obj
//	 * @return
//	 */
//	public static Map<String, Object> beanToMap(Object obj) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		try {
//			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
//			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
//			for (int i = 0; i < descriptors.length; i++) {
//				String name = descriptors[i].getName();
//				if (!"class".equals(name)) {
//					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
//				}
//			}
//		} catch (Exception e) {
//			logger.error("beanToMap-error:{}", e);
//		}
//		return params;
//	}

	/**
	 * List<Object> to List<Map<Object, Object>>
	 * 
	 * BeanMap方式，但只能转换为List<Map<Object, Object>>
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map> objsToListMap(List<? extends Object> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<Map> result = new LinkedList<>();
		for (Object obj : list) {
			result.add(new BeanMap(obj));
		}
		return result;
	}

	/**
	 * List<Object> to List<Map<String, Object>>
	 * 
	 * Field方式获取 
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> objsToListMapReflect(List<? extends Object> list, Class<?> type) {
		ValidateAssert.notNull(type, "type is null");
		List<Map<String, Object>> listMap = new ArrayList<>();
		List<Field> fields = getAllFields(type);
		try {
			for (Object o : list) {
				Map<String, Object> result = new HashMap<String, Object>();
				for (Field field : fields) {
					ReflectionUtil.makeAccessible(field);
					result.put(field.getName(), field.get(o));
				}
				listMap.add(result);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.error("objsToListMapReflect-error:{}", e);
		}
		return listMap;
	}
	
//	/**
//	 * 
//	 * @param list
//	 * @param type
//	 * @return
//	 */
//	public static List<Map<String, Object>> objsToListMapAsm(List<? extends Object> list, Class<?> type) {
//		ValidateAssert.notNull(type, "type is null");
//		List<Map<String, Object>> listMap = new ArrayList<>();
//		List<FieldNode> fields = getAllFieldsByAsm(type);
//		try {
//			for (Object o : list) {
//				Map<String, Object> result = new HashMap<String, Object>();
//				for (FieldNode field : fields) {
//					result.put(field.name, field.);
//				}
//				listMap.add(result);
//			}
//		} catch (IllegalArgumentException e) {
//			logger.error("objsToListMapAsm-error:{}", e);
//		}
//		return listMap;
//	}
//	
//	/**
//	 * 
//	 * @param type
//	 * @return
//	 */
//	private static List<FieldNode> getAllFieldsByAsm(Class<?> type) {
//		List<FieldNode> fieldList = fieldsCacheAsm.get(type.getName());
//		if (CollectionUtils.isEmpty(fieldList)) {
//			ClassReader reader;
//			try {
//				reader = new ClassReader(type.getName());
//				ClassNode cn = new ClassNode();
//				reader.accept(cn, 0);
//				fieldList = cn.fields;
//				fieldsCacheAsm.put(type.getName(), fieldList);
//			} catch (IOException e) {
//				logger.error("getAllFieldsByAsm-error:{}", e);
//			}
//		}
//		return fieldList;
//	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	private static List<Field> getAllFields(Class<?> type) {
		List<Field> fieldList = fieldsCache.get(type.getName());
		if (CollectionUtils.isEmpty(fieldList)) {
			fieldList = new ArrayList<>();
			while (type != null && !type.getName().toLowerCase().equals("java.lang.object")) {
				fieldList.addAll(Arrays.asList(type.getDeclaredFields()));
				type = type.getSuperclass();
			}
			fieldsCache.put(type.getName(), fieldList);
		}
		return fieldList;
	}
	
	/**
	 * List<Object> to List<Map<String, Object>>
	 * 
	 * jackson 方式
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> objsToListMapJackson(List<? extends Object> list) {
		List<Map<String, Object>> listMap = new ArrayList<>();
		try {
			for (Object o : list) {
				@SuppressWarnings("unchecked")
				Map<String, Object> mappedObject = m.convertValue(o, Map.class);
				listMap.add(mappedObject);
			}
		} catch (IllegalArgumentException e) {
			logger.error("objsToListMapV3-error:{}", e);
		}
		return listMap;
	}

}

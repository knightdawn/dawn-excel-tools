package knight.su.dawn.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 * @author Edward
 * @date 2019年4月23日
 * @version 1.0
 */
public class ReflectionUtils {

	private ReflectionUtils() {
        throw new IllegalAccessError("ReflectionUtils is a tool class!");
    }

	
	/**
	 * 在运行期获得泛型类的直接超类的泛型类型，获取失败则抛出异常
	 * @param clz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getGenericClassType(Class clz) {
		Type type = clz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] types = pt.getActualTypeArguments();
			if (types.length > 0 && types[0] instanceof Class) {
				return (Class<T>) types[0];
			}
		}
		throw new IllegalArgumentException("Without actual type information");
	}
	
}

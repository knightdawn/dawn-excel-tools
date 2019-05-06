package knight.su.dawn.excelt.imp.features;

import knight.su.dawn.excelt.common.InvertEnum;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.convert.Convertion;
import knight.su.dawn.excelt.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * Created by sugengbin 2019/04/08
 */
public interface InvertValueAction {

	/**
	 * 读取cell后进行直接转换，通过注解方式
	 * 
	 * @param value
	 * @param invert
	 * @return
	 */
	default String invertValue(String value, InvertEnum invert) {
		if (Objects.isNull(invert) || invert == InvertEnum.DEFAULT) {
			return value;
		}
		Map<String, String> map = invert.getMap();
		String invertValue = map.get(value);
		return StringUtils.isNotBlank(invertValue) ? invertValue : value;
	}

	/**
	 * 读取完一行后，执行用户自定义的转换逻辑
	 * 
	 * @param instance
	 * @param field
	 * @param options
	 */
	default <T> void convertAfterMatch(T instance, Field field, ParserOption options) {
		Convertion convertion = options.searchConvertion(field.getName());
		if (Objects.nonNull(convertion)) {
			Object value = ReflectionUtil.getFieldValue(field, instance);
			value = convertion.afterTypeMatch(value);
			ReflectionUtil.setFieldValue(field, instance, value);
		}
	}

}

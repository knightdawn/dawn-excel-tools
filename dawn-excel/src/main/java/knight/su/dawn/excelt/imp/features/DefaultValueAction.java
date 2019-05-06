package knight.su.dawn.excelt.imp.features;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by sugengbin 2019/04/04
 */
public interface DefaultValueAction {

	/**
     * 
     * @param field
     * @param instance
     * @param options2
     */
	default <T> void setDefaultFieldValue(Field field, T instance, ParserOption options2) {
		Object value = options2.searchField(field.getName());
		if (Objects.nonNull(value)) {
			ReflectionUtil.setFieldValue(field, instance, value);
		}
	}
}

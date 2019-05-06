package knight.su.dawn.excelt.imp.convert;

import knight.su.dawn.excelt.imp.config.ParserOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.support.DefaultConversionService;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 
 * Created by sugengbin 2019/1/11
 */
public class DefaultTypeMatch implements TypeMatch {

	private static final Logger logger = LoggerFactory.getLogger(DefaultTypeMatch.class);
	private DefaultConversionService conversionService;

	@Override
	public Object convert(Field field, String value, ParserOption options) {
		Object matchValue = null;
		try {
			if (Objects.isNull(conversionService)) {
				conversionService = new DefaultConversionService();
			}
			Convertion convertion = options.searchConvertion(field.getName());
			if (Objects.nonNull(convertion)) {
				value = (String) convertion.beforeTypeMatch(value);
			}
			matchValue = conversionService.convert(value, field.getType());
			if (Objects.nonNull(convertion)) {
				matchValue = convertion.afterTypeMatch(matchValue);
			}
		} catch (Exception e) {
			logger.error("convert-error:{}", e.getMessage());
		}
		return matchValue;
	}

	@Override
	public Object matchWithoutConvert(Field field, String value) {
		Object matchValue = null;
		try {
			if (Objects.isNull(conversionService)) {
				conversionService = new DefaultConversionService();
			}
			matchValue = conversionService.convert(value, field.getType());
		} catch (Exception e) {
			logger.error("matchWithoutConvert-error:{}", e.getMessage());
		}
		return matchValue;
	}
	
}

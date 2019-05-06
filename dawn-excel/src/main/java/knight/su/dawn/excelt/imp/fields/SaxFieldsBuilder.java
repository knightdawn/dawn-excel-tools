package knight.su.dawn.excelt.imp.fields;

import knight.su.dawn.excelt.common.ColIdxEnum;
import knight.su.dawn.excelt.common.InvertEnum;
import knight.su.dawn.excelt.exception.InstanceException;
import knight.su.dawn.excelt.imp.annotation.ExcelCol;
import knight.su.dawn.excelt.imp.annotation.ExcelColLetter;
import knight.su.dawn.excelt.imp.config.ParserOption;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

import static knight.su.dawn.excelt.util.ReflectionUtil.initFields;

/**
 * Created by sugengbin 2019/04/17
 */
public class SaxFieldsBuilder {

	public SaxFieldsBuilder() {
		// static builder
		throw new InstanceException("builder tool, not to new Instance");
	}
	
	/**
	 * 
	 * @param type
	 * @param options
	 * @return
	 */
	public static <T> SaxFields reflectFieldMap(Class<T> type, ParserOption options) {
//		TIntObjectHashMap fieldsMap = new TIntObjectHashMap();
//		TIntObjectHashMap invertMap = new TIntObjectHashMap();
		Map<String, Field> fieldsMap = new HashMap<>();
		Map<String, InvertEnum> invertMap = new HashMap<>();
		List<Field> fieldsNoAnnotation = new ArrayList<>();
		List<Field> fields = initFields(type);
		if (Objects.nonNull(options.getColIdxMap())) {
			reflectFieldWithConfig(fields, fieldsMap, invertMap, fieldsNoAnnotation::add, options);
		} else {
			reflectFieldWithAnnotation(fields, fieldsMap, invertMap, fieldsNoAnnotation::add);
		}
		return new SaxFields(fieldsMap, fieldsNoAnnotation, invertMap);
	}

	/**
	 * 
	 * @param fields
	 * @param fieldsMap
	 * @param invertMap
	 * @param fieldsNoAnnotation
	 */
	private static void reflectFieldWithAnnotation(List<Field> fields, Map<String, Field> fieldsMap,
			Map<String, InvertEnum> invertMap, Consumer<Field> fieldsNoAnnotation) {
		ExcelCol excelCol;
		ExcelColLetter letter;
		for (Field field : fields) {
			excelCol = field.getAnnotation(ExcelCol.class);
			if (Objects.nonNull(excelCol)) {
				String code = ColIdxEnum.indexToCode(excelCol.value());
				fieldsMap.put(code, field);
				if (Objects.nonNull(excelCol.invert())) {
					invertMap.put(code, excelCol.invert());
				}
			} else {
				letter = field.getAnnotation(ExcelColLetter.class);
				if (Objects.nonNull(letter)) {
					String code = letter.value().getCode();
					fieldsMap.put(code, field);
					if (Objects.nonNull(letter.invert())) {
						invertMap.put(code, letter.invert());
					}
				} else {
					fieldsNoAnnotation.accept(field);
				}
			}
		}
	}

	/**
	 * 
	 * @param fields
	 * @param fieldsMap
	 * @param invertMap
	 * @param fieldsNoAnnotation
	 * @param options
	 */
	private static void reflectFieldWithConfig(List<Field> fields, Map<String, Field> fieldsMap,
			Map<String, InvertEnum> invertMap, Consumer<Field> fieldsNoAnnotation, ParserOption options) {
		Map<String, String> colIdxMap = options.getColIdxMap();
		for (Field field : fields) {
			String letter = colIdxMap.get(field.getName());
			if (StringUtils.isNotBlank(letter)) {
				fieldsMap.put(letter, field);
			} else {
				fieldsNoAnnotation.accept(field);
			}
		}
	}
}

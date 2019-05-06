package knight.su.dawn.excelt.imp.fields;

import knight.su.dawn.excelt.common.ColIdxEnum;
import knight.su.dawn.excelt.common.FieldMappingType;
import knight.su.dawn.excelt.common.InvertEnum;
import knight.su.dawn.excelt.exception.InstanceException;
import knight.su.dawn.excelt.imp.annotation.ExcelCol;
import knight.su.dawn.excelt.imp.annotation.ExcelColLetter;
import knight.su.dawn.excelt.imp.annotation.ExcelColName;
import knight.su.dawn.excelt.imp.config.ParserOption;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by sugengbin 2019/04/17
 */
public class SsfFieldsBuilder {

	public SsfFieldsBuilder() {
		// static builder
		throw new InstanceException("builder tool, not to new Instance");
	}

	/**
	 * 
	 * @param fields
	 * @param options2
	 * @return
	 */
	public static SsfFields reflectFieldMap(List<Field> fields, ParserOption options2) {
		Map<String, Integer> fieldMapColIdx = new HashMap<>();
		Map<String, String> fieldMapColName = new HashMap<>();
		Map<String, InvertEnum> invertMap = new HashMap<>();
		FieldMappingType type;
		List<String> fieldsNoAnnotation = new ArrayList<>();
		Map<String, String> colIdxMap = options2.getColIdxMap();
		if (Objects.nonNull(colIdxMap)) {
			type = FieldMappingType.CONIFG;
			reflectFieldWithConfig(fields, fieldMapColIdx, fieldsNoAnnotation::add, colIdxMap);
		} else {
			type = FieldMappingType.ANNOTATION;
			reflectFieldWithAnnotation(fields, fieldMapColIdx, fieldMapColName, fieldsNoAnnotation::add, invertMap);
		}
		SsfFields ssfFields = new SsfFields(type, fieldMapColIdx, fieldsNoAnnotation);
		ssfFields.setFieldMapColName(fieldMapColName);
		ssfFields.setInvertMap(invertMap);
		return ssfFields;
	}

	/**
	 * 
	 * @param fields
	 * @param fieldMapColIdx
	 * @param fieldMapColName
	 * @param fieldsNoAnnotation
	 * @return
	 */
	private static void reflectFieldWithAnnotation(List<Field> fields, Map<String, Integer> fieldMapColIdx,
			Map<String, String> fieldMapColName, Consumer<String> fieldsNoAnnotation,
			Map<String, InvertEnum> invertMap) {
		for (Field field : fields) {
			String fieldName = field.getName();
			ExcelColName excelColName = field.getAnnotation(ExcelColName.class);
			if (Objects.nonNull(excelColName)) {
				fieldMapColName.put(fieldName, excelColName.value());
				invertMap.put(fieldName, excelColName.invert());
			} else {
				ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
				if (Objects.nonNull(excelCol)) {
					fieldMapColIdx.put(fieldName, excelCol.value());
					invertMap.put(fieldName, excelCol.invert());
				} else {
					ExcelColLetter letter = field.getAnnotation(ExcelColLetter.class);
					if (Objects.nonNull(letter)) {
						fieldMapColIdx.put(fieldName, letter.value().getIndex());
						invertMap.put(fieldName, letter.invert());
					} else {
						fieldsNoAnnotation.accept(fieldName);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param fields
	 * @param fieldMapColIdx
	 * @param fieldsNoAnnotation
	 * @param colIdxMap
	 */
	private static void reflectFieldWithConfig(List<Field> fields, Map<String, Integer> fieldMapColIdx,
			Consumer<String> fieldsNoAnnotation, Map<String, String> colIdxMap) {
		for (Field field : fields) {
			String fieldName = field.getName();
			String letter = colIdxMap.get(fieldName);
			if (StringUtils.isNotBlank(letter)) {
				fieldMapColIdx.put(fieldName, ColIdxEnum.findByCode(letter).getIndex());
			} else {
				fieldsNoAnnotation.accept(fieldName);
			}
		}
	}

}

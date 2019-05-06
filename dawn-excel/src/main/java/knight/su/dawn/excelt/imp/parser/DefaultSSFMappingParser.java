package knight.su.dawn.excelt.imp.parser;

import knight.su.dawn.excelt.common.FieldMappingType;
import knight.su.dawn.excelt.common.InvertEnum;
import knight.su.dawn.excelt.imp.annotation.RowIndex;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.convert.DefaultTypeMatch;
import knight.su.dawn.excelt.imp.convert.TypeMatch;
import knight.su.dawn.excelt.imp.features.DefaultValueAction;
import knight.su.dawn.excelt.imp.features.InvertValueAction;
import knight.su.dawn.excelt.imp.features.ValidateAction;
import knight.su.dawn.excelt.imp.features.ValidateContext;
import knight.su.dawn.excelt.imp.fields.SsfFields;
import knight.su.dawn.excelt.imp.fields.SsfFieldsBuilder;
import knight.su.dawn.excelt.imp.result.CellError;
import knight.su.dawn.excelt.imp.result.ImpResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static knight.su.dawn.excelt.util.ReflectionUtil.*;
/**
 * 
 * Created by sugengbin 2019/1/4
 */
public class DefaultSSFMappingParser extends AbstractMappingParser
		implements MappingParser, ValidateAction, DefaultValueAction, InvertValueAction {

    private final DataFormatter dataFormatter;
    private final Workbook workbook;
    private final ParserOption options;
    private final Validation validation;
    private final TypeMatch typeMatch;
    private ValidateContext vc;
    private SsfFields ssfFields;

    public DefaultSSFMappingParser(Workbook workbook, ParserOption parserOption, Validation validation) {
    	this.workbook = workbook;
        this.options = parserOption;
        this.validation = validation;
        this.dataFormatter = new DataFormatter();// fixme
        this.typeMatch = new DefaultTypeMatch();
        if (Objects.nonNull(validation) || Objects.nonNull(validation.rules())) {
			vc = new ValidateContext();
		}
    }

    @Override
    public <T> ImpResult<T> parser(Class<T> type) {
        ImpResult<T> impResult = new ImpResult<>();
        // 1、获取sheet, 表头校验
        List<Sheet> sheets = getSheets(workbook, options, validation, type, impResult::setRightTemplate);
        // 1.x、正确的模板格式
        if (impResult.isRightTemplate()) {
        	Constructor<T> constructor = initConstructor(type);
            List<Field> fields = initFields(type);
            ssfFields = SsfFieldsBuilder.reflectFieldMap(fields, options);
            // 2、遍历Row
            List<CellError> errors = new ArrayList<>();
            List<T> dataResult = new ArrayList<>();
            for (Sheet sheet : sheets) {
            	parserSheet(sheet, constructor, fields, dataResult::add, errors::add);
            }
            impResult.setDataResult(dataResult);
            impResult.setErrors(errors);
        }
        return impResult;
    }

	/**
     * 
     * @param sheet
     * @param constructor
     * @param fields
     * @return
     */
	private <T> void parserSheet(Sheet sheet, Constructor<T> constructor, List<Field> fields,
			final Consumer<T> dataConsumer, final Consumer<CellError> errorConsumer) {
		int count = 0;
		for (Row row : sheet) {
			if (!isSkip(row.getRowNum(), options.getSkip()) && !isEmptyRow(row)) {
				parserRow(sheet, row, constructor, fields, dataConsumer, errorConsumer);
			}
			count++;
			validateLimit(count, validation);
		}
		validateEmpty(count);
	}

    /**
     * 
     * @param sheet
     * @param row
     * @param constructor
     * @param fields
     * @param errorConsumer
     * @return
     */
	private <T> void parserRow(Sheet sheet, Row row, Constructor<T> constructor, List<Field> fields,
			final Consumer<T> dataConsumer, final Consumer<CellError> errorConsumer) {
        T instance = null;
        try {
            instance = constructor.newInstance();
            int col = 0;
            for (Field field : fields) {
                setRowIndex(row, field, instance);
                setRowField(sheet, row, field, instance, col++);
            }
            if (validate(instance, validation, errorConsumer, (row.getRowNum() + 1), vc)) {
            	dataConsumer.accept(instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        	// TODO
            System.out.println(e);
        }
    }
    
	/**
     * 
     * @Date 2019/1/11
     * @Param: sheet
     * @Param: row
     * @Param: field
     * @Param: instance
     * @Param: col
     * @return 
     */
    private <T> void setRowField(Sheet sheet, Row row, Field field, T instance, int col) {
		String fieldName = field.getName();
		Map<String, Integer> fieldMapColIdx = ssfFields.getFieldMapColIdx();
		List<String> fieldsNoAnnotation = ssfFields.getFieldsNoAnnotation();
		if (fieldsNoAnnotation.contains(fieldName)) {
			setDefaultFieldValue(field, instance, options);
		} else {
			if (ssfFields.getType() == FieldMappingType.CONIFG) {
				setCellValue(row, field, instance, fieldMapColIdx.get(fieldName), InvertEnum.DEFAULT);
			} else {
				Map<String, InvertEnum> invertMap = ssfFields.getInvertMap();
				String excelColName = ssfFields.getFieldMapColName().get(fieldName);
				if (StringUtils.isNotBlank(excelColName)) {
		            Map<String, Integer> map = headers.get(workbook.getSheetIndex(sheet));
		            int colIndex = map.get(excelColName + col);
		            setCellValue(row, field, instance, colIndex, invertMap.get(fieldName));
				} else {
					setCellValue(row, field, instance, fieldMapColIdx.get(fieldName), invertMap.get(fieldName));
				}
			}
		}
    }
    
//  ExcelColName excelColName = field.getAnnotation(ExcelColName.class);
//  if (Objects.nonNull(excelColName)) {
//      Map<String, Integer> map = headers.get(workbook.getSheetIndex(sheet));
//      int colIndex = map.get(excelColName.value() + col);
//      setCellValue(row, field, instance, colIndex, excelColName.invert());
//  } else {
//      ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
//      if (Objects.nonNull(excelCol)) {
//          setCellValue(row, field, instance, excelCol.value(), excelCol.invert());
//      } else {
//          ExcelColLetter letter = field.getAnnotation(ExcelColLetter.class);
//          if (Objects.nonNull(letter)) {
//              setCellValue(row, field, instance, letter.value().getIndex(), letter.invert());
//          } else {
//          	setDefaultFieldValue(field, instance, options);
//          }
//      }
//  }

	/**
     * 
     * @param row
     * @param field
     * @param instance
     * @param colIndex
     * @param invert
     */
    private <T> void setCellValue(Row row, Field field, T instance, int colIndex, InvertEnum invert) {
        Cell cell = row.getCell(colIndex);
        String value = dataFormatter.formatCellValue(cell);
        value = invertValue(value, invert);
        setFieldValue(field, instance, typeMatch.convert(field, value, options));
    }

	/**
     * 
     * @Date 2019/1/11
     * @Param: row
     * @Param: field
     * @Param: instance
     * @return 
     */
    private <T> void setRowIndex(Row row, Field field, T instance) {
        RowIndex rowIndex = field.getAnnotation(RowIndex.class);
        if (Objects.nonNull(rowIndex)) {
            setFieldValue(field, instance, (row.getRowNum() + 1));
        }
    }

}

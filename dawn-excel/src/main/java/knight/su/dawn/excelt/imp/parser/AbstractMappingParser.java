package knight.su.dawn.excelt.imp.parser;

import knight.su.dawn.excelt.common.LogicalOp;
import knight.su.dawn.excelt.imp.annotation.ExcelSheet;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 
 * Created by sugengbin 2019/1/10
 */
public abstract class AbstractMappingParser {

	private static final Logger logger = LoggerFactory.getLogger(AbstractMappingParser.class);
	
    /**
     * sheet -> 对应表头信息（表头名与表头列关系）
     */
    protected Map<Integer, Map<String, Integer>> headers = new HashMap<>();

    /**
     * 
     * @param workbook0
     * @param options0
     * @param validation
     * @param type
     * @param isRightTemplate
     * @return
     */
	<T> List<Sheet> getSheets(Workbook workbook0, ParserOption options0, Validation validation, Class<T> type,
							  final Consumer<Boolean> isRightTemplate) {
        List<Sheet> sheets = new ArrayList<>();
        ExcelSheet excelSheet = type.getAnnotation(ExcelSheet.class);
        List<Integer> sheetIndexes = new ArrayList<>();
        if (Objects.nonNull(excelSheet)) {
        	sheetIndexes.addAll(Arrays.stream(excelSheet.value()).boxed().collect(Collectors.toSet()));
        } else {
        	sheetIndexes.add(0);
        }
		for (int sheetIndex : sheetIndexes) {
			Map<String, Integer> titles = new HashMap<>();
			Sheet sheet = getSheetAt(workbook0, sheetIndex);
			if (Objects.nonNull(sheet)) {
				Row titlesRow = sheet.getRow(options0.getTitlesRow());
				validateTemplate(sheet.getSheetName(), titlesRow, validation, isRightTemplate);
				for (Cell cell : titlesRow) {
					titles.put(cell.getStringCellValue() + cell.getColumnIndex(), cell.getColumnIndex());
				}
				sheets.add(sheet);
				headers.put(sheetIndex, titles);
			} else {
				logger.warn("sheet index:{}, empty sheet", sheetIndex);
			}
		}
        return sheets;
    }

	/**
	 *  
	  *  校验模板文件格式
	 * @param sheetName
	 * @param titlesRow
	 * @param validation
	 * @param isRightTemplate
	 */
    private void validateTemplate(String sheetName, Row titlesRow, Validation validation, final Consumer<Boolean> isRightTemplate) {
    	switch (validation.getValidateTemplateFlag()) {
		case 0:
		default:
			break;
		case 1:
			isRightTemplate.accept(sheetName.equals(validation.getValidateSheetName()));
			break;
		case 2:
			isRightTemplate.accept(validateTitles(titlesRow, validation.getValidateTitles()));
			break;
		case 3:
			validateByLogical(sheetName, titlesRow, validation, isRightTemplate);
			break;
		}
	}

    /**
     * 
     * @param sheetName
     * @param titlesRow
     * @param validation
     * @param isRightTemplate
     */
    private void validateByLogical(String sheetName, Row titlesRow, Validation validation,
			Consumer<Boolean> isRightTemplate) {
		if (validation.getValidateFileLogical() == LogicalOp.OR) {
			isRightTemplate.accept(sheetName.equals(validation.getValidateSheetName()) ||
					validateTitles(titlesRow, validation.getValidateTitles()));
		} else if (validation.getValidateFileLogical() == LogicalOp.AND) {
			isRightTemplate.accept(sheetName.equals(validation.getValidateSheetName()) &&
					validateTitles(titlesRow, validation.getValidateTitles()));
		}	
	}

	/**
     * 
     * @param titlesRow
     * @param validateTitles
     * @return
     */
	private Boolean validateTitles(Row titlesRow, String[] validateTitles) {
		boolean result = true;
		for (int i = 0; i < validateTitles.length; i++) {
			Cell cell = titlesRow.getCell(i);
			if (!validateTitles[i].equals(cell.getStringCellValue())) {
				result = false;
				break;
			}
		}
		return result;
	}

	
	
	/**
     * 
     * @param workbook0
     * @param sheetIndex
     * @return
     */
    private Sheet getSheetAt(Workbook workbook0, int sheetIndex) {
		Sheet sheet = null;
		try {// 获取不到sheet 返回null, log warn
			sheet = workbook0.getSheetAt(sheetIndex);
		} catch (Exception e) {
			logger.error("get sheet error：{}", e.getMessage());
		}
		return sheet;
	}

	/**
     *
     * @Date 2019/1/10
     * @Param: rowNum
     * @Param: skip
     * @return
     */
    boolean isSkip(int rowNum, int skip) {
        return rowNum < skip;
    }

    /**
     *
     * @Date 2019/1/10
     * @Param: row
     * @return
     */
    boolean isEmptyRow(Row row) {
        for (Cell cell : row) {
            if (Objects.nonNull(cell) && cell.getCellTypeEnum() != CellType.BLANK &&
                    StringUtils.isNotBlank(cell.getStringCellValue())) {
                return false; // 一个cell不为空(包括值不为空字符)，表示该行不为空
            }
        }
        return true;
    }

}

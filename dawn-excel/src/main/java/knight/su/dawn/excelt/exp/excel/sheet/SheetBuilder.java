package knight.su.dawn.excelt.exp.excel.sheet;

import knight.su.dawn.excelt.common.ExcelType;
import knight.su.dawn.excelt.common.ExceltConstants;
import knight.su.dawn.excelt.exp.excel.config.FormatOption;
import knight.su.dawn.excelt.util.ExportUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * Created by sugengbin 2019/3/02
 */
public class SheetBuilder {

	private static final Logger logger = LoggerFactory.getLogger(SheetBuilder.class);
	
	private Sheet sheet; // 当前sheet
	private FormatOption option;
	private int curRow;
	private int sheetIndex;

	/**
	 * 
	 * @param sheet
	 * @param option
	 */
	public SheetBuilder(Workbook workbook, FormatOption option, int index) {
		this.option = option;
		this.sheetIndex = index;
		if (index == 1) {
			this.sheet = workbook.getSheetAt(0);
			setSheetName(this.sheet.getSheetName());
		} else {
			String sheetName = option.getSheetName() + String.valueOf(sheetIndex);
			this.sheet = workbook.createSheet(sheetName);
		}
		this.curRow = option.getStartRow();
		initTitleRow();
	}

	private void setSheetName(String baseSheetName) {
		if (StringUtils.isBlank(baseSheetName) || baseSheetName.contains(ExceltConstants.SHEET1)) {
			this.option.setSheetName(ExceltConstants.SHEET);
		} else {
			this.option.setSheetName(baseSheetName);
		}
	}
	
	public int getCurRow() {
		return curRow;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	/**
	 * 
	 */
	private void initTitleRow() {
		if (Objects.nonNull(option.getKeys()) && Objects.nonNull(option.getTitleNames())) {
			Row titleRow = sheet.createRow(curRow++);
			titleRow.setHeightInPoints(30f);// 设置行高
			String[] titles = option.getTitleNames();
			String[] keys = option.getKeys();
			for (int i = 0; i < keys.length; i++) {
				Cell titleCell = titleRow.createCell(i);
				String value = titles.length > i ? titles[i] : keys[i];
				setCellValue(titleCell, value);
				titleCell.setCellStyle(option.getTitleStyle());
				int len = ExportUtil.length(value, ExportUtil.BYTE_NUMBER_OF_GBK_CHAR);
				len = len < 10 ? 10 : len;
				sheet.setColumnWidth(i, (len + 1) * 256);
			}
		}
	}

	/**
	 * 
	 * @param list
	 */
	public void build(List<Map<String, Object>> list) {
		Iterator<Map<String, Object>> iterator = list.iterator();
		try {
			while (iterator.hasNext()) {
				Map<String, Object> rowMap = iterator.next();
				buildRow(rowMap);
				if (option.getType() == ExcelType.FLUSH_XLSX && curRow % option.getRowAccessWindowSize() == 0) {
					((SXSSFSheet) sheet).flushRows(option.getRowAccessWindowSize());
				}
			}
		} catch (IOException e) {
			logger.error("Sheet build error:{}", e.getMessage());
		}
	}

	/**
	 * 
	 * @param rowMap
	 */
	private void buildRow(Map<String, Object> rowMap) {
		Row dataRow = sheet.createRow(curRow++);
		dataRow.setHeightInPoints((short) 20);
		String[] keys = option.getKeys();
		if (Objects.nonNull(keys)) {
			for (int i = 0; i < keys.length; i++) {
				Cell dataCell = dataRow.createCell(i);
				dataCell.setCellStyle(option.getBodyStyle());
				setCellValue(dataCell, ExportUtil.toString(rowMap.get(keys[i])));
			}
		}
	}

	/**
	 * 
	 * @param cell
	 * @param value
	 */
	private void setCellValue(Cell cell, String value) {
		if (option.getType() == ExcelType.XLS) {
			cell.setCellValue(new HSSFRichTextString(value));
		} else if (option.getType() == ExcelType.XLSX || option.getType() == ExcelType.FLUSH_XLSX) {
			cell.setCellValue(new XSSFRichTextString(value));
		}
	}
	
}

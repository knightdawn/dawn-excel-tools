package knight.su.dawn.excelt.exp.excel.config;

import knight.su.dawn.excelt.common.ExcelType;
import knight.su.dawn.excelt.exp.excel.append.WriteMode;
import knight.su.dawn.excelt.exp.validate.ValidateAssert;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.File;
import java.util.List;
/**
 * 
 * Created by sugengbin 2019/3/01
 */
public class FormatOption {

	/**
	 * 按rowSizeSingleSheet拆分sheet, 默认0不拆分sheet，单个sheet最大65535行
	 */
	private int rowSizeSingleSheet = 0;
	/**
	 * 按数据的key排序，如果不存在则按数据先后顺序
	 */
	private String[] keys;
	/**
	 * 表头名
	 */
	private String[] titleNames;
	/**
	 * 从文件第几行第startRow行开始写数据 （0下标开始标识excel第一行）
	 */
	private int startRow = 0;
	/**
	 * 表头格式
	 */
	private CellStyle titleStyle;
	/**
	 * 表体格式
	 */
	private CellStyle bodyStyle;
	/**
	 * sheet名称
	 */
	private String sheetName;
	/**
	 * 按模板创建
	 */
	private File template;
	/**
	 * excel文件格式
	 */
	private ExcelType type = ExcelType.NULL;
	/**
	 * 写入模式
	 */
	private WriteMode writeMode = WriteMode.ALL;
	/**
	 * 当ExcelType为FLUSH_XLSX生效
	 */
	private int rowAccessWindowSize = 1000;
	/**
	 * 是否进行压缩
	 */
	private boolean isZip = false;
	
	public boolean isZip() {
		return isZip;
	}

	public FormatOption toZip() {
		this.isZip = true;
		return this;
	}

	public int getRowAccessWindowSize() {
		return rowAccessWindowSize;
	}

	public FormatOption setRowAccessWindowSize(int rowAccessWindowSize) {
		this.rowAccessWindowSize = rowAccessWindowSize;
		return this;
	}

	public boolean isAppend() {
		return writeMode == WriteMode.APPEND;
	}

	public FormatOption append() {
		this.writeMode = WriteMode.APPEND;
		return this;
	}

	public ExcelType getType() {
		return type;
	}

	public FormatOption flush() {
		this.type = ExcelType.FLUSH_XLSX;
		return this;
	}

	public FormatOption setType(ExcelType type) {
		this.type = type;
		return this;
	}
	
	public File getTemplate() {
		return template;
	}

	/**
	 * 
	 * @param template
	 * @return
	 */
	public FormatOption template(File template) {
		ValidateAssert.notNull(template, "template file is null");
		this.template = template;
		return this;
	}

	public CellStyle getTitleStyle() {
		return titleStyle;
	}

	public FormatOption setTitleStyle(CellStyle titleStyle) {
	    ValidateAssert.notNull(titleStyle, "titleStyle is null");
		this.titleStyle = titleStyle;
		return this;
	}

	public CellStyle getBodyStyle() {
		return bodyStyle;
	}

	public FormatOption setBodyStyle(CellStyle bodyStyle) {
	    ValidateAssert.notNull(bodyStyle, "bodyStyle is null");
		this.bodyStyle = bodyStyle;
		return this;
	}

	public int getRowSizeSingleSheet() {
		return rowSizeSingleSheet;
	}

	public FormatOption splitWhen(int rowSizeSingleSheet) {
        ValidateAssert.isPositive(rowSizeSingleSheet, "rowSizeSingleSheet not positive");
		this.rowSizeSingleSheet = rowSizeSingleSheet;
		return this;
	}

	public String[] getKeys() {
		return keys;
	}

	public FormatOption sortBy(String... keys) {
		ValidateAssert.notNull(keys, "keys is null or empty");
		this.keys = keys;
		return this;
	}
	
	public FormatOption sortBySplit(String keys) {
	    ValidateAssert.notNull(keys, "keys is null or empty");
		this.keys = keys.split(",");
		return this;
	}
	
	public FormatOption sortBy(List<String> keys) {
		ValidateAssert.notEmpty(keys, "keys is null or empty");
		this.keys = keys.toArray(new String[keys.size()]);
		return this;
	}

	public int getStartRow() {
		return startRow;
	}

	public FormatOption startFrom(int startRow) {
		this.startRow = startRow;
		return this;
	}

	public String[] getTitleNames() {
		return titleNames;
	}

	public FormatOption intitle(String... titleNames) {
		ValidateAssert.notNull(titleNames, "titleNames is null");
		this.titleNames = titleNames;
		return this;
	}

	public FormatOption intitleBySplit(String titleNames) {
		ValidateAssert.notBlank(titleNames, "titleNames is null");
		this.titleNames = titleNames.split(",");
		return this;
	}
	
	public FormatOption intitle(List<String> titleNames) {
		ValidateAssert.notEmpty(titleNames, "titleNames is null");
		this.titleNames = titleNames.toArray(new String[titleNames.size()]);
		return this;
	}

	public String getSheetName() {
		return sheetName;
	}

	public FormatOption setSheetName(String sheetName) {
	    ValidateAssert.notBlank(sheetName, "sheetName is blank");
		this.sheetName = sheetName;
		return this;
	}

}

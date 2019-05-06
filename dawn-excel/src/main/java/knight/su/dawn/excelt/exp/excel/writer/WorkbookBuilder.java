package knight.su.dawn.excelt.exp.excel.writer;

import knight.su.dawn.excelt.common.ExceltConstants;
import knight.su.dawn.excelt.exp.excel.config.FormatOption;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.Objects;

/**
 * 
 * Created by sugengbin 2019/2/21
 */
public class WorkbookBuilder {

	/**
	 * 
	 * @param option
	 * @return
	 * @throws IOException
	 */
	public HSSFWorkbook buildHSSFWorkbook(FormatOption option) throws IOException {
		HSSFWorkbook workbook = null;
		if (Objects.nonNull(option.getTemplate())) {// 按模板创建
			workbook = new HSSFWorkbook(new POIFSFileSystem(option.getTemplate()));
		} else {
			workbook = new HSSFWorkbook();
			workbook.createSheet();
		}
		return workbook;
	}

	/**
	 * 
	 * @param option
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public XSSFWorkbook buildXSSFWorkbook(FormatOption option) throws IOException, InvalidFormatException {
		XSSFWorkbook workbook = null;
		if (Objects.nonNull(option.getTemplate())) {// 按模板创建
			workbook = new XSSFWorkbook(option.getTemplate());
		} else {
			workbook = new XSSFWorkbook();
			workbook.createSheet();
		}
		return workbook;
	}

	/**
	 * 
	 * @param option
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public SXSSFWorkbook buildSXSSFWorkbook(FormatOption option) throws IOException, InvalidFormatException {
		SXSSFWorkbook workbook = null;
		if (Objects.nonNull(option.getTemplate())) {// 按模板创建
			workbook = new SXSSFWorkbook(new XSSFWorkbook(option.getTemplate()), -1);
		} else {
			workbook = new SXSSFWorkbook(-1);
			workbook.createSheet(StringUtils.isBlank(option.getSheetName()) ? ExceltConstants.SHEET1 : option.getSheetName());
		}
		return workbook;
	}
}

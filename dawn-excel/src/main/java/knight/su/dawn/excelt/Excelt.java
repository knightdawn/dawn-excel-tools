package knight.su.dawn.excelt;
import knight.su.dawn.excelt.common.ExcelType;
import knight.su.dawn.excelt.exception.BizServiceException;
import knight.su.dawn.excelt.exp.csv.config.CsvOption;
import knight.su.dawn.excelt.exp.csv.writer.CsvWriter;
import knight.su.dawn.excelt.exp.csv.writer.impl.DefaultCsvWriter;
import knight.su.dawn.excelt.exp.excel.config.FormatOption;
import knight.su.dawn.excelt.exp.excel.writer.DefaultExcelWriter;
import knight.su.dawn.excelt.exp.excel.writer.ExcelWriter;
import knight.su.dawn.excelt.exp.excel.writer.SingletonEnum;
import knight.su.dawn.excelt.exp.validate.ValidateAssert;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.DefaultSSFMappingParser;
import knight.su.dawn.excelt.imp.parser.MappingParser;
import knight.su.dawn.excelt.imp.result.ImpResult;
import knight.su.dawn.excelt.util.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
/**
 * 
 * Created by sugengbin 2019/1/4
 */
public class Excelt {

	private static final Logger logger = LoggerFactory.getLogger(Excelt.class);
	
	/**
	 * 写入文件
	 */
	public static void write(File outputFile, FormatOption option, List<Map<String, Object>> data) {
		ExcelWriter writer = buildWriter(outputFile, option);
		writer.write(data);
	}
	
	/**
	 *   读取文件
	 *   
	 * @param excelFile
	 * @return
	 */
	public static <T> ImpResult<T> read(final File excelFile) {
		throw new BizServiceException("", "暂不支持");
	}

	/**
	 * 
	 * @param excelFile
	 * @param classType
	 * @return
	 */
	public static <T> ImpResult<T> read(final File excelFile, Class<T> classType) {
		MappingParser parser = deserializer(excelFile, ParserOption.defaultSetting(), Validation.defaultSetting());
		return parser.parser(classType);
	}

	/**
	 *
	 * @param excelFile
	 * @param classType
	 * @return
	 */
	public static <T> ImpResult<T> read(final File excelFile, Class<T> classType, Validation validation) {
		MappingParser parser = deserializer(excelFile, ParserOption.defaultSetting(), validation);
		return parser.parser(classType);
	}

	/**
	 * 
	 * @param excelFile
	 * @param classType
	 * @param parserOption
	 * @return
	 */
	public static <T> ImpResult<T> read(final File excelFile, Class<T> classType, ParserOption parserOption) {
		MappingParser parser = deserializer(excelFile, parserOption, Validation.defaultSetting());
		return parser.parser(classType);
	}

	/**
	 *
	 * @Param: excelFile
	 * @Param: classType
	 * @Param: parserOption
	 * @Param: validation
	 * @return
	 */
	public static <T> ImpResult<T> read(final File excelFile, Class<T> classType, ParserOption parserOption, Validation validation) {
		MappingParser parser = deserializer(excelFile, parserOption, validation);
		return parser.parser(classType);
	}
	
	/**
	 *
	 * @Param: file
	 * @Param: parserOption
	 * @Param: validation
	 * @return
	 */
	private static MappingParser deserializer(File file, ParserOption parserOption, Validation validation) {
		ValidateAssert.notNull(file, "input file null");
		ValidateAssert.notNull(parserOption, "ParserOption null");
		ValidateAssert.notNull(validation, "Validation null");
		try {
			ExcelType type = parserOption.getFileType();
			if (parserOption.getFileType() == ExcelType.NULL) {
				String extension = FileUtil.getExtension(file.getName());
				type = ExcelType.whichType(extension);
			}
			switch (type) {
			case XLS:
				Workbook workbook1 = new HSSFWorkbook(new BufferedInputStream(new FileInputStream(file)));
				return new DefaultSSFMappingParser(workbook1, parserOption, validation);
			case XLSX:	
				Workbook workbook2 = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(file)));
				return new DefaultSSFMappingParser(workbook2, parserOption, validation);
			default:
				throw new BizServiceException("", "不支持的文件格式"); // FIXME
			}
		} catch (Exception e) {
			throw new BizServiceException("", "create workbook error"); // FIXME
		}
	}
	
	/**
	 * 
	 * @param outputFile
	 * @param option
	 * @return
	 */
	public static ExcelWriter buildWriter(File outputFile, FormatOption option) {
		ExcelWriter writer = null;
		try {
			ExcelType fileType = ExcelType.whichType(FileUtil.getExtension(outputFile.getName()).toLowerCase());
			Workbook workbook = null;
			switch (option.getType()) {
			case FLUSH_XLSX:
				workbook = SingletonEnum.WORK_BOOK_BUILDER.getInstance().buildSXSSFWorkbook(option);
				break;
			case XLS:
				if (fileType != ExcelType.XLS) {
					throw new BizServiceException("", "设置的格式与输出文件格式不一致"); // FIXME
				}
				workbook = SingletonEnum.WORK_BOOK_BUILDER.getInstance().buildHSSFWorkbook(option);
				break;
			case XLSX:
				if (fileType != ExcelType.XLSX) {
					throw new BizServiceException("", "设置的格式与输出文件格式不一致"); // FIXME
				}
				workbook = SingletonEnum.WORK_BOOK_BUILDER.getInstance().buildXSSFWorkbook(option);
				break;
			case NULL:
				if (fileType == ExcelType.XLS) {
					workbook = SingletonEnum.WORK_BOOK_BUILDER.getInstance().buildHSSFWorkbook(option.setType(ExcelType.XLS));
				} else if(fileType == ExcelType.XLSX){
					workbook = SingletonEnum.WORK_BOOK_BUILDER.getInstance().buildXSSFWorkbook(option.setType(ExcelType.XLSX));
				} else {
					throw new BizServiceException("", "不支持的文件格式"); // FIXME
				}
				break;
			default:
				throw new BizServiceException("", "不支持的文件格式"); // FIXME
			}
			writer = new DefaultExcelWriter(outputFile, workbook, option);
		} catch(Exception e) {
			logger.error("buildWriter-error:{}", e.getMessage());
		}
		return writer;
	}
	
	/**
	 * 
	 * @param outputFile
	 * @param option
	 * @return
	 */
	public static CsvWriter buildCsvWriter(File outputFile, CsvOption option) {
		return new DefaultCsvWriter(outputFile, option);
	}
}

package knight.su.dawn.excelt.exp.excel.writer;

import com.google.common.collect.Lists;
import knight.su.dawn.excelt.exp.csv.writer.impl.DefaultCsvWriter;
import knight.su.dawn.excelt.exp.excel.config.FormatOption;
import knight.su.dawn.excelt.exp.excel.sheet.SheetBuilder;
import knight.su.dawn.excelt.exp.excel.style.DefaultHSSFCellStyle;
import knight.su.dawn.excelt.exp.option.WriterZipOp;
import knight.su.dawn.excelt.util.ExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 
 * Created by sugengbin 2019/3/05
 */
public class DefaultExcelWriter implements ExcelWriter, WriterZipOp {

	private static Logger logger = LoggerFactory.getLogger(DefaultCsvWriter.class);
	
	protected FormatOption option;
	private Workbook workBook;
	private File outputFile;
	private SheetBuilder appendSheet;

	public DefaultExcelWriter(File outputFile, Workbook workBook, FormatOption option) {
		this.outputFile = outputFile;
		this.workBook = workBook;
		this.option = option;
		initStyle();
	}

	/*
	 * 根据option#WriteMode确定全量写入还是增量写入
	 * 
	 * @see com.sf.dawn.excelt.exp.excel.ExcelWriter#write(java.io.File,
	 * java.util.List)
	 */
	@Override
	public void write(List<Map<String, Object>> data) {
		if (option.isAppend()) {
			append(data);
		} else {
			writeAll(data);
			flush();
		}
	}

	@Override
	public <T> void write(List<T> objs, Class<T> type) {
		List<Map<String, Object>> data = ExportUtil.objsToListMapReflect(objs, type);
		this.write(data);
	}
	
	/**
	 * 初始化excel格式，如果没有定义格式，使用默认格式
	 */
	private void initStyle() {
		if (Objects.isNull(option.getTitleStyle())) {
			option.setTitleStyle(DefaultHSSFCellStyle.defaultTitleStyle(workBook));
		}
		if (Objects.nonNull(option.getBodyStyle())) {
			option.setBodyStyle(DefaultHSSFCellStyle.defaultBodyStyle((HSSFWorkbook) workBook));
		}
	}

	/**
	 * write all data
	 * @param data
	 */
	private void writeAll(List<Map<String, Object>> data) {
		if (option.getRowSizeSingleSheet() <= 0L) {
			new SheetBuilder(workBook, option, 1).build(data);
		} else {
			List<List<Map<String, Object>>> lists = Lists.partition(data, option.getRowSizeSingleSheet());
			for (int i = 0; i < lists.size(); i++) {
				new SheetBuilder(workBook, option, (i + 1)).build(lists.get(i));
			}
		}
	}

	/**
	 * append data
	 * @param data
	 */
	private void append(List<Map<String, Object>> data) {
		if (Objects.isNull(appendSheet)) {
			appendSheet = new SheetBuilder(workBook, option, 1);
		}
		if (option.getRowSizeSingleSheet() <= 0L) {
			appendSheet.build(data);
		} else {// TODO 优化
			int index = appendSheet.getSheetIndex();
			int rows = option.getRowSizeSingleSheet() - appendSheet.getCurRow();
			if (rows < data.size()) {
				appendSheet.build(data.subList(0, rows));
				List<List<Map<String, Object>>> lists = Lists.partition(data.subList(rows, data.size()),
						option.getRowSizeSingleSheet());
				for (int i = 0; i < lists.size(); i++) {
					appendSheet = new SheetBuilder(workBook, option, (index + i + 1));
					appendSheet.build(lists.get(i));
				}
			} else {
				appendSheet.build(data);
			}
		}
	}

	/*
	 * flush data to output file
	 * 
	 * @see com.sf.dawn.excelt.exp.excel.writer.ExcelWriter#flush()
	 */
	@Override
	public void flush() {
		try (FileOutputStream out = new FileOutputStream(outputFile);) {
			workBook.write(out);
			out.flush();
			zip(option.isZip(), outputFile);
		} catch (IOException e) {
			logger.error("flush-error:{}", e.getMessage());
		} finally {
			afterZip(option.isZip(), outputFile);
		}
	}

	/*
	 * 增量循环写入，返回index给用户，用户返回index下的数据
	 * 
	 * @see
	 * com.sf.dawn.excelt.exp.excel.writer.ExcelWriter#write(java.util.function.
	 * Function)
	 */
	@Override
	public void write(Function<? super Integer, ? extends List<Map<String, Object>>> function) {
		List<Map<String, Object>> rows = null;
		int index = 1;
		do {
			rows = function.apply(index);
			if (CollectionUtils.isNotEmpty(rows)) {
				append(rows);
			}
			index++;
		} while (CollectionUtils.isNotEmpty(rows) && index < Integer.MAX_VALUE);
		flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sf.dawn.excelt.exp.excel.writer.ExcelWriter#writeObj(java.util.function.
	 * Function, java.lang.Class)
	 */
	@Override
	public <T> void writeObj(Function<? super Integer, ? extends List<T>> function, Class<T> type) {
		this.writeObj(function, type, Integer.MAX_VALUE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sf.dawn.excelt.exp.excel.writer.ExcelWriter#writeObj(java.util.function.Function, java.lang.Class, int)
	 */
	@Override
	public <T> void writeObj(Function<? super Integer, ? extends List<T>> function, Class<T> type, int pageSize) {
		List<Map<String, Object>> rows = null;
		List<T> objs = null;
		int index = 1;
		do {
			objs = function.apply(index);
			if (CollectionUtils.isNotEmpty(objs)) {
				rows = ExportUtil.objsToListMapReflect(objs, type);
				append(rows);
			}
			index++;
		} while (CollectionUtils.isNotEmpty(objs) && index < pageSize);
		flush();
	}

}

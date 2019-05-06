package knight.su.dawn.excelt.exp.csv.writer.impl;

import knight.su.dawn.excelt.common.ExceltConstants;
import knight.su.dawn.excelt.exp.csv.config.CsvOption;
import knight.su.dawn.excelt.exp.csv.writer.CsvWriter;
import knight.su.dawn.excelt.exp.option.WriterZipOp;
import knight.su.dawn.excelt.exp.validate.ValidateAssert;
import knight.su.dawn.excelt.util.ExportUtil;
import knight.su.dawn.excelt.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static knight.su.dawn.excelt.exp.validate.ValidateAssert.eqLength;

/**
 *
 * Date: 2019/3/10<br/>
 * 
 * @author sugengbin
 */
public class DefaultCsvWriter implements CsvWriter, WriterZipOp {

	private static Logger logger = LoggerFactory.getLogger(DefaultCsvWriter.class);
	
	private final byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
	private final String uft8bomOfCsv = new String(uft8bom);
	private CsvOption option;
	private File outputFile;

	public DefaultCsvWriter(File outputFile, CsvOption option) {
		ValidateAssert.notNull(option, "option 不能为空");
		ValidateAssert.notNull(option.getKeys(), "option.keys不能为空");
		eqLength(option.getKeys(), option.getTitleNames(), "title不为空时,需要与keys数量保持一致");
		ValidateAssert.notNull(outputFile, "输出文件不为空");
		ValidateAssert.eq(ExceltConstants.EXTENSION, FileUtil.getExtension(outputFile.getName()).toLowerCase(), "文件格式错误，只支持csv格式文件");
		this.option = option;
		this.outputFile = outputFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sf.dawn.excelt.exp.csv.writer.CsvWriter#write(java.util.function.
	 * Function)
	 */
	@Override
	public void write(Function<? super Integer, ? extends List<Map<String, Object>>> function) {
		try (FileWriter fw = new FileWriter(outputFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw, true)) {
			printTitles(out);
			List<Map<String, Object>> rows = null;
			int index = 1;
			do {
				rows = function.apply(index);
				writeCsv(out, rows);
				index++;
			} while (CollectionUtils.isNotEmpty(rows) && index < Integer.MAX_VALUE);
			zip(option.isZip(), outputFile);
		} catch (IOException e) {
			logger.error("write-error:{}", e.getMessage());
		} finally {
			afterZip(option.isZip(), outputFile);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sf.dawn.excelt.exp.csv.writer.CsvWriter#write(java.util.function.
	 * Function, java.lang.Class)
	 */
	@Override
	public <T> void write(Function<? super Integer, ? extends List<T>> function, Class<T> type) {
		this.write(function, type, Integer.MAX_VALUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sf.dawn.excelt.exp.csv.writer.CsvWriter#write(java.util.function.
	 * Function, java.lang.Class, int)
	 */
	@Override
	public <T> void write(Function<? super Integer, ? extends List<T>> function, Class<T> type, int pageSize) {
		try (FileWriter fw = new FileWriter(outputFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw, true)) {
			printTitles(out);
			List<Map<String, Object>> rows = null;
			List<T> objs = null;
			int index = 1;
			do {
				objs = function.apply(index);
				if (CollectionUtils.isNotEmpty(objs)) {
					rows = ExportUtil.objsToListMapReflect(objs, type);
					writeCsv(out, rows);
				}
				index++;
			} while (CollectionUtils.isNotEmpty(objs) && index < pageSize);
			zip(option.isZip(), outputFile);
		} catch (IOException e) {
			logger.error("write-error:{}", e.getMessage());
		} finally {
			afterZip(option.isZip(), outputFile);
		}
	}

	/**
	 * 
	 * @param out
	 */
	private void printTitles(PrintWriter out) {
		out.print(uft8bomOfCsv);
		if (Objects.nonNull(option.getTitleNames())) {
			out.println(String.join(ExceltConstants.COMMA, option.getTitleNames()));
		} else {
			out.println(String.join(ExceltConstants.COMMA, option.getKeys()));
		}
	}

	/**
	 * 
	 * @param out
	 * @param rows
	 */
	private void writeCsv(PrintWriter out, List<Map<String, Object>> rows) {
		if (CollectionUtils.isNotEmpty(rows)) {
			for (Map<String, Object> row : rows) {
				int colIndex = 0;
				for (String key : option.getKeys()) {
					if (colIndex != 0) {
						out.print(ExceltConstants.COMMA);
					}
					out.print(ExceltConstants.QUOTE);
					out.print(row.get(key));
					out.print(ExceltConstants.TAB_QUOTE);
					colIndex++;
				}
				out.println();
//				out.println(buildRow(row));
			}
			out.flush();
		}
	}

	/**
	 * 
	 * @param row
	 * @return
	 */
	private String buildRow(Map<String, Object> row) {
		List<String> rowCells = new ArrayList<>();
		for (String key : option.getKeys()) {
			rowCells.add(ExceltConstants.QUOTE + String.valueOf(row.get(key)) + ExceltConstants.TAB_QUOTE);
		}
		return String.join(ExceltConstants.COMMA, rowCells);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sf.dawn.excelt.exp.csv.writer.CsvWriter#write(java.util.List)
	 */
	@Override
	public void write(List<Map<String, Object>> data) {
		this.write(index -> {
			if (index == 1) {
				return data;
			} else {
				return null;
			}
		});
	}
}

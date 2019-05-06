package knight.su.dawn.excelt.imp.parser.sax;

import knight.su.dawn.excelt.common.InvertEnum;
import knight.su.dawn.excelt.common.SaxConstants;
import knight.su.dawn.excelt.exception.EndRowStopException;
import knight.su.dawn.excelt.imp.config.Condition;
import knight.su.dawn.excelt.imp.config.ConfigSetting;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.convert.DefaultTypeMatch;
import knight.su.dawn.excelt.imp.convert.TypeMatch;
import knight.su.dawn.excelt.imp.features.*;
import knight.su.dawn.excelt.imp.fields.SaxFields;
import knight.su.dawn.excelt.imp.result.CellError;
import knight.su.dawn.excelt.imp.result.ImpResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

import static knight.su.dawn.excelt.util.ReflectionUtil.setFieldValue;

/**
 * Created by sugengbin 2019/04/01
 */
public class SaxSheetHandler<T> extends DefaultHandler
		implements ValidateAction, InvertValueAction, DefaultValueAction, FilterAction {

	private static final Logger logger = LoggerFactory.getLogger(SaxSheetHandler.class);

	private SharedStringsTable sst;
	private T instance;
	private Constructor<T> constructor;
	private SaxFields sf;
	private List<String> exceltitlesRow;
	private List<T> datas;
	private List<CellError> errors;
	private final ParserOption options;
	private final Validation validation;
	private final Condition<T> filter;
	private Function<ImpResult<T>, Boolean> actionPart;
	private boolean aciton = false;
	private final TypeMatch typeMatch;
	private Set<Boolean> cellEmptyFlag = new HashSet<>(2);
	private ValidateContext vc;
	private String column = "";
	private int row = 0;
	private boolean nextIsString = false;
	private String lastContents;
	private int titlesRow;
	private int count = 0;

	public SaxSheetHandler(SharedStringsTable sst, SaxFields sf, Constructor<T> constructor,
			final ConfigSetting<T> setting) {
		this.options = setting.parserOption();
		this.validation = setting.validation();
		this.filter = setting.filter();
		this.sst = sst;
		this.constructor = constructor;
		this.sf = sf;
		datas = new ArrayList<T>();
		errors = new ArrayList<>();
		exceltitlesRow = new ArrayList<>();
		typeMatch = new DefaultTypeMatch();
		if (Objects.nonNull(validation) && Objects.nonNull(validation.rules())) {
			vc = new ValidateContext();
		}
		this.row = options.getTitlesRow();
		this.titlesRow = this.row;
	}

	public SaxSheetHandler(SharedStringsTable sst, SaxFields sf, Constructor<T> constructor, ConfigSetting<T> setting,
			Function<ImpResult<T>, Boolean> actionPart) {
		this(sst, sf, constructor, setting);
		this.actionPart = actionPart;
		this.aciton = true;
	}

	public List<T> getDatas() {
		validateEmpty(count);
		return datas;
	}

	public List<CellError> getErrors() {
		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if (name.equals(SaxConstants.END_CELL_VALUE_TAG)) {
			String cellType = attributes.getValue(SaxConstants.CELL_TYPE);
			nextIsString = StringUtils.isNotBlank(cellType) && cellType.equals(SaxConstants.CELL_TYPE_STRING);
			String cellCol = attributes.getValue(SaxConstants.CELL_COL_TYPE);
			int tempIndx = row + 1;// FIXME 优化
			column = cellCol.replace(tempIndx + "", "");
		}
		lastContents = "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		// step1、解析cell_id
		extractCellId(name);
		// step2、解析cell_value
		extractCellValue(name);
		// step3、行结束处理
		endEachRow(name);
	}

	/*
	 * Extracting the content of an element
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}

	/**
	 * step1、解析cell_id
	 * 
	 * @param name
	 */
	private void extractCellId(String name) {
		if (name.equals(SaxConstants.END_CELL_ID_TAG)) {// v => index of the content of a cell.
			try {
				if (nextIsString) {
					int idx = Integer.parseInt(lastContents);
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
					nextIsString = false;
				}
			} catch (NumberFormatException e) {
				logger.error("", e);
			}
		}
	}

	/**
	 * step2、解析cell_value
	 * 
	 * @param name
	 */
	private void extractCellValue(String name) {
		if (name.equals(SaxConstants.END_CELL_VALUE_TAG)) {			
			if (row != titlesRow) {
				setCellValue();
			} else if (StringUtils.isNotBlank(lastContents)) {
				addTitleCell();
			}
		}
	}

	/**
	 * step3、行结束处理
	 * 
	 * @param name
	 */
	private void endEachRow(String name) {
		if (name.equals(SaxConstants.END_ROW_TAG)) {
			afterBuildInstance();
			try {
				instance = constructor.newInstance();
			} catch (Exception e) {
				logger.error("newInstance-error:{}", e.getMessage());
			}
			row++;
			cellEmptyFlag.clear();
		}
	}

	/**
	 * afterBuildInstance to validate & action
	 */
	private void afterBuildInstance() {
		if (row == options.getTitlesRow()) {
			// 校验表头
			validateTitles(exceltitlesRow, validation.getValidateTitles(), validation.isVtf());
			return;
		}
		// 校验空行，出现空行，则不再往下读取数据
		if (!cellEmptyFlag.contains(Boolean.FALSE)) {
			logger.info("emptyRow:{}", row);
			throw new EndRowStopException(row);
		}
		// 表头不进行加1
		count++;
		// 校验是否满足条件
		if(filter(filter, instance)) {
			// 校验对象
			if (validate(instance, validation, errors::add, row, vc)) {
				datas.add(instance);
			}
		}
		// 校验限制
		validateLimit(row, validation);
		// set default and do convert
		defaultAndConvert();
		// Action for part
		actionForPartition();
	}

	/**
	 * set default and do convert
	 */
	private void defaultAndConvert() {
		// set default value
		List<Field> fn = sf.getFieldsNoAnnotation();
		for (Field f : fn) {
			setDefaultFieldValue(f, instance, options);
		}
		// convert value
		sf.getFieldsMap().forEach((k, v) -> {
			convertAfterMatch(instance, v, options);
		});
	}

	/**
	 * action then clear
	 */
	private void actionForPartition() {
		if (aciton && row % options.getSaxBatch() == 0) {
			actionPart.apply(new ImpResult<>(datas, errors));
			datas.clear();
			errors.clear();
		}
	}

	/**
	 * setCellValue
	 */
	private void setCellValue() {
		Map<String, Field> map = sf.getFieldsMap();
		Field field = map.get(column);
		if (Objects.nonNull(field)) {
			cellEmptyFlag.add(StringUtils.isBlank(lastContents));
			InvertEnum invert = sf.getInvertMap().get(column);
			String value = invertValue(lastContents, invert);
			setFieldValue(field, instance, typeMatch.matchWithoutConvert(field, value));
		}
	}

	/**
	 * 添加表头列值
	 */
	private void addTitleCell() {
		if (validation.isVtf()) {
			Map<String, Field> map = sf.getFieldsMap();
			Field field = map.get(column);
			if (Objects.nonNull(field)) {
				exceltitlesRow.add(lastContents);
			}
		} else {
			exceltitlesRow.add(lastContents);
		}
	}
	
}

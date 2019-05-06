package knight.su.dawn.excelt.imp.parser.sax;

import knight.su.dawn.core.exception.BizException;
import knight.su.dawn.excelt.common.ExceltError;
import knight.su.dawn.excelt.exception.EndRowStopException;
import knight.su.dawn.excelt.exception.ValidateException;
import knight.su.dawn.excelt.imp.config.Condition;
import knight.su.dawn.excelt.imp.config.ConfigSetting;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.features.ReflectTypeAction;
import knight.su.dawn.excelt.imp.fields.SaxFields;
import knight.su.dawn.excelt.imp.fields.SaxFieldsBuilder;
import knight.su.dawn.excelt.imp.parser.MappingParser;
import knight.su.dawn.excelt.imp.result.CellError;
import knight.su.dawn.excelt.imp.result.ImpResult;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static knight.su.dawn.excelt.util.ReflectionUtil.initConstructor;

/**
 * Created by sugengbin 2019/04/01
 */
public class SaxMappingParser implements MappingParser, ReflectTypeAction {

	private static final Logger logger = LoggerFactory.getLogger(SaxMappingParser.class);
	
	private XSSFReader r;
	private XMLReader parser;
	private SharedStringsTable sst;
	private final ConfigSetting<?> setting;
	private OPCPackage opc;

	public SaxMappingParser(File file) {
		this(file, ConfigSetting.defaultSetting());
	}
	
	public SaxMappingParser(File file, ConfigSetting<?> setting) {
		this.setting = setting;
		try {
			opc = OPCPackage.open(file);
			r = new XSSFReader(opc);
			sst = r.getSharedStringsTable(); // XML file containing all the String values, referenced by index
			parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		} catch (Exception e) {
			logger.error("init SaxMappingParser error:{}", e);
			throw new BizException("init parse error");
		}
	}

	public SaxMappingParser(File file, ParserOption option, Validation validation) {
		this(file, new ConfigSetting<>(option, validation));
	}
	
	public SaxMappingParser(File file, ParserOption option, Validation validation, Condition<?> filter) throws Exception {
		this(file, new ConfigSetting<>(filter, option, validation));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> ImpResult<T> parser(Class<T> type) {
		ImpResult<T> impResult = new ImpResult<>();
		List<T> data = new ArrayList<>();
		List<CellError> errors = new ArrayList<>();
		SaxFields sf = SaxFieldsBuilder.reflectFieldMap(type, setting.parserOption());
		Constructor<T> constructor = initConstructor(type);
		SaxSheetHandler<T> handler = new SaxSheetHandler(sst, sf, constructor, setting);
		innerParser(type, handler, errors::addAll, data::addAll, impResult);
		impResult.setDataResult(data);
		impResult.setErrors(errors);
		return impResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> void parser(Class<T> type, Function<ImpResult<T>, Boolean> actionPart) {
		ImpResult<T> impResult = new ImpResult<>();
		List<T> data = new ArrayList<>();
		List<CellError> errors = new ArrayList<>();
		SaxFields sf = SaxFieldsBuilder.reflectFieldMap(type, setting.parserOption());
		Constructor<T> constructor = initConstructor(type);
		SaxSheetHandler<T> handler = new SaxSheetHandler(sst, sf, constructor, setting, actionPart);
		innerParser(type, handler, errors::addAll, data::addAll, impResult);
		impResult.setDataResult(data);
		impResult.setErrors(errors);
		actionPart.apply(impResult);
	}
	
	private <T> void innerParser(Class<T> type, SaxSheetHandler<T> handler, 
			Consumer<List<CellError>> errorConsumer, Consumer<List<T>> dataConsumer, ImpResult<T> impResult) {
		parser.setContentHandler(handler);
		Iterator<InputStream> sheets;
		InputStream sheet = null;
		try {
			try {
				sheets = r.getSheetsData();
				if (sheets.hasNext()) {// Browsing sheets 1 and extracting data.
					sheet = sheets.next();
					InputSource sheetSource = new InputSource(sheet);
					parser.parse(sheetSource);
				}
			} catch (EndRowStopException end) {
				logger.info("end row to stop:{}, excel row:{}", end.getEndRow(), end.getEndRow() + 1);
			}
			if (Objects.nonNull(dataConsumer)) {
				dataConsumer.accept(handler.getDatas());
			}
			if (Objects.nonNull(errorConsumer)) {
				errorConsumer.accept(handler.getErrors());
			}
		} catch (ValidateException ve) {
			logger.info("validate not pass,{},{}", ve.getErrorCode(), ve.getMessage());
			setImpResult(impResult, ve.getErrorCode(), ve.getMessage());
		} catch (Exception e) {
			logger.error("innerParser-error:{}", e);
		} finally {
			try {
				if (sheet != null) {
					sheet.close();
				}
				if (opc != null) {
					opc.close();
				}
			} catch (IOException e) {
				logger.error("close sheet-error:{}", e);
			}
		}
	}
	
	/**
	 * 
	 * @param impResult
	 * @param errorCode
	 * @param msg
	 */
	private <T> void setImpResult(ImpResult<T> impResult, String errorCode, String msg) {
		impResult.setGlobalPass(false);
		impResult.setErrorMsg(msg);
		if (ExceltError.EMPTY_FILE_CODE.equals(errorCode)) {
			impResult.setEmptyFile(true);
		} else if (ExceltError.OVER_LIMIT_CODE.equals(errorCode)) {
			impResult.setOverLimit(true);
		} else if (ExceltError.WRONG_TEMPLATE_CODE.equals(errorCode)) {
			impResult.setRightTemplate(false);
		}
	}

}

package knight.su.dawn.excelt.imp.config;

import knight.su.dawn.core.constant.CommonConsts;
import knight.su.dawn.excelt.common.ColIdxEnum;
import knight.su.dawn.excelt.common.ExcelType;
import knight.su.dawn.excelt.imp.convert.Convertion;
import knight.su.dawn.excelt.imp.convert.DefaultConvertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static knight.su.dawn.excelt.exp.validate.ValidateAssert.*;

/**
 * 
 * Created by sugengbin 2019/1/4
 */
public class ParserOption {

	private static final Logger logger = LoggerFactory.getLogger(ParserOption.class);
	
	/**
	 * <pre>
	 * excel行从0下标开始，从第skip行开始读取,默认1,跳过表头
	 * skip=1：跳过第0行，从第1行开始读取
	 * </pre>
	 */
	private int skip = 1;

	/**
	 * <pre>
	 * 表头行，默认0行为表头
	 * </pre>
	 */
	private int titlesRow = 0;

	/**
	 * <pre>
	 * 继承层次，默认1，会获取对应第一层父类的属性
	 * </pre>
	 */
	private int extendLevel = 1; 

	/**
	 * <pre>
	 * 转换集合 
	 * field属性名 -> 转换操作
	 * </pre>
	 */
	private Map<String, Convertion> convertions = new HashMap<>();
	
	/**
	 * <pre>
	 * 全局字段赋值
	 * field属性名 -> 属性值
	 * </pre>
	 */
	private Map<String, Object> globalFieldMap = new HashMap<>();
	
	/**
	 * sax解析方法，批次处理的条数
	 */
	private int saxBatch = 2000;
	
	/**
	 * 配置列与字段的映射关系
	 */
	private Map<String, String> colIdxMap;
	
	/**
	 * 设置输入文件类型，为null时通过文件后缀进行判断
	 */
	private ExcelType fileType = ExcelType.NULL;
	
	public ExcelType getFileType() {
		return fileType;
	}

	public ParserOption isXlsFile() {
		this.fileType = ExcelType.XLS;
		return this;
	}
	
    public ParserOption isXlsxFile() {
		this.fileType = ExcelType.XLSX;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public static ParserOption defaultSetting() {
		return new ParserOption();
	}

	/**
	 * <pre>
	 * input 	 -> A:cityCode,B:name, C:date
	 * colIdxMap -> (cityCode,A) (name,B) (date,C)
	 * </pre>
	 * @param colFields
	 * @return
	 */
	public ParserOption mapColField(String colFields) {
		notBlank(colFields, "colFields blank");
		colIdxMap = new HashMap<>();
		String[] cols = colFields.split(CommonConsts.COMMA);
		for (String col : cols) {
			String[] oneCol = col.split(CommonConsts.COLON);
			eqLength(oneCol, 2, "confirm format config [Letter:field]");
			notNull(ColIdxEnum.findByCode(oneCol[0]), "cannot find the col letter :" + oneCol[0]);
			colIdxMap.put(oneCol[1].trim(), oneCol[0].trim());
		}
		return this;
	}
	
	public Map<String, String> getColIdxMap() {
		return colIdxMap;
	}

	/**
	 * 
	 * @param skip
	 * @return
	 */
	public ParserOption readStartBy(int skip) {
		isPositive(skip, "readStartBy not positive");
		this.skip = skip;
		return this;
	}

	/**
	 * 
	 * @param headerRow
	 * @return
	 */
	public ParserOption setTitleRow(int headerRow) {
		isPositive(headerRow, "setTitleRow not positive");
		this.titlesRow = headerRow;
		return this;
	}

	/**
	 * 
	 * @param extendLevel
	 * @return
	 */
	public ParserOption extendLevel(int extendLevel) {
        isPositive(extendLevel, "extendLevel not positive");
		this.extendLevel = extendLevel;
		return this;
	}

	/**
	 *
	 * @param convertionInput
	 * @return
	 */
	public ParserOption registerConvert(Convertion... convertionInput) {
		for (Convertion convertion : convertionInput) {
			notNull(convertion, "convertion null");
			notBlank(convertion.field(), "field blank");
			convertions.put(convertion.field(), convertion);
		}
		return this;
	}
	
	/**
	 * field -> function 注册
	 * 
	 * @param field
	 * @param function
	 * @return
	 */
	public ParserOption registerConvert(final String field, final Function<Object, Object> function) {
		notBlank(field, "field blank");
		notNull(function, "function null");
		this.registerConvert(new DefaultConvertion() {
			@Override
			public Object afterTypeMatch(Object value) {
				return function.apply(value);
			}
			@Override
			public String field() {
				return field;
			}
		});
		return this;
	}
	
	
	/**
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public ParserOption setGlobalField(String field, Object value) {
		notBlank(field, "field blank");
		globalFieldMap.put(field, value);
		return this;
	}
	
	public int getSaxBatch() {
		return saxBatch;
	}

	public ParserOption setSaxBatch(int saxBatch) {
		isPositive(saxBatch, "saxBatch not positive");
		if (saxBatch > 10000) {
			logger.warn("saxBatch[{}] > 10000, saxBatch too large to partion", saxBatch);
		}
		this.saxBatch = saxBatch;
		return this;
	}

	public Object searchField(String fieldKey){
		return globalFieldMap.get(fieldKey);
	}
	
	public Convertion searchConvertion(String fieldKey) {
		return convertions.get(fieldKey);
	}
	
	public int getSkip() {
		return skip;
	}

	public int getTitlesRow() {
		return titlesRow;
	}

	public int getExtendLevel() {
		return extendLevel;
	}
}

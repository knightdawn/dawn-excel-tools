package knight.su.dawn.excelt.exp.csv.config;

import knight.su.dawn.excelt.exp.validate.ValidateAssert;

import java.util.List;
/**
 *
 * Date: 2019/3/10<br/>
 *
 * @author sugengbin
 */
public class CsvOption {
	
	/**
	 * 字段名，文件按keys顺序输出
	 */
	private String[] keys;
	
	/**
	 * 表头名，需要与keys数理对应，否则以keys为准
	 */
	private String[] titleNames;
	/**
	 * 是否压缩
	 */
	private boolean zip = false;
	
	public boolean isZip() {
		return zip;
	}

	public CsvOption toZip() {
		this.zip = true;
		return this;
	}

	public String[] getTitleNames() {
		return titleNames;
	}

	public CsvOption inTitles(String... titleNames) {
        ValidateAssert.notNull(titleNames, "titleNames is null or empty");
		this.titleNames = titleNames;
		return this;
	}

	public CsvOption inTitles(String inputTitleNames) {
        ValidateAssert.notBlank(inputTitleNames, "titleNames is null or empty");
		this.titleNames = inputTitleNames.split(",");
		return this;
	}

	public CsvOption inTitles(List<String> inputTitleNames) {
        ValidateAssert.notEmpty(inputTitleNames, "titleNames is null or empty");
		this.titleNames = inputTitleNames.toArray(new String[inputTitleNames.size()]);
		return this;
	}

	public String[] getKeys() {
		return keys;
	}

	public CsvOption sortBy(String inputKeys) {
        ValidateAssert.notBlank(inputKeys, "keys is null or empty");
		this.keys = inputKeys.split(",");
		return this;
	}
	
	public CsvOption sortBy(String... keys) {
	    ValidateAssert.notNull(keys, "keys is null or empty");
		this.keys = keys;
		return this;
	}

	public CsvOption sortBy(List<String> keys) {
	    ValidateAssert.notEmpty(keys, "keys is null or empty");
		this.keys = keys.toArray(new String[keys.size()]);
		return this;
	}

}

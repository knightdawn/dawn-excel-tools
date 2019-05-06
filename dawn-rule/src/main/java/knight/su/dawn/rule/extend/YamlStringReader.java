package knight.su.dawn.rule.extend;

import java.io.StringReader;

/**
 *
 * Date: 2019年1月21日<br/>
 * 
 * @author sugengbin
 */
public class YamlStringReader {

	private StringReader stringReader;

	public YamlStringReader(String s) {
		this.stringReader = new StringReader(convert(s));
	}

	/**
	 * 
	 * @return
	 */
	public StringReader getStringReader() {
		return stringReader;
	}

	/**
	 * <pre>
	 * 其他可读性配置组件：https://gist.github.com/JosefJezek/9206547
	 * wiki: https://en.wikipedia.org/wiki/YAML
	 * 验证（yaml -> json格式）：https://codebeautify.org/yaml-to-json-xml-csv
	 * 转换yaml格式不支持的字符
	 * 1、yaml不支持tab缩进，用两个空格代替；
	 * 2、TODO
	 * 
	 * </pre>
	 * @param input
	 * @return
	 */
	private String convert(String input) {
		return input.replaceAll("\t", "  ");
	}
}

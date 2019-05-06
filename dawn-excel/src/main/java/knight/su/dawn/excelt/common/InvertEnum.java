package knight.su.dawn.excelt.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Date: 2019/1/18<br/>
 * 
 * @author sugengbin
 */
public enum InvertEnum {

	DEFAULT(null),
	I01(buildMap());

	static Map<String, String> buildMap() {
		Map<String, String> yesNo = new HashMap<>();
		yesNo.put("是", "1");
		yesNo.put("否", "0");
		return yesNo;
	}
	
	private Map<String, String> map;

	private InvertEnum(Map<String, String> map) {
		this.map = map;
	}
	
	public Map<String, String> getMap() {
		return this.map;
	}
}

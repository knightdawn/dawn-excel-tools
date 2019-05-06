package knight.su.dawn.rule.extend;

import knight.su.dawn.rule.api.Condition;
import knight.su.dawn.rule.basic.Facts;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * Date: 2019年1月16日<br/>
 * 
 * @author sugengbin
 */
public class Conditions {

	private static final String QUESTION_MARK = "?";
	private static final String AND_MARK = "&";
	private static final String EQUAL_SIGN = "=";
	Map<String, Condition> conditions = new HashMap<>();
	
	/**
	 * 
	 * @param key
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public boolean register(String key, Condition condition) throws Exception {
		if (key.contains(QUESTION_MARK) || key.contains(AND_MARK)) {
			throw new Exception(); // FIXME	
		}
		if (conditions.containsKey(key)) {
			throw new Exception(); // FIXME	
		}
		conditions.put(key, condition);
		return Boolean.TRUE;
	}

	/**
	 * 
	 * @param expression
	 * @param facts 
	 * @return
	 */
	public Condition search(String expression, Facts facts) {
		// checkCity?cityCode=val.citya
		// checkCity?cityCode=val.cityb&areaCode=val.cityc
		// checkList?val.a&val.b
		Objects.requireNonNull(expression);
		expression = expression.trim();
		if (!expression.contains(QUESTION_MARK)) {
			return null;
		}
		String[] exps = expression.split(QUESTION_MARK);
		if (exps.length != 2) {
			return null;
		}
		// FIXME
		return conditions.get(exps[0]);
	}
}

package knight.su.dawn.rule.el.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import knight.su.dawn.rule.api.Action;
import knight.su.dawn.rule.basic.Facts;

/**
 *
 * Date: 2019年1月18日<br/>
 * 
 * @author sugengbin
 */
public class AviatorAction implements Action {

	private String expression;
	private Expression exp;
	private int index;
	private String resultKey;

	public AviatorAction(String expression, int index) {
		this.expression = String.format("\"%s\"", expression);
		this.exp = AviatorEvaluator.compile(this.expression);
		this.index = index;
		this.resultKey = "result_" + index;
	}

	@Override
	public void execute(Facts facts) throws Exception {
		Object result = exp.execute(facts.asMap());
//		if (result instanceof String) {
		facts.put(resultKey, result);
//		}
	}

}

package knight.su.dawn.rule.el.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import knight.su.dawn.rule.api.Condition;
import knight.su.dawn.rule.basic.Facts;
import knight.su.dawn.rule.extend.ExtendCondition;

/** 
 *
 * Date:     2019年1月18日<br/> 
 * @author   sugengbin 
 */
public class AviatorCondition extends ExtendCondition implements Condition {

	private Expression exp;
	
	public AviatorCondition(String expression) {
		super(expression);
		this.exp = AviatorEvaluator.compile(expression);
	}

	@Override
	public boolean evaluateEl(Facts facts) {
		return (boolean) exp.execute(facts.asMap());
	}

}

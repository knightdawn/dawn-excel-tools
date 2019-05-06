package knight.su.dawn.rule.extend;

import knight.su.dawn.rule.api.Condition;
import knight.su.dawn.rule.basic.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * Date: 2019年1月16日<br/>
 * 
 * @author sugengbin
 */
public abstract class ExtendCondition implements Condition {

	private static Logger LOGGER = LoggerFactory.getLogger(ExtendCondition.class);
	private String expression;

	public ExtendCondition(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean evaluate(Facts facts) {
		try {
			Condition extendCondition = SingletonEnum.CONDITIONS.getInstance().search(this.expression, facts);
			if (Objects.nonNull(extendCondition)) {
				return extendCondition.evaluate(facts);
			} else {
				return evaluateEl(facts);
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public abstract boolean evaluateEl(Facts facts);

	public String getExpression() {
		return expression;
	}

}

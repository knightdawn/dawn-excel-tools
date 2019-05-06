package knight.su.dawn.rule.el.aviator;

import knight.su.dawn.rule.api.Action;
import knight.su.dawn.rule.api.Condition;
import knight.su.dawn.rule.api.ElType;
import knight.su.dawn.rule.api.Rule;
import knight.su.dawn.rule.basic.BasicRule;
import knight.su.dawn.rule.basic.Facts;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Date: 2019年1月18日<br/>
 * 
 * @author sugengbin
 */
public class AviatorRule extends BasicRule {

	private Condition condition = Condition.FALSE;
	private List<Action> actions = new ArrayList<>();

	public AviatorRule() {
		super(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY, ElType.AVIATOR);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public AviatorRule name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * @param description
	 * @return
	 */
	public AviatorRule description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * 
	 * @param priority
	 * @return
	 */
	public AviatorRule priority(int priority) {
		this.priority = priority;
		return this;
	}

	/**
	 * 
	 * @param condition
	 * @return
	 */
	public AviatorRule when(String condition) {
		this.condition = new AviatorCondition(condition);
		return this;
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	public AviatorRule then(String action) {
		this.actions.add(new AviatorAction(action, 0));
		return this;
	}

	/**
	 * 
	 * @param action
	 * @param index
	 * @return
	 */
	public AviatorRule then(String action, int index) {
		this.actions.add(new AviatorAction(action, index));
		return this;
	}
	
	@Override
	public boolean evaluate(Facts facts) {
		return condition.evaluate(facts);
	}

	@Override
	public void execute(Facts facts) throws Exception {
		for (Action action : actions) {
			action.execute(facts);
		}
	}
}

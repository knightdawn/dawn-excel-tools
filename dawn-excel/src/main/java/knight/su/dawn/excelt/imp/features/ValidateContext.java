package knight.su.dawn.excelt.imp.features;

import knight.su.dawn.excelt.imp.result.ValidateResultListener;
import knight.su.dawn.rule.basic.DefaultRulesEngine;
import knight.su.dawn.rule.basic.Facts;

/**
 * Created by sugengbin 2019/04/09
 */
public class ValidateContext {

	private DefaultRulesEngine rulesEngine;
	private ValidateResultListener listener;
	private Facts facts;

	public ValidateContext() {
		rulesEngine = new DefaultRulesEngine();
		listener = new ValidateResultListener();
		rulesEngine.registerRuleListener(listener);
		facts = new Facts();
	}
	
	public void resetListener() {
		listener.reset();
	}
	
	public DefaultRulesEngine getRulesEngine() {
		return rulesEngine;
	}

	public void setRulesEngine(DefaultRulesEngine rulesEngine) {
		this.rulesEngine = rulesEngine;
	}

	public ValidateResultListener getListener() {
		return listener;
	}

	public void setListener(ValidateResultListener listener) {
		this.listener = listener;
	}

	public Facts getFacts() {
		return facts;
	}

	public void setFacts(Facts facts) {
		this.facts = facts;
	}

}

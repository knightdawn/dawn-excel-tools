package knight.su.dawn.excelt.imp.result;

import knight.su.dawn.rule.api.Rule;
import knight.su.dawn.rule.basic.DefaultRuleListener;
import knight.su.dawn.rule.basic.Facts;

/**
 * 
 * Created by sugengbin 2019/1/4
 */
public class ValidateResultListener extends DefaultRuleListener {

	private boolean pass = true;

	@Override
	public void afterEvaluate(Rule paramRule, Facts paramFacts, boolean result) {
		if (pass) {
			this.pass = !result;
		}
	}

	public boolean isPass() {
		return this.pass;
	}
	
	public void reset() {
		this.pass = true;
	}
}

/**
 * The MIT License
 *
 *  Copyright (c) 2018, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package knight.su.dawn.rule.el.mvel;

import knight.su.dawn.rule.api.Condition;
import knight.su.dawn.rule.basic.Facts;
import knight.su.dawn.rule.extend.ExtendCondition;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * This class is an implementation of {@link Condition} that uses
 * <a href="https://github.com/mvel/mvel">MVEL</a> to evaluate the condition.
 *
 * 
 * Date: 2019年1月16日<br/>
 * add abstract ExtendCondition: extend custom condition
 * @author sugengbin
 */
public class MVELCondition extends ExtendCondition implements Condition {

	private static Logger LOGGER = LoggerFactory.getLogger(MVELCondition.class);

	private Serializable compiledExpression;

	/**
	 * Create a new {@link MVELCondition}.
	 *
	 * @param expression
	 *            the condition written in expression language
	 */
	public MVELCondition(String expression) {
		super(expression);
		compiledExpression = MVEL.compileExpression(expression);
	}

	@Override
	public boolean evaluateEl(Facts facts) {
		try {
			return (boolean) MVEL.executeExpression(compiledExpression, facts.asMap());
		} catch (Exception e) {
			LOGGER.debug("Unable to evaluate expression: '" + getExpression() + "' on facts: " + facts, e);
			return false;
		}
	}
}

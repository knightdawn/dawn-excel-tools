package knight.su.dawn.mvel2;

import knight.su.dawn.Demo;
import knight.su.dawn.Error;
import knight.su.dawn.rule.basic.DefaultRulesEngine;
import knight.su.dawn.rule.basic.Facts;
import knight.su.dawn.rule.basic.Rules;
import knight.su.dawn.rule.el.mvel.MVELRuleFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** 
 *
 * Date:     2019年1月17日<br/> 
 * @author   sugengbin 
 */
public class MvelTest2 {

	@Test
	public void testCurrentCity() throws IOException {
		Demo demo = new Demo();
		demo.setA("020");
		Facts facts = new Facts();
		facts.put("val", demo);
		facts.put("currentCity", "755");
		Error error = new Error();
		facts.put("error", error);
		Rules rules = MVELRuleFactory.createRulesFrom("src/test/resources/mvel2/checkCurrentCity.yml");
		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
		List<Error> errors = new ArrayList<>();
		rulesEngine.fire(rules, facts, result -> {
			Error e = result.get("error");
			errors.add(new Error(e.getRow(), e.getMsg()));
		});
		
		assertThat(errors.size()).isEqualTo(1);
		assertThat(errors.get(0).getMsg()).isEqualTo("a与当前选择城市不一致");
	}
	
//	@Test
//	public void testNotExistList() throws IOException {
//		Demo demo = new Demo();
//		demo.setA("D");
//		Facts facts = new Facts();
//		facts.put("val", demo);
//		List<String> list = Arrays.asList("A","B","C","F","Y");
//		facts.put("array", list);
//		Error error = new Error();
//		facts.put("error", error);
//		Rules rules = MVELRuleFactory.createRulesFrom("src/test/resources/mvel2/checkNotExistList.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			Error e = result.get("error");
//			errors.add(new Error(e.getRow(), e.getMsg()));
//		});
//		
//		assertThat(errors.size()).isEqualTo(2);
//		assertThat(errors.get(0).getMsg()).isEqualTo("a不在对应类型中");
//	}
//	
//	@Test
//	public void testCheckRepeat() throws IOException {
//		Demo demo = new Demo();
//		demo.setA("a,b,c,b,d");
//		Facts facts = new Facts();
//		facts.put("val", demo);
//		Error error = new Error();
//		facts.put("error", error);
//		Rules rules = MVELRuleFactory.createRulesFrom("src/test/resources/mvel2/checkRepeat.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			Error e = result.get("error");
//			errors.add(new Error(e.getRow(), e.getMsg()));
//		});
//		
//		assertThat(errors.size()).isEqualTo(1);
//		assertThat(errors.get(0).getMsg()).isEqualTo("a中存在重复编码");
//	}
}

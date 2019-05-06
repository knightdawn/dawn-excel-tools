package knight.su.dawn.mvel2;

import knight.su.dawn.Demo;
import knight.su.dawn.Error;
import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.rule.basic.DefaultRulesEngine;
import knight.su.dawn.rule.basic.Facts;
import knight.su.dawn.rule.basic.Rules;
import knight.su.dawn.rule.el.mvel.MVELRuleFactory;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * Date: 2019年1月16日<br/>
 * 
 * @author sugengbin
 */
public class MvelTest {

	@Test
	public void testCheckLength() throws IOException {
		Demo demo = new Demo();
		demo.setA("asdf");
		demo.setC("asdfasdf");
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);
		Rules rules = MVELRuleFactory.createRulesFrom("src/test/resources/mvel2/checkLength.yml");
		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
		List<Error> errors = new ArrayList<>();
		rulesEngine.fire(rules, facts, result -> {
			Error e = result.get("error");
			errors.add(new Error(e.getRow(), e.getMsg()));
		});
		
		assertThat(errors.size()).isEqualTo(1);
		assertThat(errors.get(0).getMsg()).isEqualTo("c长度不能超过5");
	}
	
	@Test
	public void testMvel() throws IOException {
		TickTockUtil tickTock = new TickTockUtil("", 0);
		Reader reader = new StringReader(new String(Files.readAllBytes(Paths.get("src/test/resources/mvel2/test.yml"))));
		tickTock.clock("reader");
		Demo demo = new Demo();
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);
		
		Rules rules = MVELRuleFactory.createRulesFrom(reader);
		tickTock.clock("create rules");
		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
		List<Error> errors = new ArrayList<>();
		rulesEngine.fire(rules, facts, result -> {
			Error e = result.get("error");
			errors.add(new Error(e.getRow(), e.getMsg()));
		});
		tickTock.clock("rules fire");
		assertThat(errors.size()).isEqualTo(2);
		assertThat(errors.get(0).getMsg()).isEqualTo("a不能为空");
		assertThat(errors.get(1).getMsg()).isEqualTo("a只能为1或2");
		tickTock.clock("assert");
	}
	
	@Test
	public void testCheckPattern() throws IOException {
		Demo demo = new Demo();
		demo.setA("12:00");
		demo.setC("abc");
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);
		Rules rules = MVELRuleFactory.createRulesFrom("src/test/resources/mvel2/checkPattern.yml");
		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
		List<Error> errors = new ArrayList<>();
		rulesEngine.fire(rules, facts, result -> {
			Error e = result.get("error");
			errors.add(new Error(e.getRow(), e.getMsg()));
		});
		
		assertThat(errors.size()).isEqualTo(2);
		assertThat(errors.get(0).getMsg()).isEqualTo("a不符合HH:mm:ss格式");
		assertThat(errors.get(1).getMsg()).isEqualTo("c不符合数字格式");
	}
	
	@Test
	public void testCheckOptions() throws IOException {
		Demo demo = new Demo();
		demo.setA("4");
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);
		Rules rules = MVELRuleFactory.createRulesFrom("src/test/resources/mvel2/checkOptions.yml");
		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
		List<Error> errors = new ArrayList<>();
		rulesEngine.fire(rules, facts, result -> {
			Error e = result.get("error");
			errors.add(new Error(e.getRow(), e.getMsg()));
		});
		
		assertThat(errors.size()).isEqualTo(1);
		assertThat(errors.get(0).getMsg()).isEqualTo("a只能是1、2、3");
	}
}

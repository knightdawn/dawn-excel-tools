package knight.su.dawn.aviator;

import knight.su.dawn.Demo;
import knight.su.dawn.Error;
import knight.su.dawn.rule.basic.DefaultRulesEngine;
import knight.su.dawn.rule.basic.Facts;
import knight.su.dawn.rule.basic.Rules;
import knight.su.dawn.rule.el.aviator.AviatorRuleFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * Date: 2019年1月21日<br/>
 * 
 * @author sugengbin
 */
public class AviatorRuleTest1 {

	@Test
	public void test() throws IOException {
		// 测试数据
		Demo demo = new Demo();
		demo.setA("2");
		demo.setC(null);
		Facts facts = new Facts();
		facts.put("val", demo);
		// 解析
		Rules rules = AviatorRuleFactory.createRulesFrom("src/test/resources/aviator/test.yml");
		List<Error> errors = new ArrayList<>();
		// 执行
		new DefaultRulesEngine().fire(rules, facts, result -> {
			String msg = result.get("result_0"); // 获取action执行结果，key:result_index, index代表action顺序
			errors.add(new Error(1, msg));
		});
		assertThat(errors.size()).isEqualTo(1);
		assertThat(errors.get(0).getMsg()).isEqualTo("c不能为空");
	}
//	
//	@Test
//	public void testCheckLength() throws IOException {
//		Demo demo = new Demo();
//		demo.setA("asdf");
//		demo.setC("asdfasdf");
//		Facts facts = new Facts();
//		facts.put("val", demo);
//		Error error = new Error();
//		facts.put("error", error);
//		Rules rules = AviatorRuleFactory.createRulesFrom("src/test/resources/aviator/checkLength.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			String e = result.get("result_0");
//			errors.add(new Error(1, e));
//		});
//		
//		assertThat(errors.size()).isEqualTo(1);
//		assertThat(errors.get(0).getMsg()).isEqualTo("c长度不能超过5");
//	}
//	
//	@Test
//	public void testCheckPattern() throws IOException {
//		Demo demo = new Demo();
//		demo.setA("12:00");
//		demo.setC("abc");
//		Facts facts = new Facts();
//		facts.put("val", demo);
//		Error error = new Error();
//		facts.put("error", error);
//		Rules rules = AviatorRuleFactory.createRulesFrom("src/test/resources/aviator/checkPattern.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			String e = result.get("result_0");
//			errors.add(new Error(1, e));
//		});
//		
//		assertThat(errors.size()).isEqualTo(2);
//		assertThat(errors.get(0).getMsg()).isEqualTo("a不符合HH:mm:ss格式");
//		assertThat(errors.get(1).getMsg()).isEqualTo("c不符合数字格式");
//	}
//	
//	/**
//	 * FIXME: 定义数组列表 ？？
//	 * @throws IOException
//	 */
//	@Test
//	public void testCheckOptions() throws IOException {
//		Demo demo = new Demo();
//		demo.setA("4");
//		Facts facts = new Facts();
//		facts.put("val", demo);
//		Error error = new Error();
//		facts.put("error", error);
//		Rules rules = AviatorRuleFactory.createRulesFrom("src/test/resources/aviator/checkOptions.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			String e = result.get("result_0");
//			errors.add(new Error(1, e));
//		});
//		
//		assertThat(errors.size()).isEqualTo(1);
//		assertThat(errors.get(0).getMsg()).isEqualTo("a只能是1、2、3");
//	}
//	
//	@Test
//	public void testCurrentCity() throws IOException {
//		Demo demo = new Demo();
//		demo.setA("020");
//		Facts facts = new Facts();
//		facts.put("val", demo);
//		facts.put("currentCity", "755");
//		Error error = new Error();
//		facts.put("error", error);
//		Rules rules = AviatorRuleFactory.createRulesFrom("src/test/resources/aviator/checkCurrentCity.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			String e = result.get("result_0");
//			errors.add(new Error(1, e));
//		});
//		
//		assertThat(errors.size()).isEqualTo(1);
//		assertThat(errors.get(0).getMsg()).isEqualTo("a与当前选择城市不一致");
//	}
//	
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
//		Rules rules = AviatorRuleFactory.createRulesFrom("src/test/resources/aviator/checkNotExistList.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			String e = result.get("result_0");
//			errors.add(new Error(1, e));
//		});
//		
//		assertThat(errors.size()).isEqualTo(1);
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
//		Rules rules = AviatorRuleFactory.createRulesFrom("src/test/resources/aviator/checkRepeat.yml");
//		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
//		List<Error> errors = new ArrayList<>();
//		rulesEngine.fire(rules, facts, result -> {
//			String e = result.get("result_0");
//			errors.add(new Error(1, e));
//		});
//		
//		assertThat(errors.size()).isEqualTo(1);
//		assertThat(errors.get(0).getMsg()).isEqualTo("a中存在重复编码");
//	}
}

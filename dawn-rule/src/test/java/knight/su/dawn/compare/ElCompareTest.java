package knight.su.dawn.compare;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import knight.su.dawn.Demo;
import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.rule.basic.Facts;
import org.junit.Test;
import org.mvel2.MVEL;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * aviator 4.1.1
 * mvel2 2.4.0
 * </pre>
 * Date: 2019年1月22日<br/>
 * 
 * @author sugengbin
 */
public class ElCompareTest {

	/**
	 *  'A' == 'A' || 'B' == 'B' && 'ABCD' == t && 'A' == 'A' 
	 *  
	 */
	@Test
	public void test1() { 
		String test = "1234";
		Map<String, Object> map = new HashMap<>();
		map.put("t", test);
		/**
		 *  单次运行结果
		1、aviator 默认：AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
	    exp: 'A' == 'A' || 'B' == 'B' && 'ABCD' == t && 'A' == 'A' 
		mvel
		mvel2.10.耗时: 5毫秒
		mvel2.100.耗时: 0毫秒
		mvel2.1000.耗时: 2毫秒
		mvel2.10000.耗时: 3毫秒
		mvel2.100000.耗时: 12毫秒
		mvel2.1000000.耗时: 38毫秒
		aviator
		aviator.10.耗时: 65毫秒
		aviator.100.耗时: 50毫秒
		aviator.1000.耗时: 230毫秒
		aviator.10000.耗时: 1378毫秒
		aviator.100000.耗时: 11797毫秒
		aviator.1000000.耗时: 119790毫秒
		
		2、设置 AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.COMPILE); 
		exp: 'A' == 'A' || 'B' == 'B' && 'ABCD' == t && 'A' == 'A' 
		mvel
		mvel2.10.耗时: 5毫秒
		mvel2.100.耗时: 0毫秒
		mvel2.1000.耗时: 2毫秒
		mvel2.10000.耗时: 4毫秒
		mvel2.100000.耗时: 13毫秒
		mvel2.1000000.耗时: 38毫秒
		aviator
		aviator.10.耗时: 44毫秒
		aviator.100.耗时: 46毫秒
		aviator.1000.耗时: 201毫秒
		aviator.10000.耗时: 1303毫秒
		aviator.100000.耗时: 12605毫秒
		aviator.1000000.耗时: 127668毫秒
		
		3、修改为 exp: 'A' == 'a' || 'B' == 'b' && 'ABCD' == t && 'A' != '123' || 123 == t
		exp: 'A' == 'a' || 'B' == 'b' && 'ABCD' == t && 'A' != '123' || 123 == t 
		mvel
		mvel2.10.耗时: 29毫秒
		mvel2.100.耗时: 1毫秒
		mvel2.1000.耗时: 4毫秒
		mvel2.10000.耗时: 16毫秒
		mvel2.100000.耗时: 48毫秒
		mvel2.1000000.耗时: 199毫秒
		aviator
		aviator.10.耗时: 52毫秒
		aviator.100.耗时: 50毫秒
		aviator.1000.耗时: 268毫秒
		aviator.10000.耗时: 1573毫秒
		aviator.100000.耗时: 14120毫秒
		aviator.1000000.耗时: 147890毫秒
		 */
//		AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.COMPILE); 
//		String exp = " 'A' == 'a' || 'B' == 'b' && 'ABCD' == t && 'A' != '123' || 123 == t ";
//		System.out.println("exp:" + exp);
//		testMvel2(MVEL.compileExpression(exp), map);
//		testAviator(exp, map);
	}
	
	/**
	 *  1000+(100*t)-(600-3*100)%((68-9) -3)*2-t
	 */
	@Test
	public void test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("t", 123);
		/**
		 * exp: 1000+(100*t)-(600-3*100)%((68-9) -3)*2-t 
		mvel
		mvel2.10.耗时: 23毫秒
		mvel2.100.耗时: 1毫秒
		mvel2.1000.耗时: 8毫秒
		mvel2.10000.耗时: 19毫秒
		mvel2.100000.耗时: 60毫秒
		mvel2.1000000.耗时: 242毫秒
		aviator
		aviator.10.耗时: 50毫秒
		aviator.100.耗时: 51毫秒
		aviator.1000.耗时: 204毫秒
		aviator.10000.耗时: 1286毫秒
		aviator.100000.耗时: 11662毫秒
		aviator.1000000.耗时: 118919毫秒
		 */
//		String exp = " 1000+(100*t)-(600-3*100)%((68-9) -3)*2-t ";
//		System.out.println("exp:" + exp);
//		testMvel2(MVEL.compileExpression(exp), map);
//		testAviator(exp, map);
	}
	
	/**
	 *  val.a == '1' ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5 
	 */
	@Test
	public void test3() {
		Demo demo = new Demo();
		demo.setA("1");
		demo.setB("5");
		Facts facts = new Facts();
		facts.put("val", demo);
		
		/**
		 * exp:  val.a == '1' ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5  
		mvel
		mvel2.10.耗时: 27毫秒
		mvel2.100.耗时: 11毫秒
		mvel2.1000.耗时: 10毫秒
		mvel2.10000.耗时: 28毫秒
		mvel2.100000.耗时: 83毫秒
		mvel2.1000000.耗时: 303毫秒
		aviator
		aviator.10.耗时: 130毫秒
		aviator.100.耗时: 74毫秒
		aviator.1000.耗时: 348毫秒
		aviator.10000.耗时: 1747毫秒
		aviator.100000.耗时: 16004毫秒
		
		动态数据
		exp:  val.a == '1' ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5  
		mvel
		mvel2.10.耗时: 26毫秒
		mvel2.100.耗时: 8毫秒
		mvel2.1000.耗时: 11毫秒
		mvel2.10000.耗时: 34毫秒
		mvel2.100000.耗时: 112毫秒
		mvel2.1000000.耗时: 313毫秒
		aviator
		aviator.10.耗时: 107毫秒
		aviator.100.耗时: 77毫秒
		aviator.1000.耗时: 319毫秒
		aviator.10000.耗时: 1796毫秒
		aviator.100000.耗时: 15694毫秒
		aviator.1000000.耗时: 160744毫秒
		 */
		
//		String exp = "  val.a == '1' ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5  ";
//		System.out.println("exp:" + exp);
//		testMvel2(MVEL.compileExpression(exp), facts.asMap());
//		testAviator(exp, facts.asMap());
	}
	
	@Test
	public void test4() throws IOException { 
		/**
		 *  mvel
			mvel2.10.耗时: 48毫秒
			mvel2.100.耗时: 22毫秒
			mvel2.1000.耗时: 60毫秒
			mvel2.10000.耗时: 175毫秒
			mvel2.100000.耗时: 536毫秒
			mvel2.1000000.耗时: 4320毫秒
			aviator
			aviator.10.耗时: 82毫秒
			aviator.100.耗时: 36毫秒
			aviator.1000.耗时: 208毫秒
			aviator.10000.耗时: 1167毫秒
			aviator.100000.耗时: 9366毫秒
			aviator.1000000.耗时: 93105毫秒
		 */
		
		AviatorEvaluator.addFunction(new AbstractFunction() {
			@Override
			public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
				String value = FunctionUtils.getStringValue(arg1, env);
				String[] list = value.split(",");
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < list.length; i++) {
					String key = list[i];
					if (Objects.isNull(map.get(key))) {
						map.put(key, list[i]);
					} else {
						return AviatorBoolean.TRUE;
					}
				}
				return AviatorBoolean.FALSE;
			}
			@Override
			public String getName() {
				return "isRepeat";
			}
		});
		Demo demo = new Demo();
		demo.setA("a,b,c,b,d");
		Facts facts = new Facts();
		facts.put("val", demo);
		String exp = "list = val.a.split(\",\");\n" +
				"map = [\"1\":\"\"];\n" +
				"flag = 0;\n" +
				"for (int i = 0; i < list.length; i++) {\n" +
				"    key = list[i];\n" +
				"    if (map[key] == null) {\n" +
				"       map[key] = key;\n" +
				"    } else {\n" +
				"      flag = 1;\n" +
				"    }\n" +
				"}\n" +
				"flag != 0;";
		testMvel2(MVEL.compileExpression(exp), facts.asMap());
		testAviator("isRepeat(val.a)", facts.asMap());
	}
	
	private void testAviator(String exp, Map<String, Object> map) {
		TickTockUtil tickTock = new TickTockUtil("aviator", 0);
		testAviatorTest1(exp, 10, map);
		tickTock.clock("aviator.10.耗时");
		testAviatorTest1(exp, 100, map);
		tickTock.clock("aviator.100.耗时");
		testAviatorTest1(exp, 1000, map);
		tickTock.clock("aviator.1000.耗时");
		testAviatorTest1(exp, 10000, map);
		tickTock.clock("aviator.10000.耗时");
		testAviatorTest1(exp, 100000, map);
		tickTock.clock("aviator.100000.耗时");
		testAviatorTest1(exp, 1000000, map);
		tickTock.clock("aviator.1000000.耗时");
	}
	
	private void testMvel2(Serializable exp, Map<String, Object> map) {
		TickTockUtil tickTock = new TickTockUtil("mvel", 0);
		testMvel2Test1(exp, 10, map);
		tickTock.clock("mvel2.10.耗时");
		testMvel2Test1(exp, 100, map);
		tickTock.clock("mvel2.100.耗时");
		testMvel2Test1(exp, 1000, map);
		tickTock.clock("mvel2.1000.耗时");
		testMvel2Test1(exp, 10000, map);
		tickTock.clock("mvel2.10000.耗时");
		testMvel2Test1(exp, 100000, map);
		tickTock.clock("mvel2.100000.耗时");
		testMvel2Test1(exp, 1000000, map);
		tickTock.clock("mvel2.1000000.耗时");
	}
	
	private void testMvel2Test1(Serializable exp, int num, Map<String, Object> map) {
		for (int i = 0; i < num; i++) {
//			Demo demo = (Demo) map.get("val");
//			demo.setB(i);
//			map.put("val", demo);
			map.put("t", i);
			MVEL.executeExpression(exp, map);
		}
	}
	
	private void testAviatorTest1(String exp, int num, Map<String, Object> map) {
		for (int i = 0; i < num; i++) {
//			Demo demo = (Demo) map.get("val");
//			demo.setB(i);
//			map.put("val", demo);
			map.put("t", i);
			AviatorEvaluator.execute(exp, map);
		}
	}
}

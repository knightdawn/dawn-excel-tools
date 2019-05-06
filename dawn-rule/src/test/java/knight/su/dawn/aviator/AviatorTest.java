package knight.su.dawn.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import knight.su.dawn.Demo;
import knight.su.dawn.Error;
import knight.su.dawn.rule.basic.Facts;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * Date: 2019年1月21日<br/>
 * 
 * @author sugengbin
 */
public class AviatorTest {

	/**
	 * 1、不支持null,使用nil对象代替
	 */
	@Test
	public void test() {
		Demo demo = new Demo();
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);

		Boolean result = (Boolean) AviatorEvaluator.execute("val.a == nil", facts.asMap());
		assertThat(result).isTrue();
	}
	
	/**
	 * FIXME: Can't assignment value to `error.msg`
	 */
	@Test
	public void test1() {
		Demo demo = new Demo();
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);

		AviatorEvaluator.execute("error.msg = \"test\"", facts.asMap());
		assertThat(error.getMsg()).isEqualTo("test");
	}
	
	/**
	 * 2、return String
	 */
	@Test
	public void test2() {
		Demo demo = new Demo();
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);

		String result = (String)AviatorEvaluator.execute("\"test\"", facts.asMap());
		assertThat(result).isEqualTo("test");
	}

	/**
	 * 3、正则
	 */
	@Test
	public void test3() {
		Demo demo = new Demo();
		demo.setC("");
		Facts facts = new Facts();
		facts.put("val", demo);
		Error error = new Error();
		facts.put("error", error);

		Boolean result = (Boolean)AviatorEvaluator.execute("!(val.c=~/[0-9]+/)", facts.asMap());
		assertThat(result).isTrue();
	}
	
	/**
	 * 4、inner function
	 */
	@Test
	public void test4() {
		Demo demo = new Demo();
		demo.setA("D");
		Facts facts = new Facts();
		facts.put("val", demo);
		List<String> list = Arrays.asList("A","B","C","F","Y");
		facts.put("array", list);

		Boolean result = (Boolean)AviatorEvaluator.execute("(!include(array, val.a))", facts.asMap());
		assertThat(result).isTrue();
	}
	
	/**
	 * 5、custom function
	 */
	@Test
	public void test5() {
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
		demo.setA("1,2,3,4,3,6");
		demo.setC("a,b,c,d,e");
		Facts facts = new Facts();
		facts.put("val", demo);

		Boolean result1= (Boolean)AviatorEvaluator.execute("isRepeat(val.a)", facts.asMap());
		Boolean result2 = (Boolean)AviatorEvaluator.execute("isRepeat(val.c)", facts.asMap());
		assertThat(result1).isTrue();
		assertThat(result2).isFalse();
	}
	
	/**
	 * 6、compare
	 */
	@Test
	public void test6() {
		Demo demo = new Demo();
		demo.setB("5");
		Facts facts = new Facts();
		facts.put("val", demo);
		
		Boolean result1 = (Boolean)AviatorEvaluator.execute(" 0<= val.b && val.b < 5 ", facts.asMap());
		assertThat(result1).isFalse();
	}
	

	/**
	 * 7、bool ? exp1: exp2
	 */
	@Test
	public void test7() {
		Demo demo = new Demo();
		demo.setA("1");
		demo.setB("5");
		Facts facts = new Facts();
		facts.put("val", demo);
		
		Boolean result1 = (Boolean)AviatorEvaluator.execute(" long(val.a) == 1 ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5 ", facts.asMap());
		assertThat(result1).isFalse();
		Boolean result11 = (Boolean)AviatorEvaluator.execute(" val.a == '1' ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5 ", facts.asMap());
		assertThat(result11).isFalse();
		demo.setA("2");
		Boolean result2 = (Boolean)AviatorEvaluator.execute(" long(val.a) == 1 ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5 ", facts.asMap());
		assertThat(result2).isTrue();
		Boolean result22 = (Boolean)AviatorEvaluator.execute(" val.a == '1' ? 0 < val.b && val.b < 5 : 0 <= val.b && val.b <= 5 ", facts.asMap());
		assertThat(result22).isTrue();
	}
	
	@Test
	public void test8() {
		AviatorEvaluator.addFunction(new AbstractFunction() {
	         @Override
	         public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
	             String value = FunctionUtils.getStringValue(arg1, env);
	             if (value == null || "".equals(value)) {
	                 return AviatorBoolean.TRUE;
	             }
	             String[] cf = value.split(":");
	             System.out.println(Arrays.toString(cf));
	             if (cf.length != 3) {
	                 return AviatorBoolean.FALSE;
	             }
	             int len = value.trim().length();
	             if (len > 8 || len < 5) {
	                 return AviatorBoolean.FALSE;
	             }
	             return AviatorBoolean.TRUE;
	         }
	         @Override
	         public String getName() {
	             return "checkHms";
	         }
	     });
		
		Demo demo = new Demo();
		demo.setA("0.132465465");
		Facts facts = new Facts();
		facts.put("val", demo);

		Boolean result1= (Boolean)AviatorEvaluator.execute("checkHms(val.a)", facts.asMap());
		assertThat(result1).isFalse();
	}
	 
}

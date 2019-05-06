package knight.su.dawn.core.number.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试基本类型对应的包装类型、缓冲池优化
 * @author Edward
 * @date 2019年4月28日
 * @version 1.0
 */
public class IntegerCacheTest {
	
	@Test
	public void test() {
		Integer a = 120, b = 8, c = 128; // 此处编译期会做优化，通过Integer.valueOf(int)进行装箱操作
		Long g = 128L;
		
		Assert.assertTrue(c == (a + b)); // a+b两个Integer对象先进行拆箱，然后进行数值计算，然后c也进行拆箱，比较基本类型大小
		Assert.assertTrue(g == (a + b)); // 前面同上，后面右侧的计算结果int转换为long，然后g进行拆箱，比较基本类型大小
		Assert.assertFalse(g.equals(a + b)); // a+b最后计算结果会进行装箱，因为equals接收对象参数，最后因为一个是Long，一个是Integer，不等
	}
	
	
	@Test
	public void test2() {
        Integer a = 1, b = 1;  // 自动装箱
        Integer c = 128, d = 128; // 自动装箱
        Integer e = new Integer(1), f = new Integer(1);
        
        Assert.assertTrue(a == b);    // true 自动装箱，还是用到了缓存
        Assert.assertFalse(c == d); // false 自动装箱，不在缓存范围
        Assert.assertTrue(c.equals(d));    // true 都是Integer对象，拆箱，进行值对比
        Assert.assertFalse(a == e); // false a是自动装箱，e直接在堆中new了对象
        Assert.assertFalse(e == f);    // false 两个堆对象
        Assert.assertTrue(a == 1); // a拆箱，比较值
	}

}

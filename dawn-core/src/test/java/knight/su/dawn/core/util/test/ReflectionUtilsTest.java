package knight.su.dawn.core.util.test;

import knight.su.dawn.core.util.ReflectionUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试获取父类的泛型参数类型
 * @author Edward
 * @date 2019年4月28日
 * @version 1.0
 */
public class ReflectionUtilsTest {

	@Test
	public void test() {
		@SuppressWarnings("unused")
		BaseDao<User> baseDao = new BaseDaoImpl();
		Assert.assertTrue(BaseDao.getGenericClassType() == User.class);
	}
	
	private static class BaseDaoImpl extends BaseDao<User> {
		public BaseDaoImpl() {
			super();
		}
	}
	
	private static abstract class BaseDao<T> {
		private static Class<?> clz;
		public BaseDao() {
			clz = ReflectionUtils.getGenericClassType(getClass());
		}
		
		public static Class<?> getGenericClassType() {
			return clz;
		}
 	}
	
	@SuppressWarnings("unused")
	private static class User {
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
}


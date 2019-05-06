package knight.su.dawn.core.demo;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.alibaba.fastjson.JSON;

/**
 * 序列化性能测试
 * @author Edward
 * @date 2019年4月21日
 * @version 1.0
 */
public class SerializationPerformanceTestingDemo {

	public static void main(String[] args) {
		Student student = new Student("Edward", 20, 3);
		int times = 100000;

		constructTest(student, times); // 直接new的方式 
		jsonTest(student, times); // json序列化和反序列化
		serializationUtilsTest(student, times); // JDK自带的序列化和反序列化
		
//		constructTest-times: 6
//		jsonTest-times: 449
//		SerializationUtils-times: 1047
		
		//深度克隆的方法：
		//protostuff
		//Hessian
		//Kryo
	}
	
	public static void constructTest(final Student student, final int times) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			new Student(student.getName(), student.getAge(), student.getLevel());
		}
		System.out.println("constructTest-times: " + (System.currentTimeMillis() - start));
	}
	
	public static void jsonTest(final Student student, final int times) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			JSON.parseObject(JSON.toJSONString(student), Student.class);
		}
		System.out.println("jsonTest-times: " + (System.currentTimeMillis() - start));
	}
	
	public static void serializationUtilsTest(final Student student, final int times) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			SerializationUtils.clone(student);
		}
		System.out.println("SerializationUtils-times: " + (System.currentTimeMillis() - start));
	}
	
	
	@SuppressWarnings("unused")
	private static class Student implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		private int age;
		private int level;
		public Student() {
			super();
		}
		public Student(String name, int age, int level) {
			this.name = name;
			this.age = age;
			this.level = level;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
	}
	
}

package knight.su.dawn.core.demo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class GenericDemo {

	
	public static void main(String[] args) {
		Foo<String> foo = new Foo<String>();
//		System.out.println(foo.getT());
//		System.out.println(foo.getArray().length);
	}
	
	
	@SuppressWarnings({"unused", "unchecked"})
	private static class Foo<T> {
		private T t;
		private T[] array;
		private List<T> list = new ArrayList<T>();
		private String a;
		private int b;
		private Foo() {
			super();
			try {
				Field[] fields = Foo.class.getDeclaredFields();
				for (Field field : fields) {
					System.out.println(field.getClass());
					System.out.println(field.getType());
					System.out.println(field.getGenericType());
					System.out.println("");
				}
				
//				t = (T) getClassType("t", Foo.class).newInstance();
//				array = (T[]) Array.newInstance(getClassType("array", Foo.class), 5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private Class<?> getClassType(String propertyName, Class<?> beanClass) 
				throws Exception {
//			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, beanClass);
//			System.out.println(propertyName + " : " + propertyDescriptor.getPropertyType());
//			return propertyDescriptor.getPropertyType();
//			Type type = beanClass.getDeclaredField(propertyName).getGenericType();
			return beanClass.getDeclaredField(propertyName).getClass();
		}

		public T getT() {
			return t;
		}
		public void setT(T t) {
			this.t = t;
		}
		public T[] getArray() {
			return array;
		}
		public void setArray(T[] array) {
			this.array = array;
		}
		public List<T> getList() {
			return list;
		}
		public void setList(List<T> list) {
			this.list = list;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}
	}
	
}

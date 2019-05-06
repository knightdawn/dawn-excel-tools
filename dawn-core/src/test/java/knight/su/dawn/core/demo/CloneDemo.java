package knight.su.dawn.core.demo;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class CloneDemo {

	public static void main(String[] args) throws Exception {
		Student student = new Student();
		student.setName("Edward");
		student.setAge(10);
		Amount amount = new Amount();
		amount.setFee(100);
		student.setAmount(amount);
		
		Student student2 = (Student) student.clone(); // 克隆一个对象
		
		// 修改引用对象，测试对克隆后的对象是否产生影响。
		amount.setFee(200);
		
		System.out.println(ToStringBuilder.reflectionToString(student)
				+ " : " + student.getAmount().getFee());
		System.out.println(ToStringBuilder.reflectionToString(student2)
				+ " : " + student2.getAmount().getFee());
	}
	
	@SuppressWarnings("unused")
	private static class Student implements Cloneable { // 实现Cloneable接口，覆写clone方法
		private String name;
		private int age;
		private Amount amount;
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
		public Amount getAmount() {
			return amount;
		}
		public void setAmount(Amount amount) {
			this.amount = amount;
		}
		
		@Override
		public Object clone() throws CloneNotSupportedException {
			Student student = (Student) super.clone();
			
			// 重新new一个引用对象，即可实现深度克隆
			Amount amount = new Amount();
			amount.setFee(student.getAmount().getFee());
			student.setAmount(amount);
			
			return student;
		}
	}
	
	private static class Amount {
		private int fee;
		public int getFee() {
			return fee;
		}
		public void setFee(int fee) {
			this.fee = fee;
		}
	}
	
}

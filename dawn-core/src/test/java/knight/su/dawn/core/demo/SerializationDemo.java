package knight.su.dawn.core.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * JDK自带序列化和反序列化方式，速度慢，占空间
 * @author Edward
 * @date 2019年4月18日
 * @version 1.0
 */
public class SerializationDemo {
	
	public static void main(String[] args) {
	    SerializationDemo.testSerial();
	    SerializationDemo.testClone();
	    SerializationDemo.testCloneList();
	}

	/**
	 * 测试clone集合
	 * 使用apache的包提供的序列化工具SerializationUtils
	 */
    public static void testCloneList() {
	    List<PublicAccount> list = new ArrayList<>();
	    for (int i = 0; i < 5; i++) {
	        list.add(new PublicAccount("stu" + i, i));
	    }
	    
	    // List接口没有实现序列化接口，但ArrayList是有实现这个接口的，需要强制转化下
	    Serializable serializable = (Serializable) list;
    
	    @SuppressWarnings("unchecked")
        List<PublicAccount> newList = (List<PublicAccount>) SerializationUtils.clone(serializable);
        
	    System.out.println(newList.size());
	    System.out.println(newList.get(3).getName());
	}
	
	
	/**
	 * 测试对象序列化到文件，并从文件中读取和反序列化成对象的方法
	 */
	public static void testSerial() {
		PublicAccount stu1 = new PublicAccount("Zhang", 18);
		
		// 写入对象到文件中
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("D://testSerial");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(stu1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		PublicAccount stu2 = null;
		
		// 从文件中读取对象
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("D://testSerial");
			ois = new ObjectInputStream(fis);
			stu2 = (PublicAccount) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("序列化前对象：" + ToStringBuilder.reflectionToString(stu1));
		System.out.println("反序列化后对象：" + ToStringBuilder.reflectionToString(stu2));
	}
	
	
	public static void testClone() {
		PublicAccount stu1 = new PublicAccount("Edward", 20);
		PrivateAmount privateAmount = new PrivateAmount();
		privateAmount.setFee1(100);
		privateAmount.setFee2(200);
		stu1.setPrivateAmount(privateAmount);
		PublicAccount stu2 = SerializationDemo.clone(stu1);
		
		// 修改原始对象的值，看看是否影响拷贝出来的对象
		privateAmount.setFee1(300);
		
		System.out.println("(stu1 == stu2):" + (stu1 == stu2));
		System.out.println("stu1：" + ToStringBuilder.reflectionToString(stu1) 
				+ " : " + ToStringBuilder.reflectionToString(stu1.getPrivateAmount()));
		System.out.println("stu2：" + ToStringBuilder.reflectionToString(stu2)
				+ " : " + ToStringBuilder.reflectionToString(stu2.getPrivateAmount()));
	}
	
	
	/**
	 * 拷贝生成一个新对象
	 * @param obj
	 * @return
	 */
	public static <T extends Serializable> T clone(Serializable obj) {
		return SerializationDemo.deserialize(SerializationDemo.serialize(obj));
	}
	
	
	/**
	 * 序列化
	 * @param obj
	 * @return
	 */
	public static <T extends Serializable> byte[] serialize(Serializable obj) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream(512);
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializeException();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	public static <T extends Serializable> T deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream oos = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			oos = new ObjectInputStream(bais);
			@SuppressWarnings("unchecked")
			T obj = (T) oos.readObject();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SerializeException();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	@SuppressWarnings("unused")
	private static class PublicAccount implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		private int amount;
		private transient PrivateAmount privateAmount;
		
		public PublicAccount() { // 反序列化不会被调用
		}
		public PublicAccount(String name, int amount) {
			this.name = name;
			this.amount = amount;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public PrivateAmount getPrivateAmount() {
			return privateAmount;
		}
		public void setPrivateAmount(PrivateAmount privateAmount) {
			this.privateAmount = privateAmount;
		}
		
		// 序列化回调机制，writeObject和readObject是私有方法，可以自定义序列化方式
		private void writeObject(ObjectOutputStream out) throws Exception {
			out.defaultWriteObject(); // 默认写入本对象方式，没有打transient属性的字段都会序列化
			if (Objects.nonNull(privateAmount)) {
				out.writeInt(privateAmount.getFee1()); // 控制自定义属性或transient属性的写入方式			
			}
		}
		private void readObject(ObjectInputStream in) throws Exception {
			in.defaultReadObject();
			if (in.available() > 0) { // 默认读取本对象方式，没有打transient属性的字段都会反序列化
				privateAmount = new PrivateAmount();
				privateAmount.setFee1(in.readInt()); // 读取顺序跟写入顺序一致，特别注意判空
				// 判空方式，除了上面的available，也可以写入一个标记状态true或false，或者size之类的
			}
		}
		
	}
	
	@SuppressWarnings("unused")
	private static class PrivateAmount implements Serializable {
		private static final long serialVersionUID = 1L;
		private int fee1;
		private int fee2;
		public int getFee1() {
			return fee1;
		}
		public void setFee1(int fee1) {
			this.fee1 = fee1;
		}
		public int getFee2() {
			return fee2;
		}
		public void setFee2(int fee2) {
			this.fee2 = fee2;
		}
	}
	
	
	@SuppressWarnings("unused")
	private static class SerializeException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	    public SerializeException() {
	        super();
	    }
	    public SerializeException(String messamount) {
	        super(messamount);
	    }
	    public SerializeException(String messamount, Throwable cause) {
	        super(messamount, cause);
	    }
	    public SerializeException(Throwable cause) {
	        super(cause);
	    }
	}

}

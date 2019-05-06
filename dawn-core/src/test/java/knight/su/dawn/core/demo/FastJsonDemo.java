package knight.su.dawn.core.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJsonDemo {

	public static void main(String[] args) {
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");
		group.setDate(new Date());

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.addUser(guestUser);
		group.addUser(rootUser);

		// 1、序列化：标准JavaBean对象转换为JSON字符串
		String jsonString = JSON.toJSONString(group);
		System.out.println(jsonString);
		// {"id":0,"name":"admin","users":[{"id":2,"name":"guest"},{"id":3,"name":"root"}]}
		
		// 2、反序列化：JSON字符串转换为标准JavaBean对象
		Group group2 = JSON.parseObject(jsonString, Group.class);
		System.out.println(ToStringBuilder.reflectionToString(group2));
		
		// 3、泛型反序列化：利用TypeReference的匿名实现类(即其子类)
		// 一般情况下用方法2的类.class比较好，不过对于多泛型参数比如Map<String, Object>明显用下面的方式好点
		Group group3 = JSON.parseObject(jsonString, new TypeReference<Group>(){});
		System.out.println(ToStringBuilder.reflectionToString(group3));
		
	    //++++++++++++++++++++++++++++++++
		
		// 当然也可以通过JSONObject和JSONArray来获取信息
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		System.out.println(jsonObject.getIntValue("id"));
		System.out.println(jsonObject.getString("name"));
		
		JSONArray jsonArray = jsonObject.getJSONArray("users");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			System.out.println(ToStringBuilder.reflectionToString(jsonObject2));
		}
		
	    //++++++++++++++++++++++++++++++++
		// 关闭循环引用检测和生成功能，这样浏览器万一不支持此功能也不影响，不过代码如果真的存在循环引用，那么会报StackOverflow错误
		jsonString = JSON.toJSONString(group, SerializerFeature.DisableCircularReferenceDetect);
		JSON.parseObject(jsonString, Group.class, Feature.DisableCircularReferenceDetect);
		
		// fastjson处理日期的API很简单
		JSON.toJSONStringWithDateFormat(group, "yyyy-MM-dd HH:mm:ss.SSS");
	}
	
	
	@SuppressWarnings("unused")
	private static class Group {
		@JSONField(name = "ID")
	    private Long       id;
		@JSONField(name = "na_me")
	    private String     name;
//		@JSONField(serialize = false) // 不序列
	    private List<User> users = new ArrayList<User>();
		@JSONField(name = "date", format = "yyyy-MM-dd")
		private Date date;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public List<User> getUsers() {
	        return users;
	    }

	    public void setUsers(List<User> users) {
	        this.users = users;
	    }

	    public void addUser(User user) {
	        users.add(user);
	    }

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}
	
	@SuppressWarnings("unused")
	private static class User {
	    private Long   id;
	    private String name;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	}
	
}

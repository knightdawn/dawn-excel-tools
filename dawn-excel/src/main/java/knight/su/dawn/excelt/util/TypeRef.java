package knight.su.dawn.excelt.util;

import java.lang.reflect.ParameterizedType;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public abstract class TypeRef<T> {
	private JavaType jt = TypeFactory.defaultInstance().constructType(
			((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

	/**
	 * 包内访问
	 * 
	 * @return
	 */
	JavaType getType() {
		return jt;
	}

}

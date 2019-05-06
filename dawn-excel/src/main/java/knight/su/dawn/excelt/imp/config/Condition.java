package knight.su.dawn.excelt.imp.config;

import knight.su.dawn.excelt.exp.validate.ValidateAssert;

import java.util.function.Predicate;

/**
 * Created by sugengbin 2019/04/18
 */
public class Condition<T> {

	private Predicate<T> filter;

	public void register(Predicate<T> filter) {
		ValidateAssert.notNull(filter, "filter null");
		this.filter = filter;
	}

	public boolean doFilter(T t) {
		return filter.test(t);
	}
}

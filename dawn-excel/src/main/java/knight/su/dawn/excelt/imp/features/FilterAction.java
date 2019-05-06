package knight.su.dawn.excelt.imp.features;

import knight.su.dawn.excelt.imp.config.Condition;

import java.util.Objects;

/**
 * Created by sugengbin 2019/04/18
 */
public interface FilterAction {

	default <T> boolean filter(Condition<T> filter, T instance) {
		if (Objects.isNull(filter)) {
			return true;
		}
		return filter.doFilter(instance);
	}
}

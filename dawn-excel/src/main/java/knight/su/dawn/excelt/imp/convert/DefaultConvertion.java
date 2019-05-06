package knight.su.dawn.excelt.imp.convert;

/**
 * 
 * Created by sugengbin 2019/1/25
 */
public abstract class DefaultConvertion implements Convertion {

	@Override
	public Object beforeTypeMatch(Object value) {
		return value;
	}

	@Override
	public Object afterTypeMatch(Object value) {
		return value;
	}

}

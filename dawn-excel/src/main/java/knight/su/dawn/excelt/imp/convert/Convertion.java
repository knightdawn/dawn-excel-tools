package knight.su.dawn.excelt.imp.convert;

/**
 * 
 * Created by sugengbin 2019/1/25
 */
public interface Convertion {

	/**
	 * 
	 * @param value
	 * @return
	 */
	Object beforeTypeMatch(Object value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	Object afterTypeMatch(Object value);

	/**
	 * 
	 * @return
	 */
	String field();
}

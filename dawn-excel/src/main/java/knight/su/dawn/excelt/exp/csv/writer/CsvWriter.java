package knight.su.dawn.excelt.exp.csv.writer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * Date: 2019/3/10<br/>
 * 
 * @author sugengbin
 */
public interface CsvWriter {

	/**
	 * 
	 * @param function
	 */
	void write(Function<? super Integer, ? extends List<Map<String, Object>>> function);

	/**
	 * 
	 * @param data
	 */
	void write(List<Map<String, Object>> data);

	/**
	 * 
	 * @param function
	 * @param type
	 */
	<T> void write(Function<? super Integer, ? extends List<T>> function, Class<T> type);

	/**
	 * 
	 * @param function
	 * @param type
	 * @param pageSize
	 */
	<T> void write(Function<? super Integer, ? extends List<T>> function, Class<T> type, int pageSize);
}

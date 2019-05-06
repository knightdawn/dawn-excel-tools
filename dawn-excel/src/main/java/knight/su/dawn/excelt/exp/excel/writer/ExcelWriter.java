package knight.su.dawn.excelt.exp.excel.writer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 
 * Created by sugengbin 2019/3/05
 */
public interface ExcelWriter {

	/**
	 * 
	 * @param function
	 * @param type
	 */
	<T> void writeObj(Function<? super Integer, ? extends List<T>> function, Class<T> type);

	/**
	 * 
	 * @param function
	 * @param type
	 * @param pageSize
	 */
	<T> void writeObj(Function<? super Integer, ? extends List<T>> function, Class<T> type, int pageSize);
	
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
	 * @param objs
	 * @param type
	 */
	<T> void write(List<T> objs, Class<T> type);
	
	/**
	 * 
	 */
	void flush();
}

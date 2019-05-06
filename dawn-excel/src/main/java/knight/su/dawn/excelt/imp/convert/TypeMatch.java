package knight.su.dawn.excelt.imp.convert;

import knight.su.dawn.excelt.imp.config.ParserOption;

import java.lang.reflect.Field;

/**
 * 
 * Created by sugengbin 2019/1/11
 */
public interface TypeMatch {

	/**
	 * for hssf xssf
	 * 
	 * @param field
	 * @param value
	 * @param options
	 * @return
	 */
    Object convert(Field field, String value, ParserOption options);
    
    /**
     * for sax 
     * 
     * @param field
     * @param value
     * @return
     */
    Object matchWithoutConvert(Field field, String value);
}

package knight.su.dawn.excelt.imp.parser;

import knight.su.dawn.excelt.imp.result.ImpResult;

/**
 * 
 * Created by sugengbin 2019/1/4
 */
public interface MappingParser {

	<T> ImpResult<T> parser(Class<T> type);
}

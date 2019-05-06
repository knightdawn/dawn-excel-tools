package knight.su.dawn.excelt.exp.validate;

import knight.su.dawn.excelt.exception.BizServiceException;
import knight.su.dawn.excelt.exception.ValidateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by sugengbin 2019/03/22
 */
public class ValidateAssert extends Assert {

    /**
     * @param object
     * @param msg
     */
    public static void notNull(Object object, String msg) {
        if (Objects.isNull(object)) {
            throw new BizServiceException("NULL", msg);
        }
    }

    /**
     * @param collection
     * @param msg
     */
    public static <T> void notEmpty(Collection<T> collection, String msg) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ValidateException("EMPTY", msg);
        }
    }

    /**
     * @param param
     * @param msg
     */
    public static void notBlank(String param, String msg) {
        if (StringUtils.isBlank(param)) {
            throw new ValidateException("BLANK", msg);
        }
    }

    /**
     * @param firstParam
     * @param secondParam
     * @param msg
     */
    public static void eq(String firstParam, String secondParam, String msg) {
        firstParam = StringUtils.trimToEmpty(firstParam);
        secondParam = StringUtils.trimToEmpty(secondParam);
        if (!firstParam.equals(secondParam)) {
            throw new ValidateException("NOT EQUAL", msg);
        }
    }

    /**
     *
     * @param firstArray
     * @param secondArray
     * @param msg
     */
    public static void eqLength(Object[] firstArray, Object[] secondArray, String msg) {
        int firstArrayLen = firstArray == null ? 0 : firstArray.length;
        int secondArrayLen = secondArray == null ? 0 : secondArray.length;
        if (firstArrayLen != 0 && secondArrayLen != 0 && firstArrayLen != secondArrayLen) {
            throw new ValidateException("NOT EQUAL LENGTH", msg);
        }
    }

    /**
     * @param input
     * @param msg
     */
    public static void isPositive(int input, String msg) {
        if (input < 0) {
            throw new ValidateException("NOT POSITIVE", msg);
        }
    }
    
    /**
     * 
     * @param array
     * @param len
     */
	public static void eqLength(Object[] array, int len, String msg) {
		if (array.length != len) {
			throw new ValidateException("ARRAY LENGTH IS NOT " + len, msg);
		}
	}
}

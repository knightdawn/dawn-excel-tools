package knight.su.dawn.core.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * Uuid生成工具类
 * @author Edward
 * @date 2019年4月15日
 * @version 1.0
 */
public class UuidUtils {
    private static final String KEY_TEMPLATE = "%s_%s";

    private UuidUtils() {
        throw new IllegalAccessError("UuidUtils is a tool class!");
    }
    
    
    /**
     * 生成一个32位的Uuid字符串（也许可能存在重复）
     * 原生生成的Uuid字符串中间的"-"被替换成空，可能存在重复的情况
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
    }
    
    
    /**
     * 生成一个46位的Uuid字符串（基本不可能重复）
     * 原生生成的Uuid字符串中间的"-"被替换成空，可能存在重复的情况，故在uuid字符串后面加上当前时间戳（毫秒）
     * @return
     */
    public static String uuidWithMills() {
        return String.format(KEY_TEMPLATE, uuid(), System.currentTimeMillis());
    }

    /**
     * 生成一个“32位的Uuid字符串” + “一个指定后缀”
     * @param postfix
     * @return
     */
    public static String genKey(String postfix) {
        return String.format(KEY_TEMPLATE, uuid(), postfix);
    }

    /**
     * 生成一个“32位的Uuid字符串” + “一个指定后缀”
     * @param postfix
     * @return
     */
    public static String genKey(long postfix) {
        return String.format(KEY_TEMPLATE, uuid(), postfix);
    }
    
    
}

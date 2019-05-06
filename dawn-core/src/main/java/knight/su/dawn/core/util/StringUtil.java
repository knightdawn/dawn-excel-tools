package knight.su.dawn.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @author Edward
 * @date 2019年4月15日
 * @version 1.0
 */
public class StringUtil {
    
    private StringUtil() {
        throw new IllegalAccessError("StringUtils is a tool class!");
    }
    
    
    /**
     * 当字符串长度过长时，截取保留指定长度
     * @author 01370886
     * @date 2019年4月15日
     * @param str 字符串
     * @param charNums 字符数
     * @return
     */
    public static String cutStrWhileLarge(String str, int charNums) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        if (str.length() > charNums) {
            return str.substring(0, charNums);
        }
        return str;
    }
    

    /**
     * 去掉字符串的最后一个字符
     * @param str
     * @return
     */
    public static String removeLastChar(String str) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        return str.substring(0, str.length() - 1);
    }
    
    
    /**
     * 去掉字符串的最后多个字符
     * @param str
     * @param multiChar 末尾的多个字符
     * @return
     */
    public static String removeLastMultiChar(String str, String multiChar) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(multiChar)) {
            return str;
        }
        if (multiChar.length() >= str.length()) {
            // 去掉字符串长度超过或等于源字符串则返回空
            return StringUtils.EMPTY;
        }
        return str.substring(0, str.length() - multiChar.length());
    }
    
    
    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String getUpperName(String name) {
        byte[] items = name.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
    

}

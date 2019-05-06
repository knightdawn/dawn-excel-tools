package knight.su.dawn.core.constant;

/**
 * 通用基础常量类
 * @author Edward
 * @date 2019年3月30日
 * @version 1.0
 */
public class CommonConsts {
    
    private CommonConsts() {
        throw new IllegalAccessError("CommonConsts is a constant class!");
    }
    
    /** UTF-8编码 */
    public static final String CHARSET_UTF8 = "UTF-8";
    /** GB2312编码 - 中文字符集的V1.0版 */
    public static final String CHARSET_GB2312 = "GB2312";
    /** GBK编码  - 中文字符集的V2.0版*/
    public static final String CHARSET_GBK = "GBK";
    /** GB18030编码 - 中文字符集的V3.0版 */
    public static final String CHARSET_GB18030 = "GB18030";
    
    
    /** 下划线 */
    public static final String UNDER_LINE = "_";
    /** 横杆 */
    public static final String INTERMEDIATE_LINE = "-";
    /** 英文逗号 */ 
    public static final String COMMA = ",";
    /** 中文逗号*/
    public static final String COMMA_CN = "，";
    /** 英文冒号 */
    public static final String COLON = ":";
    /** 斜杆*/
    public static final String DIAGONAL = "/";
    /** 点*/
    public static final String POINT = ".";
    /** 中文-是 */
    public static final String YES_CN = "是";
    /** 中文-否 */
    public static final String NO_CN = "否";
    
    
}

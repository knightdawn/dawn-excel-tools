package knight.su.dawn.core.util;

import knight.su.dawn.core.constant.CommonConsts;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 * @author Edward
 * @date 2019年4月15日
 * @version 1.0
 */
public class TimeUtils {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /** 严格时间格式 ~ HH:mm:ss */
    public static final int STRICT = 1;
    /** 非严格时间格式，允许忽略掉前面的零  */
    public static final int NON_STRICT = 2;
    
    
    private TimeUtils() {
        throw new IllegalAccessError("TimeUtils is a tool class!");
    }
    
    
    /**
     * 校验时间字符串是不是严格标准时间格式 ~ “HH:mm:ss”
     * @param timeStr 
     * @return
     */
    public static boolean validateTime(String timeStr) {
        return validateTime(timeStr, STRICT);
    }
    
    
    /**
     * 校验时间字符串的合法性
     * @param timeStr 时间字符串  ~ “HH:mm:ss”
     * @param strictPattern 严格标准模式传 TimeUtils.STRICT， 非严格标准模式传 TimeUtils.NON_STRICT
     * @return
     */
    public static boolean validateTime(String timeStr, int strictPattern) {
        if (StringUtils.isBlank(timeStr)) {
            return false;
        }
        
        String[] items = timeStr.split(CommonConsts.COLON);
        if (STRICT == strictPattern) { // 严格模式
            if (timeStr.length() != 8) {
                return false;
            }
            if (items.length != 3
                    || items[0].length() != 2
                    || items[1].length() != 2
                    || items[2].length() != 2) {
                return false;
            }
        } else { // 非严格模式
            if (timeStr.length() < 5 
                    || timeStr.length() > 8) {
                return false;
            }
            if (items.length != 3
                    || (items[0].length() != 1 && items[0].length() != 2)
                    || (items[1].length() != 1 && items[1].length() != 2)
                    || (items[2].length() != 1 && items[2].length() != 2)) {
                return false;
            }
        }
        
        // 校验小时
        if (!checkHour(items[0])) {
            return false;
        }
        // 校验分钟
        if (!checkMinuteOrSecond(items[1])) {
            return false;
        }
        // 校验秒
        if (!checkMinuteOrSecond(items[2])) {
            return false;
        }
        return true;
    }
    
    
    /**
     * 将时间字符串格式化严格标准时间格式   ~ “HH:mm:ss”
     * 默认是严格标准或非严格标准的时间格式，如果不是，可能返回原字符串或部分处理后的字符串，建议配合validateTime()方法使用
     * @param timeStr 
     * @return
     */
    public static String formatStrictTime(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return timeStr;
        }
        String[] items = timeStr.split(CommonConsts.COLON);
        if (items.length != 3) {
            return timeStr;
        }
        
        StringBuilder sb = new StringBuilder();
        // 补齐小时部分
        fillTimeData(sb, items[0]);
        // 补齐分钟部分
        fillTimeData(sb, items[1]);
        // 补齐秒部分
        fillTimeData(sb, items[2]);
        
        return StringUtil.removeLastChar(sb.toString());
    }
    
    
    /**
     * 将LocalTime格式化为严格标准时间格式   ~ “HH:mm:ss”
     * @param localTime
     * @return
     */
    public static String formatLocalTime(LocalTime localTime) {
        return localTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * 求两个时间相隔的秒数
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDiffSeconds(String startTime, String endTime) {
        return getDiffSeconds(LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
    
    /**
     * 求两个时间相隔的秒数
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDiffSeconds(LocalTime startTime, LocalTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }
    
    
    /**
     * 判断开始时间是否早于(<=)结束时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean startBeforeEnd(String startTime, String endTime) {
        return startBeforeEnd(LocalTime.parse(startTime), LocalTime.parse(endTime));
    }
    
    /**
     * 判断开始时间是否早于(<=)结束时间
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean startBeforeEnd(LocalTime startTime, LocalTime endTime) {
        return startTime.compareTo(endTime) <= 0;
    }
    
    
    /*
     * 校验小时字符串是否合法
     * @param hourStr 一位或两位字符串
     * @return
     */
    private static boolean checkHour(String hourStr) {
        char firstChar = hourStr.charAt(0);
        if (hourStr.length() == 1) {
            if ('0' > firstChar || firstChar > '9') {
                return false;
            }
        } else {
            if ('0' > firstChar || firstChar > '2') {
                return false;
            }
            char secondChar = hourStr.charAt(1);
            if (firstChar == '0' || firstChar == '1') {
                // 小时，首位字符为0和1时，第二位可以为0~9
                if ('0' > secondChar || secondChar > '9') {
                    return false;
                }
            } else {
                // 小时，首位字符为2时，第二位可以为0~3
                if ('0' > secondChar || secondChar > '3') {
                    return false;
                }
            }
        }
        return true;
    }
    
    /*
     * 校分钟和秒字符串是否合法
     * @param minuteOrSecond 一位或两位字符串
     * @return
     */
    private static boolean checkMinuteOrSecond(String minuteOrSecondStr) {
        char firstChar = minuteOrSecondStr.charAt(0);
        if (minuteOrSecondStr.length() == 1) {
            if ('0' > firstChar || firstChar > '9') {
                return false;
            }
        } else {
            if ('0' > firstChar || firstChar > '5') {
                return false;
            }
            char secondChar = minuteOrSecondStr.charAt(1);
            if ('0' > secondChar || secondChar > '9') {
                return false;
            }            
        }
        return true;
    }
    
    
    /*
     * 不足两位则前面补零
     * @param sb
     * @param timeData
     */
    private static void fillTimeData(StringBuilder sb, String timeData) {
        if (timeData.length() == 1) {
            sb.append("0");
        }
        sb.append(timeData);
        sb.append(CommonConsts.COLON);
    }
    
}

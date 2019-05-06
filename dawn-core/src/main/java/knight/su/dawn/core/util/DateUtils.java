package knight.su.dawn.core.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用JDK8接口实现的日期时间工具类
 * @author Edward
 * @date 2018年7月22日
 * @version 1.0
 */
public class DateUtils {

    /** 日期时间模式：yyyy-MM-dd HH:mm:ss.SSS */
    public static final String PATTERN_YMDHMSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    /** 日期时间模式：yyyy-MM-dd HH:mm:ss.S */
    public static final String PATTERN_YMDHMSS = "yyyy-MM-dd HH:mm:ss.S";
    /** 日期时间模式：yyyy-MM-dd HH:mm:ss */
    public static final String PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    /** 日期时间模式：yyyy-MM-dd */
    public static final String PATTERN_YMD = "yyyy-MM-dd";
    /** 日期时间模式：yyyy/MM/dd */
    public static final String PATTERN_YMDX = "yyyy/MM/dd";
    /** 日期时间模式：yyyyMMdd */
    public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
    /** 时间戳模式，这个模式只是一个标记  - MS */
    public static final String TIMESTAMP = "MS";
    
    // 日期时间模式格式化器缓存Map
    private static final Map<String, DateTimeFormatter> dateTimeFormatterMap = new ConcurrentHashMap<>();
    
    /**
	 * 当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}
    
    /**
     * 将 Date 对象格式化为指定模式的字符串
     * @param date date对象
     * @param pattern 日期时间模式
     * @return
     */
    public static String format(Date date, String pattern) {
        checkPatternValid(pattern);
        return toLocalDateTime(date).format(getDateTimeFormatter(pattern));
    }
    
    /**
     * 将日期时间字符串 转换为  Date 对象
     * @param time 日期时间字符串
     * @param pattern 日期时间模式
     * @return
     */
    public static Date parseDate(String time, String pattern){
        checkPatternValid(pattern);
        return toDate(LocalDateTime.parse(time, getDateTimeFormatter(pattern)));
    }
    
    /**
     * 将 Date 转换为 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    
    /**
     * 将 LocalDateTime 转换为 Date
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * 从Map中获取pattern对应的Formatter，不存在则新建Formatter并放入Map中
     * @param pattern 日期模式
     * @return
     */
    private static DateTimeFormatter getDateTimeFormatter(String pattern) {
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterMap.get(pattern);
        if (Objects.isNull(dateTimeFormatter)) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            
            // putIfAbsent方法如果Map中已存在Formatter则返回，利用ConcurrentHashMap的分段锁机制代替同步锁降低性能损耗
            DateTimeFormatter sourceDateTimeFormatter = dateTimeFormatterMap.putIfAbsent(pattern, dateTimeFormatter);
            if (Objects.nonNull(sourceDateTimeFormatter)) {
                dateTimeFormatter = sourceDateTimeFormatter;
            }
        }
        return dateTimeFormatter;
    }
    
    /*
     * 校验Pattern的合法性
     */
    private static void checkPatternValid(String pattern) {
        if (Objects.isNull(pattern) || pattern.trim().length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be empty!");
        }
    }
    
    /**
	 * 比较两个日期相隔的秒数,毫秒精确到小数点后两位
	 * 
	 * @param startDate
	 * @param endDate
	 * @return endDate-startDate
	 */
	public static BigDecimal getDiffMillisecond(Date startDate, Date endDate) {
		Objects.requireNonNull(startDate);
		Objects.requireNonNull(endDate);
		long m = startDate.getTime();
		long m2 = endDate.getTime();
		long diff = m2 - m;
		BigDecimal value = BigDecimal.valueOf(diff / 1000.00);
		value = value.setScale(2, BigDecimal.ROUND_HALF_UP); // 保留小数点后两位
		return value;
	}
    
	/**
	 * 忽略秒数，比较两个日期相隔的分钟 ,有正负
	 * 
	 * @param date1
	 * @param date2
	 * @return date2-date1
	 */
	public static long getDiffMins(Date date1, Date date2) {
		Objects.requireNonNull(date1);
		Objects.requireNonNull(date2);
		long m1 = date1.getTime() / 60000;
		long m2 = date2.getTime() / 60000;
		return (m2 - m1);
	}
	
    private DateUtils() {}
    
}

package knight.su.dawn.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用工具类
 * 
 * 特别注意，本类接口返回的List如果为空，一般返回Collections.emptyList()，EMPTY_LIST是自定义的空集合类，不能执行增删改操作的。
 * 
 * @author Edward
 * @date 2018年8月3日
 * @version 1.0
 */
public class CommonUtils {

    private CommonUtils() {
        throw new IllegalAccessError("CommonUtils is a tool class!");
    }
    
    
    /**
     * 按给定分隔符对字符串进行分解成列表，中间空或空白字符串项会被过滤掉
     * @param str 待分解的字符串
     * @param delimeter 分隔符
     * @return 返回list
     */
    public static List<String> splitToList(String str, String delimeter) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        String[] array = str.split(delimeter);
        if (Objects.isNull(array) || array.length == 0) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>(array.length);
        for (String item : array) {
            if (StringUtils.isNotBlank(item)) {
                list.add(item.trim());
            }
        }
        return list;
    }
    
    
    /**
     * 按给定分隔符对字符串进行分解成集合，中间空或空白字符串项会被过滤掉
     * @param str 待分解的字符串
     * @param delimeter 分隔符
     * @return 返回list
     */
    public static Set<String> splitToSet(String str, String delimeter) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptySet();
        }
        String[] array = str.split(delimeter);
        if (Objects.isNull(array) || array.length == 0) {
            return Collections.emptySet();
        }
        Set<String> set = new HashSet<>(array.length);
        for (String item : array) {
            if (StringUtils.isNotBlank(item)) {
                set.add(item.trim());
            }
        }
        return set;
    }
    
    
    /**
     * 将List转换为Set，有判空逻辑
     * @param list
     * @return
     */
    public static <T> Set<T> listToSet(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptySet();
        }
        return new HashSet<>(list);
    }
    
    
    /**
     * 将Set转换为List，有判空逻辑
     * @param set
     * @return
     */
    public static <T> List<T> setToList(Set<T> set) {
        if (CollectionUtils.isEmpty(set)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(set);
    }
    
    
    /**
     * 将异常堆栈信息转化为字符串
     * @param e
     * @return
     */
    public static String stackTraceToStr(Throwable e) {
        if (Objects.isNull(e)) {
            return StringUtils.EMPTY;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
    
}

package knight.su.dawn.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页相关工具类
 * @author Edward
 * @date 2019年4月17日
 * @version 1.0
 */
public class PageUtils {

    private PageUtils() {
        throw new IllegalAccessError("PageUtils is a tool class!");
    }
    
    
    /**
     * 根据记录总数和每页记录数计算页码总数
     * @param size 记录在那个数
     * @param pageSize 每页记录数
     * @return
     */
    public static int getTotalPage(int size, int pageSize) {
        int remainder = size % pageSize; // 余数
        int result = size / pageSize; // 整除结果
        if (remainder == 0) {
            return result;
        }
        return result + 1; // 如果有余数，页码需要加1
    }
    
    
    /**
     * 从给定集合中返回指定范围的子集合
     * @param list 总数据集合
     * @param currentPage 当前页码，从1开始
     * @param pageSize 每页查询记录数
     * @return
     */
    public static <T> List<T> splitList(List<T> list, int currentPage, int pageSize) {
        int fromIndex = (currentPage - 1) * pageSize;
        if (fromIndex >= list.size()) {
            return new ArrayList<>();
        }
        int toIndex = fromIndex + pageSize;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }
    
}

package knight.su.dawn.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 算数工具类
 * @author Edward
 * @date 2019年4月16日
 * @version 1.0
 */
public class MathUtils {
    
    private MathUtils() {
        throw new IllegalAccessError("MathUtils is a tool class!");
    }
    
    
    /**
     * 不丢失精度的浮点数加法
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(Number a, Number b) {
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(a));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(b));
        return bigDecimal1.add(bigDecimal2);
    }

    /**
     * 不丢失精度的浮点数减法
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal subtract(Number a, Number b) {
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(a));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(b));
        return bigDecimal1.subtract(bigDecimal2);
    }
    
    /**
     * 不丢失精度的浮点数乘法
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiply(Number a, Number b) {
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(a));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(b));
        return bigDecimal1.multiply(bigDecimal2);
    }
    
    /**
     * 不丢失精度的浮点数除法
     * 默认保留两位小数，采用数学上经典的四舍五入模式
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal divide(Number a, Number b) {
        return divide(a, b, 2, RoundingMode.HALF_UP);
    }
    
    
    /**
     * 不丢失精度的浮点数除法
     * @param a
     * @param b
     * @param scale 保留小数位数
     * @param roundingMode 四舍五入模式
     * @return
     */
    public static BigDecimal divide(Number a, Number b, int scale, RoundingMode roundingMode) {
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(a));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(b));
        return bigDecimal1.divide(bigDecimal2, scale, roundingMode);
    }
    
    
    /**
     * 数值大小比较（其实也可以用来比较相等）
     * 
     * 返回负数(<0)，表示第一个数小于第二个数
     * 返回0，表示两个数相等
     * 返回正数(>0)，表示第一个数大于第二个数
     * 
     * @param a 可以是浮点数，也可以是整数
     * @param b 可以是浮点数，也可以是整数
     */
    public static int compareTo(Number a, Number b) {
       BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(a));
       BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(b));
       return bigDecimal1.compareTo(bigDecimal2);
    }
    
    
    /**
     * 浮点数相等比较函数，浮点数比较只能根据精度范围
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isDoubleEquals(double value1, double value2) {
        return Math.abs(value1 - value2) <= 0.0001;
    }
    
    /**
     * 浮点数相等比较函数，浮点数比较只能根据精度范围
     * @param value1
     * @param value2
     * @return
     */
    public static boolean isFloatEquals(float value1, float value2) {
        return Math.abs(value1 - value2) <= 0.0001;
    }

    
    /**
     * 根据指定四舍五入模式格式化浮点数
     * @param in 浮点数
     * @param num 保留小数位
     * @param type 四舍五入模式
     * @return
     */
    public static double formatDouble(double in, int num, RoundingMode type) {
        num = (num < 0) ? 2 : num;
        BigDecimal bg = BigDecimal.valueOf(in).setScale(num, type);
        return bg.doubleValue();
    }

    
}

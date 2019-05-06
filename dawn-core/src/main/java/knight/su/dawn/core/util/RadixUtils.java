package knight.su.dawn.core.util;

import java.math.BigDecimal;

/**
 * 进制转换工具，对JDK API的补充
 * 
 * 以下操作请调用JDK对应API来实现
 * 1、其余进制数转换为十进制数，主要调用Integer.valueOf(s, radis); 如果是带小数的场景，请调用本接口的toDecimalSupportPoint方法来实现（精度不会丢失）
 * 2、十进制转换为其余进制（常见的如二进制、八进制、十六进制） ，分别调用Integer.toBinaryString，Integer.toHexString，Integer.toOctalString
 *    支持正负数运算，不过要注意正数返回原码，负数返回的是补码！！！
 * 3、其余非十进制转换为二进制，先将其余非十进制转换为十进制，再通过算法2转换为对应的进制数（上面的组合可得）
 * 
 * @author Edward
 * @date 2019年1月29日
 */
public class RadixUtils {
    
    private RadixUtils() {
        throw new IllegalAccessError("RadisUtils is a tool class!");
    }
    
    
    /**
     * 将二进制数组转化为十六进制字符串
     * @param byteArray
     * @return
     */
    public static String byteArrayToHex(byte[] byteArray) {
        StringBuffer sb = new StringBuffer(byteArray.length * 2);
        for (int i = 0; i < byteArray.length; i++) {
            sb.append(Character.forDigit((byteArray[i] & 240) >> 4, 16)); 
            sb.append(Character.forDigit(byteArray[i] & 15, 16));
        }
        return sb.toString();
    }
    
    
    
    /**
     * 其余进制数转化为十进制数，支持带小数的场景，算是对JDK的补充吧
     * 
     * @param s 代表某进制数的字符串
     *              比如十六进制数“BA05”（不要带前缀0x或后缀H等）
     *              正负数通过带前缀符号 “+” 或  “-”来识别，默认情况下为正数
     *              支持小数操作，通过小数点 “.” 识别
     *              
     * @param radix 进制数
     *              取值范围2~36
     *              
     * @return 如果计算结果超过int的最大位数（31位为有效数值方位），返回结果则是溢出后的值
     */
    public static double toDecimalSupportPoint(String s, int radix) {
        if (s == null || s.isEmpty()) {
            throw new NumberFormatException("For input string: " + s);
        }
        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
        }
        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
        }
        
        int i = 0, len = s.length();
        char firstChar = s.charAt(0);
        boolean negative = false;
        boolean symbol = false;
        if (firstChar < '0') { // 首字符校验，可能是 “+” 或 “-” 或 “.”
            if (firstChar == '-') {
                symbol = true;
                negative = true;
                i++;
            } else if (firstChar == '+') {
                symbol = true;
                i++;
            } else if (firstChar != '.') {
                throw new NumberFormatException("For input string: " + s);
            }
        }
        
        double result = 0D;
        while (i < len) {
            char c = s.charAt(i);
            if (c == '.' && i != len - 1) {
                if (i == 0 || (i == 1 && symbol)) { // 仅有小数部分
                    String pointStr = s.substring(i + 1);
                    result = pointCalc(pointStr, radix);
                } else { // 整数部分和小数部分都有
                    String integerStr = s.substring(symbol ? 1 : 0, i);
                    result = Integer.valueOf(integerStr, radix);
                    
                    String pointStr = s.substring(i + 1);
                    double pointResult = pointCalc(pointStr, radix);
                    
                    // 高精度计算
                    BigDecimal big1 = new BigDecimal(String.valueOf(result));
                    BigDecimal big2 = new BigDecimal(String.valueOf(pointResult));
                    result = big1.add(big2).doubleValue();
                }
                break;
            }
            i++;
        }
        
        if (i >= len) { // 仅有整数部分
            char lastChar = s.charAt(len - 1);
            int to = len;
            if (lastChar == '.') {
                to = len - 1;
            }
            String integerStr = s.substring(symbol ? 1 : 0, to);
            result = Integer.valueOf(integerStr, radix);
        }
        return negative ? -result : result;
    }
    
    
    /*
     * 小数部分计算，支持高精度计算，避免精度丢失
     * @return
     */
    private static double pointCalc(String s, int radix) {
        BigDecimal resultBig = new BigDecimal("0");
        BigDecimal radixBig = new BigDecimal(String.valueOf(radix));
        int len = s.length(), i = len - 1;
        while (i >= 0) {
            char c = s.charAt(i--);
            int digit = Character.digit(c, radix);
            if (digit == -1) {
                throw new NumberFormatException("For input string: " + c);
            }
            BigDecimal digitBig = new BigDecimal(String.valueOf(digit));
            resultBig = resultBig.add(digitBig); 
            resultBig = resultBig.divide(radixBig);
        }
        return resultBig.doubleValue();
    }
    
    
}

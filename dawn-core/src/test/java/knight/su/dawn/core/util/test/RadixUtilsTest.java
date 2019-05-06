package knight.su.dawn.core.util.test;

import knight.su.dawn.core.util.RadixUtils;

public class RadixUtilsTest {

    public static void main(String[] args) {
        // 测试带小数的其他进制数转十进制函数
        System.out.println(RadixUtils.toDecimalSupportPoint(".123", 16)); // 0.071044921875
        System.out.println(RadixUtils.toDecimalSupportPoint("-.123", 16)); // -0.071044921875
        
        System.out.println(RadixUtils.toDecimalSupportPoint("-123.", 16)); // -291.0
        System.out.println(RadixUtils.toDecimalSupportPoint("123.", 16)); // 291.0
        
        System.out.println(RadixUtils.toDecimalSupportPoint("+123", 16)); // 291.0
        System.out.println(RadixUtils.toDecimalSupportPoint("-123", 16)); // -291.0
        
        System.out.println(RadixUtils.toDecimalSupportPoint("12.3", 16)); // 18.1875
        System.out.println(RadixUtils.toDecimalSupportPoint("-12.3", 16)); // -18.1875
        
        System.out.println(RadixUtils.toDecimalSupportPoint("0.35", 16)); // 0.20703125
        System.out.println(RadixUtils.toDecimalSupportPoint("-0.35", 16)); // -0.20703125
         
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.valueOf("-0000000000000000000000000000101", 2)); // 特别注意：valueOf用法是这样，如果为负数，那么应该用负号表示，比如int的-5要用左侧那样的表示方式
    }
    
}

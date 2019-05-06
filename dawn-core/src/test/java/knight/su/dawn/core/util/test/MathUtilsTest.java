package knight.su.dawn.core.util.test;

import knight.su.dawn.core.util.MathUtils;
import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

    @Test
    public void test1() {
        // 浮点数比较相等
        float f1 = 1.25F, f2 = 1.25F;
        Assert.assertTrue(MathUtils.isFloatEquals(f1, f2));
        
        double d1 = 1.25D, d2 = 1.25D;
        Assert.assertTrue(MathUtils.isDoubleEquals(d1, d2));
        
        Double d3 = 1.25D, d4 = 1.25D;
        Assert.assertTrue(MathUtils.isDoubleEquals(d3, d4));
    }
    
    @Test
    public void test2() {
        // 浮点数比较大小
        float f1 = 1.25F, f2 = 1.25F;
        double d1 = 1.25D, d2 = 1.25D;
        Double d3 = 1.25D, d4 = 1.25D;
        Double d5 = 1.2D, d6 = 1.3D;
        Double d7 = 2D, d8 = 123D;
        Integer i1 = 2, i2 = 123;
        
        Assert.assertTrue(0 == MathUtils.compareTo(d1, d2));
        Assert.assertTrue(0 == MathUtils.compareTo(f1, f2));
        Assert.assertTrue(0 == MathUtils.compareTo(f1, d2));
        Assert.assertTrue(0 == MathUtils.compareTo(d1, f2));
        Assert.assertTrue(0 == MathUtils.compareTo(d3, d4));
        
        Assert.assertTrue(-1 == MathUtils.compareTo(d5, d6));
        Assert.assertTrue(1 == MathUtils.compareTo(d6, d5));
        Assert.assertTrue(0 == MathUtils.compareTo(d7, i1));
        Assert.assertTrue(0 == MathUtils.compareTo(d8, i2));
        
        Assert.assertTrue(-1 == MathUtils.compareTo(d5, i1));
        Assert.assertTrue(1 == MathUtils.compareTo(i1, d5));
    }
    
    @Test
    public void test3() {
        Double d5 = 1.2D, d6 = 1.3D;
        // 浮点数运算
        System.out.println(d6 + d5); // 2.5
        System.out.println(d6 - d5); // 0.10000000000000009
        System.out.println(d6 * d5); // 1.56
        System.out.println(d6 / d5); // 1.0833333333333335
        
        // 不丢失精度的加减乘除
        Assert.assertTrue(MathUtils.isDoubleEquals(2.5D, MathUtils.add(d6, d5).doubleValue()));
        Assert.assertTrue(MathUtils.isDoubleEquals(0.1D, MathUtils.subtract(d6, d5).doubleValue()));
        Assert.assertTrue(MathUtils.isDoubleEquals(1.56D, MathUtils.multiply(d6, d5).doubleValue()));
        Assert.assertTrue(MathUtils.isDoubleEquals(1.08D, MathUtils.divide(d6, d5).doubleValue()));
    }
    
}

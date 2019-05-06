package knight.su.dawn.core.util.test;

import knight.su.dawn.core.util.TimeUtils;
import org.junit.Assert;
import org.junit.Test;

public class TimeUtilsTest {
    
    @Test
    public void testStrict() {
        // 严格模式下
        
        Assert.assertTrue(TimeUtils.validateTime("23:09:10"));
        Assert.assertTrue(TimeUtils.validateTime("12:59:59"));
        Assert.assertTrue(TimeUtils.validateTime("09:59:59"));
        Assert.assertFalse(TimeUtils.validateTime("9:59:59"));
        Assert.assertFalse(TimeUtils.validateTime("09:9:59"));
        Assert.assertFalse(TimeUtils.validateTime("09:59:9"));
        Assert.assertFalse(TimeUtils.validateTime("09:9:9"));
        Assert.assertFalse(TimeUtils.validateTime("9:5:5"));
        Assert.assertFalse(TimeUtils.validateTime("9:9:9"));
        Assert.assertFalse(TimeUtils.validateTime("12::34"));
        Assert.assertFalse(TimeUtils.validateTime("12:20"));        
        Assert.assertFalse(TimeUtils.validateTime("12:a:34"));
        Assert.assertFalse(TimeUtils.validateTime("1a:34:45"));
        Assert.assertFalse(TimeUtils.validateTime("12:60:34"));
        Assert.assertFalse(TimeUtils.validateTime("12:34:60"));
        Assert.assertFalse(TimeUtils.validateTime("24:59:59"));
        Assert.assertFalse(TimeUtils.validateTime("30:59:59"));
        Assert.assertFalse(TimeUtils.validateTime("01:58:0a"));
    }
    
    @Test
    public void testNonStrict() {
        // 非严格模式下
        
        Assert.assertTrue(TimeUtils.validateTime("23:09:10", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("12:59:59", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("09:59:59", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("9:59:59", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("09:9:59", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("09:59:9", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("09:9:9", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("9:5:5", TimeUtils.NON_STRICT));
        Assert.assertTrue(TimeUtils.validateTime("9:9:9", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("12::34", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("12:20", TimeUtils.NON_STRICT));        
        Assert.assertFalse(TimeUtils.validateTime("12:a:34", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("1a:34:45", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("12:60:34", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("12:34:60", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("24:59:59", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("30:59:59", TimeUtils.NON_STRICT));
        Assert.assertFalse(TimeUtils.validateTime("01:58:0a", TimeUtils.NON_STRICT));
    }
    
    
    @Test
    public void testFormatStrictTime() {
        Assert.assertTrue("23:09:10".equals(TimeUtils.formatStrictTime("23:09:10")));
        Assert.assertTrue("12:59:59".equals(TimeUtils.formatStrictTime("12:59:59")));
        Assert.assertTrue("09:59:59".equals(TimeUtils.formatStrictTime("09:59:59")));
        Assert.assertTrue("09:59:59".equals(TimeUtils.formatStrictTime("9:59:59")));
        Assert.assertTrue("09:09:59".equals(TimeUtils.formatStrictTime("09:9:59")));
        Assert.assertTrue("09:59:09".equals(TimeUtils.formatStrictTime("09:59:9")));
        Assert.assertTrue("09:09:09".equals(TimeUtils.formatStrictTime("09:9:9")));
        Assert.assertTrue("09:05:05".equals(TimeUtils.formatStrictTime("9:5:5")));
        Assert.assertTrue("09:09:09".equals(TimeUtils.formatStrictTime("9:9:9")));
        Assert.assertTrue("9:9".equals(TimeUtils.formatStrictTime("9:9")));
        Assert.assertTrue("09:0a:03".equals(TimeUtils.formatStrictTime("9:a:3")));
    }
    
}

package knight.su.dawn.core.reflect.test;

import jdk.nashorn.internal.ir.annotations.Ignore;

import org.junit.Test;

public class ReflectTest {
    
    /**
     * 1、获取一个类的Class对象四种方式 
     */
    @Ignore
    @Test
    public void test() throws Exception {
        Class<?> clz = null;
        
        // 方法一：不会执行初始化操作
//        clz = ReflectClassDomain.class;
        
        // 方法二：会执行完整的初始化操作（包括静态变量的初始化和实例变量分配和初始化、构函数的执行等）
//        ReflectClassDomain reflectClassDomain = new ReflectClassDomain();
//        clz = reflectClassDomain.getClass();
//                静态方法块执行了！
//                构造方法块执行了！
//                构造函数执行了！

        // 方法三：会执行静态变量和静态方法块的初始化操作，不会创建对象
//        clz = Class.forName("com.dawn.test.ReflectClassDomain");
//        静态方法块执行了！
        
        // 方法四：不会执行初始化操作
//        clz = ClassLoader.getSystemClassLoader().loadClass("com.dawn.test.ReflectClassDomain");
//        System.out.println(clz);
        
        /**
                             总结：
         1、通过类名.class方式是最简单的，而且不会触发初始化操作
         2、如果对象已经存在，通过 对象.getClass()方式比较好
         3、如果类未加载或需要动态加载，通过方法三和方法四比较好，两者用途不同，一个会初始化，一个不会初始化          
         */
    }
    
    
    /**
     * 2、JVM三种类加载器
     */
    @Ignore
    @Test
    public void test2() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            System.out.println("classLoader");
        } else {
            System.out.println(classLoader);
            System.out.println(classLoader.getParent());
            System.out.println(classLoader.getParent().getParent());
//            sun.misc.Launcher$AppClassLoader@4554617c 
//            sun.misc.Launcher$ExtClassLoader@87aac27
//            null  因为BootstrapClassLoader是由C++实现的，所以并不存在一个Java的类，因此会打印出null
        }
        
        System.out.println(String.class.getClassLoader());
        System.out.println(ReflectTest.class.getClassLoader());
//        null 此处因为String是JDK API库，默认就是由BootstrapClassLoader加载的
//        sun.misc.Launcher$AppClassLoader@4554617c   当前类默认是由AppClassLoader加载的，此类加载器也是系统默认的加载器
    }
    
    
    /**
     * 3、
     */
    @Test
    public void test3() {
    	
    	
    }
    
    

}

package knight.su.dawn.core.reflect.test;

public class ReflectClassDomain {
    static {
        System.out.println("静态方法块执行了！");
    }
    {
        System.out.println("构造方法块执行了！");
    }
    public ReflectClassDomain() {
        System.out.println("构造函数执行了！");            
    }
    
}

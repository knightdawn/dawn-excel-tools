package knight.su.dawn.excelt.imp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Created by sugengbin 2019/1/10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ExcelSheet {

	/**
	 * 对应excel的Sheet索引，从0开始，默认0
	 * 
	 * @return
	 */
	int[] value() default { 0 };
}

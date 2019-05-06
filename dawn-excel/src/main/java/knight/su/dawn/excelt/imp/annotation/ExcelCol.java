package knight.su.dawn.excelt.imp.annotation;

import knight.su.dawn.excelt.common.InvertEnum;

import java.lang.annotation.*;

/**
 * 
 * Created by sugengbin 2019/1/10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelCol {

	/**
	 * excel文件 [列索引]，从下标0开始
	 * 
	 * @return
	 */
	int value();
	
	/**
	 * 映射关系转换 默认为DEFAULT，不做转换
	 * 
	 * @return
	 */
	InvertEnum invert() default InvertEnum.DEFAULT;
}

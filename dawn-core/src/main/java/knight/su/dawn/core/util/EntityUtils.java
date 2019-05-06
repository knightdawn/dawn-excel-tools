package knight.su.dawn.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 泛型方法类用来拷贝转化对象
 * @author Edward
 * @date 2018年5月10日
 * @version 1.0
 */
public class EntityUtils {
    private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);
    
    private EntityUtils() {
        throw new IllegalAccessError("EntityUtils is a tool class!");
    }
    

//	/**
//	 * 实体转换
//	 * @param source 源对象
//	 * @param targetClass 目标对象的Class类型
//	 * @return
//	 */
//	public static <S, T> T transform(S source, Class<? extends T> targetClass) {
//	    if (Objects.isNull(source)) {
//	        return null;
//	    }
//
//	    T target = null;
//        try {
//            target = targetClass.newInstance();
//            BeanUtils.copyProperties(target, source);
//        } catch (Exception e) {
//            logger.error("transform error!", e);
//            throw new BizException("transform error!");
//        }
//        return target;
//	}
	
	
//	/**
//	 * 实体列表转换
//	 * @param sourceList
//	 * @param targetClass
//	 * @return
//	 */
//	public static <S,T> List<T> transform(List<S> sourceList, Class<? extends T> targetClass) {
//		List<T> result = new ArrayList<T>();
//		if (CollectionUtils.isNotEmpty(sourceList)) {
//			for (S source : sourceList) {
//				result.add(transform(source, targetClass));
//			}
//		}
//		return result;
//	}
//
//
//	/**
//	 * 实体列表转换，并返回第一个元素，数组为空则返回null
//	 * @param sourceList
//	 * @param targetClass
//	 * @return
//	 */
//	public static <S, T> T transformAndReturnFirst(List<S> sourceList, Class<? extends T> targetClass) {
//		if (CollectionUtils.isNotEmpty(sourceList)) {
//			return transform(sourceList.get(0), targetClass);
//		}
//		return null;
//	}
	
	
}

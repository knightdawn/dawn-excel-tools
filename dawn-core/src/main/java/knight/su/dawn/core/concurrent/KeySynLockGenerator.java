package knight.su.dawn.core.concurrent;

import knight.su.dawn.core.util.MurmurHash;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * 给具有相同值的key生成一个唯一的同步对象锁（Object对象）工具类
 * 
 * 说明：
 * 1、key虽然是Object对象，但其对应的类必须自定义实现hashCode和equals方法，否则无法识别两个key是否相等；
 * 2、key主要针对String对象和Java基本类型对应的包装类型对象（如Long），前者因为String的不可变特性，后者因为缓存池范围有限原因；
 * 3、同步机制会独占使用同步对象锁（在对象头中做CAS标记），所以不同同步场景避免互相干扰，需要区分生成各自的同步对象锁。
 * 4、如果同步场景生成的同步对象锁不是特别多时，可以直接使用getObjectLock方法即可。
 * 5、如果同步场景生成的同步对象锁特别多，那么不能再使用getObjectLock方法，此方法会导致生成大量的同步对象锁，占用过多内存。
 *    此时应该使用getPartitionObjectLock方法，此方法采用哈希分片思想，将key分为多个片段，每个片段生成一个唯一的同步对象锁，
 *    通过调节分片数量，可以兼顾并发度和内存消耗的平衡，分片数大，并发度高，内存占用高，分片数小，并发度低，内存占用低。
 * 
 * @author Edward
 * @date 2019年4月27日
 * @version 1.0
 */
public class KeySynLockGenerator {
	private static final Map<Object, Map<Object, Object>> keyUniqueObjLockMap = 
			new ConcurrentHashMap<>();
	private KeySynLockGenerator() {
		throw new IllegalAccessError("KeySynLockGenerator is a tool class!");
    }
	
	
	/**
	 * 获取key对应的唯一同步对象锁
	 * 
	 * 特别注意：
	 * 如果key的集合特别大，那么此方法会占用大量内存
	 * 
	 * @param scene 同步场景标记
	 * 		     针对每一种同步场景，设定一个唯一的同步标记（避免不同场景下相同值的key生成一样的同步对象锁）
	 *        
	 * @param key 其对应的类必须自定义实现hashCode和equals方法，否则无法识别两个key是否相等
	 * @return
	 */
	public static Object getObjectLock(String scene, Object key) {
        return getObjectLock(getObjLockMap(scene), key);
    }
	
	
	/**
	 * 获取key对应的唯一同步对象锁
	 * 
	 * 特别注意：
	 * 此方法不是给每个key生成一个同步对象锁，而是根据分片数给key计算一个分片值，然后给分片值生成一个同步对象锁。
	 * 此方法可以通过调节分片数来达到内存和并发度的均衡控制。
	 * 
	 * @param scene 同步场景标记
	 * 		     针对每一种同步场景，设定一个唯一的同步标记（避免不同场景下相同值的key生成一样的同步对象锁）
	 * 
	 * @param partition 分片数
	 *        分片数过低，则并发度低，内存占用低；分片数高，则并发度高，内存占用高。
	 *        
	 * @param key 其对应的类必须自定义实现hashCode和equals方法，否则无法识别两个key是否相等
	 * @return
	 */
	public static Object getPartitionObjectLock(String scene, int partition, Object key) {
		if (key == null) {
			throw new KeyLockGeneratorException("key cannot be null!");
		}
		
		// 根据分片数计算key的分片值
		long keyMod = MurmurHash.mod(key.toString(), partition);
		// 获取分片值对应的唯一同步对象锁
        return getObjectLock(getObjLockMap(scene), keyMod);
	}
	
	
	/*
	 * 获取指定同步场景的同步对象锁集合
	 * @param scene 同步场景标记
	 * @return
	 */
	private static Map<Object, Object> getObjLockMap(String scene) {
		if (StringUtils.isBlank(scene)) {
			throw new KeyLockGeneratorException("scene cannot be null!");
		}
		
		Map<Object, Object> objLockMap = keyUniqueObjLockMap.get(scene);
		if (objLockMap == null) {
			Map<Object, Object> newObjLockMap = new ConcurrentHashMap<>();
			objLockMap = keyUniqueObjLockMap.putIfAbsent(scene, newObjLockMap);
			if (objLockMap == null) {
				objLockMap = newObjLockMap;
			}
    	}
		return objLockMap;
	}
	
	
	/*
	 * 获取key对应的唯一同步对象锁
	 * @param objLockMap key对应唯一同步对象锁集合
	 * @param key
	 * @return
	 */
	private static Object getObjectLock(Map<Object, Object> objLockMap, Object key) {
		if (key == null) {
			throw new KeyLockGeneratorException("key cannot be null!");
		}
		
    	Object lock = objLockMap.get(key);
    	if (lock == null) {
			Object newLock = new Object();
			lock = objLockMap.putIfAbsent(key, newLock);
			if (lock == null) {
				lock = newLock;
			}
    	}
        return lock;
    }
	
	
	/*
	 * 给具有相同值的key生成一个唯一的同步对象锁（Object对象）工具类运行期异常类
	 * @author Edward
	 * @date 2019年4月27日
	 * @version 1.0
	 */
	@SuppressWarnings("unused")
	private static class KeyLockGeneratorException extends RuntimeException {
	    private static final long serialVersionUID = -7551694048864252690L;

		public KeyLockGeneratorException() {
	        super();
	    }

	    public KeyLockGeneratorException(final String message) {
	        super(message);
	    }
	}
	
}

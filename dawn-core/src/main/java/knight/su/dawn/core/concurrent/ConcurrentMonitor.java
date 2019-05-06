package knight.su.dawn.core.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同步并发监视器
 * 
 * 针对三种场景做监控：
 * 1、采用String对象作为同步对象锁，因为String的不可变特性，具有相同值的两个String对象可能有不同的堆引用地址。
 * 2、采用包装对象（如Long）作为同步对象锁，即便有相同值，其引用可能相等（缓存池缘故）也可能不相等（缓存池范围外）
 *   2和3的这种场景，实际上不建议使用作为同步对象锁，但有些特殊场景用好确实可以大大提高系统并发计算能力。
 * 3、同步场景中同步机制使用不当，有线程实际没有持有同步对象锁却进入了同步代码块。
 * 
 * 实现方式：
 * 1、针对每一种同步并发场景增加计数器，当有一个线程进入同步块则计数器原子加1，当有一个线程退出同步块则计数器减1
 * 2、当有多个线程同时进入同步块时则根据选择告警方式进行告警
 * 
 * @author Edward
 * @date 2019年4月27日
 * @version 1.0
 */
public class ConcurrentMonitor {
	private static final Logger logger = LoggerFactory.getLogger(ConcurrentMonitor.class);
	private static final Map<Object, AtomicInteger> lockCounterMap = new ConcurrentHashMap<>();
	private static final String CONCURRENT_WARN_MSG_TEMPLATE = "同步并发监视器发现当前有%s个线程同时进入同步并发块！";
	
    private ConcurrentMonitor() {
        throw new IllegalAccessError("ConcurrentMonitor is a tool class!");
    }
	
	/**
	 * 通知监视器，有线程进入同步块
	 * 如果有多个线程同时进入同步块则默认打印异常日志告警
	 * @param lock 同步对象锁
	 */
	public static void lockIn(Object lock) {
		lockIn(lock, WARN.LOG);
	}
	
	/**
	 * 通知监视器，有线程进入同步块
	 * 如果有多个线程同时进入同步块则根据选择的告警类别进行告警
	 * @param lock 同步对象锁
	 * @param warn {@link WARN} 当同步并发监视器发现异常时发出警告方式
	 */
	public static void lockIn(Object lock, WARN warn) {
		AtomicInteger lockCounter = ConcurrentMonitor.getLockCounter(lock);
		int counts = lockCounter.incrementAndGet();
		if (counts > 1) {
			String warnMsg = String.format(CONCURRENT_WARN_MSG_TEMPLATE, counts);
			if (WARN.LOG == warn) {
				logger.error(warnMsg);
			} else {
				throw new ConcurrentMonitorException(warnMsg);
			}
		}
	}
	
	/**
	 * 通知监视器，有线程退出同步块
	 * @param lock
	 */
	public static void lockOut(Object lock) {
		AtomicInteger lockCounter = ConcurrentMonitor.getLockCounter(lock);
		lockCounter.decrementAndGet();
	}
	
	/*
	 * 获取同步对象锁对应的计数器
	 */
	private static AtomicInteger getLockCounter(Object lock) {
		AtomicInteger lockCounter = lockCounterMap.get(lock);
    	if (lockCounter == null) {
    		AtomicInteger newLockCounter = new AtomicInteger(0);
			// 已存在KV则返回V，本次放入则返回null
    		lockCounter = lockCounterMap.putIfAbsent(lock, newLockCounter);
			if (lockCounter == null) {
				lockCounter = newLockCounter;
			}
    	}
        return lockCounter;
	}
	
	
	/**
	 * 当同步并发监视器发现异常时发出警告方式枚举类
	 * @author Edward
	 * @date 2019年4月27日
	 * @version 1.0
	 */
	public static enum WARN {
		LOG(1, "打印异常日志"),
		EXCEPTION(2, "抛异常");
		
		private int type;
		private String desc;
		
		private WARN(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}
		public int getType() {
			return type;
		}
		public String getDesc() {
			return desc;
		}
	}
	
	
	/*
	 * 同步并发监视器运行期异常类
	 * @author Edward
	 * @date 2019年4月27日
	 * @version 1.0
	 */
	@SuppressWarnings("unused")
	private static class ConcurrentMonitorException extends RuntimeException {
	    private static final long serialVersionUID = -7551694048864252690L;

		public ConcurrentMonitorException() {
	        super();
	    }

	    public ConcurrentMonitorException(final String message) {
	        super(message);
	    }
	}
	
}

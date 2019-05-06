package knight.su.dawn.core.concurrent.test;

import knight.su.dawn.core.concurrent.ConcurrentMonitor;
import knight.su.dawn.core.concurrent.KeySynLockGenerator;

import java.util.concurrent.*;

/**
 * 
 * 【测试以String或包装对象作为同步对象锁的场景】
 * 
 * 1、String是不可变对象，而字面量将会添加到常量池中，非字面量方式创建的String对象位于堆中，即便值相同，而是不同的对象。
 * 2、synchronized同步代码块，需要一个对象锁，不同对象则是不同锁。
 *   举个例子：
 *   问题描述：单节点现在有很多任务要计算，每个任务有一个唯一标记taskId，同一任务执行时必须独占资源，不同任务可以并发执行。
 *   设计方案：采用synchronized同步代码块的方式，以taskId作为同步锁，可以起到单任务同步执行，不同任务并发执行。
 *   存在问题：不同taskId生成的String对象在Java堆中有不同的引用，无法作为唯一同步锁。
 *   
 *   分析解决：
 *   方案一、相同值的String对象虽然引用地址不同，但是hashCode值是一样的，不过因为hashCode值是int类型，使用hashCode值作为同步锁，
 *         必然有装箱操作，而hashCode最大是Integer.MAX_VALUE，这已经超过Integer.IntegerCache缓存范围，所以生成的Integer对象
 *         也是在Java堆中，同样存在不同对象应用问题。【不可取】
 *   方案二、通过String.intern()将String对象常量化，这样值相同的String对象引用就一样了，都是常量池中的值，也就可以作为同步对象锁，此方案存在一个问题，
 *         如果taskId数量特别多，比如有10万个，那么常量池会很大，性能也会慢慢下降，甚至影响到整个系统。
 *         当然我们通过JVM参数（-XX：StringTableSize=99999）扩大常量池，但总归有个上限，不能无限增加，而且堆中一直维护这个大的常量池，内存占用总是不会。
 *         基于这种天然的优势和缺陷，使用此方案需要根据实际场景慎重使用。【性能最佳但占用内存且有风险的方案-慎重使用】
 *   方案三、针对值一样的String对象（通过hashCode方法和equals方法判定），生成唯一映射的Object对象，可以通过ConcurrentHashMap<String, Object>
 *         来做这个事。此方案存在一个问题，如果String特别多的场景，ConcurrentHashMap对象也会比较大，同样存在方案二的问题。
 *         
 *         那么如果在每次同步块执行完毕释放同步锁前，手动释放ConcurrentHashMap中的KV呢？实际上也不行，假设有三个线程并发执行，
 *         三个线程都已经从ConcurrentHashMap获取到taskId对应的对象引用，线程1和线程2没有获取到对象引用对应的同步锁处于阻塞状态，而线程3获取到同步锁并
 *         正在执行中，执行完毕后顺便移除ConcurrentHashMap中taskId对应的对象引用，此时有同样taskId的线程4来了，又在ConcurrentHashMap
 *         中生成一个新的对象引用（方案一解释了没法生成同样的对象引用，此处一定是新的），那么线程1、2、4虽然有同样的taskId，线程4却有不一样的对象引用，
 *         相当于有不同的同步对象锁，同步无法保证了。
 *         
 *         针对这种场景，如果拥有同样taskId的线程每次从ConcurrentHashMap获取对象引用，就+1，线程获得同步锁执行完毕就减1，记录值为0就移除引用可否？
 *         可以，但实现起来麻烦，而且为了原子+1和-1，即便是CAS，这么多的CAS性能也不怎么样。
 *         
 *         综上：为了实现难易和性能考虑，此方案可以使用，不过同样存在方案二内存占用问题，不过比方案二好，至少内存占用过多可以通过留下手动调用清除内存+新服务暂停使用
 *         很短时间来完成内存的回收（系统控制暂停接收新同步任务，清除内存，等已有任务执行完毕，重启接收新同步任务）。另外方案二内存占用过多是会影响整个常量池的性能的，
 *         此方案不会，不过性能上来说方案二相对好一点。【占用内存的方案-考虑使用】
 *   方案四、采用数据分片，分而治之的思想，假设有10万个taskId，采用哈希算法均匀分为1000个数据片，每个数据片生成一个唯一的同步对象锁，这样分属同一数据片的任务可以同步执行，
 *         对象锁也不会生成太多，可以一直保留在内存中，数据分片较多，任务并发执行度也比较好。【兼顾内存和性能最均衡的方案-建议使用】
 *   
 * @author Edward
 * @date 2019年4月27日
 * @version 1.0
 */
public class SynchronizedTest {
private static String SCENE = "TASK_SCHEDULE";	
    
    public static void main(String[] args) throws Exception {
        TaskBiz taskBiz = TaskBiz.getInstance();
//         此处taskId用同一个字面量，可以起到同一把锁的作用，下面两个线程会排队执行
//        String taskId = "A1001";
//        Thread thread1 = new Thread(new MyRunnable(taskBiz, taskId));
//        Thread thread2 = new Thread(new MyRunnable(taskBiz, taskId));
        long start = System.currentTimeMillis();
        
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);
        int threadCounts = 1000;
        for (int i = 0; i < threadCounts; i++) {
        	if (i % 2 == 0) {
        		// 此处模拟撞车
//            	completionService.submit(new MyCallable(taskBiz, new String("1000")));
        		completionService.submit(new MyCallable(taskBiz, Long.valueOf(1000L))); // 100没问题，1000就有问题，因为Long缓冲池问题
        	} else {
//            	completionService.submit(new MyCallable(taskBiz, new String("100" + i)));        		
        		completionService.submit(new MyCallable(taskBiz, Long.valueOf(200 + i)));
        	}
        }
        for (int i = 0; i < threadCounts; i++) {
            Future<String> future = completionService.take();
            String msg = future.get();
//            System.out.println(msg);
        }
        System.out.println("全部线程执行完毕！" + (System.currentTimeMillis() - start) + "(ms)");
        
//        Thread.sleep(100);
        
//        for (int i = 0; i < threadCounts; i++) {
//        	completionService.submit(new MyCallable(taskBiz, new String("A100" + i)));
//        }
//        for (int i = 0; i < threadCounts; i++) {
//            Future<String> future = completionService.take();
//            String msg = future.get();
////            System.out.println(msg);
//        }
//        System.out.println("全部线程执行完毕！" + (System.currentTimeMillis() - start) + "(ms)");
    }
    
    
    public static class MyCallable implements Callable<String> {
        private Object taskId;
        private TaskBiz taskBiz;
        public MyCallable(TaskBiz taskBiz, Object taskId) {
            this.taskBiz = taskBiz;
            this.taskId = taskId; 
        }
        
        @Override
        public String call() {
            taskBiz.dealWithTaskId(taskId);
            return Thread.currentThread().getName() + " - 执行完毕！";
        }
        
        public Object getTaskId() {
            return taskId;
        }

        public void setTaskId(Object taskId) {
            this.taskId = taskId;
        }
    }
    
    
    /**
     * 
     * 方案三简单实现
     * 
     * 业务处理类，单例实现
     * @author 01370886
     * @date 2019年4月26日
     */
    public static class TaskBiz {
        private static TaskBiz taskBiz = new TaskBiz();
        private TaskBiz() {
        }
        public static TaskBiz getInstance() {
            return taskBiz;
        }
        
        public void dealWithTaskId(Object taskId) {
//        	Object lock = taskId; // 直接用String对象，可能有问题
//        	Object lock = taskId.toString().intern(); // 方案二，利用常量池的特性
        	Object lock = KeySynLockGenerator.getPartitionObjectLock(SCENE, 100, taskId); // 方案四
//        	Object lock = KeySynLockGenerator.getObjectLock(SCENE, taskId); // 方案三
            synchronized (lock) {
            	ConcurrentMonitor.lockIn(lock, ConcurrentMonitor.WARN.LOG);
//            	try {
//					Thread.sleep(20);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
            	// do something in 
            	ConcurrentMonitor.lockOut(lock);
            }
        }
    }

}

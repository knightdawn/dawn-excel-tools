package knight.su.dawn.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 根据分片数计算分片值算法
 * 
 * 核心算法取自hadoop包，计算性能大概是单线程 45万次/秒
 * 
 * This is a very fast, non-cryptographic hash suitable for general hash-based
 * lookup.  See http://murmurhash.googlepages.com/ for more details.
 * 
 * <p>The C version of MurmurHash 2.0 found at that site was ported
 * to Java by Andrzej Bialecki (ab at getopt org).</p>
 * @author Edward
 * @date 2019年4月27日
 * @version 1.0
 */
public class MurmurHash {
	private MurmurHash() {
        throw new IllegalAccessError("MurmurHash is a tool class!");
    }
    
    /**
     * 计算字符串值基于指定分片数的模值
     * @param value 值
     * @param partition 分片数
     * @return
     */
    public static long mod(String value, int partition) {
        if (StringUtils.isBlank(value)) {
            throw new MurmurHashException("value不能为空！");            
        }
        if (partition <= 0) {
            throw new MurmurHashException("partition必须大于0！");
        }
        long hashCode = hash(value.getBytes());
        if (hashCode < 0) {
            hashCode = 0 - hashCode;
        }
        long mod = hashCode % partition;
        return mod;
    }
    
    private static int hash(byte[] bytes) {
        return hash(bytes, bytes.length, -1);
    }
    
    private static int hash(byte[] data, int length, int seed) {
        int m = 0x5bd1e995;
        int r = 24;

        int h = seed ^ length;

        int len_4 = length >> 2;

        for (int i = 0; i < len_4; i++) {
            int i_4 = i << 2;
            int k = data[i_4 + 3];
            k = k << 8;
            k = k | (data[i_4 + 2] & 0xff);
            k = k << 8;
            k = k | (data[i_4 + 1] & 0xff);
            k = k << 8;
            k = k | (data[i_4 + 0] & 0xff);
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
            h ^= k;
        }

        // avoid calculating modulo
        int len_m = len_4 << 2;
        int left = length - len_m;

        if (left != 0) {
            if (left >= 3) {
                h ^= (int) data[length - 3] << 16;
            }
            if (left >= 2) {
                h ^= (int) data[length - 2] << 8;
            }
            if (left >= 1) {
                h ^= (int) data[length - 1];
            }

            h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }
    
    /*
	 * 根据分片数计算分片值算法运行时异常
	 * @author Edward
	 * @date 2019年4月27日
	 * @version 1.0
	 */
	@SuppressWarnings("unused")
	private static class MurmurHashException extends RuntimeException {
	    private static final long serialVersionUID = -7551694048864252690L;

		public MurmurHashException() {
	        super();
	    }

	    public MurmurHashException(final String message) {
	        super(message);
	    }
	}
    
}

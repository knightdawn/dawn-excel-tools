package knight.su.dawn.core.util.security;

import knight.su.dawn.core.constant.CommonConsts;
import knight.su.dawn.core.exception.ArithmeticSecurityException;
import knight.su.dawn.core.util.RadixUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 【消息摘要算法加密】
 * 
 * <b>使用java的原生类java.security.MessageDigest实现数字签名算法MD5和SHA</b>
 * 
 * <p>生成的是二进制数组，需要转化为十六进制（Hex）、MD5返回32为字符，SHA返回40位字符
 * <br>两者都是用hash散列计算出来，MD5速度快但抗攻击强大不如SHA-1</p>
 * 
 * @author Edward
 * @date 2016年8月3日
 * @version 1.0
 */
public class DigestUtils {
	private static final Logger logger = LoggerFactory.getLogger(DigestUtils.class);
	private static final String MD5 = "MD5";
	private static final String SHA = "SHA-1";
	
	private DigestUtils() {
		throw new IllegalAccessError("DigestUtils is a tool class!");
	}
	
	
	/**
	 * 将明文转化为MD5加密串（十六进制形式）
	 * @param content 加密明文
	 * @param charset 字符集类型，可使用常量类 {@link CommonConsts}
	 * @return
	 */
	public static String md5ToHex(String content, String charset) {
		return digestToHex(MD5, content, charset);
	}
	
	
	/**
	 * 将明文转化为MD5加密串（十六进制形式），默认采用UTF-8编码
	 * @param content 加密明文
	 * @return
	 */
	public static String md5ToHex(String content) {
		return digestToHex(MD5, content, CommonConsts.CHARSET_UTF8);
	}
	
	
	/**
	 * 将明文转化为SHA-1加密串（十六进制形式）
	 * @param content 加密明文
	 * @param charset 字符集类型，可使用常量类 {@link CommonConsts}
	 * @return
	 */
	public static String sha1ToHex(String content, String charset) {
		return digestToHex(SHA, content, charset);
	}
	
	
	/**
	 * 将明文转化为SHA-1加密串（十六进制形式），默认采用UTF-8编码
	 * @param content 加密明文
	 * @return
	 */
	public static String sha1ToHex(String content) {
		return digestToHex(SHA, content, CommonConsts.CHARSET_UTF8);
	}
	
	
	/*
	 * 摘要算法加密
	 * @param algorithm 算法类型
	 * @param content 加密明文
	 * @param charset 字符集类型
	 * @return
	 */
	private static String digestToHex(String algorithm, String content, String charset) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			if (null == charset || "".equals(charset)) {
				md.update(content.getBytes()); // 使用平台默认的字符集
			} else {
				md.update(content.getBytes(charset));
			}
			return RadixUtils.byteArrayToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error("digestToHex error! invalid-algorithm={}", algorithm, e);
			throw ArithmeticSecurityException.buildArithmeticSecurityException(
					ArithmeticSecurityException.Code.INVALID_ALGORITHM);
		} catch (UnsupportedEncodingException e) {
			logger.error("digestToHex error! invalid-charset={}", charset, e);
			throw ArithmeticSecurityException.buildArithmeticSecurityException(
					ArithmeticSecurityException.Code.INVALID_CHARSET);
		}
	}
	
	
//	public static void main(String[] args) {
//		String content = "中文test";
//		System.out.println(md5ToHex(content, "GBK2")); // 3bdefec7a9c04572268d68db7763da4a
//		System.out.println(sha1ToHex(content)); // abc104b83a4d3a67ede25d72383a8b0f00722c02
//	}
	
}

package knight.su.dawn.core.util.security;

import knight.su.dawn.core.constant.CommonConsts;
import knight.su.dawn.core.exception.ArithmeticSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 【AES加解密算法】 -- 代替DES算法
 * 对称加密算法AES，可逆的加密算法，目前SSL（google暴露不安全，要用第三版TSL了）中常用的加密算法
 * AES理论上支持128位、192位、256位秘钥，但JDK7只实现了128位，
 * 如果需要256位秘钥调用组件bouncycastle（下载新的jar包覆盖jdk的security下的包）实现。
 * AES加密后得到的二进制流用Base64算法进行URL安全编码，方便网络传输
 * @author Edward
 * @date 2016年8月3日
 * @version 1.0
 */
public class AESUtils {
	private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);
    private static final String KEY_ALGORITHM = "AES"; // 秘钥生成指定AES算法
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"; // ECB模式较简单，但容易被攻击，CBC模式不容易被攻击
    private static final String IV_KEY = "1029384756012581"; // 使用CBC模式需要初始化IV，且必须是16位的
    
    private AESUtils() {
    	throw new IllegalAccessError("AESUtils is a tool class!");
    }
    
    
    public static String encrypt(String key, String content) {
        return encryptToBase64(key, content, CommonConsts.CHARSET_UTF8);
    }
    
    public static String encrypt(String key, String content, String charset) {
        return encryptToBase64(key, content, charset);
    }
    
    public static String decrypt(String key, String content) {
        return decryptFromBase64(key, content, CommonConsts.CHARSET_UTF8);
    }
    
    public static String decrypt(String key, String content, String charset) {
        return decryptFromBase64(key, content, charset);
    }

    
    /**
     * 初始化一个128位的AES秘钥
     * @return
     * @throws Exception
     */
    public static SecretKeySpec initKey() throws Exception {
        // 获取秘钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        
        // 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
        keyGenerator.init(128);
        
        // 生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        return skeySpec;
    }
    
    /*
     * 通过AES算法对明文加密并返回base64编码的密文
     * @param key 秘钥
     * @param content 明文
     * @param charset 编码
     * @return
     * @throws Exception
     */
    private static String encryptToBase64(String key, String content, 
            String charset) {
        try {
			SecretKeySpec skeySpec = getKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(content.getBytes(charset));
			return Base64.getUrlEncoder().encodeToString(encrypted);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			logger.error("encryptToBase64 error! key={}, content={}, charset={}", key, content, charset, e);
			throw ArithmeticSecurityException.buildArithmeticSecurityException(
					ArithmeticSecurityException.Code.AES_FAIL);
		} catch (UnsupportedEncodingException e) {
			logger.error("encryptToBase64 error! invalid-charset={}", charset, e);
			throw ArithmeticSecurityException.buildArithmeticSecurityException(
					ArithmeticSecurityException.Code.INVALID_CHARSET);
		}
    }

    /*
     * 先将base64密文解码，再通过AES算法解密出明文
     * @param key
     * @param content
     * 
     * @param charset
     * @return
     * @throws Exception
     */
    private static String decryptFromBase64(String key, String content, 
            String charset) {
        try {
			SecretKeySpec skeySpec = getKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.getUrlDecoder().decode(content);
			return new String(cipher.doFinal(encrypted1), charset);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException e) {
			logger.error("decryptFromBase64 error! key={}, content={}, charset={}", key, content, charset, e);
			throw ArithmeticSecurityException.buildArithmeticSecurityException(
					ArithmeticSecurityException.Code.AES_FAIL);
		} catch (UnsupportedEncodingException e) {
			logger.error("decryptFromBase64 error! invalid-charset={}", charset, e);
			throw ArithmeticSecurityException.buildArithmeticSecurityException(
					ArithmeticSecurityException.Code.INVALID_CHARSET);
		}
    }
    
    /*
     * 转化字符串秘钥为AES秘钥（即便字符串秘钥没有16位也没有关系）
     * @param key 秘钥字符串
     * @return
     * @throws Exception
     */
    private static SecretKeySpec getKey(String key) {
        // 创建一个空的16位字节数组（默认值为0）
        byte[] keyBytes = new byte[16];
        byte[] bTmp = key.getBytes();
        for (int i = 0; i < bTmp.length && i < keyBytes.length; i++) {
            keyBytes[i] = bTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        return skeySpec;
    }
    
    /*public static void main(String[] args) {
	      try {
	          String content = "{'name': '太阳之光', 'password': '123456', 'sex':'true'}";
	          String key = "5d41402abc4b2a76"; // 16位秘钥
	          System.out.println("AES算法加密前的明文：" + content);
	          System.out.println("AES算法加密用的秘钥：" + key);
	          
	          String encryptStr = encrypt(key, content, CommonConsts.CHARSET_UTF8);
	          System.out.println("AES算法加密后的密文：" + encryptStr);
	          
	          String decryptStr = decrypt(key, encryptStr, CommonConsts.CHARSET_UTF8);
	          System.out.println("AES算法解密后的明文：" + decryptStr);
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	  }*/
    
}

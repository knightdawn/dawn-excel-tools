package knight.su.dawn.core.demo;

import knight.su.dawn.core.constant.CommonConsts;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * JDK8提供的Base64编码API
 * @author Edward
 * @date 2019年4月16日
 * @version 1.0
 */
public class Base64Demo {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		// URL不安全编码方式
		Base64.Encoder encoder = Base64.getEncoder();
		Base64.Decoder decoder = Base64.getDecoder();
		
		String base64Text = encoder.encodeToString("古道西风瘦马".getBytes(CommonConsts.CHARSET_UTF8));
		System.out.println("非URL安全加密串： " + base64Text);
		System.out.println("非URL安全解密串： " + new String(decoder.decode(base64Text), CommonConsts.CHARSET_UTF8));
		
		// URL安全编码方式
		Base64.Encoder urlEncoder = Base64.getUrlEncoder();
		Base64.Decoder urlDecoder = Base64.getUrlDecoder();
		
		String base64Text2 = urlEncoder.encodeToString("古道西风瘦马".getBytes(CommonConsts.CHARSET_UTF8)); 
		System.out.println("URL安全加密串： " + base64Text2);
		System.out.println("URL安全解密串： " + new String(urlDecoder.decode(base64Text2), CommonConsts.CHARSET_UTF8));
		
		// 总结: URL安全编码方式就是将 "+" -> "-"、           "/" -> "_"
	}
	
}

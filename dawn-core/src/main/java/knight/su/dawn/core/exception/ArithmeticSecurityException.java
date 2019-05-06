package knight.su.dawn.core.exception;

/**
 * 加密算法安全异常
 * @author Edward
 * @date 2019年3月30日
 * @version 1.0
 */
public class ArithmeticSecurityException extends RuntimeException {
    private static final long serialVersionUID = 4354961475168597022L;
    private String errorCode;
    
    public ArithmeticSecurityException() {
        super();
    }
    
    public ArithmeticSecurityException(Throwable cause) {
        super(cause);
    }

    public ArithmeticSecurityException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ArithmeticSecurityException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
    
    public ArithmeticSecurityException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }
    
    /**
     * 根据异常枚举常量构建一个异常类
     * @param code 异常枚举常量，参见 {@link Code}
     * @return
     */
    public static ArithmeticSecurityException buildArithmeticSecurityException(Code code) {
    	return new ArithmeticSecurityException(code.getValue(), code.getText());
    }
    
    /**
     * 根据异常枚举常量构建一个异常类，同时往上抛出异常链
     * @param code 异常枚举常量，参见 {@link Code}
     * @param e 上层异常类
     * @return
     */
    public static ArithmeticSecurityException buildArithmeticSecurityException(Code code, 
    		Exception e) {
    	return new ArithmeticSecurityException(code.getValue(), code.getText(), e);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    @Override
    public String toString() {
        String s = getClass().getName();
        String errorCodeMsg = this.errorCode == null ? "" : this.errorCode;
        String message = getLocalizedMessage();
        message = message == null ? errorCodeMsg : (errorCodeMsg + " : " + message);
        return ("".equals(message)) ? s : (s + ": " + message);
    }
    
    
    /**
     * 异常状态码
     * @author Edward
     * @date 2019年3月31日
     * @version 1.0
     */
    public static enum Code {
    	INVALID_ALGORITHM("1001", "非法算法类型"),
    	INVALID_CHARSET("1002", "非法字符集"),
    	AES_FAIL("1003", "AES算法加解密失败");
    	
    	private Code(String value, String text) {
    		this.value = value;
    		this.text = text;
    	}
    	
    	private String value;
    	private String text;
    	
    	public String getValue() {
    		return value;
    	}
    	
    	public String getText() {
    		return text;
    	}
    }

}

package knight.su.dawn.excelt.exception;

/**
 *
 * Date: 2016/7/17<br/>
 * 
 * @author sugengbin
 */
public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    private Object extraInfo;

    public ValidateException() {
        super();
    }

    public ValidateException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ValidateException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public ValidateException(String errorCode, String errorMessage, Object extraInfo) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.extraInfo = extraInfo;
    }

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
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
        message = message == null ? errorCodeMsg : (errorCodeMsg + message);
        return ("".equals(message)) ? s : (s + ": " + message);
    }
}

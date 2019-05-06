package knight.su.dawn.core.exception;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = -7551694048864252690L;

    public BizException() {
        super();
    }

    public BizException(final String message) {
        super(message);
    }
    
}

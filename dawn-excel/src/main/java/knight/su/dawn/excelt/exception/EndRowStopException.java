package knight.su.dawn.excelt.exception;

/**
 * 
 * Created by sugengbin 2019/04/09
 */
public class EndRowStopException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private int endRow;

	public EndRowStopException() {
		super();
	}

	public EndRowStopException(int endRow) {
		super();
		this.endRow = endRow;
	}
	
	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public EndRowStopException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public EndRowStopException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}

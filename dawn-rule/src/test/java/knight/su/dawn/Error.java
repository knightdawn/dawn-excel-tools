package knight.su.dawn;

/**
 * 
 * @Date 2019/1/14
 * @author 449632
 */
public class Error {
	private String msg;
	private int row;

	public Error() {
	}
	
	public Error(int row, String msg) {
		this.row = row;
		this.msg = msg;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ErrorTest{" + "msg='" + msg + '\'' + ", row=" + row + '}';
	}
}

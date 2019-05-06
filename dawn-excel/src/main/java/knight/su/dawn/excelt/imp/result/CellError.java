package knight.su.dawn.excelt.imp.result;

import java.io.Serializable;

/**
 * 
 * Created by sugengbin 2019/1/4
 */
public class CellError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2058444263122403068L;
	private String msg; // 错误信息
	private int row; // 行数
	private String value; // 原值 FIXME

	public CellError(int row, String msg) {
		this.row = row + 1;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public CellError setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public int getRow() {
		return row;
	}

	public CellError setRow(int row) {
		this.row = row + 1;
		return this;
	}

	public String getValue() {
		return value;
	}

	public CellError setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return "row=" + this.row + ",msg=" + this.msg;
	}
}

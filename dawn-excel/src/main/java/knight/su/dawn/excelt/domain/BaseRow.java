package knight.su.dawn.excelt.domain;

import knight.su.dawn.excelt.imp.annotation.RowIndex;

/**
 *
 * Date: 2019/1/10<br/>
 * 
 * @author sugengbin
 */
public class BaseRow {

	@RowIndex
	protected int row;

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}

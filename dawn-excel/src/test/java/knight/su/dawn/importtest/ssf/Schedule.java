package knight.su.dawn.importtest.ssf;

import knight.su.dawn.excelt.domain.BaseRow;
import knight.su.dawn.excelt.imp.annotation.ExcelCol;
import knight.su.dawn.excelt.imp.annotation.ExcelColLetter;

import static knight.su.dawn.excelt.common.ColIdxEnum.*;
import static knight.su.dawn.excelt.common.InvertEnum.I01;

/**
 * 
 * @Date 2019/1/11
 * @author sugengbin
 */
public class Schedule extends BaseRow {
	@ExcelCol(0)
	private String a;
	@ExcelCol(value = 4, invert = I01)
	private Integer e;
	@ExcelCol(2)
	private String c;
	@ExcelCol(1)
	private String b;
	@ExcelColLetter(D)
	private String d;

	private String cityCode;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getE() {
		return e;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "a=" + this.a + ",b=" + this.b + ",c=" + this.c + ",d=" + this.d + ",e=" + this.e + ",row=" + this.row + ",cityCode=" + this.cityCode;
	}
}
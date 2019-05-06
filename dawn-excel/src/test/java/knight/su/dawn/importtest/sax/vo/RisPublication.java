package knight.su.dawn.importtest.sax.vo;

import knight.su.dawn.excelt.common.InvertEnum;
import knight.su.dawn.excelt.imp.annotation.ExcelCol;

/**
 * Created by sugengbin 2019/04/01
 */
public class RisPublication {

	@ExcelCol(0)
	private String a;
	@ExcelCol(1)
	private String b;
	@ExcelCol(2)
	private String c;
	@ExcelCol(3)
	private String d;
	@ExcelCol(value = 4, invert = InvertEnum.I01)
	private String e;

	private String cityCode;
	
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	@Override
	public String toString() {
		return "a=" + this.a + ",b=" + this.b + ",c=" + this.c + ",d=" + this.d + ",e=" + this.e + ",cityCode="
				+ this.cityCode;
	}
}

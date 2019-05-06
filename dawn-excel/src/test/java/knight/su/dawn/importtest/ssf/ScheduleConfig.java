package knight.su.dawn.importtest.ssf;

import knight.su.dawn.excelt.domain.BaseRow;

/**
 * 
 * @Date 2019/1/11
 * @author sugengbin
 */
public class ScheduleConfig extends BaseRow {
	private String a;
	private String c;
	private String b;
	private String d;

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

	@Override
	public String toString() {
		return "a=" + this.a + ",b=" + this.b + ",c=" + this.c + ",d=" + this.d + ",row=" + this.row + ",cityCode=" + this.cityCode;
	}
}
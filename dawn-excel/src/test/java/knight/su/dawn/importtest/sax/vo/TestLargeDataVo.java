package knight.su.dawn.importtest.sax.vo;

import knight.su.dawn.excelt.imp.annotation.ExcelColLetter;

import static knight.su.dawn.excelt.common.ColIdxEnum.*;

//import com.sf.dawn.estools.persistence.annotation.EsColumn;
//import com.sf.dawn.estools.persistence.annotation.EsDocId;
//import com.sf.dawn.estools.persistence.annotation.EsIndex;
//import com.sf.dawn.estools.persistence.annotation.EsType;

/**
 * Created by sugengbin 2019/04/08
 */
//@EsIndex(name = "waybill_test")
//@EsType(name = "waybill")
public class TestLargeDataVo {

//	@EsDocId
	private String docId;

//	@EsColumn(name = "waybill")
	@ExcelColLetter(A)
	private String waybill;
	
//	@EsColumn(name = "start")
	@ExcelColLetter(B)
	private String start;
	
//	@EsColumn(name = "end")
	@ExcelColLetter(C)
	private String end;
	
//	@EsColumn(name = "tm")
	@ExcelColLetter(D)
	private String tm;
	
//	@EsColumn(name = "type")
	@ExcelColLetter(E)
	private String type;
	
//	@EsColumn(name = "weight")
	@ExcelColLetter(F)
	private String weight;
	
//	@EsColumn(name = "bussiness")
	@ExcelColLetter(G)
	private String bussiness;
	
//	@EsColumn(name = "date")
	@ExcelColLetter(H)
	private String date;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWaybill() {
		return waybill;
	}

	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBussiness() {
		return bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "waybill=" + this.waybill + "," + "start=" + this.start + "," + "end=" + this.end + "," + "tm=" + this.tm
				+ "," + "type=" + this.type + "," + "weight=" + this.weight + "," + "bussiness=" + this.bussiness + ","
				+ "date=" + this.date;
	}
}

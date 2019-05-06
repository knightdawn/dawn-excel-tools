package knight.su.dawn.importtest.sax.vo;

import knight.su.dawn.excelt.imp.annotation.ExcelColLetter;
import knight.su.dawn.excelt.util.JsonUtil;

import java.util.Date;

import static knight.su.dawn.excelt.common.ColIdxEnum.*;

/**
 * 支线仿真 - 快件运单信息
 * @author 01370886
 * @date 2019年4月5日
 */
public class TestWaybill {

    private String waybillId; // 数据集id

    private String cityCode; // 城市编码

    @ExcelColLetter(A)
    private String waybillNo; // 运单号

    @ExcelColLetter(B)
    private String srcDeptCode; // 始发地网点代码

    @ExcelColLetter(C)
    private String srcUnitArea; // 始发地单元区域代码

    @ExcelColLetter(D)
    private String srcCityCode; // 始发地城市代码

    @ExcelColLetter(E)
    private String srcAreaCode; // 始发地区部代码

    @ExcelColLetter(F)
    private String destDeptCode; // 目的地网点代码

    @ExcelColLetter(G)
    private String destUnitArea; // 目的地单元区域代码

    @ExcelColLetter(H)
    private String destCityCode; // 目的地城市代码

    @ExcelColLetter(I)
    private String destAreaCode; // 目的地区部代码

    @ExcelColLetter(J)
    private String transitDepotOrDeptCode; // 散货中转场/网点代码（非必填，集货为空）

    @ExcelColLetter(K)
    private String date; // 日期

    @ExcelColLetter(L)
    private String availableTime; // 可发车时间（集货），可装车时间（散货）(hh:mm:ss)

    @ExcelColLetter(O)
    private String storeOrBulk; // 集/散

    @ExcelColLetter(M)
    private Float pieceWeight; // 重量（必填）

    @ExcelColLetter(N)
    private String productType; // 产品类型（对应带货清单里面的路由代码）

    private Date createTm; // 创建时间

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getSrcDeptCode() {
        return srcDeptCode;
    }

    public void setSrcDeptCode(String srcDeptCode) {
        this.srcDeptCode = srcDeptCode;
    }

    public String getSrcUnitArea() {
        return srcUnitArea;
    }

    public void setSrcUnitArea(String srcUnitArea) {
        this.srcUnitArea = srcUnitArea;
    }

    public String getSrcCityCode() {
        return srcCityCode;
    }

    public void setSrcCityCode(String srcCityCode) {
        this.srcCityCode = srcCityCode;
    }

    public String getSrcAreaCode() {
        return srcAreaCode;
    }

    public void setSrcAreaCode(String srcAreaCode) {
        this.srcAreaCode = srcAreaCode;
    }

    public String getDestDeptCode() {
        return destDeptCode;
    }

    public void setDestDeptCode(String destDeptCode) {
        this.destDeptCode = destDeptCode;
    }

    public String getDestUnitArea() {
        return destUnitArea;
    }

    public void setDestUnitArea(String destUnitArea) {
        this.destUnitArea = destUnitArea;
    }

    public String getDestCityCode() {
        return destCityCode;
    }

    public void setDestCityCode(String destCityCode) {
        this.destCityCode = destCityCode;
    }

    public String getDestAreaCode() {
        return destAreaCode;
    }

    public void setDestAreaCode(String destAreaCode) {
        this.destAreaCode = destAreaCode;
    }

    public String getTransitDepotOrDeptCode() {
        return transitDepotOrDeptCode;
    }

    public void setTransitDepotOrDeptCode(String transitDepotOrDeptCode) {
        this.transitDepotOrDeptCode = transitDepotOrDeptCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getStoreOrBulk() {
        return storeOrBulk;
    }

    public void setStoreOrBulk(String storeOrBulk) {
        this.storeOrBulk = storeOrBulk;
    }

    public Float getPieceWeight() {
        return pieceWeight;
    }

    public void setPieceWeight(Float pieceWeight) {
        this.pieceWeight = pieceWeight;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }
    
    @Override
	public String toString() {
    	return JsonUtil.object2Json(this);
    }
}

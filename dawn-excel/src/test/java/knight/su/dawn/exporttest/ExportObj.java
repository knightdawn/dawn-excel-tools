package knight.su.dawn.exporttest;

import knight.su.dawn.core.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ExportObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2266114722564068804L;

	private String taskNo;
	private Long createdTime;
	private int bagSize = 0;
	private int packageSize = 0;
	private String ddsTaskType;
	private String currentOperTime;
	private String currentRouteInfo;
	private String taskDescBrief;
	private Integer operateType;
	private String isAbnormal;
	private String abnormalType = "— —";
	private String handoverCode;
	private String taskStartDept;
	private String taskEndDept;
	private String rider;
	private String currProcessor;
	private Long riderReciveTime;
	private String timeLimitedDtail;
	private String expectGetTime = "— —";
	private String expectSignTime = "— —";
	private Long completeTime;
	private Long riderDeliveredTime;
	private Long riderPickupTime;
	private String capacity;
	private String taskStatusDesc;

	public static ExportObj build() {
		return build(1);
	}
	
	public static ExportObj build(int i) {
		ExportObj eo = new ExportObj();
		eo.setTaskNo("taskNo" + i);
		eo.setCreatedTime(new Date().getTime());
		eo.setBagSize(123);
		eo.setPackageSize(123123);
		eo.setDdsTaskType("001");
		eo.setCurrentOperTime(DateUtils.format(new Date(), DateUtils.PATTERN_YMDHMS));
		eo.setCurrentRouteInfo("11111111111111111111111111111111111111111111111111112323123123");
		eo.setTaskDescBrief("this is a task desc.........................");
		eo.setOperateType(3);
		eo.setIsAbnormal("否");
		eo.setAbnormalType("1231231");
		eo.setHandoverCode("1231231");
		eo.setTaskStartDept("677123123");
		eo.setTaskEndDept("03000sfs");
		eo.setRider("1231231231");
		eo.setCurrProcessor("sdfdafaf");
		eo.setRiderReciveTime(new Date().getTime());
		eo.setTimeLimitedDtail("time limited detail");
		eo.setExpectGetTime(DateUtils.format(new Date(), DateUtils.PATTERN_YMDHMS));
		eo.setExpectSignTime(DateUtils.format(new Date(), DateUtils.PATTERN_YMDHMS));
		eo.setCompleteTime(new Date().getTime());
		eo.setRiderPickupTime(new Date().getTime());
		eo.setCapacity("2341234");
		eo.setTaskStatusDesc("task desc");
		return eo;
	}
	
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public int getBagSize() {
		return bagSize;
	}

	public void setBagSize(int bagSize) {
		this.bagSize = bagSize;
	}

	public int getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(int packageSize) {
		this.packageSize = packageSize;
	}

	public String getDdsTaskType() {
		return ddsTaskType;
	}

	public void setDdsTaskType(String ddsTaskType) {
		this.ddsTaskType = ddsTaskType;
	}

	public String getCurrentOperTime() {
		return currentOperTime;
	}

	public void setCurrentOperTime(String currentOperTime) {
		this.currentOperTime = currentOperTime;
	}

	public String getCurrentRouteInfo() {
		return currentRouteInfo;
	}

	public void setCurrentRouteInfo(String currentRouteInfo) {
		this.currentRouteInfo = currentRouteInfo;
	}

	public String getTaskDescBrief() {
		return taskDescBrief;
	}

	public void setTaskDescBrief(String taskDescBrief) {
		this.taskDescBrief = taskDescBrief;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public String getAbnormalType() {
		return abnormalType;
	}

	public void setAbnormalType(String abnormalType) {
		this.abnormalType = abnormalType;
	}

	public String getHandoverCode() {
		return handoverCode;
	}

	public void setHandoverCode(String handoverCode) {
		this.handoverCode = handoverCode;
	}

	public String getTaskStartDept() {
		return taskStartDept;
	}

	public void setTaskStartDept(String taskStartDept) {
		this.taskStartDept = taskStartDept;
	}

	public String getTaskEndDept() {
		return taskEndDept;
	}

	public void setTaskEndDept(String taskEndDept) {
		this.taskEndDept = taskEndDept;
	}

	public String getRider() {
		return rider;
	}

	public void setRider(String rider) {
		this.rider = rider;
	}

	public String getCurrProcessor() {
		return currProcessor;
	}

	public void setCurrProcessor(String currProcessor) {
		this.currProcessor = currProcessor;
	}

	public Long getRiderReciveTime() {
		return riderReciveTime;
	}

	public void setRiderReciveTime(Long riderReciveTime) {
		this.riderReciveTime = riderReciveTime;
	}

	public String getTimeLimitedDtail() {
		return timeLimitedDtail;
	}

	public void setTimeLimitedDtail(String timeLimitedDtail) {
		this.timeLimitedDtail = timeLimitedDtail;
	}

	public String getExpectGetTime() {
		return expectGetTime;
	}

	public void setExpectGetTime(String expectGetTime) {
		this.expectGetTime = expectGetTime;
	}

	public String getExpectSignTime() {
		return expectSignTime;
	}

	public void setExpectSignTime(String expectSignTime) {
		this.expectSignTime = expectSignTime;
	}

	public Long getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Long completeTime) {
		this.completeTime = completeTime;
	}

	public Long getRiderDeliveredTime() {
		return riderDeliveredTime;
	}

	public void setRiderDeliveredTime(Long riderDeliveredTime) {
		this.riderDeliveredTime = riderDeliveredTime;
	}

	public Long getRiderPickupTime() {
		return riderPickupTime;
	}

	public void setRiderPickupTime(Long riderPickupTime) {
		this.riderPickupTime = riderPickupTime;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getTaskStatusDesc() {
		return taskStatusDesc;
	}

	public void setTaskStatusDesc(String taskStatusDesc) {
		this.taskStatusDesc = taskStatusDesc;
	}

}

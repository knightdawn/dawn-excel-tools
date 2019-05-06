package knight.su.dawn.excelt.imp.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created by sugengbin 2019/1/4
 */
public class ImpResult<T> {

	private boolean isRightTemplate = true; // 是否正确模板文件格式
	private boolean isEmptyFile = false; // 是否空文件
	private boolean isOverLimit = false; // 是否超过条数限制
	private boolean globalPass = true; // 以上三个全局校验是否校验通过
	private String errorMsg;
	private List<T> dataResult; // 读取后的数据
	private List<CellError> errors; // 错误列表

	public ImpResult() {
		dataResult = new ArrayList<>();
		errors = new ArrayList<>();
	}
	
	public ImpResult(List<T> dataResult, List<CellError> errors) {
		this.dataResult = dataResult;
		this.errors = errors;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isRightTemplate() {
		return isRightTemplate;
	}

	public void setRightTemplate(boolean isRightTemplate) {
		this.isRightTemplate = isRightTemplate;
	}

	public List<CellError> getErrors() {
		return errors;
	}

	public void setErrors(List<CellError> errors) {
		this.errors = errors;
	}

	public List<T> getDataResult() {
		return dataResult;
	}

	public void setDataResult(List<T> dataResult) {
		this.dataResult = dataResult;
	}

	public boolean isEmptyFile() {
		return isEmptyFile;
	}

	public void setEmptyFile(boolean isEmptyFile) {
		this.isEmptyFile = isEmptyFile;
	}

	public boolean isOverLimit() {
		return isOverLimit;
	}

	public void setOverLimit(boolean isOverLimit) {
		this.isOverLimit = isOverLimit;
	}

	public boolean isGlobalPass() {
		return globalPass;
	}

	public void setGlobalPass(boolean globalPass) {
		this.globalPass = globalPass;
	}
	
}

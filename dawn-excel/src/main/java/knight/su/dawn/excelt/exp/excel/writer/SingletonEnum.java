package knight.su.dawn.excelt.exp.excel.writer;

/**
 * 
 * Created by sugengbin 2019/02/21
 */
public enum SingletonEnum {

	WORK_BOOK_BUILDER;
	private WorkbookBuilder instance;

	private SingletonEnum() {
		instance = new WorkbookBuilder();
	}

	public WorkbookBuilder getInstance() {
		return instance;
	}
}

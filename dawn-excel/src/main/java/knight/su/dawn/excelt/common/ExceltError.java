package knight.su.dawn.excelt.common;
/**
 * 
 * Created by sugengbin 2019/04/11
 */
public class ExceltError {

	public static final String EMPTY_FILE_CODE = "EMPTY_FILE";
	public static final String EMPTY_FILE = "文件内容为空";
	
	public static final String OVER_LIMIT_CODE = "OVER_LIMIT";
	public static final String OVER_LIMIT = "文件记录超过限制%s";
	
	public static final String WRONG_TEMPLATE_CODE = "WRONG_TEMPLATE";
	public static final String WRONG_TEMPLATE =  "导入表格和系统配置模版不一致,请下载最新模版";
	public static final String WRONG_TEMPLATE_FORMAT =  "导入表格第%s列和系统配置模版[%s]不一致,请下载最新模版";
	public static final String WRONG_TEMPLATE_COL_NUM =  "导入表格列数与系统配置模板不一致，请下载最新模版";
}

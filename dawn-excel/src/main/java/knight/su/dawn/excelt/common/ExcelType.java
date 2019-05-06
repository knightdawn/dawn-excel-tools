package knight.su.dawn.excelt.common;

/**
 *
 * Date: 2019/1/4<br/>
 * 
 * @author sugengbin
 */
public enum ExcelType {

	FLUSH_XLSX(".xlsx.flush"),
	XLSX(".xlsx"), 
	XLS(".xls"),
	NULL("");
	private String extension;

	private ExcelType(String extension) {
		this.extension = extension;
	}

	public static ExcelType whichType(String extension) {
		ExcelType[] types = ExcelType.values();
		for (ExcelType type : types) {
			if (type.getExtension().equals(extension)) {
				return type;
			}
		}
		return NULL;
	}

	public String getExtension() {
		return extension;
	}

}

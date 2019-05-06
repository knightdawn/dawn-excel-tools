package knight.su.dawn.excelt.exp.option;

import knight.su.dawn.excelt.common.ExceltConstants;
import knight.su.dawn.excelt.util.FileUtil;

import java.io.File;
import java.util.Arrays;

/**
 * Created by sugengbin 2019/03/22
 */
public interface WriterZipOp {

	/**
	 * 
	 * @param isZip
	 * @param file
	 */
	default void beforeZip(boolean isZip, File file) {

	}

	/**
	 * 
	 * @param isZip
	 * @param file
	 */
	default void zip(boolean isZip, File file) {
		if (isZip) {
			String zipFilePath = FileUtil.rmExtension(file.getAbsolutePath()) + ExceltConstants.ZIP;
			FileUtil.zip(Arrays.asList(file), zipFilePath);
		}
	}

	/**
	 * 
	 * @param isZip
	 * @param sourceFile
	 */
	default void afterZip(boolean isZip, File sourceFile) {
		if (isZip && sourceFile.exists()) {
			sourceFile.delete();
		}
	}
}

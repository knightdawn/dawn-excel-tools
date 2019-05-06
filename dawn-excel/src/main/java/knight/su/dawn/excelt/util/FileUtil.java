package knight.su.dawn.excelt.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Date: 2019/1/4<br/>
 * 
 * @author sugengbin
 */
public class FileUtil {
	


	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static final String APPLICATION_X_XLS_TEMPLATE = "application/x-xls";
	public static final String CONTENT_DISPOSITION = "Content-disposition";
	public static final String ATTACHMENT_FILENAME = "attachment;filename=";
	public static final String DATESTR = "datestr";
	public static final String GB2312 = "gb2312";
	public static final String ISO8859_1 = "ISO8859-1";

	private FileUtil() {
		super();
	}

	/**
	 * 备份文件
	 * 
	 * @param fileName
	 * @param bytes
	 * @return
	 * @throws
	 * @throws IOException
	 */
	public static File bakup(String fileName, byte[] bytes) throws IOException {
		File file = new File(fileName);
		FileOutputStream fos = null;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			BufferedOutputStream out = new BufferedOutputStream(fos);
			out.write(bytes);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("backup error:{}", e);
			throw e;
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		return file;
	}

	/**
	 * 解压zip文件
	 * 
	 * @param sourceFile
	 * @param destDir
	 * @throws
	 */
	public static void unzip(File sourceFile, String destDir) {
		logger.info("unzip file: {} to {}", sourceFile.getName(), destDir);
		try {
			unzip(new FileInputStream(sourceFile), destDir);
		} catch (Exception e) {
			logger.error("文件解压失败", e);
		}
	}

	/**
	 * 解压zip文件
	 * 
	 * @param fileName
	 * @param destDir
	 * @throws
	 * @throws IOException
	 */
	public static void unzip(String fileName, String destDir) throws IOException {
		logger.info("unzip file: {} to {}", fileName, destDir);
		unzip(new FileInputStream(fileName), destDir);
	}

	/**
	 * 解压zip文件
	 * 
	 * @param fileInStream
	 * @param destDir
	 * @throws
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void unzip(FileInputStream fileInStream, String destDir) throws IOException {
		ZipInputStream zinStream = new ZipInputStream(fileInStream, Charset.forName("UTF-8"));// GB2312
		ZipEntry entry = null;
		FileOutputStream fileOutStream = null;
		while ((entry = zinStream.getNextEntry()) != null) {
			if (entry.isDirectory()) {
				logger.info("创建子目录: {}", entry.getName());
				new File(entry.getName()).mkdir();
			} else {
				logger.info("解压文件:{}", entry.getName());
				File destFile = new File(destDir, entry.getName());
				if (!destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				destFile.createNewFile();
				fileOutStream = new FileOutputStream(destFile);
				byte[] buf = new byte[2048];
				int len = 0;
				while ((len = zinStream.read(buf, 0, buf.length)) != -1) {
					fileOutStream.write(buf, 0, len);
				}
				fileOutStream.close();
			}
			zinStream.closeEntry();
		}
		zinStream.close();
	}

	/**
	 * 添加多个文件到压缩文件中
	 * 
	 * @param fileList
	 * @param zipFilePath
	 */
	public static void zip(List<File> fileList, String zipFilePath) {
		File zipFile = new File(zipFilePath);
		if (zipFile.exists()) {
			zipFile.delete();
		}
		FileOutputStream fileOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		FileInputStream fileInputStream = null;
		try {
			fileOutputStream = new FileOutputStream(zipFilePath);
			zipOutputStream = new ZipOutputStream(fileOutputStream);
			for (File file : fileList) {
				fileInputStream = new FileInputStream(file);
				zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = fileInputStream.read(buf)) > 0) {
					zipOutputStream.write(buf, 0, bytesRead);
				}
				fileInputStream.close();
			}
			zipOutputStream.closeEntry();
		} catch (IOException e) {
			logger.error("zip file error", e);
		} finally {
			try {
				if (zipOutputStream != null) {
					zipOutputStream.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFile(File source, File dest) throws IOException {
		copyFileUsingFileStreams(source, dest);
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingFileStreams(File source, File dest) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.error("文件复制异常", e);
		} finally {
			input.close();
			output.close();
		}
	}


	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		if (i >= 0) {
			return fileName.substring(i);
		}
		return "";
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String rmExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		if (i >= 0) {
			return fileName.substring(0, i);
		}
		return "";
	}
}

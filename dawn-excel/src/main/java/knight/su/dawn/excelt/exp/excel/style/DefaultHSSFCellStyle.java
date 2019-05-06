package knight.su.dawn.excelt.exp.excel.style;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * Created by sugengbin 2019/3/03
 */
public class DefaultHSSFCellStyle {

	/**
	 * 
	 * @param sworkBook
	 * @return
	 */
	public static CellStyle defaultTitleStyle(Workbook workBook) {
		CellStyle titleCellStyle = workBook.createCellStyle();
		DataFormat df = workBook.createDataFormat();
		titleCellStyle.setDataFormat(df.getFormat("@"));
		Font defont = workBook.createFont();
		defont.setFontName("宋体");
		defont.setFontHeightInPoints((short) 10);// 10号字体
		defont.setBold(true);// 加粗
		titleCellStyle.setFont(defont);
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
		titleCellStyle.setBorderTop(BorderStyle.THIN);
		titleCellStyle.setBorderLeft(BorderStyle.THIN);
		titleCellStyle.setBorderBottom(BorderStyle.THIN);
		titleCellStyle.setBorderRight(BorderStyle.THIN);
		titleCellStyle.setWrapText(true); // 单元格自动换行
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
		titleCellStyle.setFillBackgroundColor(HSSFColorPredefined.WHITE.getIndex());
		return titleCellStyle;
	}

	/**
	 * 
	 * @param sworkBook
	 * @return
	 */
	public static CellStyle defaultBodyStyle(Workbook sworkBook) {
		CellStyle bodyCellStyle = sworkBook.createCellStyle();
		DataFormat df = sworkBook.createDataFormat();
		bodyCellStyle.setDataFormat(df.getFormat("@"));
		Font defont = sworkBook.createFont();
		defont.setFontName("宋体");
		defont.setFontHeightInPoints((short) 10);// 10号字体
		bodyCellStyle.setFont(defont);
		bodyCellStyle.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
		bodyCellStyle.setBorderTop(BorderStyle.THIN);
		bodyCellStyle.setBorderLeft(BorderStyle.THIN);
		bodyCellStyle.setBorderBottom(BorderStyle.THIN);
		bodyCellStyle.setBorderRight(BorderStyle.THIN);
		bodyCellStyle.setWrapText(false); // 单元格自动换行
		bodyCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
		bodyCellStyle.setFillBackgroundColor(HSSFColorPredefined.WHITE.getIndex());
		return bodyCellStyle;
	}
}

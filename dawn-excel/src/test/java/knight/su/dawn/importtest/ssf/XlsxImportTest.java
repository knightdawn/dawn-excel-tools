package knight.su.dawn.importtest.ssf;

import knight.su.dawn.core.util.DateUtils;
import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.result.CellError;
import knight.su.dawn.excelt.imp.result.ImpResult;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by sugengbin 2019/03/23
 */
public class XlsxImportTest {
	
	@Test
	public void test()  throws IOException {
		TickTockUtil tick = new TickTockUtil("", 0);
		ParserOption parserOption = new ParserOption();
		parserOption.registerConvert("d", value -> String.format("%s %s", DateUtils.format(DateUtils.now(), DateUtils.PATTERN_YMD), value))
					.setGlobalField("cityCode", "755");
		Validation validation = new Validation();
		validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/test.yml"))));
		File file = Paths.get("src/test/resources/import/testXlsxFile.xlsx").toFile();
		ImpResult<Schedule> impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		List<Schedule> list = impResult.getDataResult();
		list.forEach(System.out::println);
		List<CellError> errors = impResult.getErrors();
		errors.forEach(System.out::println);
		tick.clock("over");
	}
	
//	@Test
//	public void test()  throws IOException {
//		TickTockUtil tick = new TickTockUtil("", 0);
//		ParserOption parserOption = new ParserOption();		
//		parserOption.registerConvert("d", value -> String.format("%s %s", DateUtils.format(DateUtils.now(), DateUtils.PATTERN_YMD), value))
//					.setGlobalField("cityCode", "755");
//		Validation validation = new Validation();
//		validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/large_aviator.yml"))));
//		File file = Paths.get("d:\\user\\449632\\桌面\\运单测试\\运单3.xlsx").toFile();
//		ImpResult<Schedule> impResult = Excelt.read(file, Schedule.class, parserOption, validation);
//		List<Schedule> list = impResult.getDataResult();
//		list.forEach(System.out::println);
//		List<CellError> errors = impResult.getErrors();
//		errors.forEach(System.out::println);
//		tick.clock("over");
//	}
}

package knight.su.dawn.importtest.ssf;

import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.result.ImpResult;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/17
 */
public class SsfFeildMapConfigTest {

	@Test
	public void test() {
		ParserOption parserOption = new ParserOption();
		parserOption.mapColField("A:a,B:b,D:d");
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ImpResult<ScheduleConfig> impResult = Excelt.read(file, ScheduleConfig.class, parserOption, Validation.defaultSetting());
		impResult.getDataResult().forEach(System.out::println);
		impResult.getErrors().forEach(System.out::println);
		
		System.out.println("field_config1 -> field_config2");
		parserOption.mapColField("A:a,B:b,C:d");
		parserOption.setGlobalField("cityCode", "755");
		file = Paths.get("src/test/resources/import/ssf/field_config2.xlsx").toFile();
		impResult = Excelt.read(file, ScheduleConfig.class, parserOption, Validation.defaultSetting());
		impResult.getDataResult().forEach(System.out::println);
		impResult.getErrors().forEach(System.out::println);
	}
	
	@Test
	public void testType() {
		ParserOption parserOption = new ParserOption();
		parserOption.mapColField("A:a,B:b,D:d").isXlsxFile();
		File file = Paths.get("src/test/resources/import/ssf/field_config1.tmp").toFile();
		ImpResult<ScheduleConfig> impResult = Excelt.read(file, ScheduleConfig.class, parserOption, Validation.defaultSetting());
		impResult.getDataResult().forEach(System.out::println);
		impResult.getErrors().forEach(System.out::println);
	}
}

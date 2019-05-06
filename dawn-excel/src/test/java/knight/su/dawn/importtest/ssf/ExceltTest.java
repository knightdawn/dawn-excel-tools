package knight.su.dawn.importtest.ssf;

import knight.su.dawn.core.util.DateUtils;
import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.convert.DefaultConvertion;
import knight.su.dawn.excelt.imp.result.CellError;
import knight.su.dawn.excelt.imp.result.ImpResult;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author sugengbin
 * @Date 2019/1/11
 */
public class ExceltTest {


	@Test
	public void test() {
		File file = new File("src/test/resources/import/test.xlsx");
		ImpResult<Schedule> impResult = Excelt.read(file, Schedule.class, Validation.defaultSetting());
		// 数据
		impResult.getDataResult().forEach(System.out::println);
		// 错误
		impResult.getErrors().forEach(System.out::println);
	}
	
//	@Test
//	public void testExcel2() throws IOException {
//		Validation validation = new Validation();
//		validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/test.yml"))));
//		File file = new File("/Users/sugengbin/work_services/tmp/班次信息表.xls");
//		ImpResult<Schedule> impResult = Excelt.read(file, Schedule.class, validation);
//		List<Schedule> list = impResult.getDataResult();
//		list.forEach(System.out::println);
//		List<CellError> errors = impResult.getErrors();
//		errors.forEach(System.out::println);
//	}
	
	@Test
	public void testRegisterConvert() throws IOException {
		ParserOption parserOption = new ParserOption();
		parserOption.registerConvert(new DefaultConvertion() {
			@Override
			public Object afterTypeMatch(Object value) {
				return String.format("%s %s", DateUtils.format(DateUtils.now(), DateUtils.PATTERN_YMD), value);
			}
			@Override
			public String field() {
				return "d";
			}
		}).setGlobalField("cityCode", "755");
		Validation validation = new Validation();
		validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/test.yml"))));
		File file = Paths.get("src/test/resources/import/testRegisterConvert.xls").toFile();
		ImpResult<Schedule> impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		List<Schedule> list = impResult.getDataResult();
		list.forEach(System.out::println);
//		assertThat(list.size()).isEqualTo(2);
		
		List<CellError> errors = impResult.getErrors();
//		assertThat(errors.size()).isZero();
		errors.forEach(System.out::println);
	}
	
	@Test
	public void testExcel3() throws IOException {
		ParserOption parserOption = new ParserOption();
//		parserOption.registerConvert(new DefaultConvertion() {
//			@Override
//			public Object afterTypeMatch(Object value) {
//				return String.format("%s %s", DateUtils.format(DateUtils.now(), DateUtils.PATTERN_YMD), value);
//			}
//			@Override
//			public String field() {
//				return "d";
//			}
//		}).setGlobalField("cityCode", "755");
		
		parserOption.registerConvert("d", value -> String.format("%s %s", DateUtils.format(DateUtils.now(), DateUtils.PATTERN_YMD), value))
					.setGlobalField("cityCode", "755");
		// 怎么验证模板准确性
		// 1、表头验证
		// 2、sheet名称验证
		// 
		Validation validation = new Validation();
		validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/test.yml"))));
		File file = Paths.get("src/test/resources/import/test.xlsx").toFile();
		ImpResult<Schedule> impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		List<Schedule> list = impResult.getDataResult();
		list.forEach(System.out::println);
		List<CellError> errors = impResult.getErrors();
		errors.forEach(System.out::println);
	}
}

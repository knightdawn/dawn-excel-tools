package knight.su.dawn.importtest.ssf;

import knight.su.dawn.core.util.DateUtils;
import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.common.LogicalOp;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sugengbin 2019/03/23
 */
public class ValidateTemplateTest {

	@Test
	public void test() throws IOException {
		ParserOption parserOption = new ParserOption();
		parserOption.registerConvert("d",
				value -> String.format("%s %s", DateUtils.format(DateUtils.now(), DateUtils.PATTERN_YMD), value))
				.setGlobalField("cityCode", "755");
		
		// 表头校验 失败
		Validation validation = buildValidation()
				.validateTitles("a", "b", "c", "d", "f");
		File file = Paths.get("src/test/resources/import/testXlsxFile.xlsx").toFile();
		ImpResult<Schedule> impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		List<Schedule> list = impResult.getDataResult();
		list.forEach(System.out::println);
		List<CellError> errors = impResult.getErrors();
		errors.forEach(System.out::println);
		assertThat(impResult.isRightTemplate()).isFalse();

		// 表头校验 成功
		validation = buildValidation()
				.validateTitles("a", "b", "c", "d", "e");
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isTrue();

		// sheet名校验 失败
		validation = buildValidation()
				.validateSheet("SheetTemplate1");
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isFalse();
		
		// sheet名校验 成功
		validation = buildValidation()
				.validateSheet("SheetTemplate");
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isTrue();
		
		// 表头校验、sheet名校验 OR 失败
		validation = buildValidation()
				.validateTitles("a", "b", "123", "d", "e")
				.validateSheet("SheetTemplate1");
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isFalse();
		
		// 表头校验、sheet名校验 AND 失败
		validation = buildValidation()
				.validateTitles("a", "b", "123", "d", "e")
				.validateSheet("SheetTemplate")
				.setValidateFileLogical(LogicalOp.AND);
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isFalse();
		
		// 表头校验、sheet名校验 AND 失败
		validation = buildValidation()
				.validateTitles("a", "b", "c", "d", "e")
				.validateSheet("SheetTemplate1")
				.setValidateFileLogical(LogicalOp.AND);
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isFalse();
		
		// 表头校验、sheet名校验 AND 失败
		validation = buildValidation()
				.validateTitles("a1", "b1", "c1", "d", "e")
				.validateSheet("SheetTemplate1")
				.setValidateFileLogical(LogicalOp.AND);
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isFalse();
		
		// 表头校验、sheet名校验 AND 成功
		validation = buildValidation()
				.validateTitles("a", "b", "c", "d", "e")
				.validateSheet("SheetTemplate")
				.setValidateFileLogical(LogicalOp.AND);
		impResult = Excelt.read(file, Schedule.class, parserOption, validation);
		assertThat(impResult.isRightTemplate()).isTrue();
	}

	private Validation buildValidation() throws IOException {
		Validation validation = new Validation();
		validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/test.yml"))));
		return validation;
	}
}

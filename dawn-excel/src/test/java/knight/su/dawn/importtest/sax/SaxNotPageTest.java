package knight.su.dawn.importtest.sax;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.excelt.imp.result.ImpResult;
import knight.su.dawn.importtest.sax.vo.RisPublication;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sugengbin 2019/03/23
 */
public class SaxNotPageTest {

	@Test
	public void test() throws Exception {
		File file = Paths.get("src/test/resources/import/notPage.xlsx").toFile();
		ImpResult<RisPublication> result = new SaxMappingParser(file, ParserOption.defaultSetting(), Validation.defaultSetting())
				.parser(RisPublication.class);
		result.getDataResult().forEach(System.out::println);
		result.getErrors().forEach(System.out::println);
		assertThat(result.getDataResult().size()).isEqualTo(3);
		assertThat(result.getErrors().size()).isEqualTo(0);
	}
	
	@Test
	public void testConvertion() throws Exception {
		File file = Paths.get("src/test/resources/import/notPage.xlsx").toFile();
		ParserOption option = ParserOption.defaultSetting();
		option.registerConvert("d", value -> String.format("%s %s", "2019-04-18", value));
		ImpResult<RisPublication> result = new SaxMappingParser(file, option, Validation.defaultSetting())
				.parser(RisPublication.class);
		result.getDataResult().forEach(System.out::println);
		result.getErrors().forEach(System.out::println);
		assertThat(result.getDataResult().size()).isEqualTo(3);
		assertThat(result.getDataResult().get(0).getD()).isEqualTo("2019-04-18 20:55:00");
		assertThat(result.getErrors().size()).isEqualTo(0);
	}
	
	@Test
	public void testValidate() throws Exception {
		File file = Paths.get("src/test/resources/import/notPage.xlsx").toFile();
		Validation validation = Validation.defaultSetting();
		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/notPage.yml"))));
		ImpResult<RisPublication> result = new SaxMappingParser(file, ParserOption.defaultSetting(), validation)
				.parser(RisPublication.class);
		result.getDataResult().forEach(System.out::println);
		result.getErrors().forEach(System.out::println);
//		assertThat(result.getDataResult().size()).isEqualTo(2);
//		assertThat(result.getErrors().size()).isEqualTo(1);
	}
	
	@Test
	public void testDefaultValue() throws Exception {
		File file = Paths.get("src/test/resources/import/notPage.xlsx").toFile();
		ParserOption option = ParserOption.defaultSetting();
		option.setGlobalField("cityCode", "755");
		ImpResult<RisPublication> result = new SaxMappingParser(file, option, Validation.defaultSetting())
				.parser(RisPublication.class);
		result.getDataResult().forEach(System.out::println);
		result.getErrors().forEach(System.out::println);
		assertThat(result.getDataResult().size()).isEqualTo(3);
		assertThat(result.getErrors().size()).isEqualTo(0);
	}
}

package knight.su.dawn.importtest.sax;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.ssf.ScheduleConfig;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/17
 */
public class SaxFieldMapConfigTest {

	@Test
	public void test() throws Exception {
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ParserOption options = new ParserOption()
				.setSaxBatch(2000)
				.mapColField("A:a,B:b,D:d");
		Validation validation = new Validation();
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			System.out.println(result.getDataResult().size());
			System.out.println(result.getErrors().size());
			Assertions.assertThat(result.getDataResult().get(0).getD()).isEqualTo("20:55:00");
			Assertions.assertThat(result.getDataResult().size()).isEqualTo(6);
			Assertions.assertThat(result.getErrors().size()).isEqualTo(0);
			return true;
		});
		
		System.out.println("field_config1 -> field_config2");
		
		options.mapColField("A:a,B:b,C:d");
		file = Paths.get("src/test/resources/import/ssf/field_config2.xlsx").toFile();
		parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			System.out.println(result.getDataResult().size());
			System.out.println(result.getErrors().size());
			Assertions.assertThat(result.getDataResult().get(0).getD()).isEqualTo("20:55:00");
			Assertions.assertThat(result.getDataResult().size()).isEqualTo(6);
			Assertions.assertThat(result.getErrors().size()).isEqualTo(0);
			return true;
		});
	}
}

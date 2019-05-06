package knight.su.dawn.importtest.sax;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.TestWaybill;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/12
 */
public class SaxConvertionTest {

	@Test
	public void test() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/3.xlsx").toFile();
		ParserOption options = new ParserOption()
				.setSaxBatch(2000)
				.registerConvert("transitDepotOrDeptCode", value -> StringUtils.isBlank((String)value) ? "NULL" : value);
		Validation validation = new Validation();
		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(TestWaybill.class, result -> {
			System.out.println(result.getDataResult().size());
			System.out.println(result.getErrors().size());
			Assertions.assertThat(result.isEmptyFile()).isFalse();
			result.getDataResult().forEach(System.out::println);
			Assertions.assertThat(result.getDataResult().get(0).getTransitDepotOrDeptCode()).isEqualTo("NULL");
			return true;
		});
	}
}

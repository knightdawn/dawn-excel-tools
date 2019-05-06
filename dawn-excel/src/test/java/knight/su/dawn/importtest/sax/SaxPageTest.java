package knight.su.dawn.importtest.sax;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.RisPublication;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/03/23
 */
public class SaxPageTest {

	@Test
	public void test() throws Exception {
		File file = Paths.get("src/test/resources/import/page.xlsx").toFile();
		ParserOption option = ParserOption.defaultSetting();
		option.setSaxBatch(2);
		SaxMappingParser parser = new SaxMappingParser(file, option, Validation.defaultSetting());
		parser.parser(RisPublication.class, result -> {
			System.out.println("do page action ...");
			result.getDataResult().forEach(System.out::println);
			result.getErrors().forEach(System.out::println);
			return true;
		});
	}

}

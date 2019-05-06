package knight.su.dawn.importtest.sax;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.RisPublication;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/08
 */
public class SaxValidateTest {

	@Test
	public void test() throws Exception {
		TickTockUtil tick = new TickTockUtil("", 0);
		File file = Paths.get("src/test/resources/import/sax/rule.xlsx").toFile();
		Validation validation = new Validation();
		validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/test.yml"))));
		SaxMappingParser parser = new SaxMappingParser(file, new ParserOption(), validation);
		parser.parser(RisPublication.class, result -> {
			result.getDataResult().forEach(System.out::println);
			System.out.println(result.getDataResult().size());
			result.getErrors().forEach(System.out::println);
			System.out.println(result.getErrors().size());
			tick.clock("tick");
			return true;
		});
		tick.clock("over");
	}
}

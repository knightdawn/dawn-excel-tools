package knight.su.dawn.importtest.sax;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.TestColLetterVo;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/08
 */
public class SaxColLetterTest {

	@Test
	public void testSplitPart() throws Exception {
		TickTockUtil tick = new TickTockUtil("", 0);
		File file = Paths.get("src/test/resources/import/sax/test.xlsx").toFile();
		SaxMappingParser parser = new SaxMappingParser(file, new ParserOption(), new Validation());
		parser.parser(TestColLetterVo.class, result -> {
			result.getDataResult().forEach(System.out::println);
			System.out.println(result.getDataResult().size());
			tick.clock("tick");
			return true;
		});
		tick.clock("over");
	}
}

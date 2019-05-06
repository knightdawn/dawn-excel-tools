package knight.su.dawn.importtest.sax;

import knight.su.dawn.core.util.DateUtils;
import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.RisPublication;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/08
 */
public class SaxConvertTest {

	
	@Test
	public void test() throws Exception {
		TickTockUtil tick = new TickTockUtil("", 0);
		File file = Paths.get("src/test/resources/import/sax/test.xlsx").toFile();
		ParserOption options = new ParserOption();
		options.registerConvert("d", value -> String.format("%s %s", DateUtils.format(DateUtils.now(), DateUtils.PATTERN_YMD), value))
			   .setGlobalField("cityCode", "755");
		SaxMappingParser parser = new SaxMappingParser(file, options, new Validation());
		parser.parser(RisPublication.class, result -> {
			result.getDataResult().forEach(System.out::println);
			System.out.println(result.getDataResult().size());
			tick.clock("tick");
			return true;
		});
		tick.clock("over");
	}
}

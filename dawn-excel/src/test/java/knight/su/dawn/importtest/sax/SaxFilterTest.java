package knight.su.dawn.importtest.sax;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.imp.config.Condition;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.TestColLetterVo;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/18
 */
public class SaxFilterTest {

	@Test
	public void test1() throws Exception {
		TickTockUtil tick = new TickTockUtil("1", 0);
		File file = Paths.get("src/test/resources/import/sax/test.xlsx").toFile();
		Condition<TestColLetterVo> filter = new Condition<>();
		filter.register(t -> "派件".equals(t.getC())); // 只保留满足派件信息的
		new SaxMappingParser(file, ParserOption.defaultSetting(), Validation.defaultSetting(), filter)
				.parser(TestColLetterVo.class, result -> {
					result.getDataResult().forEach(System.out::println);
					return true;
				});
		tick.clock("over");
	}
	
	@Test
	public void test2() throws Exception {
		TickTockUtil tick = new TickTockUtil("2", 0);
		File file = Paths.get("src/test/resources/import/sax/test.xlsx").toFile();
		new SaxMappingParser(file, ParserOption.defaultSetting(), Validation.defaultSetting())
				.parser(TestColLetterVo.class, result -> {
					result.getDataResult().forEach(System.out::println);
					return true;
				});
		tick.clock("over");
	}
}

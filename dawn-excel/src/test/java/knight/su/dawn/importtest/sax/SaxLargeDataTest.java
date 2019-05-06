package knight.su.dawn.importtest.sax;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.TestLargeDataVo;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/08
 */
public class SaxLargeDataTest {

	@Test
	public void test() {
		try {
			TickTockUtil tick = new TickTockUtil("", 0);
			// d:\\user\\449632\\桌面\\运单测试\\运单2.xlsx
			File file = Paths.get("d:\\user\\449632\\桌面\\运单测试\\运单2.xlsx").toFile();
			Validation validation = new Validation();
//			validation.validate(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/large.yml"))));
			SaxMappingParser parser = new SaxMappingParser(file, new ParserOption(), validation);
			parser.parser(TestLargeDataVo.class, result -> {
//				result.getDataResult().forEach(System.out::println);
				System.out.println(result.getDataResult().size());
				tick.clock("tick");
				return true;
			});
			tick.clock("over");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

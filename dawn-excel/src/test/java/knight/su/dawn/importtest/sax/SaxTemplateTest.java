package knight.su.dawn.importtest.sax;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.TestWaybill;
import knight.su.dawn.importtest.ssf.ScheduleConfig;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/12
 */
public class SaxTemplateTest {

	@Test
	public void testCorrect() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/3.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000);
		Validation validation = new Validation()
				.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))))
				.validateTitles(
						"运单号（必填）,始发地网点代码,始发地单元区域代码,始发地城市代码,始发地区部代码,目的地网点代码,目的地单元区域代码,目的地城市代码,目的地区部代码,散货中转场/网点代码,日期,可发车/装车时间（必填）\n（00:00:00）,重量（必填）,产品类型,集/散（必填）"
								.split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(TestWaybill.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isTrue();
			System.out.println("testCorrect:" + result.getErrorMsg());
			return true;
		});
	}

	@Test
	public void testInCorrect() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/3.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000);
		Validation validation = new Validation()
				.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))))
				.validateTitles("".split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(TestWaybill.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isFalse();
			System.out.println("testInCorrect:" + result.getErrorMsg());
			return true;
		});
	}

	@Test
	public void testNotVtfTrue() throws Exception {
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000).mapColField("A:a,B:b,D:d");
		Validation validation = new Validation();
		validation.validateTitles("a,b,c,d,e".split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isTrue();
			System.out.println("testNotVtfTrue:" + result.getErrorMsg());
			return true;
		});
	}
	
	@Test
	public void testNotVtfFalse() throws Exception {
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000).mapColField("A:a,B:b,D:d");
		Validation validation = new Validation();
		validation.validateTitles("a,b,c,d".split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isFalse();
			System.out.println("testNotVtfFalse:" + result.getErrorMsg());
			return true;
		});
	}
	
	@Test
	public void testVtfFalse() throws Exception {
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000).mapColField("A:a,B:b,D:d");
		Validation validation = new Validation();
		validation.validateTemplateWithField();
		validation.validateTitles("a,b,c,d".split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isFalse();
			System.out.println("testVtfFalse:" + result.getErrorMsg());
			return true;
		});
	}
	
	@Test
	public void testVtfTrue() throws Exception {
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000).mapColField("A:a,B:b,D:d");
		Validation validation = new Validation();
		validation.validateTemplateWithField();
		validation.validateTitles("a,b,d".split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isTrue();
			System.out.println("testVtfTrue:" + result.getErrorMsg());
			return true;
		});
	}
	
	@Test
	public void testVtfSeqFalse() throws Exception {
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000).mapColField("A:a,B:b,D:d");
		Validation validation = new Validation();
		validation.validateTemplateWithField();
		validation.validateTitles("a,d,b".split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isFalse();
			System.out.println("testVtfSeqFalse:" + result.getErrorMsg());
			return true;
		});
	}
	
	@Test
	public void testVtfSeqFalse1() throws Exception {
		File file = Paths.get("src/test/resources/import/ssf/field_config1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000).mapColField("A:a,B:b,D:d");
		Validation validation = new Validation();
		validation.validateTemplateWithField();
		validation.validateTitles("a,b".split(","));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(ScheduleConfig.class, result -> {
			Assertions.assertThat(result.isRightTemplate()).isFalse();
			System.out.println("testVtfSeqFalse1:" + result.getErrorMsg());
			return true;
		});
	}
}

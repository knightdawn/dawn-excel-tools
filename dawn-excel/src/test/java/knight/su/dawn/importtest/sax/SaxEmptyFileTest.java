package knight.su.dawn.importtest.sax;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.excelt.imp.result.ImpResult;
import knight.su.dawn.importtest.sax.vo.TestWaybill;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/12
 */
public class SaxEmptyFileTest {
	
	// 空文件
	@Test
	public void test1() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000);
		Validation validation = new Validation();
		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(TestWaybill.class, result -> {
			System.out.println(result.getDataResult().size());
			System.out.println(result.getErrors().size());
			Assertions.assertThat(result.isEmptyFile()).isTrue();
			return true;
		});
	}
	
	//  test1 全读
	@Test
	public void test11() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/1.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000);
		Validation validation = new Validation();
		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		ImpResult<TestWaybill> result = parser.parser(TestWaybill.class);
		Assertions.assertThat(result.isEmptyFile()).isTrue();
		Assertions.assertThat(result.isGlobalPass()).isFalse();
	}
	
	// 只有表头的空文件
	@Test
	public void test2() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/2.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000);
		Validation validation = new Validation();
		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(TestWaybill.class, result -> {
			System.out.println(result.getDataResult().size());
			System.out.println(result.getErrors().size());
			Assertions.assertThat(result.isEmptyFile()).isTrue();
			return true;
		});
	}
	
//  test2 全读
	@Test
	public void test22() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/2.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000);
		Validation validation = new Validation();
		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		ImpResult<TestWaybill> result = parser.parser(TestWaybill.class);
		Assertions.assertThat(result.isEmptyFile()).isTrue();
		Assertions.assertThat(result.isGlobalPass()).isFalse();
	}
	
	// 有一条数据
	@Test
	public void test3() throws Exception {
		File file = Paths.get("src/test/resources/import/sax/3.xlsx").toFile();
		ParserOption options = new ParserOption().setSaxBatch(2000);
		Validation validation = new Validation();
		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
		parser.parser(TestWaybill.class, result -> {
			System.out.println(result.getDataResult().size());
			System.out.println(result.getErrors().size());
			Assertions.assertThat(result.isEmptyFile()).isFalse();
			return true;
		});
	}
	
}

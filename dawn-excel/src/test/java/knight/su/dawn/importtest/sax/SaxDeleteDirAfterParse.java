package knight.su.dawn.importtest.sax;

import knight.su.dawn.excelt.imp.config.ParserOption;
import knight.su.dawn.excelt.imp.config.Validation;
import knight.su.dawn.excelt.imp.parser.sax.SaxMappingParser;
import knight.su.dawn.importtest.sax.vo.TestWaybill;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by sugengbin 2019/04/12
 */
public class SaxDeleteDirAfterParse {

	@Test
	public void test() {
		try {
			File file = Paths.get("E:\\test\\bpSimWaybill\\sugengbin_test_waybill_id\\快件运单123.xlsx").toFile();
			ParserOption options = new ParserOption().setSaxBatch(2000);
			Validation validation = new Validation().validateAviator(
					new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
			SaxMappingParser parser = new SaxMappingParser(file, options, validation);
			parser.parser(TestWaybill.class, result -> {
				System.out.println(result.getDataResult().size());
				System.out.println(result.getErrors().size());
				Assertions.assertThat(result.isEmptyFile()).isFalse();
				return true;
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			File dir = Paths.get("E:\\test\\bpSimWaybill\\sugengbin_test_waybill_id\\").toFile();
			if (dir.exists()) {
				try {
					File[] files = dir.listFiles();
					for (File file : files) {
						file.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

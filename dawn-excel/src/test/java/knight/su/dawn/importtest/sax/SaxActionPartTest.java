package knight.su.dawn.importtest.sax;

import org.junit.Test;

/**
 * Created by sugengbin 2019/04/08
 */
public class SaxActionPartTest {

	@Test
	public void testSplitPart() throws Exception {
//		TickTockUtil all = new TickTockUtil("all", 0, false, SaxActionPartTest.class);
//		TickTockUtil tick = new TickTockUtil("", 0, false, SaxActionPartTest.class);
//		
//		// 初始化es集群配置，并获取es连接
////        EsClient.configure("bdp-es-orderwaybill", "10.202.116.33", 9300);
////        tick.clock("es configure");
//		File file = Paths.get("E:\\test\\bpSimWaybill\\sugengbin_test_waybill_id\\test2.xlsx").toFile();
//		ParserOption options = new ParserOption().setSaxBatch(2000);
//		Validation validation = new Validation();
//		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/bpSimWaybill.yml"))));
//		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
//		parser.parser(TestWaybill.class, result -> {
//			System.out.println(result.getDataResult().size());
//			System.out.println(result.getErrors().size());
//			result.getDataResult().forEach(System.out::println);
//			result.getErrors().forEach(System.out::println);
//			tick.clock("文件读取并校验非空");
////			EsCommonDao.bulkInsert(result.getDataResult());
////			try {
////				Thread.sleep(1000);
////			} catch (InterruptedException e) {
////			}
//			tick.clock("es insert");
//			return true;
//		});
//		all.clock("总耗时");
	}
	
//	@Test
//	public void testAll() throws Exception {
//		TickTockUtil all = new TickTockUtil("all", 0, false, SaxActionPartTest.class);
//		TickTockUtil tick = new TickTockUtil("", 0, false, SaxActionPartTest.class);
//		
//		// 初始化es集群配置，并获取es连接
//        EsClient.configure("bdp-es-orderwaybill", "10.202.116.33", 9300);
//        tick.clock("es configure");
//		File file = Paths.get("d:\\user\\449632\\桌面\\运单测试\\运单3.xlsx").toFile();
//		ParserOption options = new ParserOption();
//		Validation validation = new Validation();
//		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/large_aviator.yml"))));
//		SaxMappingParser parser = new SaxMappingParser(file, options, validation);
//		ImpResult<TestLargeDataVo> result = parser.parser(TestLargeDataVo.class);
//		tick.clock("全部读取文件");
//		List<List<TestLargeDataVo>> lists = Lists.partition(result.getDataResult(), 2000);
//		for (List<TestLargeDataVo> list : lists) {
//			EsCommonDao.bulkInsert(list);
//		}
//		tick.clock("es 写入");
//		all.clock("总耗时");
//	}
	
//	@Test
//	public void testOld() throws Exception {
//		TickTockUtil all = new TickTockUtil("all", 0, false, SaxActionPartTest.class);
//		TickTockUtil tick = new TickTockUtil("", 0, false, SaxActionPartTest.class);
//		
//		// 初始化es集群配置，并获取es连接
//        EsClient.configure("bdp-es-orderwaybill", "10.202.116.33", 9300);
//        tick.clock("es configure");
//		File file = Paths.get("d:\\user\\449632\\桌面\\运单测试\\运单3.xlsx").toFile();
//		ParserOption parserOption = new ParserOption();
//		Validation validation = new Validation();
//		validation.validateAviator(new String(Files.readAllBytes(Paths.get("src/test/resources/rule/large_aviator.yml"))));
//		ImpResult<TestLargeDataVo> result = Excelt.read(file, TestLargeDataVo.class, parserOption, validation);
//		tick.clock("全部读取文件");
//		List<List<TestLargeDataVo>> lists = Lists.partition(result.getDataResult(), 2000);
//		for (List<TestLargeDataVo> list : lists) {
//			EsCommonDao.bulkInsert(list);
//		}
//		tick.clock("es 写入");
//		all.clock("总耗时");
//	}
}

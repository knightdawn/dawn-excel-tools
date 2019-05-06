package knight.su.dawn.exporttest.serialize;

import org.junit.Test;

public class ExportTest1JsonSplit {

	// 版本1 ，内存消化500m, cpu高达60
	// 分sheet情况一样
	@Test
	public void testExport1() {
//		TickTockUtil tick = new TickTockUtil("", 0);
//		// 构造6W个复杂对象
//		List<ExportObj> list = new ArrayList<>();
//		IntStream.range(0, 60000).forEach(i -> {
//			list.add(ExportObj.build());
//		});
//		tick.clock("build");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		// 转成List<Map> 优化
//		String jsonData = JsonUtil.object2Json(list);
//		List<Map<String, Object>> data = JsonUtil.json2Object(jsonData, new TypeRef<List<Map<String, Object>>>() {
//		});
//		tick.clock("objsToListMap");
//		// 使用工具写入excel
//		File outputFile = new File("src/main/resources/text.xls");
//		FormatOption option = new FormatOption()
//				.template(new File("src/main/resources/1.xls"))
//				.intitle("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25")
//				.sortBy("taskNo", "createdTime", "bagSize = 0", "packageSize = 0", "ddsTaskType", "currentOperTime",
//						"currentRouteInfo", "taskDescBrief", "operateType", "isAbnormal", "abnormalType",
//						"handoverCode", "taskStartDept", "taskEndDept", "rider", "currProcessor", "riderReciveTime",
//						"timeLimitedDtail", "expectGetTime", "expectSignTime", "completeTime", "riderDeliveredTime",
//						"riderPickupTime", "capacity", "taskStatusDesc")
//				.startFrom(0).splitWhen(10000);
//		Excelt.buildWriter(outputFile, option).write(data);
//		tick.clock("write excel");
	}
	
}

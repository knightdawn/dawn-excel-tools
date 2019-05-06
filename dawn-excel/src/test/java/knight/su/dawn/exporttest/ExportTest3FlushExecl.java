package knight.su.dawn.exporttest;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.exp.excel.config.FormatOption;
import knight.su.dawn.excelt.exp.excel.writer.ExcelWriter;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExportTest3FlushExecl {
	
	// 版本3 1w分组 330m cpu 30
	@Test
	public void testExport2() {
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		TickTockUtil tick1 = new TickTockUtil("", 0);
//		TickTockUtil tick = new TickTockUtil("", 0);
////      构造Excel对象
//		int count = 0;
//		File output = new File("src/main/resources/text0419001.xlsx");
//		FormatOption option = new FormatOption()
//				.intitleBySplit("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25")
//				.sortBy("taskNo", "createdTime", "bagSize = 0", "packageSize = 0", "ddsTaskType", "currentOperTime",
//						"currentRouteInfo", "taskDescBrief", "operateType", "isAbnormal", "abnormalType",
//						"handoverCode", "taskStartDept", "taskEndDept", "rider", "currProcessor", "riderReciveTime",
//						"timeLimitedDtail", "expectGetTime", "expectSignTime", "completeTime", "riderDeliveredTime",
//						"riderPickupTime", "capacity", "taskStatusDesc")
//				.startFrom(0).flush().setRowAccessWindowSize(100).splitWhen(10001);
//		ExcelWriter writer = Excelt.buildWriter(output, option);
//		tick.clock("build writer");
//		do {
////		      1、构造1w个对象
//			List<ExportObj> list = new ArrayList<>();
//			for (int i = 0; i < 5000; i++) {
//				list.add(ExportObj.build(i));
//			}
//			tick.clock("build 1w obj");
//			count += list.size();
////		      2、转成List<Map>
//			List<Map<String, Object>> data = ExportUtil.objsToListMapReflect(list, ExportObj.class);
//			tick.clock("to List<Map");
////		      3、写入excel对象 
//			writer.write(data);
//			tick.clock("append");
//		} while (count < 60000);
//		writer.flush();
//		tick1.clock("整体耗时");
	}
	
	@Test
	public void testExport22() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TickTockUtil tick1 = new TickTockUtil("", 0);
		TickTockUtil tick = new TickTockUtil("", 0);
//      构造Excel对象
		File output = new File("src/main/resources/text419005.xlsx");
		FormatOption option = new FormatOption()
				.intitleBySplit("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25")
				.sortBy("taskNo", "createdTime", "bagSize = 0", "packageSize = 0", "ddsTaskType", "currentOperTime",
						"currentRouteInfo", "taskDescBrief", "operateType", "isAbnormal", "abnormalType",
						"handoverCode", "taskStartDept", "taskEndDept", "rider", "currProcessor", "riderReciveTime",
						"timeLimitedDtail", "expectGetTime", "expectSignTime", "completeTime", "riderDeliveredTime",
						"riderPickupTime", "capacity", "taskStatusDesc")
				.flush().setRowAccessWindowSize(100).splitWhen(10001);
		ExcelWriter writer = Excelt.buildWriter(output, option);
		tick.clock("build writer");
		writer.writeObj((index -> {
			System.out.println(index);
			List<ExportObj> list = new ArrayList<>();
			for (int i = 0; i < 5000; i++) {
				list.add(ExportObj.build(i));
			}
			if (index * 5000 > 60000) {
				return new ArrayList<>(); // return null;
			}
			return list;
		}), ExportObj.class);
		tick1.clock("整体耗时");
	}

}

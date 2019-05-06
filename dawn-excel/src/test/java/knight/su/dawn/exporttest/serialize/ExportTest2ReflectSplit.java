package knight.su.dawn.exporttest.serialize;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.exp.excel.config.FormatOption;
import knight.su.dawn.excelt.util.ExportUtil;
import knight.su.dawn.exporttest.ExportObj;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ExportTest2ReflectSplit {

	// 版本2，内存消化370m, cpu 50
	@Test
	public void testExport11() {
		TickTockUtil tick = new TickTockUtil("", 0);
		// 构造6W个复杂对象
		List<ExportObj> list = new ArrayList<>();
		IntStream.range(0, 60000).forEach(i -> {
			list.add(ExportObj.build());
		});
		tick.clock("build");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 转成List<Map> 优化
		List<Map<String, Object>> data = ExportUtil.objsToListMapReflect(list, ExportObj.class);
		tick.clock("objsToListMapV2");
		// 使用工具写入excel
		File output = new File("src/main/resources/text1.xls");
		FormatOption option = new FormatOption()
				.intitle("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25")
				.sortBy("taskNo", "createdTime", "bagSize = 0", "packageSize = 0", "ddsTaskType", "currentOperTime",
						"currentRouteInfo", "taskDescBrief", "operateType", "isAbnormal", "abnormalType",
						"handoverCode", "taskStartDept", "taskEndDept", "rider", "currProcessor", "riderReciveTime",
						"timeLimitedDtail", "expectGetTime", "expectSignTime", "completeTime", "riderDeliveredTime",
						"riderPickupTime", "capacity", "taskStatusDesc")
				.startFrom(0).splitWhen(20000);
		Excelt.write(output, option, data);
		tick.clock("write excel");
	}
}

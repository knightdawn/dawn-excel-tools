package knight.su.dawn.exporttest;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.exp.excel.config.FormatOption;
import knight.su.dawn.excelt.exp.excel.writer.ExcelWriter;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExportTest4Zip {

	@Test
	public void testExport22() {
		TickTockUtil tick1 = new TickTockUtil("", 0);
		TickTockUtil tick = new TickTockUtil("", 0);
		File output = new File("src/main/resources/text4.xlsx");
		FormatOption option = new FormatOption()
				.intitleBySplit("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25")
				.sortBy("taskNo", "createdTime", "bagSize = 0", "packageSize = 0", "ddsTaskType", "currentOperTime",
						"currentRouteInfo", "taskDescBrief", "operateType", "isAbnormal", "abnormalType",
						"handoverCode", "taskStartDept", "taskEndDept", "rider", "currProcessor", "riderReciveTime",
						"timeLimitedDtail", "expectGetTime", "expectSignTime", "completeTime", "riderDeliveredTime",
						"riderPickupTime", "capacity", "taskStatusDesc")
				.flush().setRowAccessWindowSize(100).toZip();
		ExcelWriter writer = Excelt.buildWriter(output, option);
		tick.clock("build writer");
		writer.writeObj((index -> {
			System.out.println(index);
			List<ExportObj> list = new ArrayList<>();
			for (int i = 0; i < 5000; i++) {
				list.add(ExportObj.build(i));
			}
			if (index * 5000 >= 60000) {
				return new ArrayList<>(); // return null;
			}
			return list;
		}), ExportObj.class);
		tick1.clock("整体耗时");
	}
}

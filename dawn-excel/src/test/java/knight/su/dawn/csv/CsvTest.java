package knight.su.dawn.csv;

import knight.su.dawn.excelt.Excelt;
import knight.su.dawn.excelt.exp.csv.config.CsvOption;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CsvTest {

	@Test
	public void testCsv() throws IOException {
		File file = new File("src/main/resources/1.csv");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		CsvOption option = new CsvOption().sortBy("taskNo", "createdTime", "ddsTaskType", "currentRouteInfo",
				"taskEndDept", "currentRouteInfo", "bagSize", "packageSize", "currentOperTime", "taskDescBrief",
				"operateType", "isAbnormal", "abnormalType", "handoverCode", "taskStartDept", "rider", "currProcessor",
				"riderReciveTime", "timeLimitedDtail", "expectGetTime", "expectSignTime", "completeTime",
				"riderDeliveredTime", "riderPickupTime", "capacity", "taskStatusDesc").toZip();
		Excelt.buildCsvWriter(file, option).write(t -> {
			List<CsvExportObj> list = new ArrayList<>();
			IntStream.range(0, 1000).forEach(i -> {
				list.add(CsvExportObj.build(i));
			});
			return list;
		}, CsvExportObj.class, 60);
	}
}

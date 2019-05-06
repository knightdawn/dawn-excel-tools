package knight.su.dawn.util;

import knight.su.dawn.core.util.TickTockUtil;
import knight.su.dawn.excelt.util.ExportUtil;
import knight.su.dawn.excelt.util.JsonUtil;
import knight.su.dawn.excelt.util.TypeRef;
import knight.su.dawn.exporttest.ExportObj;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ExportUtilTest {

	private static final Logger logger = LoggerFactory.getLogger(ExportUtilTest.class);
	
//	 更稳定，70-80ms内处理
	@Test
	public void testObjsToListMapV2() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<ExportObj> list = new ArrayList<>();
		IntStream.range(0, 60000).forEach(i -> {
			list.add(ExportObj.build());
		});
		TickTockUtil tickTock = new TickTockUtil("", 0);
		List<Map<String, Object>> listMap = ExportUtil.objsToListMapReflect(list, ExportObj.class);
		tickTock.clock("objsToListMapV2");
		logger.info(String.valueOf(listMap.size()));
		
//		03-08 16:51:52.172|INFO |main| c.s.d.c.u.TickTockUtil.log(TickTockUtil.java:101)|
//		03-08 16:51:52.245|INFO |main| c.s.d.c.u.TickTockUtil.log(TickTockUtil.java:101)|objsToListMapV2: 74毫秒
//		03-08 16:51:52.246|INFO |main| c.s.d.u.ExportUtilTest.testObjsToListMapV2(ExportUtilTest.java:35)|60000
	}
	
	// 测试继承的类
	@Test
	public void testObjsToListMapV22() {
		List<ExportObjExtend> list = new ArrayList<>();
		list.add(ExportObjExtend.build());
		TickTockUtil tickTock = new TickTockUtil("", 0);
		List<Map<String, Object>> listMap = ExportUtil.objsToListMapReflect(list, ExportObjExtend.class);
		tickTock.clock("testObjsToListMapV22");
		for (Map<String, Object> map : listMap) {
			logger.info(String.valueOf(map.get("base")));
			logger.info(String.valueOf(map.get("taskNo")));
		}
	}
	
	@Test
	public void testObjsToListMapV3() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<ExportObj> list = new ArrayList<>();
		IntStream.range(0, 60000).forEach(i -> {
			list.add(ExportObj.build());
		});
		TickTockUtil tickTock = new TickTockUtil("", 0);
		ExportUtil.objsToListMapJackson(list);
		tickTock.clock("objsToListMapV3");
		
//		03-08 16:10:11.259|INFO |main| c.s.d.c.u.TickTockUtil.log(TickTockUtil.java:101)|
//		03-08 16:10:11.508|INFO |main| c.s.d.c.u.TickTockUtil.log(TickTockUtil.java:101)|objsToListMapV3: 249毫秒
		// 峰值 20m左右
	}
	
	@Test
	public void testObjsToListMapV4() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<ExportObj> list = new ArrayList<>();
		IntStream.range(0, 60000).forEach(i -> {
			list.add(ExportObj.build());
		});
		TickTockUtil tickTock = new TickTockUtil("", 0);
		String jsonData = JsonUtil.object2Json(list);
		JsonUtil.json2Object(jsonData, new TypeRef<List<Map<String, Object>>>() {
		});
		tickTock.clock("objsToListMapV4");
//		03-08 16:15:34.769|INFO |main| c.s.d.c.u.TickTockUtil.log(TickTockUtil.java:101)|
//		03-08 16:15:35.188|INFO |main| c.s.d.c.u.TickTockUtil.log(TickTockUtil.java:101)|objsToListMapV4: 420毫秒
		
		// 峰值200M
	}
	
}

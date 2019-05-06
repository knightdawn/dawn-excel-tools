package knight.su.dawn.excelt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.stream.IntStream;

/**
 *
 * Date: 2019年3月7日<br/>
 * 
 * @author sugengbin
 */
public class CsvFileTest {

	public static void main(String[] args) {
		try (FileWriter fileWriter = new FileWriter("src/main/resources/1.csv", true);
			 BufferedWriter bw = new BufferedWriter(fileWriter);
			 PrintWriter out = new PrintWriter(bw)) {
			IntStream.range(0, 1000000).forEach(i -> {
//				String value = "测试1,测试2,测试3,测试4,测试5,测试6,测试7,测试8,测试9,测试10" + i;
				StringBuilder sb = new StringBuilder();
				sb.append("测试1,测试2,测试3,测试4,测试5,测试6,测试7,测试8,测试9,测试10");
				sb.append(i);
				out.append(sb);
				if (i % 200 == 0) {
					try {
						Thread.sleep(20);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

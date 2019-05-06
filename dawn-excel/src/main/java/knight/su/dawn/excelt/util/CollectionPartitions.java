package knight.su.dawn.excelt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * Date: 2018/9/7<br/>
 * 
 * @author sugengbin
 */
public class CollectionPartitions<T> {

	/**
	 * 按limit 拆分为多个List
	 * 
	 * @param map
	 * @param limit
	 * @return
	 */
	public List<List<T>> mapPartition(Map<String, List<T>> map, int limit) {
		List<List<T>> list = new ArrayList<>();
		if (map != null && limit != 0) {
			List<T> once = new ArrayList<>();
			int count = 1;
			for (Map.Entry<String, List<T>> entry : map.entrySet()) {
				once.addAll(entry.getValue());
				if (count % limit == 0) {
					list.add(once);
					once = new ArrayList<>();
				} else if (count == map.size()) {
					list.add(once);
				}
				count++;
			}
		}
		return list;
	}

	/**
	 * 将List按key合并为map
	 * 
	 * @param list
	 * @param buildKey
	 * @return
	 */
	public Map<String, List<T>> map(List<T> list, BuildKey<T> buildKey) {
		Map<String, List<T>> map = new HashMap<>();
		for (T vo : list) {
			String key = buildKey.key(vo);
			List<T> merge = map.get(key);
			if (merge == null) {
				merge = new ArrayList<>();
			}
			merge.add(vo);
			map.put(key, merge);
		}
		return map;
	}

	/**
	 * 对List进行按key过滤，相同key只保留一个value, 添加开关
	 * 
	 * @param list
	 * @param buildKey
	 * @param open
	 * @return
	 */
	public List<T> filterByKey(List<T> list, BuildKey<T> buildKey, boolean open) {
		if (!open) {
			return list;
		}
		return filterByKey(list, buildKey);
	}
	
	/**
	 * 对List进行按key过滤，相同key只保留一个value
	 * 
	 * @param list
	 * @param buildKey
	 * @return
	 */
	public List<T> filterByKey(List<T> list, BuildKey<T> buildKey) {
		Map<String, T> map = new HashMap<>();
		for (T t : list) {
			String key = buildKey.key(t);
			if (StringUtils.isNotBlank(key)) {
				map.put(key, t);
			}
		}
		List<T> filterData = new ArrayList<>();
		for (Map.Entry<String, T> entry : map.entrySet()) {
			filterData.add(entry.getValue());
		}
		return filterData;
	}
	
}

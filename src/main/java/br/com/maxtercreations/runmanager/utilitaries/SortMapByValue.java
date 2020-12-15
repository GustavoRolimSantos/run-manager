package br.com.maxtercreations.runmanager.utilitaries;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.maxtercreations.runmanager.database.RunnerUtils;

public class SortMapByValue {

	public Map<RunnerUtils, Long> sortByComparator(Map<RunnerUtils, Long> unsortMap, boolean order) {
		List<Entry<RunnerUtils, Long>> list = new LinkedList<Entry<RunnerUtils, Long>>(unsortMap.entrySet());

		Collections.sort(list, new Comparator<Entry<RunnerUtils, Long>>() {
			public int compare(Entry<RunnerUtils, Long> o1, Entry<RunnerUtils, Long> o2) {
				return order ? o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue());
			}
		});

		Map<RunnerUtils, Long> sortedMap = new LinkedHashMap<RunnerUtils, Long>();

		for (Entry<RunnerUtils, Long> entry : list)
			sortedMap.put(entry.getKey(), entry.getValue());

		return sortedMap;
	}

}
package com.toncho.collector.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.toncho.collector.dbcollector.Collectors;
import com.toncho.collector.dbcollector.impl.AbstractCollector;
import com.toncho.collector.dbcollector.meta.Column;
import com.toncho.collector.dbcollector.meta.Table;

/**
 * 数据库采集器任务
 */
public class DbExecuteCollector extends AbstractExecuteCollector {

	private Logger log = Logger.getLogger(DbExecuteCollector.class);

	@Override
	public void run() {
		log.debug("采集开始... mc = " + getMetaContext());
		Map<String, String> config = getMetaContext().getConfigMap();
		log.info("数据库参数：" + config);
		try {
			AbstractCollector collector = Collectors.getInstance().createCollector(config);
			Map<String, Map<String, Object>> resultMap = new HashMap<>();
			Set<String> tableNames = collector.getTableNames();
			if (tableNames != null) {
				for (String tn : tableNames) {
					Table ti = collector.getTableInfo(tn);
					Map<String, Column> columnsMap = collector.getColumns(tn);
					// Map<String, Partition> partitions =
					// collect.getPartitions(tn);
					Map<String, Object> tempMap = new HashMap<>();
					tempMap.put("_tb", ti);
					tempMap.put("_cl", columnsMap);
					// tempMap.put("_pt", partitions);
					resultMap.put(tn, tempMap);
				}
			}
			System.out.println(JSON.toJSONString(resultMap));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
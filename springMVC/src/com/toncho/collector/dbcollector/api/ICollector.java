package com.toncho.collector.dbcollector.api;

import java.util.Map;
import java.util.Set;

import com.toncho.collector.dbcollector.meta.Column;
import com.toncho.collector.dbcollector.meta.Partition;
import com.toncho.collector.dbcollector.meta.Table;

public interface ICollector {

	/**
	 * 获取所有的表
	 * 
	 * @return
	 */
	Set<String> getTableNames();

	/**
	 * 获取表信息
	 * 
	 * @param tableName
	 * @return
	 */
	Table getTableInfo(String tableName);

	/**
	 * 获取列信息
	 * 
	 * @param tableName
	 * @return
	 */
	Map<String, Column> getColumns(String tableName);

	/**
	 * 获取分区信息
	 * 
	 * @param tablenName
	 * @return
	 */
	Map<String, Partition> getPartitions(String tablenName);

}

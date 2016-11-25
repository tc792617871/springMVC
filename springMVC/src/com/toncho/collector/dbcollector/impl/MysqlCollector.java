package com.toncho.collector.dbcollector.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.toncho.collector.dbcollector.JDBCKits;
import com.toncho.collector.dbcollector.api.ResultSetExtractor;
import com.toncho.collector.dbcollector.meta.Column;
import com.toncho.collector.dbcollector.meta.Partition;
import com.toncho.collector.dbcollector.meta.Table;

public class MysqlCollector extends AbstractCollector {

	public static final String GET_PARTIONS_SQL = "select TABLE_NAME, PARTITION_NAME, SUBPARTITION_NAME, CREATE_TIME, UPDATE_TIME "
			+ "from information_schema.PARTITIONS where TABLE_NAME = ?";

	@Override
	public Map<String, Partition> getPartitions(String tablenName) {
		String message = "Get database(mysql)  partition information error!";
		Map<String, Partition> partitions = JDBCKits.query(getDbm(), GET_PARTIONS_SQL, message,
				new ResultSetExtractor<Map<String, Partition>>() {

					public Map<String, Partition> extractData(ResultSet rs) throws SQLException {
						Map<String, Partition> partitions = new HashMap<String, Partition>();
						while (rs.next()) {
							Partition p = new Partition();
							String name = rs.getString("PARTITION_NAME");
							String subName = rs.getString("SUBPARTITION_NAME");
							Timestamp createTime = rs.getTimestamp("CREATE_TIME");
							Timestamp updateDate = rs.getTimestamp("UPDATE_TIME");
							p.setPartitionName(name);
							p.setSubpartitionName(subName);
							p.setCreateTime(new Date(createTime.getTime()));
							if (updateDate != null) {
								p.setUpdateTime(new Date(updateDate.getTime()));
							}
							partitions.put(name, p);
						}
						return partitions;
					}
				}, tablenName);
		return partitions;
	}

	@Override
	public Set<String> getTableNames() {
		return super.getTableNames();
	}

	@Override
	public Table getTableInfo(String tableName) {
		Table t = super.getTableInfo(tableName);
		if (t != null) {
			String em = "get table " + tableName + " detail info error : ";
			String sql = "SELECT * FROM information_schema.`TABLES` where TABLE_SCHEMA = ? and TABLE_NAME = ? ";
			JDBCKits.query(getDbm(), sql, em, new ResultSetExtractor<String>() {

				@Override
				public String extractData(ResultSet rs) throws SQLException {
					while (rs.next()) {
						Timestamp ct = rs.getTimestamp("CREATE_TIME");
						Timestamp ut = rs.getTimestamp("UPDATE_TIME");
						if (ct != null) {
							t.setCreateTime(new Date(ct.getTime()));
						}
						if (ut != null) {
							t.setUpdateTime(new Date(ut.getTime()));
						}
					}
					return null;
				}
			}, getDbName(), tableName);
			if (t.getUpdateTime() == null) {
				t.setUpdateTime(t.getCreateTime());
			}
		}
		return t;
	}

	@Override
	public Map<String, Column> getColumns(String tableName) {
		return super.getColumns(tableName);
	}

}

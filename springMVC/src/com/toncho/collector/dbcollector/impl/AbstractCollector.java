package com.toncho.collector.dbcollector.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.toncho.collector.dbcollector.JDBCKits;
import com.toncho.collector.dbcollector.api.ICollector;
import com.toncho.collector.dbcollector.meta.Column;
import com.toncho.collector.dbcollector.meta.Partition;
import com.toncho.collector.dbcollector.meta.Table;

public abstract class AbstractCollector implements ICollector {

	private DatabaseMetaData dbm;

	private String driverType;

	private String dbIP;

	private String dbPort;

	private String dbName;

	@Override
	public Set<String> getTableNames() {
		Set<String> tables = new HashSet<String>();
		ResultSet rs;
		try {
			rs = dbm.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tables.add(tableName);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return tables;
	}

	@Override
	public Table getTableInfo(String tableName) {
		List<String> types = new ArrayList<String>();
		types.add("TABLE");
		String[] typeStr = new String[types.size()];
		for (int i = 0; i < typeStr.length; ++i) {
			typeStr[i] = types.get(i);
		}
		Table table = new Table();
		try {
			ResultSet rs = dbm.getTables(null, null, tableName, typeStr);
			while (rs.next()) {
				String tableN = rs.getString("TABLE_NAME");
				if (!tableN.equals(tableName))
					continue;
				table.setName(tableN);
				table.setComment(rs.getString("REMARKS"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Table " + tableName + " information get error : " + e.getMessage(), e);
		}

		return table;
	}

	@Override
	public Map<String, Column> getColumns(String tableName) {
		Map<String, Column> columns = new HashMap<String, Column>();
		ResultSet rs = null;
		try {
			rs = dbm.getColumns(null, null, tableName, null);
			while (rs.next()) {
				if (!rs.getString("TABLE_NAME").equals(tableName))
					continue;
				Column column = packColumn(rs);
				columns.put(column.getName(), column);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Table " + tableName + " get column information error : " + e.getMessage(), e);
		} finally {
			JDBCKits.closeResultSet(rs);
		}
		return columns;
	}

	@Override
	public Map<String, Partition> getPartitions(String tablenName) {
		return null;
	}

	protected Column packColumn(ResultSet rs) throws SQLException {
		String name = rs.getString("COLUMN_NAME");
		Column column = new Column();
		column.setName(name);
		column.setComment(rs.getString(12));
		column.setDefaultValue(rs.getString("COLUMN_DEF"));
		column.setLength(rs.getInt("CHAR_OCTET_LENGTH"));
		column.setNullable(rs.getInt("NULLABLE") == 1 ? true : false);
		column.setPrecision(rs.getInt("COLUMN_SIZE"));
		column.setScale(rs.getInt("DECIMAL_DIGITS"));
		column.setType(rs.getInt("DATA_TYPE"));
		column.setTypeName(rs.getString("TYPE_NAME"));
		return column;
	}

	public DatabaseMetaData getDbm() {
		return dbm;
	}

	public void setDbm(DatabaseMetaData dbm) {
		this.dbm = dbm;
	}

	public String getDriverType() {
		return driverType;
	}

	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}

	public String getDbIP() {
		return dbIP;
	}

	public void setDbIP(String dbIP) {
		this.dbIP = dbIP;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}

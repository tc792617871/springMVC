package com.toncho.collector.dbcollector.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.toncho.collector.dbcollector.JDBCKits;
import com.toncho.collector.dbcollector.api.ResultSetExtractor;
import com.toncho.collector.dbcollector.meta.Column;
import com.toncho.collector.dbcollector.meta.Partition;
import com.toncho.collector.dbcollector.meta.Table;

public class HiveCollector extends AbstractCollector {

	private static final String ALL_TABLE_SQL = "select d.DB_ID AS dbID, d.`NAME` as dbName, d.OWNER_NAME as dbOwner, d.DB_LOCATION_URI as dbLocation, d.`DESC` as dbDesc, tb.TBL_ID AS tbID, tb.`OWNER` tbOwner, tb.TBL_NAME as tbName, tb.TBL_TYPE as tbType, tb.CREATE_TIME as tbCT, tb.LAST_ACCESS_TIME as tbLAT from DBS d JOIN TBLS tb on d.DB_ID = tb.DB_ID ";

	private static final String TABLE_SQL = "select d.DB_ID AS dbID, d.`NAME` as dbName, d.OWNER_NAME as dbOwner, d.DB_LOCATION_URI as dbLocation, d.`DESC` as dbDesc, tb.TBL_ID AS tbID, tb.`OWNER` tbOwner, tb.TBL_NAME as tbName, tb.TBL_TYPE as tbType, tb.CREATE_TIME as tbCT, tb.LAST_ACCESS_TIME as tbLAT, tp.PARAM_KEY AS tb_pk, tp.PARAM_VALUE AS tb_pv from DBS d JOIN TBLS tb on d.DB_ID = tb.DB_ID LEFT JOIN TABLE_PARAMS tp ON tb.TBL_ID = tp.TBL_ID where tb.TBL_ID = ? ";

	private static final String COLUMN_SQL = "SELECT tb.TBL_ID as tbID, tb.TBL_NAME as tbName, cl.`COMMENT` as clComment, cl.COLUMN_NAME as clName, cl.TYPE_NAME as clType, cl.INTEGER_IDX as clIDX from COLUMNS_V2 cl JOIN SDS s ON cl.CD_ID = s.CD_ID JOIN TBLS tb ON tb.SD_ID = s.SD_ID WHERE tb.TBL_ID = ? ";

	private static final String PARTITION_SQL = "SELECT pt.PART_NAME as ptName, pt.CREATE_TIME as ptCT, pt.LAST_ACCESS_TIME as ptLAT, pt.PART_ID as ptID, pt.TBL_ID as tbID, pp.PARAM_KEY AS pp_pk, pp.PARAM_VALUE AS pp_pv FROM `PARTITIONS` pt LEFT JOIN PARTITION_PARAMS pp ON (pt.PART_ID = pp.PART_ID AND pp.PARAM_KEY = 'transient_lastDdlTime') where pt.TBL_ID = ? ";

	@Override
	public Set<String> getTableNames() {
		String em = "Get database(hive) table information error!";
		Set<String> tbSet = JDBCKits.query(getDbm(), ALL_TABLE_SQL, em, new ResultSetExtractor<Set<String>>() {

			@Override
			public Set<String> extractData(ResultSet rs) throws SQLException {
				Set<String> ts = new HashSet<>();
				while (rs.next()) {
					String dbName = rs.getString("dbName");
					Integer tbID = rs.getInt("tbID");
					String tbName = rs.getString("tbName");
					ts.add(tbID + JDBCKits.SIGNAL_0 + dbName + JDBCKits.SIGNAL_AT + tbName);
				}
				return ts;
			}
		});
		return tbSet;
	}

	@Override
	public Table getTableInfo(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			throw new RuntimeException("table name is null");
		}
		String tbID = tableName.split(JDBCKits.SIGNAL_0)[0];
		String em = "Get database(hive) table detail information error!";
		Table t = JDBCKits.query(getDbm(), TABLE_SQL, em, new ResultSetExtractor<Table>() {

			@Override
			public Table extractData(ResultSet rs) throws SQLException {
				Table t = new Table();
				while (rs.next()) {
					String dbName = rs.getString("dbName");
					String tbName = rs.getString("tbName");
					t.setName(dbName + JDBCKits.SIGNAL_AT + tbName);
					t.setOwner(rs.getString("tbOwner"));
					// Integer createTime = rs.getInt("tbCT");
					// if (createTime != null) {
					// t.setCreateTime(new Date(createTime));
					// }
					t.setCreateTime(new Date());
					String pk = rs.getString("tb_pk");
					if ("comment".equalsIgnoreCase(pk)) {
						String pv = rs.getString("tb_pv");
						if (!StringUtils.isEmpty(pv)) {
							t.setComment(pv);
						}
					}
					// if ("transient_lastDdlTime".equalsIgnoreCase(pk)) {
					// String pv = rs.getString("tb_pv");
					// if (!StringKit.isEmpty(pv)) {
					// t.setUpdateTime(new Date(Integer.parseInt(pv)));
					// }
					// }
					t.setUpdateTime(t.getCreateTime());
				}
				return t;
			}
		}, tbID);
		return t;
	}

	@Override
	public Map<String, Column> getColumns(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			throw new RuntimeException("table name is null");
		}
		String tbID = tableName.split(JDBCKits.SIGNAL_0)[0];
		String em = "Get database(hive) column detail information error!";
		Map<String, Column> cm = JDBCKits.query(getDbm(), COLUMN_SQL, em,
				new ResultSetExtractor<Map<String, Column>>() {

					@Override
					public Map<String, Column> extractData(ResultSet rs) throws SQLException {
						Map<String, Column> cm = new HashMap<>();
						while (rs.next()) {
							Column c = new Column();
							c.setComment(rs.getString("clComment"));
							c.setName(rs.getString("clName"));
							c.setTypeName(rs.getString("clType"));
							cm.put(c.getName(), c);
						}
						return cm;
					}
				}, tbID);
		return cm;
	}

	@Override
	public Map<String, Partition> getPartitions(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			throw new RuntimeException("table name is null");
		}
		String tbID = tableName.split(JDBCKits.SIGNAL_0)[0];
		String em = "Get database(hive) column detail information error!";
		Map<String, Partition> pm = JDBCKits.query(getDbm(), PARTITION_SQL, em,
				new ResultSetExtractor<Map<String, Partition>>() {

					@Override
					public Map<String, Partition> extractData(ResultSet rs) throws SQLException {
						Map<String, Partition> pm = new HashMap<>();
						while (rs.next()) {
							Partition p = new Partition();
							p.setPartitionName(rs.getString("ptName"));
							String ct = rs.getString("ptCT");
							if (ct != null) {
								p.setCreateTime(new Date(Long.valueOf(ct) * 1000));
							}
							String pv = rs.getString("pp_pv");
							if (!StringUtils.isEmpty(pv)) {
								p.setUpdateTime(new Date(Long.valueOf(pv) * 1000));
							}
							pm.put(p.getPartitionName(), p);
						}
						return pm;
					}
				}, tbID);
		return pm;
	}

}

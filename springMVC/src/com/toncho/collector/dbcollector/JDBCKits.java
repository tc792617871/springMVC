package com.toncho.collector.dbcollector;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.toncho.collector.dbcollector.api.ResultSetExtractor;

public class JDBCKits {

	private static final Logger logger = Logger.getLogger(JDBCKits.class);

	public static final String SIGNAL_0 = "\0";

	public static final String SIGNAL_AT = "::";

	public static final String SIGNAL_XP = "::";

	private static JDBCKits instance = new JDBCKits();

	public static JDBCKits getInstance() {
		return instance;
	}

	public static Connection getConnection(DataSource dataSource) {
		Objects.requireNonNull(dataSource, "data source is null");
		Connection con = null;
		try {
			con = dataSource.getConnection();
			if (!con.isReadOnly()) {
				con.setReadOnly(true);
			}
			logger.debug("Get the connection " + con + " " + con.hashCode());
		} catch (SQLException e) {
			throw new RuntimeException("Could not get JDBC Connection", e);
		}
		return con;
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				logger.debug("close connection " + con + " " + con.hashCode());
				con.close();
			} catch (SQLException ex) {
				logger.error("could not close JDBC Connection", ex);
			} catch (Throwable ex) {
				logger.debug("unexpected exception on closing JDBC Connection", ex);
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				logger.debug("close ResultSet " + rs + " " + rs.hashCode());
				rs.close();
			} catch (SQLException ex) {
				logger.error("could not close JDBC ResultSet", ex);
			} catch (Throwable ex) {
				logger.debug("unexpected exception on closing JDBC ResultSet", ex);
			}
		}
	}

	public static void closePreparedStatement(PreparedStatement rs) {
		if (rs != null) {
			try {
				logger.debug("close PreparedStatement " + rs + " " + rs.hashCode());
				rs.close();
			} catch (SQLException ex) {
				logger.error("Could not close JDBC PreparedStatement", ex);
			} catch (Throwable ex) {
				logger.debug("Unexpected exception on closing JDBC PreparedStatement", ex);
			}
		}
	}

	public static <T> T query(DatabaseMetaData dbm, String sql, String exceptionMessage,
			ResultSetExtractor<T> rsExtractor, Object... args) {
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection con = dbm.getConnection();
			st = con.prepareStatement(sql);
			for (int i = 1; i <= args.length; ++i) {
				st.setObject(i, args[i - 1]);
			}
			rs = st.executeQuery();
			return rsExtractor.extractData(rs);
		} catch (SQLException e) {
			throw new RuntimeException(exceptionMessage + " " + e.getMessage(), e);
		} finally {
			closePreparedStatement(st);
			closeResultSet(rs);
		}
	}

}

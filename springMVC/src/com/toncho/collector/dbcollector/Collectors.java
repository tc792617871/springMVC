package com.toncho.collector.dbcollector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.toncho.collector.dbcollector.constants.TypeInitConstants;
import com.toncho.collector.dbcollector.impl.AbstractCollector;
import com.toncho.collector.dbcollector.impl.HiveCollector;
import com.toncho.collector.dbcollector.impl.MysqlCollector;
import com.toncho.collector.dbcollector.impl.OracleCollector;
import com.toncho.collector.dbcollector.impl.SqlServerCollector;
import com.toncho.util.Base64Util;

public class Collectors {

	private static Collectors instance = new Collectors();
	private static final Logger log = Logger.getLogger(Collectors.class);

	public static Collectors getInstance() {
		return instance;
	}

	public AbstractCollector createCollector(Map<String, String> paramMap) throws ClassNotFoundException {
		if (paramMap != null) {
			AbstractCollector idc = null;
			String driverType = paramMap.get("databaseType");
			String databaseName = paramMap.get("databaseName");
			String user = paramMap.get("databaseUser");
			String password = paramMap.get("databasePassword");
			if (!StringUtils.isEmpty(password)) {
				password = Base64Util.decode(password);
			}
			String ip = paramMap.get("databaseIPAdress");
			String port = paramMap.get("databasePort");
			String encode = paramMap.get("databaseEncode");
			if (StringUtils.isEmpty(encode)) {
				encode = "UTF-8";
			}
			StringBuilder builder = new StringBuilder();
			if (TypeInitConstants.DRIVER_MYSQL.equalsIgnoreCase(driverType)) {
				Class.forName("com.mysql.jdbc.Driver");
				builder.append("jdbc:mysql://").append(ip).append(":").append(port).append("/").append(databaseName)
						.append("?useUnicode=true&characterEncoding=").append(encode);
				idc = new MysqlCollector();
				idc.setDriverType(TypeInitConstants.DRIVER_MYSQL.toLowerCase());
			} else if (TypeInitConstants.DRIVER_ORACLE.equalsIgnoreCase(driverType)) {
				Class.forName("Oracle.jdbc.driver.OracleDriver");
				builder.append("jdbc:oracle:thin:@").append(ip).append(":").append(port).append("/")
						.append(databaseName);
				idc = new OracleCollector();
				idc.setDriverType(TypeInitConstants.DRIVER_ORACLE.toLowerCase());
			} else if (TypeInitConstants.DRIVER_MSSQ.equalsIgnoreCase(driverType)) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				builder.append("jdbc:sqlserver://").append(ip).append(":").append(port).append(";databaseName=")
						.append(databaseName);
				idc = new SqlServerCollector();
				idc.setDriverType(TypeInitConstants.DRIVER_MSSQ.toLowerCase());
			} else if (TypeInitConstants.DRIVER_HIVE.equalsIgnoreCase(driverType)) {
				if (StringUtils.isEmpty(databaseName)) {
					databaseName = "hive";
				}
				Class.forName("com.mysql.jdbc.Driver");
				builder.append("jdbc:mysql://").append(ip).append(":").append(port).append("/").append(databaseName)
						.append("?useUnicode=true&characterEncoding=").append(encode);
				idc = new HiveCollector();
			} else {
				throw new RuntimeException("unexpected database type : " + driverType);
			}
			String url = builder.toString();
			Connection connection = null;
			try {
				connection = DriverManager.getConnection(url, user, password);
				idc.setDbm(connection.getMetaData());
				idc.setDbIP(ip);
				idc.setDbName(databaseName);
				idc.setDbPort(port);
				return idc;
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
				JDBCKits.closeConnection(connection);
				throw new RuntimeException(e);
			}
		}
		return null;
	}

}

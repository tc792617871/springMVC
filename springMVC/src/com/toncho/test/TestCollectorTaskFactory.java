package com.toncho.test;

import java.util.HashMap;
import java.util.Map;

import com.toncho.collector.dbcollector.constants.TypeInitConstants;
import com.toncho.collector.task.CollectorTaskFactory;
import com.toncho.collector.task.MetaContext;
import com.toncho.collector.task.TaskType;
import com.toncho.util.Base64Util;

public class TestCollectorTaskFactory {

	public static void main(String[] args) {

		MetaContext context = new MetaContext();
		Map<String, String> configMap = new HashMap<>();
		configMap.put("databaseType", TypeInitConstants.DRIVER_MYSQL);
		configMap.put("databaseUser", "mysql");
		configMap.put("databasePassword", Base64Util.encode("mysql"));
		configMap.put("databaseIPAdress", "172.172.210.227");
		configMap.put("databasePort", "3306");
		configMap.put("databaseName", "kdrill");
		configMap.put("databaseEncode", "UTF-8");
		context.setConfigMap(configMap);
		context.setTaskType(TaskType.DB);

		CollectorTaskFactory.getInstance().executeTask(context);
		System.out.println("数据采集中！");
	}

}

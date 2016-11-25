package com.toncho.collector.task;

import java.util.Map;

public class MetaContext {

	private TaskType taskType;

	private Map<String, String> configMap;

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public Map<String, String> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}
}

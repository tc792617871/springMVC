package com.toncho.collector.task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 采集任务工厂
 */
public class CollectorTaskFactory {

	private ThreadPoolExecutor pool;

	// private final static Collectors collectors = Collectors.getInstance();

	private final static Logger log = Logger.getLogger(CollectorTaskFactory.class);

	private final static CollectorTaskFactory collectorTaskFactory = new CollectorTaskFactory();

	public static CollectorTaskFactory getInstance() {
		return collectorTaskFactory;
	}

	public CollectorTaskFactory() {
		if (pool != null) {
			pool.shutdown();
		}
		pool = new ThreadPoolExecutor(10, 100, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000));
	}

	public void executeTask(MetaContext mc) {
		if (mc == null) {
			return;
		}
		try {
			TaskType taskType = mc.getTaskType();
			AbstractExecuteCollector aec = null;
			if (taskType == TaskType.DB) {
				aec = new DbExecuteCollector();

			} else if (taskType == TaskType.FILE) {
				aec = new FileExecuteCollector();
			}
			aec.setMetaContext(mc);
			pool.execute(aec);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}

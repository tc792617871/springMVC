package com.toncho.collector.task;

public class AbstractExecuteCollector implements Runnable{

	private MetaContext metaContext;

	public MetaContext getMetaContext() {
		return metaContext;
	}

	public void setMetaContext(MetaContext metaContext) {
		this.metaContext = metaContext;
	}

	@Override
	public void run() {
		
	}

}

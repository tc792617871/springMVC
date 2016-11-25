package com.toncho.collector.dbcollector.meta;

import java.util.Date;

import com.toncho.util.pojo.StringAndEqualsPojo;

public class Partition extends StringAndEqualsPojo {
	private static final long serialVersionUID = 8028473657727208250L;

	private String partitionName;

	private String subpartitionName;

	private String partitionMethod;

	private String subpartitionMethod;

	private Date createTime;

	private Date updateTime;

	private String partitionComment;

	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}

	public String getSubpartitionName() {
		return subpartitionName;
	}

	public void setSubpartitionName(String subpartitionName) {
		this.subpartitionName = subpartitionName;
	}

	public String getPartitionMethod() {
		return partitionMethod;
	}

	public void setPartitionMethod(String partitionMethod) {
		this.partitionMethod = partitionMethod;
	}

	public String getSubpartitionMethod() {
		return subpartitionMethod;
	}

	public void setSubpartitionMethod(String subpartitionMethod) {
		this.subpartitionMethod = subpartitionMethod;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPartitionComment() {
		return partitionComment;
	}

	public void setPartitionComment(String partitionComment) {
		this.partitionComment = partitionComment;
	}
}

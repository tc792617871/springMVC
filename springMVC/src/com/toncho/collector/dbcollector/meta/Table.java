package com.toncho.collector.dbcollector.meta;

import java.util.Date;
import java.util.Map;

import com.toncho.util.pojo.StringAndEqualsPojo;

public class Table extends StringAndEqualsPojo {

	private static final long serialVersionUID = -1493291006455025499L;

	// Default value
	private String name;

	private String comment;

	private Date createTime;

	private Date updateTime;

	private String owner;

	private Map<String, String> attrMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	public void setAttrMap(Map<String, String> attrMap) {
		this.attrMap = attrMap;
	}

}

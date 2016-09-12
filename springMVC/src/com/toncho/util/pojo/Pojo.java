package com.toncho.util.pojo;

import java.io.Serializable;

import com.toncho.util.JSONUtil;

public interface Pojo extends Serializable {
	/**
	 * 将Pojo转换成json字符串
	 * 
	 * @return json字符串
	 */
	default String toJsonString() {
		return JSONUtil.toJson(this);
	}
}

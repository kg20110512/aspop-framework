package com.vyd.base.jpa.dao.support;

/**
 * <p>Description: 查询参数</p>
 * @author Dirk
 * @date 2017年5月20日 上午10:50:24
 */
public class QueryParameter {

	/**
	 * 查询条件枚举
	 */
	private QueryEnum queryEnum;
	
	/**
	 * 查询条件值
	 */
	private Object[] args;

	public QueryParameter(QueryEnum queryEnum, Object[] args) {
		this.queryEnum = queryEnum;
		this.args = args;
	}

	public QueryEnum getQueryEnum() {
		return queryEnum;
	}

	public void setQueryEnum(QueryEnum queryEnum) {
		this.queryEnum = queryEnum;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}

package com.vyd.base.jpa.dao.support;

/**
 * <p>Description: 查询条件枚举</p>
 * @author Dirk
 * @date 2017年5月20日 上午10:48:29
 */
public enum QueryEnum {
	/**
	 * Equals
	 */
	EQUAL,
	/**
	 * Greater than
	 */
	GREATER_THAN,
	/**
	 * Greater equal than
	 */
	GREATER_EQUAL_THAN,
	/**
	 * Less than
	 */
	LESS_THAN,
	/**
	 * Less equal than
	 */
	LESS_EQUAL_THAN,
	/**
	 * Not equal
	 */
	NOT_EQUAL,
	/**
	 * In
	 */
	IN,
	/**
	 * Not in
	 */
	NOT_IN,
	/**
	 * Right like
	 */
	RIGHT_LIKE,
	/**
	 * Not right like
	 */
	NOT_RIGHT_LIKE,
	/**
	 * Left like
	 */
	LEFT_LIKE,
	/**
	 * Not left like
	 */
	NOT_LEFT_LIKE,
	/**
	 * All like
	 */
	ALL_LIKE,
	/**
	 * Not all like
	 */
	NOT_ALL_LIKE,
	/**
	 * Between
	 */
	BETWEEN,
	/**
	 * Not between
	 */
	NOT_BETWEEN,
	/**
	 * Or
	 */
	OR;
}

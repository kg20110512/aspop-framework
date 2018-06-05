package com.vyd.base.jpa.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 分页查询</p>
 * @author Dirk
 * @date 2017年5月20日 上午10:46:58 
 * @param <T>
 */
public class PagedQueryVO<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询条件
	 */
	protected Map<String, QueryParameter> queryParameterMap = new HashMap<String, QueryParameter>();

	/**
	 * 排序条件
	 */
	protected Map<String, OrderByEnum> orderByMap = new Hashtable<String, OrderByEnum>();

	/**
	 * 当前页数
	 */
	protected int pageNumber = 1;

	/**
	 * 每页显示条数
	 */
	protected int pageSize = 0;

	/**
	 * 总条数
	 */
	protected long totalSize = 0;

	/**
	 * 总页数
	 */
	protected int pageCounts = 1;

	/**
	 * 每页显示条数选择集合，用于下拉列表框
	 */
	protected int[] pageSizeArray = { 10, 50, 100, 500 };
	
	/**
	 * 查询结果
	 */
	protected List<T> results = new ArrayList<T>();
	
	/**
	 * 
	 * <p>Description: 展示页数代码，用于下拉列表框</p>
	 * @return 列表集合
	 */
	public List<Integer> getPageCountList() {
		List<Integer> rtn = new ArrayList<Integer>();
		for (int i = 1; i <= getPageCounts(); i++) {
			rtn.add(i);
		}
		return rtn;
	}

	/**
	 * 添加查询条件
	 * 
	 * @param propertyName 属性名
	 *            
	 * @param queryEnum 查询条件
	 *            
	 * @param args 属性值集合
	 *            
	 * @return
	 */
	public PagedQueryVO<T> addWhereCondition(String propertyName, QueryEnum queryEnum, Object... args) {
		QueryParameter queryParameter = new QueryParameter(queryEnum, args);
		this.queryParameterMap.put(propertyName, queryParameter);
		return this;
	}

	/**
	 * 添加排序条件
	 * 
	 * @param propertyName 属性名
	 *            
	 * @param orderBy 排序方式
	 *            
	 * @return
	 */
	public PagedQueryVO<T> addOrderBy(String propertyName, OrderByEnum orderBy) {
		this.orderByMap.put(propertyName, orderBy);
		return this;
	}

	/**
	 * 获取查询条件 
	 * 
	 * @return
	 */
	public Map<String, QueryParameter> getQueryParameterMap() {
		return queryParameterMap;
	}

	/**
	 * get map of Order by 
	 * 
	 * @return
	 */
	public Map<String, OrderByEnum> getOrderByMap() {
		return orderByMap;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the totalSize
	 */
	public long getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize
	 *            the totalSize to set
	 */
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return the results
	 */
	public List<T> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}

	public int[] getPageSizeArray() {
		return pageSizeArray;
	}

	public void setPageSizeArray(int[] pageSizeArray) {
		this.pageSizeArray = pageSizeArray;
	}

	public int getPageCounts() {
		return pageCounts;
	}

	public void setPageCounts(int pageCounts) {
		this.pageCounts = pageCounts;
	}
}
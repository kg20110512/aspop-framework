/*
 * QueryCriteriaVo.java
 *
 * Created Date: 2017年6月5日
 *				
 * Copyright (c)  Yuandian Technologies Co., Ltd.
 *
 * This software is the confidential and proprietary information of
 *  Yuandian Technologies Co., Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * Yuandian Technologies Co., Ltd.
 */

package com.vyd.base.jpa.dao.support;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Alisa.Yang
 * @version  <br>
 * <p>类的描述</p>
 */

public class QueryCriteriaVo<K, T>{
	
	private CriteriaBuilder builder;
	
	private CriteriaQuery<K> query;
	
	private Root<T> entity;

	
	public CriteriaBuilder getBuilder() {
		
		return builder;
	}

	
	public void setBuilder(CriteriaBuilder builder) {
		
		this.builder = builder;
	}

	
	public CriteriaQuery<K> getQuery() {
		
		return query;
	}

	
	public void setQuery(CriteriaQuery<K> query) {
		
		this.query = query;
	}

	
	public Root<T> getEntity() {
		
		return entity;
	}

	
	public void setEntity(Root<T> entity) {
		
		this.entity = entity;
	}
	
	
}

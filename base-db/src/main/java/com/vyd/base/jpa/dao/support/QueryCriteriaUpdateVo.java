package com.vyd.base.jpa.dao.support;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 * jpa CriteriaUpdate可指定更新
 * @author Alisa
 *
 * @param <T>
 */
public class QueryCriteriaUpdateVo <T>{

	private CriteriaBuilder builder;
	
	private CriteriaUpdate<T> update;
	
	private Root<T> entity;

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public CriteriaUpdate<T> getUpdate() {
		return update;
	}

	public void setUpdate(CriteriaUpdate<T> update) {
		this.update = update;
	}

	public Root<T> getEntity() {
		return entity;
	}

	public void setEntity(Root<T> entity) {
		this.entity = entity;
	}
	
	
}

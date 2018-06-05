package com.vyd.base.jpa.dao.support;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

/**
 * jpa CriteriaDelete根据条件删除，
 * 方便批量删除时不再需要实体对象
 * @author Alisa
 *
 * @param <T>
 */
public class QueryCriteriaDeleteVo <T>{

	private CriteriaBuilder builder;
	
	private CriteriaDelete<T> delete;
	
	public CriteriaBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public CriteriaDelete<T> getDelete() {
		return delete;
	}

	public void setDelete(CriteriaDelete<T> delete) {
		this.delete = delete;
	}

	public Root<T> getEntity() {
		return entity;
	}

	public void setEntity(Root<T> entity) {
		this.entity = entity;
	}

	private Root<T> entity;

	
	
}

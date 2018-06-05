/**
 * 
 */
package com.vyd.base.jpa.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.vyd.base.jpa.dao.IBaseDao;
import com.vyd.base.jpa.dao.support.PagedQueryVO;
import com.vyd.base.jpa.dao.support.QueryCriteriaDeleteVo;
import com.vyd.base.jpa.dao.support.QueryCriteriaUpdateVo;
import com.vyd.base.jpa.dao.support.QueryCriteriaVo;
import com.vyd.base.jpa.dao.support.QueryEnum;
import com.vyd.base.jpa.dao.support.QueryParameter;

/**
 * <p>Description: 基于Hibernate通用DAO实现类</p>
 * @author Dirk
 * @date 2017年5月14日 上午10:26:39 
 */
@Repository
public class BaseDaoImpl implements IBaseDao {
	
	/**
	 * slf4j实例，用于记录日志信息
	 */
	private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	/**
	 * JAP entityManager实例
	 */
	@PersistenceContext
	private EntityManager manager;
	
	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#save(java.lang.Object)
	 */
	@Override
	public <T> void save(T entity) {
		try {
			manager.persist(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("保存实体{}成功",entity.getClass().getName());
			}
		} catch (Exception e) {
			logger.error("保存实体{}出现异常，异常信息为{}",entity.getClass().getName(),e);
			throw e;
		}
	}
	
	@Override
	public <T> void saveBatch(List<T> list){
		for(int i=1; i<=list.size(); i++){
			this.save(list.get(i-1));
			//manager.persist(list.get(i-1));
			if(i%30 == 0){
				manager.flush();
				manager.clear();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public <T> T merge(T entity) {
		try {
			T t = manager.merge(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("保存或更新实体{}成功",entity.getClass().getName());
			}
			return t;
		} catch (Exception e) {
			logger.error("保存或更新实体{}出现异常，异常信息为{}",entity.getClass().getName(),e);
			throw e;
		}
	}
	
	@Override
	public <T> void mergeBatch(List<T> list){
		for(int i=1; i<=list.size(); i++){
			this.merge(list.get(i-1));
			if(i%30 == 0){
				manager.flush();
				manager.clear();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#delete(java.lang.Object)
	 */
	@Override
	public <T> void delete(T entity) {
		try {
			manager.remove(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("删除实体{}成功",entity.getClass().getName());
			}
		} catch (Exception e) {
			logger.error("删除实体{}出现异常，异常信息为{}",entity.getClass().getName(),e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#findById(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T findById(Class<T> entityClass, Object primaryKey) {
		return manager.find(entityClass, primaryKey);
	}

	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#findById(java.lang.Class, java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public <T> T findById(Class<T> entityClass, Object primaryKey, LockModeType lockModeType) {
		return manager.find(entityClass, primaryKey,lockModeType);
	}
	
	@Override
	public <K, T> QueryCriteriaVo<K, T> getCriteriaQuery(Class<K> queryClass, Class<T> entityClass) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<K> cq = cb.createQuery(queryClass);
		Root<T> entity = cq.from(entityClass);
		
		QueryCriteriaVo<K, T> vo = new QueryCriteriaVo<K, T>();
		vo.setBuilder(cb);
		vo.setQuery(cq);
		vo.setEntity(entity);
		
		return vo;
	}
	
	@Override
	public <T> QueryCriteriaVo<T, T> getCriteriaQuery(Class<T> entityClass){
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> entity = cq.from(entityClass);
		
		QueryCriteriaVo<T, T> vo = new QueryCriteriaVo<T, T>();
		vo.setBuilder(cb);
		vo.setQuery(cq);
		vo.setEntity(entity);
		
		return vo;
	}
	
	public <T> QueryCriteriaVo<Tuple, T> getCriteriaTupleQuery(Class<T> entityClass){
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<T> entity = cq.from(entityClass);
		
		QueryCriteriaVo<Tuple, T> vo = new QueryCriteriaVo<Tuple, T>();
		vo.setBuilder(cb);
		vo.setQuery(cq);
		vo.setEntity(entity);
		return vo;
	}
	
	@Override
	public <T> QueryCriteriaUpdateVo<T> getCriteriaUpdate(Class<T> entityClass){
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaUpdate<T> cu = cb.createCriteriaUpdate(entityClass);
		Root<T> root = cu.from(entityClass);
		
		QueryCriteriaUpdateVo<T> vo = new QueryCriteriaUpdateVo<T>();
		vo.setBuilder(cb);
		vo.setUpdate(cu);
		vo.setEntity(root);
		
		return vo;
	}
	
	@Override
	public <T> QueryCriteriaDeleteVo<T> getCriteriaDelete(Class<T> entityClass){
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaDelete<T> cd = cb.createCriteriaDelete(entityClass);
		Root<T> root = cd.from(entityClass);
		
		QueryCriteriaDeleteVo<T> vo = new QueryCriteriaDeleteVo<T>();
		vo.setBuilder(cb);
		vo.setDelete(cd);
		vo.setEntity(root);
		
		return vo;
	}
	
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.dao.IBaseDao#findBy(com.vyd.base.jpa.dao.support.PagedQueryVO)
	 */
	@SuppressWarnings("unchecked")
//	@Override
	private <T> PagedQueryVO<T> findBy(PagedQueryVO<T> pagedQueryVO, Class<T> entityClass) {
		if (pagedQueryVO.getPageSize() <= 0) {
			Query query = getQuery(pagedQueryVO, false, entityClass);
			pagedQueryVO.setResults(query.getResultList());
			return pagedQueryVO;
		}
		
		long totalSize = (Long) getQuery(pagedQueryVO, true, entityClass).getSingleResult();
		
		Query query = getQuery(pagedQueryVO, false, entityClass);
		query.setFirstResult((pagedQueryVO.getPageNumber() - 1) * pagedQueryVO.getPageSize());
		query.setMaxResults(pagedQueryVO.getPageSize());
		pagedQueryVO.setResults(query.getResultList());
		pagedQueryVO.setTotalSize(totalSize);
		int pageCounts = 0;
		if (totalSize % pagedQueryVO.getPageSize() != 0) {
			pageCounts = (int) (totalSize / pagedQueryVO.getPageSize()) + 1;
		} else {
			pageCounts = (int) (totalSize / pagedQueryVO.getPageSize());
		}
		pagedQueryVO.setPageCounts(pageCounts == 0 ? 1 : pageCounts);
		return pagedQueryVO;
	}

	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.dao.IBaseDao#findList(com.vyd.base.jpa.dao.support.PagedQueryVO, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
//	@Override
	private <T> List<T> findList(PagedQueryVO<T> pagedQueryVO, Class<T> entityClass) {
		Query query = getQuery(pagedQueryVO, false, entityClass);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.dao.IBaseDao#findCount(com.vyd.base.jpa.dao.support.PagedQueryVO, java.lang.Class)
	 */
//	@Override
	private <T> Long findCount(PagedQueryVO<T> pagedQueryVO, Class<T> entityClass) {
		return (Long)getQuery(pagedQueryVO, true, entityClass).getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#findAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
		Root<T> entity = cq.from(entityClass);
		cq.select(entity);
		return manager.createQuery(cq).getResultList();
	}
	
	@Override
	public <T> List<T> findListByCriteriaQuery(CriteriaQuery<T> cq){
		
		return manager.createQuery(cq).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findPageListByCriteriaQuery(CriteriaQuery<T> cq, int pageNumber, int pageSize){
		
		Query query = manager.createQuery(cq);
		if(pageNumber > 0 && pageSize > 0){
			query.setFirstResult((pageNumber - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}
	
	@Override
	public <T> T findUniqueByCriteriaQuery(Class<T> entityClass, SingularAttribute<T, ?> property, Object value){
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> entity = cq.from(entityClass);
		Predicate condition = cb.equal(entity.get(property), value);
		cq.where(condition);
		List<T> list = this.findListByCriteriaQuery(cq);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public <T> Long findCountByCriteriaQuery(CriteriaQuery<T> cq){
		
//		CriteriaBuilder cb = manager.getCriteriaBuilder();
//		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//		Root<T> entity = cq.from(entityClass);
//		cq.select(cb.count(entity));
//		cq.where(getPredicates(cb,cq,entity,queryVO));
		
		return (Long) manager.createQuery(cq).getSingleResult();
		
	}

	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#detach(java.lang.Object)
	 */
	@Override
	public <T> void detach(T entity) {
		try {
			manager.detach(entity);
			if (logger.isDebugEnabled()) {
				logger.debug("从persistence context中清除实体{}成功",entity.getClass().getName());
			}
		} catch (Exception e) {
			logger.error("从persistence context中清除实体{}出现异常，异常信息为{}",entity.getClass().getName(),e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.vyd.db.hibernate.dao.IBaseDao#contains(java.lang.Object)
	 */
	@Override
	public <T> boolean contains(T entity) {
		return manager.contains(entity);
	}
	
	@Override
	public <T> int update(CriteriaUpdate<T> cu){
		int result = manager.createQuery(cu).executeUpdate();
		return result;
	}
	
	@Override
	public <T> int delete(CriteriaDelete<T> cd){
		int result = manager.createQuery(cd).executeUpdate();
		return result;
	}
	
	
	private <T> Query getQuery(PagedQueryVO<T> queryVO, boolean isGetTotalSize, Class<T> entityClass) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		if (isGetTotalSize) {
			CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
			Root<T> entity = cq.from(entityClass);
			cq.select(criteriaBuilder.count(entity));
			cq.where(getPredicates(criteriaBuilder,cq,entity,queryVO));
			return manager.createQuery(cq);
		} else {
			CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
			Root<T> entity = cq.from(entityClass);
			cq.select(entity);
			cq.where(getPredicates(criteriaBuilder, cq, entity, queryVO));
			return manager.createQuery(cq);
		}
	}

	/**
	 * <p>Description: 组装查询条件</p>
	 * @param criteriaBuilder
	 * @param cq
	 * @param entity
	 * @param queryVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> Predicate[] getPredicates(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> cq, Root<T> entity,PagedQueryVO<T> queryVO) {
		Map<String, QueryParameter> queryMap = queryVO.getQueryParameterMap();
		List<Predicate> result = new ArrayList<Predicate>();
		
		queryMap.entrySet().forEach(entry -> {
			Path<?> path = getPath(entity, entry.getKey());
			// 获取参数值
			Object value = entry.getValue().getArgs()[0];
			switch(entry.getValue().getQueryEnum()) {
			case EQUAL:
				if (value instanceof Date) {
					result.add(criteriaBuilder.equal(path, (Date) value));
				} else if (value == null) {
					result.add(criteriaBuilder.isNull(path));
				} else {
					result.add(criteriaBuilder.equal(path, value));
				}
				break;
			case GREATER_EQUAL_THAN:
				if (value instanceof Date) {
					result.add(criteriaBuilder.greaterThanOrEqualTo((Expression<? extends Date>)path, (Date) value));
				} else if (value instanceof Number) {
					result.add(criteriaBuilder.ge((Expression<? extends Number>)path, (Number)value));
				} else {
					logger.info("不支持的数据类型[GREATER_EQUAL_THAN-->{}],值为[{}]",value.getClass().getName(),value);
				}
				break;
			case GREATER_THAN:
				if (value instanceof Date) {
					result.add(criteriaBuilder.greaterThan((Expression<? extends Date>)path, (Date)value));
				} else if (value instanceof Number) {
					result.add(criteriaBuilder.gt((Expression<? extends Number>)path, (Number)value));
				} else {
					logger.info("不支持的数据类型[GREATER_THAN-->{}],值为[{}]",value.getClass().getName(),value);
				}
				break;
			case LESS_EQUAL_THAN:
				if (value instanceof Date) {
					result.add(criteriaBuilder.lessThanOrEqualTo((Expression<? extends Date>)path, (Date) value));
				} else if (value instanceof Number) {
					result.add(criteriaBuilder.le((Expression<? extends Number>)path, (Number)value));
				} else {
					logger.info("不支持的数据类型[LESS_EQUAL_THAN-->{}],值为[{}]",value.getClass().getName(),value);
				}
				break;
			case LESS_THAN:
				if (value instanceof Date) {
					result.add(criteriaBuilder.lessThan((Expression<? extends Date>)path, (Date)value));
				} else if (value instanceof Number) {
					result.add(criteriaBuilder.lt((Expression<? extends Number>)path, (Number) value));
				} else {
					logger.info("不支持的数据类型[LESS_THAN-->{}],值为[{}]",value.getClass().getName(),value);
				}
				break;
			case NOT_EQUAL:
				result.add(criteriaBuilder.notEqual(path, value));
				break;
			case RIGHT_LIKE:
				result.add(criteriaBuilder.like((Expression<String>) path, StringUtils.join(value,"%")));
				break;
			case NOT_RIGHT_LIKE:
				result.add(criteriaBuilder.notLike((Expression<String>) path, StringUtils.join(value,"%")));
				break;
			case LEFT_LIKE:
				result.add(criteriaBuilder.like((Expression<String>) path, StringUtils.join("%",value)));
				break;
			case NOT_LEFT_LIKE:
				result.add(criteriaBuilder.notLike((Expression<String>) path, StringUtils.join("%",value)));
				break;
			case ALL_LIKE:
				result.add(criteriaBuilder.like((Expression<String>) path, StringUtils.join("%",value,"%")));
				break;
			case NOT_ALL_LIKE:
				result.add(criteriaBuilder.notLike((Expression<String>) path, StringUtils.join("%",value,"%")));
				break;
			case IN:
				result.add(path.in(entry.getValue().getArgs()));
				break;
			case NOT_IN:
				result.add(criteriaBuilder.not(path.in(entry.getValue().getArgs())));
				break;
			case BETWEEN:
				Object[] betweenValues = entry.getValue().getArgs();
				Date sDate = (Date)betweenValues[0];
				Date eDate = (Date)betweenValues[1];
				result.add(criteriaBuilder.between((Expression<? extends Date>) path, sDate, eDate));
				break;
			case NOT_BETWEEN:
				Object[] notBetweenValues = entry.getValue().getArgs();
				Date snDate = (Date)notBetweenValues[0];
				Date enDate = (Date)notBetweenValues[1];
				result.add(criteriaBuilder.between((Expression<? extends Date>) path, snDate, enDate));
				break;
			case OR:
				Predicate[] orArray = new Predicate[entry.getValue().getArgs().length];
				int index = 0;
				for (Object obj : entry.getValue().getArgs()) {
					if (obj != null) {
						orArray[index++] = criteriaBuilder.equal(path, obj);
					} else {
						orArray[index++] = criteriaBuilder.isNull(path);
					}
				}
				result.add(criteriaBuilder.or(orArray));
				break;
			default:
				break;
			}
		});
		
		Predicate[] predicates = new Predicate[result.size()];
		return result.toArray(predicates);
	}
	
	private <T> Path<?> getPath(Root<T> entity, String propertyName) {
		Path<?> result = entity;
		while (propertyName.contains(".")) {
			result = result.get(propertyName.split("\\.")[0]);
			propertyName = propertyName.substring(propertyName.indexOf(".")+1);          
		}
		return result.get(propertyName);
	}

//	@Override
	private <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
		List<T> list = findListByProperty(entityClass, propertyName, value);
		return !list.isEmpty() ? list.get(0) : null;
	}

//	@Override
	private <T> List<T> findListByProperty(Class<T> entityClass, String propertyName, Object value) {
		PagedQueryVO<T> queryVO = new PagedQueryVO<>();
		queryVO.addWhereCondition(propertyName, QueryEnum.EQUAL, value);
		Query query = getQuery(queryVO, false, entityClass);
		return query.getResultList();
	}
	
	
}
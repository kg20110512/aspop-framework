/**
 * 
 */
package com.vyd.base.jpa.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.vyd.base.jpa.dao.IBaseDao;
import com.vyd.base.jpa.dao.support.QueryCriteriaVo;
import com.vyd.base.jpa.service.IBaseService;

/**
 * <p>Description: </p>
 * @author Dirk
 * @date 2017年5月19日 下午2:36:34 
 */
@Service
public class BaseServiceImpl implements IBaseService {
	
	/**
	 * JPA通用DAO
	 */
	@Resource
	private IBaseDao baseDao;
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#save(java.lang.Object)
	 */
	@Override
	@Transactional
	public <T> void save(T entity) {
		baseDao.save(entity);
	}
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#merge(java.lang.Object)
	 */
	@Override
	@Transactional
	public <T> void merge(T entity) {
		baseDao.merge(entity);
	}
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#delete(java.lang.Object)
	 */
	@Override
	@Transactional
	public <T> void delete(T entity) {
		baseDao.delete(entity);
	}
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#findById(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T findById(Class<T> entityClass, Object primaryKey) {
		return baseDao.findById(entityClass, primaryKey);
	}
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#findById(java.lang.Class, java.lang.Object, javax.persistence.LockModeType)
	 */
	@Override
	public <T> T findById(Class<T> entityClass, Object primaryKey, LockModeType lockModeType) {
		return baseDao.findById(entityClass, primaryKey, lockModeType);
	}
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#findAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return baseDao.findAll(entityClass);
	}
	
	public <K, T> QueryCriteriaVo<K, T> getCriteriaQuery(Class<K> queryClass, Class<T> entityClass){
		return baseDao.getCriteriaQuery(queryClass, entityClass);
	}
	
	public <T> List<T> findPageList(CriteriaQuery<T> query, int pageNumber, int pageSize) {
		return baseDao.findPageListByCriteriaQuery(query, pageNumber, pageSize);
	}
	
	public <T> Long findCount(CriteriaQuery<T> query) {
		return baseDao.findCountByCriteriaQuery(query);
	}
	
	public int getPageCounts(long totalCount, int pageSize){
		
		int pageCounts = 0;
		if (totalCount % pageSize != 0) {
			pageCounts = (int) (totalCount / pageSize) + 1;
		} else {
			pageCounts = (int) (totalCount / pageSize);
		}
		return pageCounts == 0 ? 1 : pageCounts;
	}
	
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#detach(java.lang.Object)
	 */
	@Override
	public <T> void detach(T entity) {
		baseDao.detach(entity);
	}
	/* (non-Javadoc)
	 * @see com.vyd.base.jpa.service.IBaseService#contains(java.lang.Object)
	 */
	@Override
	public <T> boolean contains(T entity) {
		return baseDao.contains(entity);
	}
}
/**
 * 
 */
package com.vyd.base.jpa.service;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;

import com.vyd.base.jpa.dao.support.QueryCriteriaVo;

/**
 * <p>Description: JAP通用Service实现类</p>
 * @author Dirk
 * @date 2017年5月19日 下午2:34:36 
 */
public interface IBaseService {
	/**
	 * 
	 * <p>Description: 保存实体</p>
	 * @param entity 待保存的实体
	 */
	public <T> void save(T entity);
	
	/**
	 * 
	 * <p>Description: 根据传入的实体添加或更新对象</p>
	 * @param entity 待保存或更新的对象
	 */
	public <T> void merge(T entity);
	
	/**
	 * 
	 * <p>Description: 删除实体</p>
	 * @param entity 待删除的实体
	 */
	public <T> void delete(T entity);
	
	/**
	 * <p>Description: 根据主键查询实体</p>
	 * @param entityClass 待查询实体的Class
	 * @param primaryKey 待查询实体的主键
	 * @return 查询到的实体
	 */
	public <T> T findById(Class<T> entityClass, Object primaryKey);
	
	/**
	 * 
	 * <p>Description: 根据主键按锁定形式查询实体</p>
	 * @param entityClass 待查询实体的Class
	 * @param primaryKey 待查询实体的主键
	 * @param lockModeType 锁定形式
	 * @return 查询到的实体
	 */
	public <T> T findById(Class<T> entityClass, Object primaryKey, LockModeType lockModeType);
	
	/**
	 * 
	 * <p>Description: 查询全部实体</p>
	 * @param entityClass 待检索的实体类型
	 * @return 查询到的实体集合
	 */
	public <T> List<T> findAll(Class<T> entityClass);
	
	/**
	 * <p>Description: 分页查询（支持where\orderBy）</p>
	 * @param queryVO 分页查询条件
	 * @return 查询结果及结果条数
	 */
//	public <T> PagedQueryVO<T> findBy(PagedQueryVO<T> pagedQueryVO, Class<T> entityClass);

	/**
	 * <p>Description: 查询结果集</p>
	 * @param pagedQueryVO 查询条件
	 * @param entityClass 查询实体
	 * @return 结果集合
	 */
//	public <T> List<T> findList(PagedQueryVO<T> pagedQueryVO, Class<T> entityClass);
	
	/**
	 * <p>Description: 查询结果条数</p>
	 * @param pagedQueryVO 查询条件
	 * @param entityClass 查询实体
	 * @return 条数
	 */
//	public <T> Long findCount(PagedQueryVO<T> pagedQueryVO, Class<T> entityClass);
	
	/**
	 * 获取待封装的查询条件
	 * @param queryClass
	 * @param entityClass
	 * @return
	 */
	public <K, T> QueryCriteriaVo<K, T> getCriteriaQuery(Class<K> queryClass, Class<T> entityClass);
	
	/**
	 * 分页查询
	 * @param query
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> findPageList(CriteriaQuery<T> query, int pageNumber, int pageSize);
	
	/**
	 * 总条数
	 * @param query
	 * @return
	 */
	public <T> Long findCount(CriteriaQuery<T> query);
	
	/**
	 * 总页数
	 * @param totalCount
	 * @param pageSize
	 * @return
	 */
	public int getPageCounts(long totalCount, int pageSize);
	
	/**
	 * 
	 * <p>Description: 清除persistence context中指定的实体</p>
	 * @param entity 待清除的实体
	 */
	public <T> void detach(T entity);
	
	/**
	 * 
	 * <p>Description: 判断persistence context中是否包含指定对象</p>
	 * @param entity 判断对象
	 * @return boolean true:存在，false：不存在
	 */
	public <T> boolean contains(T entity);
}
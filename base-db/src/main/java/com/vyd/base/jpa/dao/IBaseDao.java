package com.vyd.base.jpa.dao;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.SingularAttribute;

import com.vyd.base.jpa.dao.support.QueryCriteriaDeleteVo;
import com.vyd.base.jpa.dao.support.QueryCriteriaUpdateVo;
import com.vyd.base.jpa.dao.support.QueryCriteriaVo;

/**
 * 
 * <p>Description: 基于Hibernate的通用DAO接口</p>
 * @author Dirk
 * @date 2017年5月14日 上午9:55:36
 */
public interface IBaseDao {
	/**
	 * 
	 * <p>Description: 保存实体</p>
	 * @param entity 待保存的实体
	 */
	public <T> void save(T entity);
	
	/**
	 * 批量保存
	 * @param list
	 */
	public <T> void saveBatch(List<T> list);
	
	/**
	 * 
	 * <p>Description: 根据传入的实体添加或更新对象</p>
	 * @param entity 待保存或更新的对象
	 */
	public <T> T merge(T entity);
	
	/**
	 * 批量更新
	 * @param list
	 */
	public <T> void mergeBatch(List<T> list);
	
	/**
	 * 可以不使用jpql，对表update某些字段的操作
	 * @param cu
	 * @return
	 */
	public <T> int update(CriteriaUpdate<T> cu);
	
	/**
	 * 
	 * <p>Description: 删除实体</p>
	 * @param entity 待删除的实体
	 */
	public <T> void delete(T entity);
	
	/**
	 * 可以不使用jpql，根据条件对表delete某些记录的操作
	 * @param cd
	 * @return
	 */
	public <T> int delete(CriteriaDelete<T> cd);
	
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
	 * <p>Description: 组装查询条件</p>
	 * @param entityClass 查询实体class
	 * @return
	 */
	public <K, T> QueryCriteriaVo<K, T> getCriteriaQuery(Class<K> queryClass, Class<T> entityClass);
	
	public <T> QueryCriteriaVo<T, T> getCriteriaQuery(Class<T> entityClass);
	
	public <T> QueryCriteriaVo<Tuple, T> getCriteriaTupleQuery(Class<T> entityClass);
	
	/**
	 * 对表执行某些字段的更新，封装待更新的字段和新值，并可以组装查询条件
	 * @param entityClass
	 * @return
	 */
	public <T> QueryCriteriaUpdateVo<T> getCriteriaUpdate(Class<T> entityClass);
	
	/**
	 * delete表的某些记录，配置维护的关联关系也会同时更新其关联表
	 * @param entityClass
	 * @return
	 */
	public <T> QueryCriteriaDeleteVo<T> getCriteriaDelete(Class<T> entityClass);
	
	/**
	 * 
	 * <p>Description: 查询全部实体</p>
	 * @param entityClass 待检索的实体类型
	 * @return 查询到的实体集合
	 */
	public <T> List<T> findAll(Class<T> entityClass);
	
	/**
	 * 根据查询条件查询结果集
	 * @param cq CriteriaQuery查询条件
	 * @return list
	 */
	public <T> List<T> findListByCriteriaQuery(CriteriaQuery<T> cq);
	
	/**
	 * 根据分页条件和查询条件查询结果集
	 * @param cq
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> findPageListByCriteriaQuery(CriteriaQuery<T> cq, int pageNumber, int pageSize);
	
	/**
	 * 查询唯一记录
	 * @param entityClass
	 * @param property
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByCriteriaQuery(Class<T> entityClass, SingularAttribute<T, ?> property, Object value);
	
	public <T> Long findCountByCriteriaQuery(CriteriaQuery<T> cq);
	
	/**
	 * <p>Description: 分页查询（支持where\orderBy）</p>
	 * @param pagedQueryVO 分页查询条件
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

	/**
	 * 根据实体属性查询唯一记录
	 * @param entityClass
	 * 			待查询实体Class
	 * @param propertyName
	 * 			待查询实体属性
	 * @param value
	 * 			待查询实体属性值
	 * @return T
	 * 			查询到的实体  查不到返回null
	 */
//	public <T> T findUniqueByProperty(Class<T> entityClass,String propertyName, Object value);

	/**
	 * 根据实体属性查询所有记录
	 * @param entityClass
	 * 			待查询的实体Class
	 * @param propertyName
	 * 			待查询实体属性
	 * @param value
	 * 			待查询实体属性值
	 * @return List<T>
	 * 			查询到的实体集合 查不到返回空集合
	 */
//	public <T> List<T> findListByProperty(Class<T> entityClass,String propertyName, Object value);
}
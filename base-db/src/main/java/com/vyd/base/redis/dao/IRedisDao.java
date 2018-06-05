/**
 * 
 */
package com.vyd.base.redis.dao;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: Redis通用DAO</p>
 * @author Dirk
 * @date 2017年5月18日 下午3:30:32 
 */
public interface IRedisDao<T> {
	
	/**
	 * <p>Description: 根据指定key保存对象</p>
	 * @param key 待保存的对象key
	 * @param entity 待保存的对象
	 */
	public void add(String key, T entity);
	
	/**
	 * <p>Description: 根据指定key保存对象，并指定此key存活时间</p>
	 * @param key 待保存对象key
	 * @param entity 待保存对象
	 * @param timeout 存活时间，单位为分钟
	 */
	public void add(String key, T entity, long timeout);
	
	/**
	 * <p>Description: 根据指定key保存对象，并指定此key存活时间及时间单位</p>
	 * @param key 待保存对象key
	 * @param entity 待保存对象
	 * @param timeout 存活时间
	 * @param timeUnit 时间单位
	 */
	public void add(String key, T entity, long timeout, TimeUnit timeUnit);
	
	/**
	 * <p>Description: 删除对象</p>
	 * @param key 待删除对象key
	 */
	public void delete(String key);
	
	/**
	 * <p>Description: 根据指定key删除对象集合</p>
	 * @param keys 待删除的对象key值集合
	 */
	public void delete(List<String> keys);
	
	/**
	 * <p>Description: 判断key值是否存在</p>
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key);
	
	/**
	 * <p>Description: 根据对象key值查询对象</p>
	 * @param key 待查询key值
	 * @return 查询到的对象
	 */
	public T find(String key);
	
	/**
	 * 验证redis中的value与入参value是否一致，验证匹配后会删除redis中的值
	 * @param redisKey
	 * @param inputCode
	 * @return
	 */
	public boolean verifyStringValue(String redisKey, String inputCode);
}
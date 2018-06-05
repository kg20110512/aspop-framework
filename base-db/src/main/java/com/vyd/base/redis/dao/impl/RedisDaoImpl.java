/**
 * 
 */
package com.vyd.base.redis.dao.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.vyd.base.redis.dao.IRedisDao;

/**
 * <p>Description: Redis通用DAO实现类</p>
 * @author Dirk
 * @date 2017年5月18日 下午3:31:14 
 */
@Repository
public class RedisDaoImpl<T> implements IRedisDao<T> {
	
	/**
	 * RedisTemplate对象注入
	 */
	@Resource
	private RedisTemplate<String, T> redisTemplate;

	@Override
	public void add(String key, T entity) {
		redisTemplate.opsForValue().set(key, entity);
	}

	@Override
	public void add(String key, T entity, long timeout) {
		this.add(key, entity, timeout, TimeUnit.MINUTES);
	}

	@Override
	public void add(String key, T entity, long timeout, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, entity, timeout, timeUnit);
	}
	
	 
	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	@Override
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public T find(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	@Override
	public boolean verifyStringValue(String redisKey, String inputCode){
		if(this.hasKey(redisKey)){
			String code = (String) this.find(redisKey);
			this.delete(redisKey);
			return inputCode.equalsIgnoreCase(code);
		}
		return false;
	}
}
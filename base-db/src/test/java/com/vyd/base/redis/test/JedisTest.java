/**
 * 
 */
package com.vyd.base.redis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vyd.base.redis.dao.IRedisDao;
import com.vyd.base.redis.entity.DemoEntity;

/**
 * <p>Description: </p>
 * @author Dirk
 * @date 2017年5月26日 下午3:45:58 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-spring-redis.xml")
public class JedisTest {
	private static final Logger logger = LoggerFactory.getLogger(JedisTest.class);
	
	@Resource
	private IRedisDao<String> redisStringDao;
	
	@Resource
	private IRedisDao<DemoEntity> redisEntityDao;
	
	@Resource
	private IRedisDao<List<DemoEntity>> redisListEntityDao;
	
	@Resource
	private IRedisDao<Map<String, DemoEntity>> redisMapEntityDao;
	
	@Test
	public void testStringAdd() {
		redisStringDao.add("test_string_add", "This is a test value",30);
	}
	
	@Test
	public void testEntityAdd() {
		DemoEntity entity = new DemoEntity();
		entity.setName("张三");
		entity.setAddress("青岛市市南区");
		redisEntityDao.add("test_entity_add", entity);
	}
	
	@Test
	public void testListEntityAdd() {
		List<DemoEntity> list = new ArrayList<>();
		DemoEntity entity1 = new DemoEntity();
		entity1.setName("张三");
		entity1.setAddress("青岛市市南区");
		DemoEntity entity2 = new DemoEntity();
		entity2.setName("李四");
		entity2.setAddress("青岛市李沧区");
		list.add(entity1);
		list.add(entity2);
		redisListEntityDao.add("test_list_entity_add", list);
	}
	
	@Test
	public void testMapEntityAdd() {
		Map<String, DemoEntity> map = new HashMap<>();
		DemoEntity entity = new DemoEntity();
		entity.setName("张三");
		entity.setAddress("青岛市市南区");
		map.put("DemoEntity1", entity);
		redisMapEntityDao.add("test_map_entity_add", map);
	}
	
	@Test
	public void testStringFind() {
		String value = redisStringDao.find("test_string_add");
		logger.info("test_string_add对应的值为[{}]",value);
	}
	
	@Test
	public void testEntityFind() {
		DemoEntity entity = redisEntityDao.find("test_entity_add");
		logger.info("test_entity_add对应的值为{}",entity.toString());
	}
	
	@Test
	public void testListEntityFind() {
		List<DemoEntity> list = redisListEntityDao.find("test_list_entity_add");
		list.forEach(de -> logger.info(de.toString()));
	}
	
	@Test
	public void testMapEntityFind() {
		Map<String, DemoEntity> map = redisMapEntityDao.find("test_map_entity_add");
		map.entrySet().forEach(entry -> logger.info("{}-->{}",entry.getKey(),entry.getValue().toString()));
	}
	
	@Test
	public void testHasKey() {
		boolean hasKey = redisStringDao.hasKey("test_map_entity_add");
		logger.info("hasKey:{}",hasKey);
	}
}
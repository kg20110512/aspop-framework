/**
 * 
 */
package com.vyd.base.jap.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vyd.base.jap.entity.DemoPerson;
import com.vyd.base.jpa.dao.support.QueryCriteriaVo;
import com.vyd.base.jpa.service.IBaseService;

/**
 * <p>Description: JPA通用DAO测试类</p>
 * @author Dirk
 * @date 2017年5月19日 下午2:13:25 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-spring-jpa.xml")
public class BaseServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceTest.class);
	
	@Resource
	private IBaseService baseService;
	
	/**
	 * <p>Description: save方法测试</p>
	 */
	@Test
	public void testSave() {
		DemoPerson person = new DemoPerson();
		person.setName("小J");
		person.setAddress("山东省青岛市崂山区");
		baseService.save(person);
	}
	
	/**
	 * <p>Description: 根据id查询实体</p>
	 */
	@Test
	public void testFindById() {
		String primaryKey = "4028b8815c1fce45015c1fce530a0000";
		DemoPerson person = baseService.findById(DemoPerson.class, primaryKey);
		logger.info(person.toString());
	}
	
	/**
	 * <p>Description: 测试查询实体全部数据</p>
	 */
	@Test
	public void testFindAll() {
		List<DemoPerson> persons = baseService.findAll(DemoPerson.class);
		logger.info("testFindAll：查询全部数据得到数据条数为[{}]",persons.size());
		persons.forEach(p -> logger.info(p.toString()));
	}
	
	/**
	 * <p>Description: 测试分页查询</p>
	 */
	@Test
	public void testFindByPage() {
		int pageSize = 5;
		
		QueryCriteriaVo<DemoPerson, DemoPerson> vo = baseService.getCriteriaQuery(DemoPerson.class, DemoPerson.class);
		vo.getQuery().select(vo.getEntity());
		vo.getQuery().where(vo.getBuilder().like(vo.getEntity().<String>get("name"), "小%"));
		vo.getQuery().orderBy(vo.getBuilder().asc(vo.getEntity().<String>get("name")));
		List<DemoPerson> list = baseService.findPageList(vo.getQuery(), 1, pageSize);
		
		QueryCriteriaVo<Long, DemoPerson> vo1 = baseService.getCriteriaQuery(Long.class, DemoPerson.class);
		vo1.getQuery().select(vo1.getBuilder().count(vo1.getEntity()));
		vo1.getQuery().where(vo1.getBuilder().like(vo1.getEntity().<String>get("name"), "小%"));
		Long totalcount = baseService.findCount(vo1.getQuery());
		
		int pageCount = baseService.getPageCounts(totalcount, pageSize);
		
		logger.info("testFindByPage:查询全部数据条数为[{}],总页数为[{}]", totalcount, pageCount);
		list.forEach(p -> logger.info(p.toString()));
	}
}
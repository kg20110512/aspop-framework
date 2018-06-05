/**
 * 
 */
package com.vyd.base.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 多线程处理工作列表</p>
 * @author Dirk
 * @date 2017年5月18日 上午6:21:28 
 */
public class TaskUtil {
	private static final Logger log = LoggerFactory.getLogger(TaskUtil.class);
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	
	public static <T,V> void exec(final List<T> workList, int threadCount, final TaskProcess<T> process) {
		if (CollectionUtils.isEmpty(workList)) {
			return;
		}
		
		ExecutorService pool = Executors.newFixedThreadPool(threadCount);
		CompletionService<Integer> completionService = new ExecutorCompletionService<>(pool);
		
		workList.forEach(work -> {
			
			completionService.submit(new Callable<Integer>() {
				
				@Override
				public Integer call() throws Exception {
					process.exec(workList.indexOf(work), work);
					return 1;
				}
			});
		});
		
		// 执行任务
		for (int i=0; i<workList.size(); i++) {
			try {
				completionService.take();
			} catch (InterruptedException e) {
				log.error("任务执行期间发生异常",e);
			}
		}
		
		if (!pool.isShutdown()) {
			pool.shutdown();
		}
	}
	
	/**
	 * <p>Description: 获取批次数</p>
	 * @param size 执行总数
	 * @param per 每次执行个数
	 * @return 执行次数
	 */
	private static int getBatchCount(int size, int per) {
		if (size <= per) {
			return 1;
		}
		
		int temp = size/per;
		return size % per == 0? temp:(temp+1);
	}
	
	public static <T,V> List<V> execList(final List<T> workList, int maxThreadCount, int perBatchCount, final TaskListProcess<T, V> process) {
		List<V> result = new ArrayList<V>();
		if (CollectionUtils.isEmpty(workList)) {
			return result;
		}
		
		// 总任务数
		int size = workList.size();
		// 执行次数
		int count = getBatchCount(size, perBatchCount);
		// 线程池数量
		int threadCount = maxThreadCount > count? count : maxThreadCount;
		
		ExecutorService pool = Executors.newFixedThreadPool(threadCount);
		CompletionService<List<V>> completionService = new ExecutorCompletionService<List<V>>(pool);
		
		// 多线程批量注册
		List<T> batchList = new ArrayList<T>();
		int batchIndex = 0;
		for (int i=0; i<size; i++) {
			if (batchList.size() < perBatchCount) {
				batchList.add(workList.get(i));
			}
			
			// 批量处理
			if (i == (size-1) || batchList.size() == perBatchCount) {
				final List<T> tempList = batchList;
				final int tempBatchIndex = batchIndex++;
				batchList = new ArrayList<T>();
				completionService.submit(new Callable<List<V>>() {
					@Override
					public List<V> call() throws Exception {
						return process.exec(tempBatchIndex, tempList);
					}
				});
			}
		}
		
		for (int i=0; i<count; i++) {
			try {
				result.addAll(completionService.take().get());
			} catch (Exception e) {
				log.error("任务执行期间发生异常",e);
			}
		}
		return result;
	}
	
	/**
	 * <p>Description: 异步执行任务</p>
	 * @param runnable
	 */
	public static void execAsyn(Runnable runnable) {
		executorService.execute(runnable);
	}
}
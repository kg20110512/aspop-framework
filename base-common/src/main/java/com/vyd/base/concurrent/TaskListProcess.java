/**
 * 
 */
package com.vyd.base.concurrent;

import java.util.List;

/**
 * <p>Description: 任务处理</p>
 * @author Dirk
 * @date 2017年5月18日 上午6:20:03 
 */
public interface TaskListProcess<T,V> {
	/**
	 * <p>Description: 任务处理列表，尽量不要有线程间共享变量，防止等待</p>
	 * @param batchIndex
	 * @param taskList
	 * @return
	 */
	public List<V> exec(int batchIndex, List<T> taskList);
}
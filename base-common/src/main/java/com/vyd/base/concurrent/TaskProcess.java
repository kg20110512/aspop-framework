/**
 * 
 */
package com.vyd.base.concurrent;

/**
 * <p>Description: 任务处理</p>
 * @author Dirk
 * @date 2017年5月18日 上午6:18:37 
 */
public interface TaskProcess<T> {
	
	/**
	 * <p>Description: 处理任务，尽量不要有线程间共享变量，防止等待</p>
	 * @param index
	 * @param task
	 */
	public void exec(int index, T task);
}
package com.vyd.base.exception;

/**
 * <p>Description: 资源未找到异常类</p>
 * @author Dirk
 * @date 2017年5月16日 下午2:46:08 
 */
public class UnknownResourceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnknownResourceException(String msg) {
		super(msg);
	}
}
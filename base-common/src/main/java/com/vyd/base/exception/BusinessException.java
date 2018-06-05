/**
 * 
 */
package com.vyd.base.exception;

import org.springframework.http.HttpStatus;

/**
 * <p>Description: </p>
 * @author Dirk
 * @date 2017年5月17日 上午10:06:39 
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Http状态码
	 */
	protected int httpStatusCode;
	
	/**
	 * 错误编码
	 */
	protected int code;
	
	/**
	 * 错误信息
	 */
	protected String message;
	
	/**
	 * 异常对象
	 */
	protected Throwable cause;
	
	/**
	 * 构造方法
	 */
	public BusinessException(int code, String message) {
		this(HttpStatus.INTERNAL_SERVER_ERROR.value(), code, message);
	}
	
	public BusinessException(int httpStatusCode, int code, String message) {
		this.httpStatusCode = httpStatusCode;
		this.code = code;
		this.message = message;
	}
	
	public BusinessException(int code, String message, Throwable cause) {
		this(HttpStatus.INTERNAL_SERVER_ERROR.value(), code, message, cause);
	}
	
	public BusinessException(int httpStatusCode, int code, String message, Throwable cause) {
		this.httpStatusCode = httpStatusCode;
		this.code = code;
		this.message = message;
		this.cause = cause;
	}

	/**
	 * @return the status
	 */
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}
}
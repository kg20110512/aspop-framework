/**
 * 
 */
package com.vyd.base.exception.errorcode;

/**
 * <p>Description: 系统异常码常量类</p>
 * 错误码命名规则:4[系统编号(0-9)][模块编号(0-9)][错误码(0-9)][错误码(0-9)]
 * @author Dirk
 * @date 2017年5月17日 上午6:17:04 
 */
public interface SysErrorCode {
	
	/**
	 * 处理成功
	 */
	public static final int SUCCESS = 0;
	
	/**
	 * 系统繁忙
	 */
	public static final int BUSY = -1;
	
	/**
	 * Http message not readable
	 */
	public static final int SYS_40001 = 40001;
	
	/**
	 * Missing Servlet Request Parameter
	 */
	public static final int SYS_40002 = 40002;
	
	/**
	 * Type Missmatch
	 */
	public static final int SYS_40003 = 40003;
	
	/**
	 * Validation Error
	 */
	public static final int SYS_40004 = 40004;
	
	/**
	 * 请求参数不合法
	 */
	public static final int SYS_40101 = 40101;
	
	/**
	 * Token错误（转换错误）
	 */
	public static final int SYS_40301 = 40301;
	
	/**
	 * token信息与用户信息不一致
	 */
	public static final int SYS_40302 = 40302;
	
	/**
	 * token已过期
	 */
	public static final int SYS_40303 = 40303;
	
	/**
	 * No Such Request HandlingMethod
	 */
	public static final int SYS_40401 = 40401;
	
	/**
	 * Object Not Found Exception
	 */
	public static final int SYS_40402 = 40402;
	
	/**
	 * Request Method Not Supported
	 */
	public static final int SYS_40501 = 40501;
	
	/**
	 * Http MediaType Not Acceptable
	 */
	public static final int SYS_40601 = 40601;
	
	/**
	 * Data Integrity violation exception
	 */
	public static final int SYS_40901 = 40901;
	
	/**
	 * Http Media Type Not Support
	 */
	public static final int SYS_41501 = 41501;
}
/**
 * 
 */
package com.vyd.base.exception.errorcode;

import java.util.Properties;

/**
 * <p>Description: 错误码工具类</p>
 * @author Dirk
 * @date 2017年5月17日 下午4:44:26 
 */
public class ErrorCodeTool {
	private static Properties props = new Properties();
	
	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}
}
/**
 * 
 */
package com.vyd.base.exception.errorcode;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * <p>Description: 错误码配置</p>
 * @author Dirk
 * @date 2017年5月17日 上午11:25:00 
 */
public class ErrorCodePropertyConfigurer implements InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(ErrorCodePropertyConfigurer.class);
	
	/**
	 * 错误代码资源路径
	 */
	private Resource[] locations;

	/**
	 * @return the locations
	 */
	public Resource[] getLocations() {
		return locations;
	}

	/**
	 * @param locations the locations to set
	 */
	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}



	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		build();
	}
	
	protected void build() {
		for (Resource location : locations) {
			if (location == null) {
				continue;
			}
			
			try {
				Properties prop = PropertiesLoaderUtils.loadProperties(new EncodedResource(location, CharEncoding.UTF_8));
				prop.entrySet().forEach(e -> {ErrorCodeTool.setProperty(e.getKey().toString(), e.getValue().toString());});
			} catch (IOException e) {
				log.error("加载错误代码属性文件[{}]发生异常",location,e);
			}
		}
	}
}

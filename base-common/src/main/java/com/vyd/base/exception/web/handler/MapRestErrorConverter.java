/**
 * 
 */
package com.vyd.base.exception.web.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * Simple {@code RestErrorConverter} implementation that creates a new Map instance based on the specified RestError
 * instance.  Some {@link org.springframework.http.converter.HttpMessageConverter HttpMessageConverter}s (like a JSON
 * converter) can easily automatically convert Maps to response bodies.  The map is populated with the following
 * default name/value pairs:
 *
 * <table>
 *     <tr>
 *         <th>Key (a String)</th>
 *         <th>Value (an Object)</th>
 *         <th>Notes</th>
 *     </tr>
 *     <tr>
 *         <td>status</td>
 *         <td>restError.{@link RestError#getStatus() getStatus()}.{@link org.springframework.http.HttpStatus#value() value()}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>code</td>
 *         <td>restError.{@link RestError#getCode() getCode()}</td>
 *         <td>Only set if {@code code > 0}</td>
 *     </tr>
 *     <tr>
 *         <td>message</td>
 *         <td>restError.{@link RestError#getMessage() getMessage()}</td>
 *         <td>Only set if {@code message != null}</td>
 *     </tr>
 *     <tr>
 *         <td>developerMessage</td>
 *         <td>restError.{@link RestError#getDeveloperMessage() getDeveloperMessage()}</td>
 *         <td>Only set if {@code developerMessage != null}</td>
 *     </tr>
 *     <tr>
 *         <td>moreInfo</td>
 *         <td>restError.{@link RestError#getMoreInfoUrl() getMoreInfoUrl()}</td>
 *         <td>Only set if {@code moreInfoUrl != null}</td>
 *     </tr>
 * </table>
 * <p/>
 * The map key names are customizable via setter methods (setStatusKey, setMessageKey, etc).
 *
 * @author Dirk
 * @date 2017年5月16日 下午1:47:52 
 */
public class MapRestErrorConverter implements RestErrorConverter<Map<String, Object>> {
	
	private static final String DEFAULT_STATUS_KEY = "status";
	private static final String DEFAULT_CODE_KEY = "code";
	private static final String DEFAULT_MESSAGE_KEY = "message";
	private static final String DEFAULT_DEVELOPER_MESSAGE_KEY = "developerMessage";
	private static final String DEFAULT_MORE_INFO_URL_KEY = "moreInfoUrl";
	
	private String statusKey = DEFAULT_STATUS_KEY;
	private String codeKey = DEFAULT_CODE_KEY;
	private String mesasgeKey = DEFAULT_MESSAGE_KEY;
	private String developerMessageKey = DEFAULT_DEVELOPER_MESSAGE_KEY;
	private String moreInfoUrlKey= DEFAULT_MORE_INFO_URL_KEY;
	
	/* (non-Javadoc)
	 * @see com.vyd.base.exception.web.handler.RestErrorConverter#convert(com.vyd.base.exception.web.handler.RestError)
	 */
	@Override
	public Map<String, Object> convert(RestError re) {
		Map<String, Object> map = createMap();
		
		HttpStatus status = re.getStatus();
		map.put(getStatusKey(), status.value());
		
		int code = re.getCode();
		if (code >= -1) {
			map.put(getCodeKey(), code);
		}
		
		String message = re.getMessage();
		if (StringUtils.isNotBlank(message)) {
			map.put(getMesasgeKey(), message);
		}
		
		String devMsg = re.getDeveloperMessage();
		if (StringUtils.isNotBlank(devMsg)) {
			map.put(getDeveloperMessageKey(), devMsg);
		}
		
		String moreInfoUrl = re.getMoreInfoUrl();
		if (StringUtils.isNotBlank(moreInfoUrl)) {
			map.put(getMoreInfoUrlKey(), moreInfoUrl);
		}
		
		return map;
	}
	
	protected Map<String, Object> createMap() {
		return new LinkedHashMap<String, Object>();
	}

	/**
	 * @return the statusKey
	 */
	public String getStatusKey() {
		return statusKey;
	}

	/**
	 * @param statusKey the statusKey to set
	 */
	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}

	/**
	 * @return the codeKey
	 */
	public String getCodeKey() {
		return codeKey;
	}

	/**
	 * @param codeKey the codeKey to set
	 */
	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	/**
	 * @return the mesasgeKey
	 */
	public String getMesasgeKey() {
		return mesasgeKey;
	}

	/**
	 * @param mesasgeKey the mesasgeKey to set
	 */
	public void setMesasgeKey(String mesasgeKey) {
		this.mesasgeKey = mesasgeKey;
	}

	/**
	 * @return the developerMessageKey
	 */
	public String getDeveloperMessageKey() {
		return developerMessageKey;
	}

	/**
	 * @param developerMessageKey the developerMessageKey to set
	 */
	public void setDeveloperMessageKey(String developerMessageKey) {
		this.developerMessageKey = developerMessageKey;
	}

	/**
	 * @return the moreInfoUrlKey
	 */
	public String getMoreInfoUrlKey() {
		return moreInfoUrlKey;
	}

	/**
	 * @param moreInfoUrlKey the moreInfoUrlKey to set
	 */
	public void setMoreInfoUrlKey(String moreInfoUrlKey) {
		this.moreInfoUrlKey = moreInfoUrlKey;
	}
}
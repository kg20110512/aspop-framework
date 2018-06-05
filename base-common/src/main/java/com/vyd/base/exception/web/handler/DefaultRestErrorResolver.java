/*
 * Copyright 2012 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vyd.base.exception.web.handler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.LocaleResolver;

import com.vyd.base.exception.BusinessException;
import com.vyd.base.exception.errorcode.SysErrorCode;

/**
 * Default {@code RestErrorResolver} implementation that converts discovered Exceptions to
 * {@link RestError} instances.
 *
  * @author Dirk
 * @date 2017年5月15日 下午6:14:35
 */
public class DefaultRestErrorResolver implements RestErrorResolver, MessageSourceAware, InitializingBean {

    public static final String DEFAULT_EXCEPTION_MESSAGE_VALUE = "_exmsg";
    public static final String DEFAULT_MESSAGE_VALUE = "_msg";
    
    private static final String KEY_STATUS = "status";
    private static final String KEY_CODE = "code";
    private static final String KEY_MSG = "msg";
    private static final String KEY_DEVMSG = "devMsg";
    private static final String KEY_INFOURL = "infoUrl";

    private static final Logger log = LoggerFactory.getLogger(DefaultRestErrorResolver.class);

    private Map<String, RestError> exceptionMappings = Collections.emptyMap();

    private Map<String, String> exceptionMappingDefinitions = Collections.emptyMap();

    private MessageSource messageSource;
    private LocaleResolver localeResolver;

    private String defaultMoreInfoUrl;
    private boolean defaultEmptyCodeToStatus;
    private String defaultDeveloperMessage;

    public DefaultRestErrorResolver() {
        this.defaultEmptyCodeToStatus = true;
        this.defaultDeveloperMessage = DEFAULT_EXCEPTION_MESSAGE_VALUE;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setLocaleResolver(LocaleResolver resolver) {
        this.localeResolver = resolver;
    }

    public void setExceptionMappingDefinitions(Map<String, String> exceptionMappingDefinitions) {
        this.exceptionMappingDefinitions = exceptionMappingDefinitions;
    }

    public void setDefaultMoreInfoUrl(String defaultMoreInfoUrl) {
        this.defaultMoreInfoUrl = defaultMoreInfoUrl;
    }

    public void setDefaultEmptyCodeToStatus(boolean defaultEmptyCodeToStatus) {
        this.defaultEmptyCodeToStatus = defaultEmptyCodeToStatus;
    }

    public void setDefaultDeveloperMessage(String defaultDeveloperMessage) {
        this.defaultDeveloperMessage = defaultDeveloperMessage;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        //populate with some defaults:
        Map<String, String> definitions = createDefaultExceptionMappingDefinitions();

        //add in user-specified mappings (will override defaults as necessary):
        if (MapUtils.isNotEmpty(exceptionMappingDefinitions)) {
            definitions.putAll(this.exceptionMappingDefinitions);
        }

        this.exceptionMappings = toRestErrors(definitions);
    }

	/**
	 * <p>Description: populate with some defaults</p>
	 * 组装后信息key值为className,value值为400, __exmsg
	 * @return
	 */
    protected final Map<String,String> createDefaultExceptionMappingDefinitions() {

        Map<String,String> m = new LinkedHashMap<String, String>();

        // 400
        applyDef(m, HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST, SysErrorCode.SYS_40001);
        applyDef(m, MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST, SysErrorCode.SYS_40002);
        applyDef(m, TypeMismatchException.class, HttpStatus.BAD_REQUEST, SysErrorCode.SYS_40003);
        applyDef(m, "javax.validation.ValidationException", HttpStatus.BAD_REQUEST, SysErrorCode.SYS_40004);

        // 404
        applyDef(m, "org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException", HttpStatus.NOT_FOUND, SysErrorCode.SYS_40401);
        applyDef(m, "org.hibernate.ObjectNotFoundException", HttpStatus.NOT_FOUND, SysErrorCode.SYS_40401);

        // 405
        applyDef(m, HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED, SysErrorCode.SYS_40501);

        // 406
        applyDef(m, HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE, SysErrorCode.SYS_40601);

        // 409
        //can't use the class directly here as it may not be an available dependency:
        applyDef(m, "org.springframework.dao.DataIntegrityViolationException", HttpStatus.CONFLICT, SysErrorCode.SYS_40901);

        // 415
        applyDef(m, HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE, SysErrorCode.SYS_41501);

        return m;
    }

    private void applyDef(Map<String,String> m, Class<?> clazz, HttpStatus status, int errorCode) {
        applyDef(m, clazz.getName(), status, errorCode);
    }

    private void applyDef(Map<String,String> m, String key, HttpStatus status, int errorCode) {
        m.put(key, definitionFor(status, errorCode));
    }

    private String definitionFor(HttpStatus status, int errorCode) {
        return status.value() + ", " + errorCode + ", " + DEFAULT_EXCEPTION_MESSAGE_VALUE;
    }

    @Override
    public RestError resolveError(ServletWebRequest request, Object handler, Exception ex) {

    	// 获取Exception的错误类型
        RestError template = getRestErrorTemplate(ex);
        if (template == null) {
            return null;
        }

        // 创建RestError对象
        RestError.Builder builder = new RestError.Builder();
        builder.setStatus(getStatusValue(template, request, ex));
        builder.setCode(getCode(template, request, ex));
        builder.setMoreInfoUrl(getMoreInfoUrl(template, request, ex));
        builder.setThrowable(ex);

        String msg = getMessage(template, request, ex);
        if (msg != null) {
            builder.setMessage(msg);
        }
        msg = getDeveloperMessage(template, request, ex);
        if (msg != null) {
            builder.setDeveloperMessage(msg);
        }

        return builder.build();
    }

    protected int getStatusValue(RestError template, ServletWebRequest request, Exception ex) {
    	if (ex instanceof BusinessException) {
    		return ((BusinessException) ex).getHttpStatusCode();
    	}
        return template.getStatus().value();
    }

    protected int getCode(RestError template, ServletWebRequest request, Exception ex) {
    	if (ex instanceof BusinessException) {
    		return ((BusinessException) ex).getCode();
    	}
        int code = template.getCode();
        if (code <= -2 && defaultEmptyCodeToStatus) {
            code = getStatusValue(template, request, ex);
        }
        return code;
    }

    protected String getMoreInfoUrl(RestError template, ServletWebRequest request, Exception ex) {
        String moreInfoUrl = template.getMoreInfoUrl();
        if (StringUtils.isEmpty(moreInfoUrl)) {
            moreInfoUrl = this.defaultMoreInfoUrl;
        }
        return moreInfoUrl;
    }

    protected String getMessage(RestError template, ServletWebRequest request, Exception ex) {
        return getMessage(template.getMessage(), request, ex);
    }

    protected String getDeveloperMessage(RestError template, ServletWebRequest request, Exception ex) {
        String devMsg = template.getDeveloperMessage();
        if (StringUtils.isEmpty(devMsg) && !StringUtils.isEmpty(defaultDeveloperMessage)) {
            devMsg = defaultDeveloperMessage;
        }
        if (DEFAULT_MESSAGE_VALUE.equals(devMsg)) {
            devMsg = template.getMessage();
        }
        
        if (ex instanceof BusinessException) {
        	if (ex.getCause() != null) {
        		devMsg = ex.getCause().getMessage();
        	} else {
        		devMsg = ex.getMessage();
        	}
        	
        	return devMsg;
        }
        return getMessage(devMsg, request, ex);
    }

    /**
     * Returns the response status message to return to the client, or {@code null} if no
     * status message should be returned.
     *
     * @return the response status message to return to the client, or {@code null} if no
     *         status message should be returned.
     */
    protected String getMessage(String msg, ServletWebRequest webRequest, Exception ex) {

        if (msg != null) {
            if (msg.equalsIgnoreCase("null") || msg.equalsIgnoreCase("off")) {
                return null;
            }
            if (msg.equalsIgnoreCase(DEFAULT_EXCEPTION_MESSAGE_VALUE)) {
                msg = ex.getMessage();
            }
            if (messageSource != null) {
                Locale locale = null;
                if (localeResolver != null) {
                    locale = localeResolver.resolveLocale(webRequest.getRequest());
                }
                msg = messageSource.getMessage(msg, null, msg, locale);
            }
        }
        
        if (ex instanceof BusinessException) {
        	msg = ex.getMessage();
        }
        return msg;
    }

    /**
     * Returns the config-time 'template' RestError instance configured for the specified Exception, or
     * {@code null} if a match was not found.
     * <p/>
     * The config-time template is used as the basis for the RestError constructed at runtime.
     * @param ex
     * @return the template to use for the RestError instance to be constructed.
     */
    private RestError getRestErrorTemplate(Exception ex) {
        Map<String, RestError> mappings = this.exceptionMappings;
        if (CollectionUtils.isEmpty(mappings)) {
            return null;
        }
        RestError template = null;
        String dominantMapping = null;
        int deepest = Integer.MAX_VALUE;
        for (Map.Entry<String, RestError> entry : mappings.entrySet()) {
            String key = entry.getKey();
            int depth = getDepth(key, ex);
            if (depth >= 0 && depth < deepest) {
                deepest = depth;
                dominantMapping = key;
                template = entry.getValue();
            }
        }
        if (template != null && log.isDebugEnabled()) {
			log.debug("Resolving to RestError template '{}' for exception of type [{}], based on exception mapping [{}]",template, ex.getClass().getName(), dominantMapping);
		}
        return template;
    }

    /**
     * Return the depth to the superclass matching.
     * <p>0 means ex matches exactly. Returns -1 if there's no match.
     * Otherwise, returns depth. Lowest depth wins.
     */
    protected int getDepth(String exceptionMapping, Exception ex) {
        return getDepth(exceptionMapping, ex.getClass(), 0);
    }

    private int getDepth(String exceptionMapping, Class<?> exceptionClass, int depth) {
        if (exceptionClass.getName().contains(exceptionMapping)) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
    }


    protected Map<String, RestError> toRestErrors(Map<String, String> smap) {
        if (CollectionUtils.isEmpty(smap)) {
            return Collections.emptyMap();
        }

        Map<String, RestError> map = new LinkedHashMap<String, RestError>(smap.size());

        for (Map.Entry<String, String> entry : smap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            RestError template = toRestError(value);
            map.put(key, template);
        }

        return map;
    }

    protected RestError toRestError(String exceptionConfig) {
        String[] values = StringUtils.commaDelimitedListToStringArray(exceptionConfig);
        if (values == null || values.length == 0) {
            throw new IllegalStateException("Invalid config mapping.  Exception names must map to a string configuration.");
        }

        RestError.Builder builder = new RestError.Builder();

        boolean statusSet = false;
        boolean codeSet = false;
        boolean msgSet = false;
        boolean devMsgSet = false;
        boolean moreInfoSet = false;

        for (String value : values) {

            String trimmedVal = StringUtils.trimWhitespace(value);

            //check to see if the value is an explicitly named key/value pair:
            String[] pair = StringUtils.split(trimmedVal, "=");
            if (pair != null) {
                //explicit attribute set:
                String pairKey = StringUtils.trimWhitespace(pair[0]);
                if (!StringUtils.hasText(pairKey)) {
                    pairKey = null;
                }
                String pairValue = StringUtils.trimWhitespace(pair[1]);
                if (!StringUtils.hasText(pairValue)) {
                    pairValue = null;
                }
                if (KEY_STATUS.equalsIgnoreCase(pairKey)) {
                    int statusCode = getRequiredInt(pairKey, pairValue);
                    builder.setStatus(statusCode);
                    statusSet = true;
                } else if (KEY_CODE.equalsIgnoreCase(pairKey)) {
                    int code = getRequiredInt(pairKey, pairValue);
                    builder.setCode(code);
                    codeSet = true;
                } else if (KEY_MSG.equalsIgnoreCase(pairKey)) {
                    builder.setMessage(pairValue);
                    msgSet = true;
                } else if (KEY_DEVMSG.equalsIgnoreCase(pairKey)) {
                    builder.setDeveloperMessage(pairValue);
                    devMsgSet = true;
                } else if (KEY_INFOURL.equalsIgnoreCase(pairKey)) {
                    builder.setMoreInfoUrl(pairValue);
                    moreInfoSet = true;
                }
            } else {
                //not a key/value pair - use heuristics to determine what value is being set:
                int val;
                if (!statusSet) {
                    val = getInt(KEY_STATUS, trimmedVal);
                    if (val > 0) {
                        builder.setStatus(val);
                        statusSet = true;
                        continue;
                    }
                }
                if (!codeSet) {
                    val = getInt(KEY_CODE, trimmedVal);
                    if (val > 0) {
                        builder.setCode(val);
                        codeSet = true;
                        continue;
                    }
                }
                if (!moreInfoSet && trimmedVal.toLowerCase().startsWith("http")) {
                    builder.setMoreInfoUrl(trimmedVal);
                    moreInfoSet = true;
                    continue;
                }
                if (!msgSet) {
                    builder.setMessage(trimmedVal);
                    msgSet = true;
                    continue;
                }
                if (!devMsgSet) {
                    builder.setDeveloperMessage(trimmedVal);
                    devMsgSet = true;
                    continue;
                }
                if (!moreInfoSet) {
                    builder.setMoreInfoUrl(trimmedVal);
                    moreInfoSet = true;
                    //noinspection UnnecessaryContinue
                    continue;
                }
            }
        }

        return builder.build();
    }

    private static int getRequiredInt(String key, String value) {
        try {
            int anInt = Integer.valueOf(value);
            return Math.max(-1, anInt);
        } catch (NumberFormatException e) {
        	String msg = org.apache.commons.lang3.StringUtils.join("Configuration element '",key,"' requires an integer value. The value specified: ",value);
            throw new IllegalArgumentException(msg, e);
        }
    }

    private static int getInt(String key, String value) {
        try {
            return getRequiredInt(key, value);
        } catch (IllegalArgumentException iae) {
            return 0;
        }
    }
}

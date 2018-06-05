/**
 * 
 */
package com.vyd.base.exception.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

/**
 * <p>Description: REST异常信息类</p>
 * @author Dirk
 * @date 2017年5月15日 下午4:41:45 
 */
public class RestError {
	/**
	 * Http状态码
	 */
	private final HttpStatus status;
	
	/**
	 * 错误代码
	 */
	private final int code;
	
	/**
	 * 错误信息
	 */
	private final String message;
	
	/**
	 * 开发人员信息
	 */
	private final String developerMessage;
	
	/**
	 * 错误信息详细说明
	 */
	private final String moreInfoUrl;
	
	/**
	 * 异常信息
	 */
	private final Throwable throwable;
	
	/**
	 * 
	 */
	public RestError(HttpStatus status, int code, String message, String developerMessage, String moreInfoUrl, Throwable throwable) {
		if (status == null) {
			throw new NullPointerException("HttpStatus argument cannot be null.");
		}
		this.status = status;
		this.code = code;
		this.message = message;
		this.developerMessage = developerMessage;
		this.moreInfoUrl = moreInfoUrl;
		this.throwable = throwable;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
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
	 * @return the developerMessage
	 */
	public String getDeveloperMessage() {
		return developerMessage;
	}

	/**
	 * @return the moreInfoUrl
	 */
	public String getMoreInfoUrl() {
		return moreInfoUrl;
	}

	/**
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof RestError) {
			RestError re = (RestError) obj;
            return ObjectUtils.nullSafeEquals(getStatus(), re.getStatus()) &&
                    getCode() == re.getCode() &&
                    ObjectUtils.nullSafeEquals(getMessage(), re.getMessage()) &&
                    ObjectUtils.nullSafeEquals(getDeveloperMessage(), re.getDeveloperMessage()) &&
                    ObjectUtils.nullSafeEquals(getMoreInfoUrl(), re.getMoreInfoUrl()) &&
                    ObjectUtils.nullSafeEquals(getThrowable(), re.getThrowable());
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(new Object[]{
                getStatus(), getCode(), getMessage(), getDeveloperMessage(), getMoreInfoUrl(), getThrowable()
        });
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append(getStatus().value())
                .append(" (").append(getStatus().getReasonPhrase()).append(" )")
                .toString();
	}
	
	public static class Builder {

        private HttpStatus status;
        private int code;
        private String message;
        private String developerMessage;
        private String moreInfoUrl;
        private Throwable throwable;

        public Builder() {
        }

        public Builder setStatus(int statusCode) {
            this.status = HttpStatus.valueOf(statusCode);
            return this;
        }

        public Builder setStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder setMoreInfoUrl(String moreInfoUrl) {
            this.moreInfoUrl = moreInfoUrl;
            return this;
        }

        public Builder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public RestError build() {
            if (this.status == null) {
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return new RestError(this.status, this.code, this.message, this.developerMessage, this.moreInfoUrl, this.throwable);
        }
    }
}
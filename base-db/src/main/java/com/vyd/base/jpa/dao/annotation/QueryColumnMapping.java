package com.vyd.base.jpa.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: </p>
 * @author Dirk
 * @date 2017年5月21日 上午7:19:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface QueryColumnMapping {
	String mappedEntityProperty() default StringUtils.EMPTY;

	boolean ignore() default false;
}
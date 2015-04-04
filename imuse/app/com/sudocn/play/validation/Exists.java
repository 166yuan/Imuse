package com.sudocn.play.validation;

import play.db.jpa.JPABase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author fyi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@CheckWith(value = ExistsCheck.class)
public @interface Exists {

	Class<? extends JPABase> value();
}

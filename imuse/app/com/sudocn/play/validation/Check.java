package com.sudocn.play.validation;

import java.lang.annotation.Annotation;

/**
 *
 * @author fyi
 * @param <T>
 */
public interface Check<T extends Annotation> {

	public void config(T annotation);

	public boolean isok(Object obj);

	public int errorCode();

	public String message();
}

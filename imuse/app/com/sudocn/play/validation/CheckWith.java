package com.sudocn.play.validation;

import java.lang.annotation.*;

/**
 *
 * @author fyi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@interface CheckWith {

	Class<? extends Check<? extends Annotation>> value();
}

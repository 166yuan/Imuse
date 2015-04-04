package com.sudocn.play.validation;

import play.mvc.ActionInvoker;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 * @author fyi
 */
public class Validator extends Controller {

	@Before
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void method() {
		Method method = request.invokedMethod;
		if (!shouldValidate(method)) {
			return;
		}
		Object[] args = getActionMethodArguments(method);

		Annotation[][] annotationsArray = method.getParameterAnnotations();
		for (int i = 0; i < annotationsArray.length; i++) {
			Annotation[] annotations = annotationsArray[i];
			for (Annotation annotation : annotations) {
				CheckWith checkWith
						= annotation.annotationType()
						.getAnnotation(CheckWith.class);
				if (checkWith == null) {
					continue;
				}
				Class checkerClass = checkWith.value();
				Check checker;
				try {
					checker = (Check) checkerClass.newInstance();
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				}
				checker.config(annotation);
				if (checker.isok(args[i])) {
					continue;
				}
				int errorCode = checker.errorCode();
				// 此处不能用switch，会导致play出错
				if (errorCode == Http.StatusCode.NOT_FOUND) {
					notFound(checker.message());
				} else if (errorCode == Http.StatusCode.BAD_REQUEST) {
					badRequest();
				}
			}
		}
	}

	private static Object[] getActionMethodArguments(Method actionMethod) {
		Object[] args;
		try {
			args = ActionInvoker.getActionMethodArgs(actionMethod, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return args;
	}

	private static boolean shouldValidate(Method method) {
		if (method.isAnnotationPresent(Validated.class)) {
			return true;
		}
		Class<?> clazz = method.getDeclaringClass();
		while (!clazz.getCanonicalName().
				equals(Object.class.getCanonicalName())) {
			if (clazz.isAnnotationPresent(Validated.class)) {
				return true;
			}
			clazz = clazz.getSuperclass();
		}
		return false;
	}
}

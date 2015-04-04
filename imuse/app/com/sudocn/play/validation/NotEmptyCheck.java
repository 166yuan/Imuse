package com.sudocn.play.validation;

import play.mvc.Http;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 *
 * @author fyi
 */
class NotEmptyCheck implements Check<NotEmpty> {

	public boolean isok(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			return !((String) obj).trim().isEmpty();
		}
		if (obj instanceof Collection) {
			Collection<?> coll = (Collection<?>) obj;
			if (coll.isEmpty()) {
				return false;
			}
			for (Object elem : coll) {
				if (!isok(elem)) {
					return false;
				}
			}
			return true;
		}
		if (obj.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(obj); i++) {
				Object elem = Array.get(obj, i);
				if (!isok(elem)) {
					return false;
				}
			}
			return true;
		}
		return true;
	}

	public void config(NotEmpty annotation) {
	}

	public int errorCode() {
		return Http.StatusCode.BAD_REQUEST;
	}

	public String message() {
		return "empty parameters";
	}
}

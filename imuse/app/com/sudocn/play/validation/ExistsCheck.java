package com.sudocn.play.validation;

import play.db.jpa.JPABase;
import play.mvc.Http;

/**
 *
 * @author fyi
 */
class ExistsCheck implements Check<Exists> {

	private Class<? extends JPABase> clazz;
	private Object id;

	public void config(Exists annotation) {
		clazz = annotation.value();
	}

	public boolean isok(Object id) {
		this.id = id;
		if (id == null) {
			return false;
		}
		Object result = JPABase.em().find(clazz, id);
		return result != null;
	}

	public int errorCode() {
		return Http.StatusCode.NOT_FOUND;
	}

	public String message() {
		return String.format("no exists %s for id %s",
				clazz.getCanonicalName(), id);
	}
}

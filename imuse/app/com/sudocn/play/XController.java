package com.sudocn.play;

import java.io.File;

import play.Play;
import play.cache.CacheFor;
import play.libs.Time;
import play.mvc.After;
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.results.RenderBinary;

import com.sudocn.play.mvc.results.RenderBinaryWithRange;
import com.sudocn.play.mvc.results.RenderJson;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;

public class XController extends Controller {

	/**
	 * Render a 200 OK application/json response
	 * 
	 * @param jsonString
	 *            The JSON string
	 */
	protected static void renderXJSON(String jsonString) {
		throw new RenderJson(jsonString);
	}

	/**
	 * Render a 200 OK application/json response
	 * 
	 * @param o
	 *            The Java object to serialize
	 */
	protected static void renderXJSON(Object o) {
		throw new RenderJson(o);
	}

	protected static void renderBinaryWithRange(File file) {
		Request request = Request.current();
		if (request.headers.containsKey("range")) {
			throw new RenderBinaryWithRange(file);
		} else {
			throw new RenderBinary(file);
		}
	}

	@After
	static void addCacheControl() {
		if(request.invokedMethod.isAnnotationPresent(CacheFor.class)){
			CacheFor cacheFor = request.invokedMethod.getAnnotation(CacheFor.class);
			response.setHeader(CACHE_CONTROL, "max-age=" + Time.parseDuration(cacheFor.value()));
		}
		
	}
}
